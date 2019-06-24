package com.leise.flow.model.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by JY-IT-D001 on 2018/8/8.
 */
@Table(name = "flow_publish_info")
public class FlowPublishInfo implements Serializable {

    private static final long serialVersionUID = -2912972794772506068L;

    @Column(name = "id")
    private long id;

    @Column(name = "flow_info_id")
    private long flowInfoId;

    @Column(name = "publish_no")
    private String publishNo;

    @Column(name = "version")
    private String version;

    @Column(name = "access_key")
    private String accessKey;

    @Column(name = "module_id")
    private String moduleId;

    @Column(name = "flow_id")
    private String flowId;

    @Column(name = "flow_version")
    private String flowVersion;

    @Column(name = "flow_info")
    private String flowInfo;

    @Column(name = "flow_data")
    private String flowData;

    @Column(name = "flow_bizlogic")
    private String flowBizlogic;

    @Column(name = "status")
    private String status;

    @Column(name = "remark")
    private String remark;

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

    public String getPublishNo() {
        return publishNo;
    }

    public void setPublishNo(String publishNo) {
        this.publishNo = publishNo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
