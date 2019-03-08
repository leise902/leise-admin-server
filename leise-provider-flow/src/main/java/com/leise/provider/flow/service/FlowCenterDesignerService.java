package com.leise.provider.flow.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.leise.flow.cache.ActionLocalCacheRegister;
import com.leise.flow.cache.FlowCacheKey;
import com.leise.flow.cache.FlowLocalCacheRegister;
import com.leise.flow.model.bizlogic.entity.FlowBizlogic;
import com.leise.flow.model.bizlogic.entity.FlowData;
import com.leise.provider.flow.model.service.FCFlowBizlogicService;
import com.leise.provider.flow.model.service.FCFlowDataService;
import com.leise.provider.flow.model.service.FCFlowInfoService;
import com.leise.provider.flow.model.service.FCModuleInfoService;

/**
 * Created by JY-IT-D001 on 2018/7/10.
 */
@Service
public class FlowCenterDesignerService {

    private final static Logger LOG = LoggerFactory.getLogger(FlowCenterDesignerService.class);

    @Autowired
    public FCModuleInfoService fcModuleInfoService;

    @Autowired
    private FCFlowInfoService fcFlowInfoService;

    @Autowired
    private FCFlowBizlogicService fcFlowBizlogicService;

    @Autowired
    private FCFlowDataService fcFlowDataService;

    @Autowired
    private FlowLocalCacheRegister flowLocalCacheRegister;

    public Map<String, List<Map<String, Object>>> getActions(String moduleId) {
        // 系统定义组件
        Map<String, Map<String, Object>> actionPropertyCache = ActionLocalCacheRegister.actionPropertyCache;
        Map<String, List<Map<String, Object>>> actionPropertyGroup = actionPropertyCache.values().stream()
                .collect(Collectors.groupingBy(map -> (String) map.get("groupId")));
        return actionPropertyGroup;
    }

    public Map<String, Object> searchModuleInfoList() {
        List<Map<String, Object>> moduleInfoList = fcModuleInfoService.search();
        Map<String, Object> result = Maps.newHashMap();
        result.put("list", moduleInfoList);
        return result;
    }

    @Transactional(rollbackFor = { DataAccessException.class, RuntimeException.class, Exception.class })
    public void updateFlowModel(Map params) {
        Map<String, Object> flowMap = (Map) params.get("modelData");
        Assert.notEmpty(flowMap, "流程信息要素不能为空");
        String flowInfoId = String.valueOf(flowMap.get("flowInfoId"));
        Assert.notNull(flowInfoId, "流程参数[flowInfoId]不能为空");
        Assert.notNull(params.get("nodeDataArray"), "流程参数[nodeDataArray]不能为空");
        Assert.notNull(params.get("linkDataArray"), "流程参数[linkDataArray]不能为空");
        Map<String, Object> bizlogicMap = Maps.newHashMap();
        bizlogicMap.put("nodeDataArray", params.get("nodeDataArray"));
        bizlogicMap.put("linkDataArray", params.get("linkDataArray"));
        String bizlogic = JSON.toJSONString(bizlogicMap);
        Map<String, Object> flowBizlogicMap = Maps.newHashMap();
        flowBizlogicMap.put("flowInfoId", Long.parseLong(flowInfoId));
        flowBizlogicMap.put("bizlogic", bizlogic);
        fcFlowInfoService.update(flowMap);
        fcFlowBizlogicService.saveOrUpdate(flowBizlogicMap);
        clearFlowCache(Long.parseLong(flowInfoId));
    }

    @Transactional(rollbackFor = { DataAccessException.class, RuntimeException.class, Exception.class })
    public void addFlowInfo(Map params) {
        Assert.notEmpty(params, "流程信息要素不能为空");
        fcFlowInfoService.insert(params);
    }

    public Map<String, Object> findFlowModel(long flowInfoId) {

        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> flowInfoMap = fcFlowInfoService.findById(flowInfoId);
        if (MapUtils.isEmpty(flowInfoMap)) {
            return result;
        }

        result.put("modelData", flowInfoMap);
        Map<String, Object> bizlogicMap = Maps.newHashMap();
        FlowBizlogic flowBizlogic = fcFlowBizlogicService.findByFlowInfoId(flowInfoId);
        if (null != flowBizlogic) {
            String bizlogic = flowBizlogic.getBizlogic();
            bizlogicMap = JSON.parseObject(bizlogic);
            result.putAll(bizlogicMap);
        }
        return result;
    }

    public Map<String, Object> findFlowInfo(Long flowInfoId) {
        Map<String, Object> flowInfoMap = fcFlowInfoService.findById(flowInfoId);
        return flowInfoMap;
    }

    public Map<String, Object> searchFlowInfoList() {
        List<Map<String, Object>> flowInfoList = fcFlowInfoService.search();
        Map<String, Object> result = Maps.newHashMap();
        result.put("list", flowInfoList);
        return result;
    }

    public Map<String, Object> queryFlowDataPage(Long flowInfoId, int pageNum, int pageSize) {
        long total = fcFlowDataService.searchPageTotalByFlowInfoId(flowInfoId);
        if (total == 0) {
            return null;
        }
        List<FlowData> flowDataPage = fcFlowDataService.searchPageByFlowInfoId(flowInfoId, pageNum, pageSize);
        Map<String, Object> result = Maps.newHashMap();
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("total", total);
        result.put("flowDataPage", flowDataPage);
        return result;
    }

    public void addFlowData(Map params) {
        fcFlowDataService.insert(params);
    }

    public void modfiyFlowData(Map params) {
        fcFlowDataService.updateById(params);
    }

    public void deleteFlowData(long flowDataId) {
        fcFlowDataService.deleteById(flowDataId);
    }

    public void clearFlowCache(long flowInfoId) {
        Map<String, Object> flowInfoMap = fcFlowInfoService.findById(flowInfoId);
        String flowId = (String) flowInfoMap.get("flowId");
        String flowVersion = (String) flowInfoMap.get("flowVersion");
        String moduleId = (String) flowInfoMap.get("moduleId");
        flowLocalCacheRegister.flowLoadingCache.invalidate(new FlowCacheKey(moduleId, flowId, flowVersion));
    }
}
