package com.leise.flow.cache;

import java.util.List;
import java.util.Map;

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
import com.leise.flow.loader.FlowLoader;
import com.leise.flow.model.dto.FlowModel;
import com.leise.flow.model.entity.FlowData;
import com.leise.flow.model.entity.FlowInfo;
import com.leise.flow.parser.FlowActionParser;
import com.leise.flow.parser.FlowLinkParser;

/**
 * Created by JY-IT-D001 on 2018/6/14.
 */
@Component
public class FlowLocalCacheRegister {

    private static final Logger LOG = LoggerFactory.getLogger(FlowLocalCacheRegister.class);

    @Autowired
    private FlowLoader flowloader;

    public LoadingCache<FlowCacheKey, FlowMetaData> flowLoadingCache = CacheBuilder.newBuilder().maximumSize(5000)
            .build(new CacheLoader<FlowCacheKey, FlowMetaData>() {

                @Override
                public FlowMetaData load(FlowCacheKey flowCacheKey) throws Exception {
                    String moduleId = flowCacheKey.getModuleId();
                    String flowId = flowCacheKey.getFlowId();
                    String flowVersion = flowCacheKey.getFlowVersion();
                    LOG.warn("!!![缓存未命中需重新加载]");
                    FlowModel flowModel = flowloader.getFlowModel(moduleId, flowId, flowVersion);
                    FlowMetaData flowMetaData = initFlowMetaData(flowModel);
                    if (null == flowMetaData) {
                        LOG.error("读取服务数据出现异常:业务逻辑数据不存在");
                    }
                    return flowMetaData;
                }
            });

    public FlowMetaData getFlowMetaData(FlowCacheKey flowCacheKey) {
        LOG.info("[从缓存读取服务数据]");
        try {
            return flowLoadingCache.get(flowCacheKey);
        }
        catch (Exception e) {
            return null;
        }
    }

    public void initFlowLocalCache(List<FlowModel> list) {
        if (CollectionUtils.isEmpty(list)) {
            LOG.warn("未查询到流程配置数据...初始化流程配置结束......");
            return;
        }
        list.stream().forEach(flowModel -> {
            FlowInfo flowInfo = flowModel.getFlowInfo();
            String moduleId = flowInfo.getModuleId();
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

        Map<String, Object> flowBizlogic = flowModel.getFlowBizlogic();
        if (null == flowBizlogic) {
            return null;
        }

        FlowInfo flowInfo = flowModel.getFlowInfo();
        List<FlowData> flowDataList = flowModel.getFlowData();
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

        List<FlowAction> flowActions = (List<FlowAction>) flowBizlogic.get("nodeDataArray");
        List<FlowLink> flowLinks = (List<FlowLink>) flowBizlogic.get("linkDataArray");
        FlowActionParser.parse(flowActions, metaData);
        FlowLinkParser.parse(flowLinks, metaData);

        return metaData;
    }

}
