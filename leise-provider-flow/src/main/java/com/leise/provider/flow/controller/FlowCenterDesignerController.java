package com.leise.provider.flow.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.leise.flow.dto.JsonResult;
import com.leise.flow.model.dto.FlowModel;
import com.leise.provider.flow.service.FlowCenterDesignerService;

/**
 * Created by JY-IT-D001 on 2018/7/10.
 */
@RestController
@RequestMapping(value = { "/designer", "/flow/designer" })
public class FlowCenterDesignerController {

    private final static Logger LOG = LoggerFactory.getLogger(FlowCenterDesignerController.class);

    @Autowired
    private FlowCenterDesignerService flowCenterDesignerService;

    @RequestMapping("/getActions")
    public JsonResult getActions(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/getActions}");
        Map<String, Object> params = JSON.parseObject(requestMsg);
        String moduleId = (String) params.get("moduleId");
        Map<String, List<Map<String, Object>>> result = flowCenterDesignerService.getActions(moduleId);
        JsonResult jsonResult = new JsonResult(0, "成功", result);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/searchModuleInfoList")
    public JsonResult searchModuleInfoList(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/searchModuleInfoList}");
        Map<String, Object> result = flowCenterDesignerService.searchModuleInfoList();
        JsonResult jsonResult = new JsonResult(0, "成功", result);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/updateFlowModel")
    public JsonResult updateFlowModel(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/updateFlowModel}, 接收到请求参数:{}", requestMsg);
        Map<String, Object> params = JSON.parseObject(requestMsg);
        flowCenterDesignerService.updateFlowModel(params);
        JsonResult jsonResult = new JsonResult(0, "成功", null);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/addFlowInfo")
    public JsonResult addFlowInfo(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/addFlowInfo}, 接收到请求参数:{}", requestMsg);
        Map<String, Object> params = JSON.parseObject(requestMsg);
        String moduleId = (String) params.get("moduleId");
        String flowId = (String) params.get("flowId");
        String flowName = (String) params.get("flowName");
        String flowVersion = (String) params.get("flowVersion");
        String requestUrl = (String) params.get("requestUrl");
        Assert.isTrue(StringUtils.isNoneEmpty(new String[] { moduleId, flowId, flowName, flowVersion, requestUrl }),
                "请求参数不能为空,请检查请求参数列表");
        flowCenterDesignerService.addFlowInfo(params);
        JsonResult jsonResult = new JsonResult(0, "成功", null);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/findFlowModel")
    public JsonResult findFlowModel(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/findFlowModel}, 接收到请求参数:{}", requestMsg);
        Map<String, Object> params = JSON.parseObject(requestMsg);
        Object flowInfoId = params.get("flowInfoId");
        Assert.notNull(flowInfoId, "请求参数不能为空,请检查请求参数列表");
        Map<String, Object> result = flowCenterDesignerService.findFlowModel(Long.valueOf(flowInfoId.toString()));
        JsonResult jsonResult = new JsonResult(0, "成功", result);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/findFlowInfo")
    public JsonResult findFlowInfo(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/findFlowInfo}, 接收到请求参数:{}", requestMsg);
        Map<String, Object> params = JSON.parseObject(requestMsg);
        Object flowInfoId = params.get("flowInfoId");
        Assert.notNull(flowInfoId, "请求参数不能为空,请检查请求参数列表");
        Map<String, Object> result = flowCenterDesignerService.findFlowInfo(Long.valueOf(flowInfoId.toString()));
        JsonResult jsonResult = new JsonResult(0, "成功", result);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/searchFlowInfoList")
    public JsonResult searchFlowInfoList(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/searchFlowInfoList}, 接收到请求参数:{}", requestMsg);
        Map<String, Object> result = flowCenterDesignerService.searchFlowInfoList();
        JsonResult jsonResult = new JsonResult(0, "成功", result);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/queryFlowDataPage")
    public JsonResult queryFlowDataPage(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/queryFlowDataPage}, 接收到请求参数:{}", requestMsg);
        Map<String, Object> params = JSON.parseObject(requestMsg);
        String flowInfoId = String.valueOf(params.get("flowInfoId"));
        int pageNum = (Integer) params.get("pageNum");
        int pageSize = (Integer) params.get("pageSize");
        Assert.isTrue(StringUtils.isNotEmpty(flowInfoId), "流程Id不能为空");
        Map<String, Object> result = flowCenterDesignerService.queryFlowDataPage(Long.valueOf(flowInfoId), pageNum,
                pageSize);
        JsonResult jsonResult = new JsonResult(0, "成功", result);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/addFlowData")
    public JsonResult addFlowData(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/addFlowData}, 接收到请求参数:{}", requestMsg);
        Map<String, Object> params = JSON.parseObject(requestMsg);
        String flowInfoId = String.valueOf(params.get("flowInfoId"));
        String dataCode = (String) params.get("dataCode");
        String dataName = (String) params.get("dataName");
        Assert.isTrue(StringUtils.isNoneEmpty(new String[] { flowInfoId, dataCode, dataName }), "请求参数不能为空,请检查请求参数列表");
        flowCenterDesignerService.addFlowData(params);
        JsonResult jsonResult = new JsonResult(0, "成功", null);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/modifyFlowData")
    public JsonResult modfiyFlowData(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/modfiyFlowData}, 接收到请求参数:{}", requestMsg);
        Map<String, Object> params = JSON.parseObject(requestMsg);
        String flowDataId = String.valueOf(params.get("flowDataId"));
        String flowInfoId = String.valueOf(params.get("flowInfoId"));
        String dataCode = (String) params.get("dataCode");
        String dataName = (String) params.get("dataName");
        Assert.isTrue(StringUtils.isNoneEmpty(new String[] { flowDataId, flowInfoId, dataCode, dataName }),
                "请求参数不能为空,请检查请求参数列表");
        flowCenterDesignerService.modfiyFlowData(params);
        JsonResult jsonResult = new JsonResult(0, "成功", null);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/deleteFlowData")
    public JsonResult deleteFlowData(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/deleteFlowData}, 接收到请求参数:{}", requestMsg);
        Map<String, Object> params = JSON.parseObject(requestMsg);
        String flowDataId = String.valueOf(params.get("flowDataId"));
        Assert.isTrue(StringUtils.isNotEmpty(flowDataId), "请求参数不能为空,请检查请求参数列表");
        flowCenterDesignerService.deleteFlowData(Long.parseLong(flowDataId));
        JsonResult jsonResult = new JsonResult(0, "成功", null);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/clearFlowCache")
    public JsonResult clearFlowCache(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/clearFlowCache}, 接收到请求参数:{}", requestMsg);
        Map<String, Object> params = JSON.parseObject(requestMsg);
        String flowInfoId = String.valueOf(params.get("flowInfoId"));
        Assert.isTrue(StringUtils.isNotEmpty(flowInfoId), "请求参数不能为空,请检查请求参数列表");
        flowCenterDesignerService.clearFlowCache(Long.parseLong(flowInfoId));
        JsonResult jsonResult = new JsonResult(0, "成功", null);
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

    @RequestMapping("/generateSourceCode")
    public JsonResult generateSourceCode(@RequestBody String requestMsg) {
        LOG.info("[请求数据]:{/designer/generateSourceCode}, 接收到请求参数:{}", requestMsg);
        Map<String, Object> params = JSON.parseObject(requestMsg);
        FlowModel flowModel = flowCenterDesignerService.generateSourceCode(params);
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
        JsonResult jsonResult = new JsonResult(0, "成功", JSON.toJSONString(flowModel, propertyFilter, SerializerFeature.PrettyFormat));
        LOG.info("[响应数据]:{}", JSON.toJSONString(jsonResult));
        return jsonResult;
    }

}
