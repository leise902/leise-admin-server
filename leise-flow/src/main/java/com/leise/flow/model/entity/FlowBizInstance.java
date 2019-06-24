package com.leise.flow.model.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Table(name = "flow_biz_instance")
public class FlowBizInstance implements Serializable {

    private static final long serialVersionUID = -2532753487303720017L;

    @Column(name = "id")
    private long id;

    @Column(name = "module_id")
    private String moduleId;

    @Column(name = "flow_id")
    private String flowId;

    @Column(name = "flow_version")
    private String flowVersion;

    @Column(name = "flow_instance")
    private String flowInstance;
    
    @Column(name = "cost")
    private long cost;
    
    @Column(name = "error_code")
    private String errorCode;
    
    @Column(name = "error_message")
    private String errorMessage;

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
    
    public String getFlowInstance() {
        return flowInstance;
    }

    
    public void setFlowInstance(String flowInstance) {
        this.flowInstance = flowInstance;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getErrorCode() {
        return errorCode;
    }

    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    
    public String getErrorMessage() {
        return errorMessage;
    }

    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
