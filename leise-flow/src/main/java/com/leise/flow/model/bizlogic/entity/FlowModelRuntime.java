package com.leise.flow.model.bizlogic.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Table(name = "flow_model_runtime")
public class FlowModelRuntime implements Serializable {

    private static final long serialVersionUID = -2532753487303720017L;

    @Column(name = "id")
    private long id;

    @Column(name = "module_id")
    private String moduleId;

    @Column(name = "flow_id")
    private String flowId;

    @Column(name = "flow_version")
    private String flowVersion;

    @Column(name = "flow_model")
    private String flowModel;

    @Column(name = "version")
    private String version;

    @Column(name = "valid_status")
    private boolean validStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getFlowModel() {
        return flowModel;
    }

    public void setFlowModel(String flowModel) {
        this.flowModel = flowModel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isValidStatus() {
        return validStatus;
    }

    public void setValidStatus(boolean validStatus) {
        this.validStatus = validStatus;
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

}
