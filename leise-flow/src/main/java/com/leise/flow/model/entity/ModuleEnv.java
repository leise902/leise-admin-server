package com.leise.flow.model.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by JY-IT-D001 on 2018/7/2.
 */
@Table(name = "module_env")
public class ModuleEnv implements Serializable {

    private static final long serialVersionUID = -1876199945307205665L;

    @Column(name = "id")
    private long id;

    @Column(name = "module_info_id")
    private String moduleInfoId;

    @Column(name = "env_name")
    private String envName;

    @Column(name = "env_address")
    private String envAddress;

    @Column(name = "flow_status")
    private String flowStatus;

    @Column(name = "valid_status")
    private boolean validStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isValidStatus() {
        return validStatus;
    }

    public void setValidStatus(boolean validStatus) {
        this.validStatus = validStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getEnvAddress() {
        return envAddress;
    }

    public void setEnvAddress(String envAddress) {
        this.envAddress = envAddress;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getModuleInfoId() {
        return moduleInfoId;
    }

    public void setModuleInfoId(String moduleInfoId) {
        this.moduleInfoId = moduleInfoId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
