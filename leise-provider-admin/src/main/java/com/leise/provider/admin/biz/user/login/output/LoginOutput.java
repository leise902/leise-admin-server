package com.leise.provider.admin.biz.user.login.output;

import com.leise.core.base.dto.Output;

public class LoginOutput implements Output {

    /**
     * 串行号
     */
    private static final long serialVersionUID = 118297222148489338L;

    private String userId;

    private String userName;

    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
