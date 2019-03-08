package com.leise.flow.controller;

import com.alibaba.fastjson.JSON;
import com.leise.flow.dto.JsonResult;
import com.leise.flow.engine.FlowEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

@Component
public class ApiController {

    private final static Logger LOG = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    FlowEngine flowEngine;

    public JsonResult doExecute(final String serviceId, final String version, final String requestMsg) throws Exception {
        LOG.info("[服务控制器]--[接收到请求数据]:[服务编号:{}]---[版本号:{}]---[请求数据:{}]", serviceId, version, requestMsg);
        Assert.notNull(serviceId, "[服务编号]格式不正确");
        Assert.notNull(requestMsg, "[请求数据]格式不正确");
        Map<String, Object> result = flowEngine.executeFlow(serviceId, version, requestMsg);
        JsonResult jsonView = new JsonResult(0, "成功", result);
        LOG.info("[服务控制器]--[返回响应数据]:{}", JSON.toJSONString(jsonView));
        return jsonView;
    }

}
