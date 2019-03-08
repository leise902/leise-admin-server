package com.leise.provider.admin.biz.user.login.input;

import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import com.leise.core.base.dto.Input;

@Validated
public class LoginInput implements Input {

    /**
     * 串行号
     */
    private static final long serialVersionUID = -7183512149335219706L;

    @Pattern(regexp = "[1-9][0-9]{5}", message = "用户编号格式有误")
    private String userId;

    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
