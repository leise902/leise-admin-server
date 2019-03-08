package com.leise.flow.cache;

import java.util.List;
import java.util.Map;

import com.leise.flow.context.FlowContext;

/**
 * Created by JY-IT-D001 on 2018/6/14.
 */
public class FlowMetaData {

    private boolean enable;

    private FlowContext context;

    private Map<String, Object> inputParams;

    private Map<String, Object> outputParams;

    private Map<Integer, FlowAction> flowActions;

    private Map<Integer, List<FlowLink>> flowLinks;

    private Integer flowStartIndex;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public FlowContext getContext() {
        return context;
    }

    public void setContext(FlowContext context) {
        this.context = context;
    }

    public Map<String, Object> getInputParams() {
        return inputParams;
    }

    public void setInputParams(Map<String, Object> inputParams) {
        this.inputParams = inputParams;
    }

    public Map<String, Object> getOutputParams() {
        return outputParams;
    }

    public void setOutputParams(Map<String, Object> outputParams) {
        this.outputParams = outputParams;
    }

    public Map<Integer, FlowAction> getFlowActions() {
        return flowActions;
    }

    public void setFlowActions(Map<Integer, FlowAction> flowActions) {
        this.flowActions = flowActions;
    }

    public Map<Integer, List<FlowLink>> getFlowLinks() {
        return flowLinks;
    }

    public void setFlowLinks(Map<Integer, List<FlowLink>> flowLinks) {
        this.flowLinks = flowLinks;
    }

    public Integer getFlowStartIndex() {
        return flowStartIndex;
    }

    public void setFlowStartIndex(Integer flowStartIndex) {
        this.flowStartIndex = flowStartIndex;
    }
}
