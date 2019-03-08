package com.leise.provider.admin.model.access.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.leise.provider.admin.model.access.entity.RoleMenuRef;

@Mapper
public interface RoleMenuRefMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RoleMenuRef record);

    int insertSelective(RoleMenuRef record);

    RoleMenuRef selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleMenuRef record);

    int updateByPrimaryKey(RoleMenuRef record);

    List<RoleMenuRef> searchByRoleIds(List<Long> list);
}