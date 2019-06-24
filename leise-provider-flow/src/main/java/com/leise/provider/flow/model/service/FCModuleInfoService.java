package com.leise.provider.flow.model.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.leise.flow.model.service.ModuleInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by JY-IT-D001 on 2018/8/7.
 */
@Repository
public class FCModuleInfoService extends ModuleInfoService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SEARCH = "select t.module_id as moduleId, t.module_name as moduleName from module_info t where t.valid_status = 1";

    public List<Map<String, Object>> search() {
        try {
            return namedParameterJdbcTemplate.queryForList(SEARCH, Maps.newHashMap());
        }
        catch (DataAccessException e) {
            return null;
        }
    }
}
