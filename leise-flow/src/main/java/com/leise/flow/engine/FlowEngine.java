package com.leise.flow.engine;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leise.flow.action.IAction;
import com.leise.flow.action.IJumpAction;
import com.leise.flow.cache.FlowAction;
import com.leise.flow.cache.FlowCacheKey;
import com.leise.flow.cache.FlowLink;
import com.leise.flow.cache.FlowLocalCacheRegister;
import com.leise.flow.cache.FlowMetaData;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionResultEnum;
import com.leise.flow.exception.ActionException;
import com.leise.flow.exception.FlowException;

/**
 * Created by JY-IT-D001 on 2018/6/28.
 */
@Component
public class FlowEngine {

    private static final Logger LOG = LoggerFactory.getLogger(FlowEngine.class);

    public final static ExpressionParser SPEL = new SpelExpressionParser();

    @Value(value = "${spring.application.name}")
    private String moduleId;

    @Autowired
    private FlowLocalCacheRegister flowLocalCacheRegister;

    @Transactional(rollbackFor = { FlowException.class, ActionException.class, RuntimeException.class, Exception.class })
    public Map<String, Object> executeFlow(final String flowId, final String flowVersion, final String requestMsg) {
        LOG.info("[启动流程引擎] -- [模块编号:{}, 流程编号:{}, 流程版本号:{}]", moduleId, flowId, flowVersion);
        FlowCacheKey flowCacheKey = new FlowCacheKey(this.moduleId, flowId, flowVersion);
        FlowMetaData flowMetaData = flowLocalCacheRegister.getFlowMetaData(flowCacheKey);
        if (flowMetaData == null) {
            throw new FlowException(999999, "无法获取流程配置信息");
        }
        FlowContext context = flowMetaData.getContext();
        String flowName = context.getFlowName();
        LOG.info("[启动流程引擎] -- [流程名称:{}]", flowName);
        Map<String, Object> inputParams = flowMetaData.getInputParams();
        JSONObject jsonObject = JSON.parseObject(requestMsg);
        if (MapUtils.isEmpty(inputParams)) {
            LOG.warn("!!![未配置输入参数]");
        } else {
            if (null != jsonObject) {
                Set<String> keySet = inputParams.keySet();
                for (String key : keySet) {
                    Object value = jsonObject.get(key);
                    inputParams.put(key, value);
                }
            }
            LOG.info("[输入参数:{}]", inputParams);
            context.putAll(inputParams);
        }
        Map<Integer, FlowAction> flowActionMap = flowMetaData.getFlowActions();
        Map<Integer, List<FlowLink>> flowLinkMap = flowMetaData.getFlowLinks();
        int flowStartIndex = flowMetaData.getFlowStartIndex();
        int curActionIndex = flowStartIndex;
        while (true) {
            FlowAction flowAction = flowActionMap.get(curActionIndex);
            IAction action = flowAction.getAction();
            ActionResultEnum result = action.execute(context);
            context.put("actionResult", result.toString());
            LOG.info("[执行组件完毕] -- [组件编号:{},组件名称:{}]", flowAction.getFlowActionId(), flowAction.getFlowActionName());
            if (flowAction.isEnd()) {
                break;
            } else {
                List<FlowLink> flowLinkList = flowLinkMap.get(curActionIndex);
                Integer nextActionIndex = getNextActionIndex(action, flowLinkList, context);
                if (null == nextActionIndex) {
                    LOG.error("@_@无法继续执行流程, 原因: 无法找到执行下一步的流程组件,组件ID:{},组件名称{}", flowAction.getFlowActionId(),
                            flowAction.getFlowActionName());
                    throw new FlowException("流程配置有误，无法找到下一步执行组件");
                } else {
                    curActionIndex = nextActionIndex.intValue();
                }
            }
        }
        Map<String, Object> outputParams = flowMetaData.getOutputParams();
        if (MapUtils.isEmpty(outputParams)) {
            LOG.warn("!!![未配置输出参数]");
        } else {
            Set<String> keySet = outputParams.keySet();
            for (String key : keySet) {
                Object value = context.get(key);
                outputParams.put(key, value);
            }
            flowMetaData.setOutputParams(outputParams);
        }
        // TODO 清除context 应在放在finally里面 需要解决内存泄漏问题
        if (MapUtils.isNotEmpty(context)) {
            context.clear();
        }
        return flowMetaData.getOutputParams();
    }

    private Integer getNextActionIndex(IAction action, List<FlowLink> flowLinkList, FlowContext context) {

        Integer nextActionIndex = null;
        if (action instanceof IJumpAction) {
            // 先判断有条件的情况
            nextActionIndex = flowLinkList
                    .stream()
                    .filter(link -> StringUtils.isNotEmpty(link.getCondition())
                            && judgeCondition(link.getCondition(), context)).findFirst().map(link -> link.getTo())
                    .orElse(null);
            // 再判断无条件的情况
            if (null == nextActionIndex) {
                nextActionIndex = flowLinkList.stream().filter(link -> StringUtils.isEmpty(link.getCondition()))
                        .findFirst().map(link -> link.getTo()).orElse(null);
            }
        } else {
            FlowLink flowLink = flowLinkList.get(0);
            nextActionIndex = flowLink.getTo();
        }
        return nextActionIndex;
    }

    private boolean judgeCondition(String condition, FlowContext context) {
        StandardEvaluationContext expressionContext = new StandardEvaluationContext();
        expressionContext.setVariables(context);
        Boolean result = SPEL.parseExpression(condition).getValue(expressionContext, Boolean.class);
        if (null == result) {
            return false;
        }
        return result;
    }

}
