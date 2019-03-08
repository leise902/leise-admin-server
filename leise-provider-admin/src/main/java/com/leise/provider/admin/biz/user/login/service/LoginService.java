package com.leise.provider.admin.biz.user.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.leise.provider.admin.base.exception.AdminErrorCode;
import com.leise.provider.admin.biz.user.login.input.LoginInput;
import com.leise.provider.admin.biz.user.login.output.LoginOutput;
import com.leise.provider.admin.model.user.entity.UserInfo;
import com.leise.provider.admin.model.user.service.UserInfoService;
import com.leise.core.base.annotation.CRService;
import com.leise.core.base.service.AbstractService;
import com.leise.core.base.utils.UUIDUtils;

@CRService(name = "用户登录", serviceId = "login", type = "T", value = "login_1.0.0", version = "1.0.0")
public class LoginService extends AbstractService<LoginInput, LoginOutput> {

    /**
     * 串行号
     */
    private static final long serialVersionUID = 4816717465263261828L;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    protected LoginOutput doExecute(LoginInput input) throws Exception {
        String userId = input.getUserId();
        UserInfo userInfo = userInfoService.selectByPrimaryKey(Long.valueOf(userId));
        Assert.notNull(userInfo, AdminErrorCode.USER_DATA_NOT_FOUND);
        Assert.isTrue(input.getPassword().equals(userInfo.getPassword()), AdminErrorCode.USER_LOGIN_PWD_ERROR);
        String token = UUIDUtils.generateUUID();
        String userName = userInfo.getUserName();
        LoginOutput output = new LoginOutput();
        output.setUserId(userId);
        output.setToken(token);
        output.setUserName(userName);
        return output;
    }

}
