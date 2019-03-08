package com.leise.flow.context;

import java.util.HashMap;

/**
 * Created by JY-IT-D001 on 2018/6/26.
 */
public class FlowContext extends HashMap<String, Object> {

    /**
     * 串行号
     */
    private static final long serialVersionUID = 5246781211722711481L;

    private String moduleId;

    private String flowId;

    private String flowName;

    private String flowVersion;

    public FlowContext(String moduleId, String flowId, String flowName, String flowVersion) {
        this.moduleId = moduleId;
        this.flowId = flowId;
        this.flowName = flowName;
        this.flowVersion = flowVersion;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowVersion() {
        return flowVersion;
    }

    public void setFlowVersion(String flowVersion) {
        this.flowVersion = flowVersion;
    }
}
