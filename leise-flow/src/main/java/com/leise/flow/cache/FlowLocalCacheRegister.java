package com.leise.flow.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.leise.flow.context.FlowContext;
import com.leise.flow.exception.FlowException;
import com.leise.flow.model.dto.FlowModel;
import com.leise.flow.model.entity.FlowData;
import com.leise.flow.model.entity.FlowInfo;
import com.leise.flow.model.service.FlowModelService;
import com.leise.flow.parser.FlowActionParser;
import com.leise.flow.parser.FlowLinkParser;

/**
 * Created by JY-IT-D001 on 2018/6/14.
 */
@Component
public class FlowLocalCacheRegister {

    private static final Logger LOG = LoggerFactory.getLogger(FlowLocalCacheRegister.class);

    @Autowired
    private FlowModelService flowModelService;

    public LoadingCache<FlowCacheKey, FlowMetaData> flowLoadingCache = CacheBuilder.newBuilder().maximumSize(5000)
            .build(new CacheLoader<FlowCacheKey, FlowMetaData>() {

                @Override
                public FlowMetaData load(FlowCacheKey flowCacheKey) throws Exception {
                    String moduleId = flowCacheKey.getModuleId();
                    String flowId = flowCacheKey.getFlowId();
                    String flowVersion = flowCacheKey.getFlowVersion();
                    LOG.warn("!!![缓存未命中]---[模块编号:{}, 流程编号:{}, 流程版本号:{}]", moduleId, flowId, flowVersion);
                    try {
                        FlowModel flowModel = flowModelService.buildFlowModel(moduleId, flowId, flowVersion);
                        FlowMetaData metaData = initFlowMetaData(flowModel);
                        return metaData;
                    }
                    catch (Exception e) {
                        throw new FlowException("加载流程失败，请进行数据检查");
                    }
                }
            });

    public FlowMetaData getFlowMetaData(FlowCacheKey flowCacheKey) {
        try {
            LOG.info("命中缓存对象:{}", this.flowLoadingCache.toString());
            return flowLoadingCache.get(flowCacheKey);
        }
        catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void initFlowLocalCache(String moduleId) {
        List<FlowModel> flowModelList = flowModelService.buildFlowModelList(moduleId);
        if (CollectionUtils.isEmpty(flowModelList)) {
            LOG.warn("未查询到流程配置数据...初始化流程配置结束......");
            return;
        }
        flowModelList.stream().forEach(flowModel -> {
            FlowInfo flowInfo = flowModel.getFlowInfo();
            String flowId = flowInfo.getFlowId();
            String flowVersion = flowInfo.getFlowVersion();
            FlowMetaData metaData = this.initFlowMetaData(flowModel);
            if (null != metaData) {
                this.flowLoadingCache.put(new FlowCacheKey(moduleId, flowId, flowVersion), metaData);
            } else {
                LOG.warn("未初始化成功,未查询到业务流程记录,流程信息:{}", JSON.toJSONString(flowModel));
            }
        });
    }

    public FlowMetaData initFlowMetaData(FlowModel flowModel) {
        FlowInfo flowInfo = flowModel.getFlowInfo();
        List<FlowData> flowDataList = flowModel.getFlowData();
        Map<String, Object> flowBizlogic = flowModel.getFlowBizlogic();
        FlowMetaData metaData = new FlowMetaData();
        String moduleId = flowInfo.getModuleId();
        String flowId = flowInfo.getFlowId();
        String flowName = flowInfo.getFlowName();
        String flowVersion = flowInfo.getFlowVersion();
        FlowContext context = new FlowContext(moduleId, flowId, flowName, flowVersion);
        Map<String, Object> inputParams = Maps.newHashMap();
        Map<String, Object> outputParams = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(flowDataList)) {
            flowDataList.forEach(flowData -> {
                context.put(flowData.getDataCode(), null);
                if (flowData.isInputFlag()) {
                    inputParams.put(flowData.getDataCode(), null);
                }
                if (flowData.isOutputFlag()) {
                    outputParams.put(flowData.getDataCode(), null);
                }
            });
        }
        metaData.setContext(context);
        metaData.setInputParams(inputParams);
        metaData.setOutputParams(outputParams);
        if (flowBizlogic != null) {

            List<FlowAction> flowActions = (List<FlowAction>) flowBizlogic.get("nodeDataArray");
            List<FlowLink> flowLinks = (List<FlowLink>) flowBizlogic.get("linkDataArray");
            FlowActionParser.parse(flowActions, metaData);
            FlowLinkParser.parse(flowLinks, metaData);

            return metaData;
        }
        return null;
    }

}
