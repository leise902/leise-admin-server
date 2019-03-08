package com.leise.provider.admin.model.sysmanage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leise.provider.admin.model.sysmanage.entity.SysMenuInfo;
import com.leise.provider.admin.model.sysmanage.mapper.SysMenuInfoMapper;

@Component
public class SysMenuInfoService {

    @Autowired
    private SysMenuInfoMapper mapper;

    public List<SysMenuInfo> searchByMenuIds(List<Long> menuIds) {
        // TODO Auto-generated method stub
        return mapper.searchByMenuIds(menuIds);
    }

}
