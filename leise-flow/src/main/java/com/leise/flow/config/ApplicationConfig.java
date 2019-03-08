package com.leise.flow.config;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "leise.application")
@Validated
public class ApplicationConfig {

    @NotNull
    private String accessKey;

    @Pattern(regexp = "remote|file|database")
    private String flowLoadingMode = "remote";

    @NotNull
    private String flowCenterUrl;
    
    private boolean enableSyncFlow = false;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getFlowLoadingMode() {
        return flowLoadingMode;
    }

    public void setFlowLoadingMode(String flowLoadingMode) {
        this.flowLoadingMode = flowLoadingMode;
    }

    public String getFlowCenterUrl() {
        return flowCenterUrl;
    }

    public void setFlowCenterUrl(String flowCenterUrl) {
        this.flowCenterUrl = flowCenterUrl;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LeiseApplicationProperties={");
        builder.append("leise.application.accessKey=").append(accessKey);
        builder.append(",leise.application.flowLoadingMode=").append(flowLoadingMode);
        builder.append(",leise.application.flowCenterUrl=").append(flowCenterUrl);
        builder.append("}");
        return builder.toString();
    }

    public boolean isEnableSyncFlow() {
        return enableSyncFlow;
    }

    public void setEnableSyncFlow(boolean enableSyncFlow) {
        this.enableSyncFlow = enableSyncFlow;
    }

}
