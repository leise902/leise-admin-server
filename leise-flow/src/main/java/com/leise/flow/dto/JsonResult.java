package com.leise.flow.dto;

import com.google.common.collect.Maps;

import java.io.Serializable;

/**
 * Created by JY-IT-D001 on 2018/7/11.
 */
public class JsonResult implements Serializable {

    private static final long serialVersionUID = -5647965996003254286L;

    private int errorCode;

    private String errorMsg;

    private Object result;
    
    public JsonResult() {
        
    }

    public JsonResult(int errorCode, String errorMsg, Object result) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.result = result == null ? Maps.newHashMap() : result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result == null ? Maps.newHashMap() : result;
    }
}
