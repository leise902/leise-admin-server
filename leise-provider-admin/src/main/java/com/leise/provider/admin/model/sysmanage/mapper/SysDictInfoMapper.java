package com.leise.provider.admin.model.sysmanage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.leise.provider.admin.model.sysmanage.entity.SysDictInfo;

@Mapper
public interface SysDictInfoMapper {

    int deleteByPrimaryKey(Long dictId);

    int insert(SysDictInfo record);

    int insertSelective(SysDictInfo record);

    SysDictInfo selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(SysDictInfo record);

    int updateByPrimaryKey(SysDictInfo record);
}