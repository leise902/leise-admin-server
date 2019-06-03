package com.leise.flow.parser;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.leise.flow.action.IAction;
import com.leise.flow.action.flow.StartAction;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.cache.ActionLocalCacheRegister;
import com.leise.flow.cache.FlowAction;
import com.leise.flow.cache.FlowMetaData;

/**
 * Created by JY-IT-D001 on 2018/7/4.
 */
public class FlowActionParser {

    private static final Logger LOG = LoggerFactory.getLogger(FlowActionParser.class);

    public static void parse(List<FlowAction> flowActions, FlowMetaData metaData) {
        Map<Integer, FlowAction> flowActionMap = Maps.newHashMap();

        for (Object object : flowActions) {
            Map map = (Map) object;
            Integer flowActionId = (Integer) map.get("key");
            String flowActionName = (String) map.get("text");
            Assert.notNull(flowActionId, "组件名称:" + flowActionName + ",流程组件配置有误[flowActionId]项不能为空");
            Assert.notNull(flowActionName, "组件ID:" + flowActionId + ",流程组件配置有误[flowActionName]项不能为空");
            FlowAction flowAction = new FlowAction(flowActionId, flowActionName);
            flowAction.setActionProperties(map);
            String refClass = (String) map.get("refClass");
            Assert.notNull(refClass, "组件名称:" + flowActionName + ",流程组件配置有误[refClass]项不能为空");
            IAction action = ActionLocalCacheRegister.ACTION_CACHE.get(refClass);
            IAction cloneAction = action.clone();
            initActionProperty(cloneAction, flowAction);
            flowAction.setAction(cloneAction);
            if (cloneAction instanceof StartAction) {
                if (null == metaData.getFlowStartIndex()) {
                    metaData.setFlowStartIndex(flowActionId);
                }
            }
            flowActionMap.put(flowActionId, flowAction);
        }
        metaData.setFlowActions(flowActionMap);
    }

    private static void initActionProperty(IAction action, FlowAction flowAction) {

        Map<String, Object> actionProperties = flowAction.getActionProperties();

        Field[] fields = action.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ActionProperty.class)) {
                String fieldName = field.getName();
                Object value = actionProperties.get(fieldName);
                field.setAccessible(true);
                try {
                    field.set(action, value);
                }
                catch (Exception e) {
                    LOG.error("初始化Action配置有误:{}", JSON.toJSONString(flowAction));
                    e.printStackTrace();
                }
            }
        }
        // 验证action初始化参数
        try {
            action.validate();
        }
        catch (Exception e) {
            LOG.error("o_0 Action组件初始化出错, 流程信息:{},异常信息:{}", JSON.toJSONString(flowAction), e.getMessage());
        }
    }
}
