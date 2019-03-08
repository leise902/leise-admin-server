package com.leise.provider.admin.model.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leise.provider.admin.model.user.entity.UserInfo;
import com.leise.provider.admin.model.user.mapper.UserInfoMapper;

@Component
public class UserInfoService {

    @Autowired
    private UserInfoMapper mapper;

    public UserInfo selectByPrimaryKey(Long userId) {
        return mapper.selectByPrimaryKey(userId);
    }

}
