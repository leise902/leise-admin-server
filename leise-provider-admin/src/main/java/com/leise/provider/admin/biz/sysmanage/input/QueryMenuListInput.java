package com.leise.provider.admin.biz.sysmanage.input;

import com.leise.core.base.dto.Input;

public class QueryMenuListInput implements Input {

    /**
     * 串行号
     */
    private static final long serialVersionUID = -1229808557061672729L;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
