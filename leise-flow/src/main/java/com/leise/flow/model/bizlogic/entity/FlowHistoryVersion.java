package com.leise.flow.model.bizlogic.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by JY-IT-D001 on 2018/8/8.
 */
@Table(name = "flow_history_version")
public class FlowHistoryVersion implements Serializable {

    private static final long serialVersionUID = -2912972794772506068L;

    @Column(name = "id")
    private long id;

    @Column(name = "flow_info_id")
    private long flowInfoId;

    @Column(name = "module_info")
    private String moduleInfo;

    @Column(name = "flow_info")
    private String flowInfo;

    @Column(name = "flow_data")
    private String flowData;

    @Column(name = "flow_bizlogic")
    private String flowBizlogic;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "valid_status")
    private boolean validStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFlowInfoId() {
        return flowInfoId;
    }

    public void setFlowInfoId(long flowInfoId) {
        this.flowInfoId = flowInfoId;
    }

    public String getModuleInfo() {
        return moduleInfo;
    }

    public void setModuleInfo(String moduleInfo) {
        this.moduleInfo = moduleInfo;
    }

    public String getFlowInfo() {
        return flowInfo;
    }

    public void setFlowInfo(String flowInfo) {
        this.flowInfo = flowInfo;
    }

    public String getFlowData() {
        return flowData;
    }

    public void setFlowData(String flowData) {
        this.flowData = flowData;
    }

    public String getFlowBizlogic() {
        return flowBizlogic;
    }

    public void setFlowBizlogic(String flowBizlogic) {
        this.flowBizlogic = flowBizlogic;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public boolean isValidStatus() {
        return validStatus;
    }

    public void setValidStatus(boolean validStatus) {
        this.validStatus = validStatus;
    }
}
