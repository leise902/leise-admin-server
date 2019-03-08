package com.leise.provider.admin.model.sysmanage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.leise.provider.admin.model.sysmanage.entity.SysMenuInfo;

@Mapper
public interface SysMenuInfoMapper {

    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenuInfo record);

    int insertSelective(SysMenuInfo record);

    SysMenuInfo selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenuInfo record);

    int updateByPrimaryKey(SysMenuInfo record);

    List<SysMenuInfo> searchByMenuIds(List<Long> menuIds);
}