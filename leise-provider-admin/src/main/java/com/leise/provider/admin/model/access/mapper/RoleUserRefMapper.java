package com.leise.provider.admin.model.access.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.leise.provider.admin.model.access.entity.RoleUserRef;

@Mapper
public interface RoleUserRefMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RoleUserRef record);

    int insertSelective(RoleUserRef record);

    RoleUserRef selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleUserRef record);

    int updateByPrimaryKey(RoleUserRef record);

    List<RoleUserRef> searchByUserId(Long userId);
}