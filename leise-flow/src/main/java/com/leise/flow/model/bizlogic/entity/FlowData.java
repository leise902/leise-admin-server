package com.leise.flow.model.bizlogic.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by JY-IT-D001 on 2018/7/2.
 */
@Table(name = "flow_data")
public class FlowData implements Serializable {

    private static final long serialVersionUID = 5749424387607029154L;

    @Column(name = "id")
    private long id;

    @Column(name = "flow_info_id")
    private long flowInfoId;

    @Column(name = "data_code")
    private String dataCode;

    @Column(name = "data_name")
    private String dataName;

    @Column(name = "input_flag")
    private boolean inputFlag;

    @Column(name = "output_flag")
    private boolean outputFlag;

    @Column(name = "collect_flag")
    private boolean collectFlag;

    @Column(name = "collect_data_codes")
    private String collectDataCodes;

    @Column(name = "valid_status")
    private boolean validStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
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

    public boolean isInputFlag() {
        return inputFlag;
    }

    public void setInputFlag(boolean inputFlag) {
        this.inputFlag = inputFlag;
    }

    public boolean isOutputFlag() {
        return outputFlag;
    }

    public void setOutputFlag(boolean outputFlag) {
        this.outputFlag = outputFlag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public boolean isCollectFlag() {
        return collectFlag;
    }

    public void setCollectFlag(boolean collectFlag) {
        this.collectFlag = collectFlag;
    }

    public String getCollectDataCodes() {
        return collectDataCodes;
    }

    public void setCollectDataCodes(String collectDataCodes) {
        this.collectDataCodes = collectDataCodes;
    }

    public long getFlowInfoId() {
        return flowInfoId;
    }

    public void setFlowInfoId(long flowInfoId) {
        this.flowInfoId = flowInfoId;
    }
}
