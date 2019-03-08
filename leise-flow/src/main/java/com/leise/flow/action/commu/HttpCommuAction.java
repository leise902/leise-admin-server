package com.leise.flow.action.commu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;
import com.leise.flow.util.ActionUtils;
import com.leise.flow.util.OkHttpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by JY-IT-D001 on 2018/8/15.
 */
@Action(id = "httpCommuAction", name = "HTTP通讯", group = ActionGroupEnum.COMMU)
public class HttpCommuAction implements IAction {

    private final static Logger LOG = LoggerFactory.getLogger(HttpCommuAction.class);

    @ActionProperty
    private long connectTimeout = 2000;

    @ActionProperty
    private long readTimeout = 30000;

    @ActionProperty
    private String method = "post";

    @ActionProperty
    private String serviceId;

    @ActionProperty
    private String requestParamNames;

    @ActionProperty
    private String responseParamNames;

    @Override
    public ActionResultEnum execute(FlowContext context) {

        String[] requestParamNameArray = ActionUtils.split(this.requestParamNames);
        String responseMessage = null;
        String url = this.serviceId;
        if (this.method.equalsIgnoreCase("post")) {
            Map<String, Object> requestParams = Arrays.asList(requestParamNameArray).stream()
                    .collect(Collectors.toMap(key -> key, key -> context.get(key)));
            String requestMessage = JSON.toJSONString(requestParams);
            responseMessage = OkHttpUtils.postJsonParams(url, requestMessage, this.connectTimeout, this.readTimeout);
        } else {
            Map<String, String> requestParams = Arrays.asList(requestParamNameArray).stream()
                    .collect(Collectors.toMap(key -> key, key -> String.valueOf((context.get(key)))));
            responseMessage = OkHttpUtils.get(url, requestParams, this.connectTimeout, this.readTimeout);
        }

        JSONObject responseParams = JSON.parseObject(responseMessage);
        String[] responseParamNameArray = ActionUtils.split(this.responseParamNames);
        Map<String, Object> result = Arrays.asList(responseParamNameArray).stream()
                .collect(Collectors.toMap(key -> key, key -> responseParams.get(key)));
        context.putAll(result);

        return ActionResultEnum.SUCCESS;
    }

    @Override
    public IAction clone() {
        try {
            return (HttpCommuAction) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void validate() {

    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getRequestParamNames() {
        return requestParamNames;
    }

    public void setRequestParamNames(String requestParamNames) {
        this.requestParamNames = requestParamNames;
    }

    public String getResponseParamNames() {
        return responseParamNames;
    }

    public void setResponseParamNames(String responseParamNames) {
        this.responseParamNames = responseParamNames;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
