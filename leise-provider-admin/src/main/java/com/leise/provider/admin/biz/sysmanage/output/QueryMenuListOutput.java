package com.leise.provider.admin.biz.sysmanage.output;

import java.util.List;

import com.leise.provider.admin.model.sysmanage.entity.SysMenuInfo;
import com.leise.core.base.dto.Output;

public class QueryMenuListOutput implements Output {

    /**
     * 串行号
     */
    private static final long serialVersionUID = -1413495292934425292L;

    private String userId;

    public List<SysMenuInfo> getList() {
        return list;
    }

    public void setList(List<SysMenuInfo> list) {
        this.list = list;
    }

    private List<SysMenuInfo> list;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
