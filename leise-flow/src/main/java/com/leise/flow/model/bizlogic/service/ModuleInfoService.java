package com.leise.flow.model.bizlogic.service;

import com.google.common.collect.Maps;
import com.leise.flow.model.bizlogic.entity.ModuleInfo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ModuleInfo findByModuleId(String moduleId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("moduleId", moduleId);
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_MODULE_ID, params, new BeanPropertyRowMapper<>(
                ModuleInfo.class));
    }

    public ModuleInfo findById(long moduleInfoId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("moduleInfoId", moduleInfoId);
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, params, new BeanPropertyRowMapper<>(
                ModuleInfo.class));
    }
}
