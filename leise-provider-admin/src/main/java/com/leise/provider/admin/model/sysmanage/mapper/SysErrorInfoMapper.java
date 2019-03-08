package com.leise.provider.admin.model.sysmanage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.leise.provider.admin.model.sysmanage.entity.SysErrorInfo;

@Mapper
public interface SysErrorInfoMapper {

    int deleteByPrimaryKey(Long errorId);

    int insert(SysErrorInfo record);

    int insertSelective(SysErrorInfo record);

    SysErrorInfo selectByPrimaryKey(Long errorId);

    int updateByPrimaryKeySelective(SysErrorInfo record);

    int updateByPrimaryKey(SysErrorInfo record);
}