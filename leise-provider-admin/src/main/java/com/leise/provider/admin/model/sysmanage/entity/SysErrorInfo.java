package com.leise.provider.admin.model.sysmanage.entity;

import java.util.Date;

public class SysErrorInfo {
    private Long errorId;

    private String errorCode;

    private String errorMsg;

    private Date createTime;

    private Date modifyTime;

    private Boolean validStatus;

    public Long getErrorId() {
        return errorId;
    }

    public void setErrorId(Long errorId) {
        this.errorId = errorId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode == null ? null : errorCode.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
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

    public Boolean getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Boolean validStatus) {
        this.validStatus = validStatus;
    }
}