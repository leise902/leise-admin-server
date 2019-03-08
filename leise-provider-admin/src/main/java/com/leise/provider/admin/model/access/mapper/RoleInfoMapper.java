package com.leise.provider.admin.model.access.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.leise.provider.admin.model.access.entity.RoleInfo;

@Mapper
public interface RoleInfoMapper {

    int deleteByPrimaryKey(Long roleId);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);
}