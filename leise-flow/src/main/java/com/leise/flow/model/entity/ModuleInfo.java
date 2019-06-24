package com.leise.flow.model.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by JY-IT-D001 on 2018/7/2.
 */
@Table(name = "module_info")
public class ModuleInfo implements Serializable {

    private static final long serialVersionUID = 875638070829754411L;

    @Column(name = "id")
    private long id;

    @Column(name = "module_id")
    private String moduleId;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "module_desc")
    private String moduleDesc;

    @Column(name = "valid_status")
    private boolean validStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

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

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
