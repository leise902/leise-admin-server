package com.leise.flow.cache;

import java.io.Serializable;

/**
 * Created by JY-IT-D001 on 2018/7/3.
 */
public class FlowLink implements Serializable {

    private static final long serialVersionUID = -637311992042420163L;

    private int from;

    private int to;

    private String condition;

    private String linkDesc;

    public FlowLink(int from, int to, String condition, String linkDesc) {
        this.from = from;
        this.to = to;
        this.condition = condition;
        this.linkDesc = linkDesc;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLinkDesc() {
        return linkDesc;
    }

    public void setLinkDesc(String linkDesc) {
        this.linkDesc = linkDesc;
    }
}
