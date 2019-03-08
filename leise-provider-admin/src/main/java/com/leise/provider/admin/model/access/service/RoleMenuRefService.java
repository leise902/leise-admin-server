package com.leise.provider.admin.model.access.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leise.provider.admin.model.access.entity.RoleMenuRef;
import com.leise.provider.admin.model.access.mapper.RoleMenuRefMapper;

@Component
public class RoleMenuRefService {

    @Autowired
    RoleMenuRefMapper mapper;

    public List<RoleMenuRef> searchByRoleIds(List<Long> roleIds) {
        return mapper.searchByRoleIds(roleIds);
    }

}
