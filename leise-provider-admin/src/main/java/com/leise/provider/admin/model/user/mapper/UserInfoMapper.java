package com.leise.provider.admin.model.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.leise.provider.admin.model.user.entity.UserInfo;

@Mapper
public interface UserInfoMapper {

    int deleteByPrimaryKey(Long userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}