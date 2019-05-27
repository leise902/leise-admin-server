package com.leise.flow.model.bizlogic.service;

import com.google.common.collect.Maps;
import com.leise.flow.model.bizlogic.entity.ModuleInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by JY-IT-D001 on 2018/7/2.
 */
@Repository
public class ModuleInfoService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String FIND_BY_MODULE_ID = "select * from module_info t where t.valid_status = 1 and t.module_id = :moduleId";

    private final String FIND_BY_ID = "select * from module_info t where t.valid_status = 1 and t.id = :moduleInfoId";

    private final String INSERT = "insert into module_info (module_id, module_name) values (:moduleId, :moduleName)";

    public ModuleInfo findByModuleId(String moduleId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("moduleId", moduleId);
        try {
            return namedParameterJdbcTemplate.queryForObject(FIND_BY_MODULE_ID, params, new BeanPropertyRowMapper<>(
                    ModuleInfo.class));
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public ModuleInfo findById(long moduleInfoId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("moduleInfoId", moduleInfoId);
        try {
            return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, params, new BeanPropertyRowMapper<>(
                    ModuleInfo.class));
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void insert(String moduleId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("moduleId", moduleId);
        params.put("moduleName", moduleId);
        namedParameterJdbcTemplate.update(INSERT, params);
    }
}
