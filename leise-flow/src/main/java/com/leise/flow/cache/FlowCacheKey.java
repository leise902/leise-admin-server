package com.leise.flow.cache;

/**
 * Created by JY-IT-D001 on 2018/7/4.
 */
public class FlowCacheKey implements Comparable<FlowCacheKey> {

    private String moduleId;
    private String flowId;
    private String flowVersion;

    public FlowCacheKey(String moduleId, String flowId, String flowVersion) {
        this.moduleId = moduleId;
        this.flowId = flowId;
        this.flowVersion = flowVersion;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof FlowCacheKey)) {
            return false;
        } else {
            FlowCacheKey otherKey = (FlowCacheKey) other;
            return this.moduleId.equals(otherKey.moduleId) && this.flowId.equals(otherKey.flowId)
                    && this.flowVersion.equals(otherKey.flowVersion);
        }
    }

    public int hashCode() {
        return this.flowId.hashCode() * 29 + this.flowVersion.hashCode();
    }

    @Override
    public int compareTo(FlowCacheKey other) {
        int result = this.flowId.compareTo(other.flowId);
        if (result == 0) {
            result = this.flowVersion.compareTo(other.flowVersion);
        }
        if (result == 0) {
            result = this.moduleId.compareTo(other.moduleId);
        }
        return result;
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

    public String getFlowVersion() {
        return flowVersion;
    }

    public void setFlowVersion(String flowVersion) {
        this.flowVersion = flowVersion;
    }

}