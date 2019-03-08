package com.leise.flow.cache;

import com.google.common.collect.Maps;
import com.leise.flow.action.IAction;
import com.leise.flow.action.flow.EndAction;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by JY-IT-D001 on 2018/7/3.
 */
public class FlowAction implements Serializable {

    private static final long serialVersionUID = -6784816661953039170L;

    private int flowActionId;

    private String flowActionName;

    private Map<String, Object> actionProperties;

    private IAction action;

    public FlowAction(int flowActionId, String flowActionName) {
        this.flowActionId = flowActionId;
        this.flowActionName = flowActionName;
        actionProperties = Maps.newHashMap();
    }

    public int getFlowActionId() {
        return flowActionId;
    }

    public void setFlowActionId(int flowActionId) {
        this.flowActionId = flowActionId;
    }

    public String getFlowActionName() {
        return flowActionName;
    }

    public void setFlowActionName(String flowActionName) {
        this.flowActionName = flowActionName;
    }

    public Map<String, Object> getActionProperties() {
        return actionProperties;
    }

    public void setActionProperties(Map<String, Object> actionProperties) {
        this.actionProperties = actionProperties;
    }

    public IAction getAction() {
        return action;
    }

    public void setAction(IAction action) {
        this.action = action;
    }

    public boolean isEnd() {
        return (this.action instanceof EndAction);
    }

}
