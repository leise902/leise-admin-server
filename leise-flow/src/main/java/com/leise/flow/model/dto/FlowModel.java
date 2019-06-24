package com.leise.flow.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.leise.flow.model.entity.FlowData;
import com.leise.flow.model.entity.FlowInfo;

public class FlowModel implements Serializable {

    /**
     * 串行号
     */
    private static final long serialVersionUID = -4204128845245900930L;

    private FlowInfo flowInfo;

    private List<FlowData> flowData;

    private Map<String, Object> flowBizlogic;

    private String version;

    public FlowInfo getFlowInfo() {
        return flowInfo;
    }

    public void setFlowInfo(FlowInfo flowInfo) {
        this.flowInfo = flowInfo;
    }

    public List<FlowData> getFlowData() {
        return flowData;
    }

    public void setFlowData(List<FlowData> flowData) {
        this.flowData = flowData;
    }

    public Map<String, Object> getFlowBizlogic() {
        return flowBizlogic;
    }

    public void setFlowBizlogic(Map<String, Object> flowBizlogic) {
        this.flowBizlogic = flowBizlogic;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
