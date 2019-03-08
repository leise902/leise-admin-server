package com.leise.flow.model.bizlogic.service;

import com.google.common.collect.Maps;
import com.leise.flow.model.bizlogic.entity.FlowInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by JY-IT-D001 on 2018/7/2.
 */
@Repository
public class FlowInfoService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SEARCH_BY_MODULE_ID_SQL = "select * from flow_info t where t.valid_status = 1 and t.module_id = :moduleId";

    private final String FIND_BY_BIZKEY_SQL = "select * from flow_info t where t.valid_status = 1 and t.module_id = :moduleId and t.flow_id = :flowId and t.flow_version = :flowVersion";

    public List<FlowInfo> searchByModuleId(String moduleId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("moduleId", moduleId);
        return namedParameterJdbcTemplate.query(SEARCH_BY_MODULE_ID_SQL, params, new BeanPropertyRowMapper<>(
                FlowInfo.class));
    }

    public FlowInfo findByBizKey(String moduleId, String flowId, String flowVersion) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("moduleId", moduleId);
        params.put("flowId", flowId);
        params.put("flowVersion", flowVersion);
        try {
            return namedParameterJdbcTemplate.queryForObject(FIND_BY_BIZKEY_SQL, params, new BeanPropertyRowMapper<>(
                    FlowInfo.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
