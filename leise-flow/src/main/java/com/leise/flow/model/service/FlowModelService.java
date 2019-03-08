package com.leise.flow.model.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.leise.flow.config.ApplicationConfig;
import com.leise.flow.enums.FlowLoadingModeEnum;
import com.leise.flow.model.bizlogic.entity.FlowBizlogic;
import com.leise.flow.model.bizlogic.entity.FlowData;
import com.leise.flow.model.bizlogic.entity.FlowInfo;
import com.leise.flow.model.bizlogic.service.FlowBizlogicService;
import com.leise.flow.model.bizlogic.service.FlowDataService;
import com.leise.flow.model.bizlogic.service.FlowInfoService;
import com.leise.flow.model.dto.FlowModel;
import com.leise.flow.util.OkHttpUtils;

@Component
public class FlowModelService {

    private static final Logger LOG = LoggerFactory.getLogger(FlowModelService.class);

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private FlowInfoService flowInfoService;

    @Autowired
    private FlowDataService flowDataService;

    @Autowired
    private FlowBizlogicService flowBizlogicService;

    private static final String BIZ_FILE_DIR = "classpath:bizlogic";

    public List<FlowModel> buildFlowModelList(String moduleId) {
        String flowLoadingMode = applicationConfig.getFlowLoadingMode();
        if (flowLoadingMode.equalsIgnoreCase(FlowLoadingModeEnum.REMOTE.toString())) {
            return this.buildFlowModelListFromFlowCenter();
        } else if (flowLoadingMode.equalsIgnoreCase(FlowLoadingModeEnum.FILE.toString())) {
            return this.buildFlowModelListFromFlie(moduleId);
        } else if (flowLoadingMode.equalsIgnoreCase(FlowLoadingModeEnum.DATABASE.toString())) {
            return this.buildFlowModelListFromDataBase(moduleId);
        } else {
            return null;
        }
    }

    public FlowModel buildFlowModel(String moduleId, String flowId, String flowVersion) {
        String flowLoadingMode = applicationConfig.getFlowLoadingMode();
        if (flowLoadingMode.equalsIgnoreCase(FlowLoadingModeEnum.REMOTE.toString())) {
            return this.buildFlowModelFromFlowCenter(moduleId, flowId, flowVersion);
        } else if (flowLoadingMode.equalsIgnoreCase(FlowLoadingModeEnum.FILE.toString())) {
            return this.buildFlowModelFromFlie(moduleId, flowId, flowVersion);
        } else if (flowLoadingMode.equalsIgnoreCase(FlowLoadingModeEnum.DATABASE.toString())) {
            return this.buildFlowModelFromDataBase(moduleId, flowId, flowVersion);
        } else {
            return null;
        }
    }

    public FlowModel buildFlowModelFromDataBase(String moduleId, String flowId, String flowVersion) {
        FlowInfo flowInfo = flowInfoService.findByBizKey(moduleId, flowId, flowVersion);
        if (flowInfo == null) {
            return null;
        }
        FlowModel flowModel = new FlowModel();
        flowModel.setFlowInfo(flowInfo);
        long flowInfoId = flowInfo.getId();
        List<FlowData> flowDataList = flowDataService.searchByFlowInfoId(flowInfoId);
        if (CollectionUtils.isNotEmpty(flowDataList)) {
            flowModel.setFlowData(flowDataList);
        }
        FlowBizlogic flowBizlogic = flowBizlogicService.findByFlowInfoId(flowInfoId);
        flowModel.setFlowBizlogic(flowBizlogic);
        return flowModel;
    }

    public FlowModel buildFlowModelFromFlie(String moduleId, String flowId, String flowVersion) {
        String fileName = StringUtils.join(new String[] { flowId, flowVersion }, "-") + ".json";
        FlowModel flowModel = null;
        File bizlogicFile = null;
        try {
            String filePath = new StringBuilder().append(BIZ_FILE_DIR).append(IOUtils.DIR_SEPARATOR).append(fileName)
                    .toString();
            bizlogicFile = ResourceUtils.getFile(filePath);
        } catch (FileNotFoundException e) {
            return null;
        }
        if (bizlogicFile.exists()) {
            // try-with-resources
            try (FileInputStream in = new FileInputStream(bizlogicFile)) {
                String content = null;
                content = IOUtils.toString(in, "utf-8");
                flowModel = JSON.parseObject(content, FlowModel.class);
            } catch (Exception e) {

            }
        }
        return flowModel;
    }

    public FlowModel buildFlowModelFromFlowCenter(String moduleId, String flowId, String flowVersion) {
        String accessKey = applicationConfig.getAccessKey();
        String flowCenterUrl = applicationConfig.getFlowCenterUrl();
        Map<String, Object> params = Maps.newHashMap();
        params.put("accessKey", accessKey);
        params.put("moduleId", moduleId);
        params.put("flowId", flowId);
        params.put("flowVersion", flowVersion);
        String requestUrl = new StringBuilder().append(flowCenterUrl).append("/queryPublishedFlow/1.0.0").toString();
        String responseMessage = OkHttpUtils.postJsonParams(requestUrl, JSON.toJSONString(params), 10000L, 10000L);
        if (StringUtils.isNotEmpty(responseMessage)) {
            JSONObject response = JSON.parseObject(responseMessage);
            JSONObject result = response.getJSONObject("result");
            FlowModel flowModel = JSON.parseObject(JSON.toJSONString(result), FlowModel.class);
            return flowModel;
        }
        return null;
    }

    public List<FlowModel> buildFlowModelListFromFlowCenter() {
        List<FlowModel> flowModelList = Lists.newArrayList();
        String accessKey = applicationConfig.getAccessKey();
        String flowCenterUrl = applicationConfig.getFlowCenterUrl();
        Map<String, Object> params = Maps.newHashMap();
        params.put("accessKey", accessKey);
        String requestUrl = new StringBuilder().append(flowCenterUrl).append("/queryPublishedFlowList/1.0.0")
                .toString();
        String responseMessage = OkHttpUtils.postJsonParams(requestUrl, JSON.toJSONString(params), 10000L, 10000L);
        if (StringUtils.isNotEmpty(responseMessage)) {
            JSONObject response = JSON.parseObject(responseMessage);
            JSONObject result = response.getJSONObject("result");
            flowModelList = JSON.parseArray(JSON.toJSONString(result.get("flowModelList")), FlowModel.class);
        }
        return flowModelList;
    }

    public List<FlowModel> buildFlowModelListFromDataBase(String moduleId) {
        List<FlowModel> flowModelList = Lists.newArrayList();
        List<FlowInfo> list = flowInfoService.searchByModuleId(moduleId);
        if (CollectionUtils.isEmpty(list)) {
            LOG.warn("未查询到流程信息.......................");
            return flowModelList;
        }
        list.stream().forEach(flowInfo -> {
            FlowModel flowModel = new FlowModel();
            flowModel.setFlowInfo(flowInfo);
            long flowInfoId = flowInfo.getId();
            List<FlowData> flowDataList = flowDataService.searchByFlowInfoId(flowInfoId);
            if (CollectionUtils.isNotEmpty(flowDataList)) {
                flowModel.setFlowData(flowDataList);
            }
            FlowBizlogic flowBizlogic = flowBizlogicService.findByFlowInfoId(flowInfoId);
            flowModel.setFlowBizlogic(flowBizlogic);
            flowModelList.add(flowModel);
        });
        return flowModelList;
    }

    public List<FlowModel> buildFlowModelListFromFlie(String moduleId) {
        List<FlowModel> flowModelList = Lists.newArrayList();
        File file = null;
        try {
            file = ResourceUtils.getFile(BIZ_FILE_DIR);
        } catch (FileNotFoundException e) {
            return flowModelList;
        }
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File bizlogicFile : files) {
                    try (FileInputStream in = new FileInputStream(bizlogicFile)) {
                        String content = IOUtils.toString(in, "utf-8");
                        FlowModel flowModel = JSON.parseObject(content, FlowModel.class);
                        flowModelList.add(flowModel);
                    } catch (IOException e) {
                        continue;
                    }
                }
            }
        }
        return flowModelList;
    }

}
