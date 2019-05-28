package com.leise.flow.model.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.leise.flow.model.bizlogic.entity.FlowBizlogic;
import com.leise.flow.model.bizlogic.entity.FlowData;
import com.leise.flow.model.bizlogic.entity.FlowInfo;
import com.leise.flow.model.bizlogic.service.FlowBizlogicService;
import com.leise.flow.model.bizlogic.service.FlowDataService;
import com.leise.flow.model.bizlogic.service.FlowInfoService;
import com.leise.flow.model.dto.FlowModel;

@Component
public class FlowModelService {

    private static final Logger LOG = LoggerFactory.getLogger(FlowModelService.class);

    @Autowired
    private FlowInfoService flowInfoService;

    @Autowired
    private FlowDataService flowDataService;

    @Autowired
    private FlowBizlogicService flowBizlogicService;

    private static final String BIZ_FILE_DIR = "classpath:bizlogic";

    public List<FlowModel> buildFlowModelList(String moduleId) {
        this.buildFlowModelListFromDataBase(moduleId);
        return this.buildFlowModelListFromFlie(moduleId);
    }

    public FlowModel buildFlowModel(String moduleId, String flowId, String flowVersion) {
        this.buildFlowModelFromDataBase(moduleId, flowId, flowVersion);
        return this.buildFlowModelFromFlie(moduleId, flowId, flowVersion);

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
        String bizlogic = flowBizlogic.getBizlogic();
        Map<String, Object> flowBizlogicMap = JSON.parseObject(bizlogic);
        flowModel.setFlowBizlogic(flowBizlogicMap);
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
        }
        catch (FileNotFoundException e) {
            return null;
        }
        if (bizlogicFile.exists()) {
            // try-with-resources
            try (FileInputStream in = new FileInputStream(bizlogicFile)) {
                String content = null;
                content = IOUtils.toString(in, "utf-8");
                flowModel = JSON.parseObject(content, FlowModel.class);
            }
            catch (Exception e) {

            }
        }
        return flowModel;
    }

    public List<FlowModel> buildFlowModelListFromDataBase(String moduleId) {
        List<FlowModel> flowModelList = Lists.newArrayList();
        List<FlowInfo> flowInfoList = flowInfoService.searchByModuleId(moduleId);
        if (CollectionUtils.isEmpty(flowInfoList)) {
            LOG.warn("未查询到流程信息.......................");
            return flowModelList;
        }

        for (FlowInfo flowInfo : flowInfoList) {
            FlowModel flowModel = new FlowModel();
            flowModel.setFlowInfo(flowInfo);
            long flowInfoId = flowInfo.getId();
            List<FlowData> flowDataList = flowDataService.searchByFlowInfoId(flowInfoId);
            if (CollectionUtils.isNotEmpty(flowDataList)) {
                flowModel.setFlowData(flowDataList);
            }
            FlowBizlogic flowBizlogic = flowBizlogicService.findByFlowInfoId(flowInfoId);
            String bizlogic = flowBizlogic.getBizlogic();
            Map<String, Object> flowBizlogicMap = JSON.parseObject(bizlogic);
            flowModel.setFlowBizlogic(flowBizlogicMap);
            String content = JSON.toJSONString(flowModel);
            try {
                String mac = DigestUtils.md5DigestAsHex(content.getBytes("utf-8"));
                flowModel.setVersion(mac);
                LOG.info("进行mac摘要计算成功.............{}", mac);
            }catch (Exception e) {
                LOG.info("进行mac摘要计算失败.............");
                e.printStackTrace();
                continue;
            }
            String contentWithMac = JSON.toJSONString(flowModel);
            String flowId = flowInfo.getFlowId();
            String flowVersion = flowInfo.getFlowVersion();
            String fileName = StringUtils.join(new String[] { flowId, flowVersion }, "-") + ".json";
            try {
                Path rootLocation = Paths.get("bizlogic");
                if (Files.notExists(rootLocation)) {
                    Files.createDirectories(rootLocation);
                }
                Path path = rootLocation.resolve(fileName);
                byte[] bytes = contentWithMac.getBytes();
                Files.write(path, bytes);
                LOG.info("写入业务文件成功.............{}", fileName);
            }
            catch (Exception e) {
                LOG.info("写入业务文件失败.............");
                e.printStackTrace();
                continue;
            }
            flowModelList.add(flowModel);
        }
        return flowModelList;
    }

    public List<FlowModel> buildFlowModelListFromFlie(String moduleId) {
        List<FlowModel> flowModelList = Lists.newArrayList();
        File file = null;
        try {
            file = ResourceUtils.getFile(BIZ_FILE_DIR);
        }
        catch (FileNotFoundException e) {
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
                    }
                    catch (IOException e) {
                        continue;
                    }
                }
            }
        }
        return flowModelList;
    }

}
