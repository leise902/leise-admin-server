package com.leise.flow.model.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.leise.flow.model.dto.FlowModel;
import com.leise.flow.model.entity.FlowBizlogic;
import com.leise.flow.model.entity.FlowData;
import com.leise.flow.model.entity.FlowInfo;
import com.leise.flow.model.entity.FlowBizDefine;

@Component
public class FlowModelService {

    private static final Logger LOG = LoggerFactory.getLogger(FlowModelService.class);

    @Autowired
    private FlowInfoService flowInfoService;

    @Autowired
    private FlowDataService flowDataService;

    @Autowired
    private FlowBizlogicService flowBizlogicService;

    @Autowired
    private FlowBizDefineService flowBizDefineService;

    // TODO 运行模式修改成数据库参数
    @Value(value = "${spring.profiles.active}")
    private String mode;

    private static final String BIZ_FILE_DIR = "classpath:bizlogic";

    public List<FlowModel> buildFlowModelList(String moduleId) {
        if ("dev".equals(mode)) {
            LOG.info("服务运行模式:{}, 此模式下业务流程数据从数据库中进行加载", mode);
            return this.buildFlowModelListFromDataBase(moduleId);
        } else if ("prod".equals(mode)) {
            LOG.info("服务运行模式:{}, 此模式下业务流程数据应用文件中进行加载", mode);
            return this.buildFlowModelListFromFlie(moduleId);
        } else {
            return null;
        }
    }

    public FlowModel buildFlowModel(String moduleId, String flowId, String flowVersion) {
        LOG.info("服务运行模式:{}", mode);
        if ("dev".equals(mode)) {
            return this.buildFlowModelFromDataBase(moduleId, flowId, flowVersion);
        } else if ("prod".equals(mode)) {
            return this.buildFlowModelFromFlie(moduleId, flowId, flowVersion);
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
                String version = flowModel.getVersion();
                FlowBizDefine flowModelRuntime = flowBizDefineService.findByBizkey(moduleId, flowId, flowVersion);
                if (null == flowModelRuntime) {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put("moduleId", moduleId);
                    params.put("flowId", flowId);
                    params.put("flowVersion", flowVersion);
                    params.put("flowModel", content);
                    params.put("version", version);
                    flowBizDefineService.insert(params);
                } else {
                    String checkVersion = flowModelRuntime.getVersion();
                    if (!version.equals(checkVersion)) {
                        Map<String, Object> params = Maps.newHashMap();
                        params.put("moduleId", moduleId);
                        params.put("flowId", flowId);
                        params.put("flowVersion", flowVersion);
                        params.put("flowModel", content);
                        params.put("version", version);
                        flowBizDefineService.updateByBizkey(params);
                    }
                }
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
                String version = DigestUtils.md5DigestAsHex(content.getBytes("utf-8"));
                flowModel.setVersion(version);
                LOG.info("进行mac摘要计算成功.............{}", version);
            }
            catch (Exception e) {
                LOG.info("进行mac摘要计算失败.............");
                e.printStackTrace();
                continue;
            }
            flowModelList.add(flowModel);
        }
        return flowModelList;
    }

    public List<FlowModel> buildFlowModelListFromFlie(String moduleId) {

        Resource resource = new ClassPathResource("bizlogic");
        File file = null;
        try {
            file = resource.getFile();
        }
        catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        List<FlowModel> flowModelList = Lists.newArrayList();
        // File file = null;
        // try {
        // file = ResourceUtils.getFile(BIZ_FILE_DIR);
        // }
        // catch (FileNotFoundException e) {
        // return flowModelList;
        // }
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File bizlogicFile : files) {
                    try (FileInputStream in = new FileInputStream(bizlogicFile)) {
                        String content = IOUtils.toString(in, "utf-8");
                        FlowModel flowModel = JSON.parseObject(content, FlowModel.class);
                        FlowInfo flowInfo = flowModel.getFlowInfo();
                        String flowId = flowInfo.getFlowId();
                        String flowVersion = flowInfo.getFlowVersion();
                        String version = flowModel.getVersion();
                        FlowBizDefine flowBizDefine = flowBizDefineService.findByBizkey(moduleId, flowId, flowVersion);
                        if (null == flowBizDefine) {
                            Map<String, Object> params = Maps.newHashMap();
                            params.put("moduleId", moduleId);
                            params.put("flowId", flowId);
                            params.put("flowVersion", flowVersion);
                            params.put("flowModel", content);
                            params.put("version", version);
                            flowBizDefineService.insert(params);
                        } else {
                            String checkVersion = flowBizDefine.getVersion();
                            if (!version.equals(checkVersion)) {
                                Map<String, Object> params = Maps.newHashMap();
                                params.put("moduleId", moduleId);
                                params.put("flowId", flowId);
                                params.put("flowVersion", flowVersion);
                                params.put("flowModel", content);
                                params.put("version", version);
                                flowBizDefineService.updateByBizkey(params);
                            }
                        }
                        flowModelList.add(flowModel);
                    }
                    catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        return flowModelList;
    }

    public void createBizFile(String moduleId, String flowId, String flowVersion) {

        PropertyFilter propertyFilter = new PropertyFilter() {

            @Override
            public boolean apply(Object object, String name, Object value) {
                if (name.equalsIgnoreCase("id")) {
                    return false;
                } else if (name.equalsIgnoreCase("createTime")) {
                    return false;
                } else if (name.equalsIgnoreCase("modifyTime")) {
                    return false;
                }
                return true;
            }
        };

        FlowInfo flowInfo = flowInfoService.findByBizKey(moduleId, flowId, flowVersion);
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
            String version = DigestUtils.md5DigestAsHex(content.getBytes("utf-8"));
            flowModel.setVersion(version);
            LOG.info("进行mac摘要计算成功.............{}", version);
        }
        catch (Exception e) {
            LOG.info("进行mac摘要计算失败.............");
            e.printStackTrace();
        }

        String contentWithVersion = JSON.toJSONString(flowModel, propertyFilter, SerializerFeature.PrettyFormat);
        String fileName = StringUtils.join(new String[] { flowId, flowVersion }, "-") + ".json";
        try {
            Path rootLocation = Paths.get("bizlogic");
            if (Files.notExists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            Path path = rootLocation.resolve(fileName);
            byte[] bytes = contentWithVersion.getBytes();
            Files.write(path, bytes);
            LOG.info("写入业务文件成功.............{}", fileName);
        }
        catch (Exception e) {
            LOG.info("写入业务文件失败.............");
            e.printStackTrace();
        }
    }

}
