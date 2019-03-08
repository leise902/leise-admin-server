package com.leise.provider.admin.model.access.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leise.provider.admin.model.access.entity.RoleUserRef;
import com.leise.provider.admin.model.access.mapper.RoleUserRefMapper;

@Component
public class RoleUserRefService {

    @Autowired
    private RoleUserRefMapper mapper;

    public List<RoleUserRef> searchByUserId(Long userId) {
        // TODO Auto-generated method stub
        return mapper.searchByUserId(userId);
    }

}
