package com.leise.flow.model.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by JY-IT-D001 on 2018/7/2.
 */
@Table(name = "flow_bizlogic")
public class FlowBizlogic implements Serializable {

    private static final long serialVersionUID = -2532753487303720017L;

    @Column(name = "id")
    private long id;

    @Column(name = "flow_info_id")
    private long flowInfoId;

    @Column(name = "bizlogic")
    private String bizlogic;

    @Column(name = "valid_status")
    private boolean validStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

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

    public String getBizlogic() {
        return bizlogic;
    }

    public void setBizlogic(String bizlogic) {
        this.bizlogic = bizlogic;
    }

}
