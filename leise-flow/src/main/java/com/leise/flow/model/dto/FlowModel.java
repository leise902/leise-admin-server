package com.leise.flow.model.dto;

import java.io.Serializable;
import java.util.List;

import com.leise.flow.model.bizlogic.entity.FlowBizlogic;
import com.leise.flow.model.bizlogic.entity.FlowData;
import com.leise.flow.model.bizlogic.entity.FlowInfo;

public class FlowModel implements Serializable {

    /**
     * 串行号
     */
    private static final long serialVersionUID = -4204128845245900930L;

    private FlowInfo flowInfo;

    private List<FlowData> flowData;

    private FlowBizlogic flowBizlogic;

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

    public FlowBizlogic getFlowBizlogic() {
        return flowBizlogic;
    }

    public void setFlowBizlogic(FlowBizlogic flowBizlogic) {
        this.flowBizlogic = flowBizlogic;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
