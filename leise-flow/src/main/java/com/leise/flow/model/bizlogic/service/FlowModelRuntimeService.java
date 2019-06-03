package com.leise.flow.model.bizlogic.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.leise.flow.model.bizlogic.entity.FlowModelRuntime;

/**
 * Created by JY-IT-D001 on 2018/7/2.
 */
@Repository
public class FlowModelRuntimeService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String FIND_BY_BIZKEY_SQL = "select t.id, t.module_id, t.flow_id, t.flow_version, t.flow_model, t.version, t.valid_status, t.create_time, t.modify_time from flow_model_runtime t where t.valid_status = 1 and t.flow_id = :flowId and t.flow_version = :flowVersion and t.module_id = :moduleId";

    private final String INSERT = "insert into flow_model_runtime (module_id, flow_id, flow_version, flow_model, version) values (:moduleId, :flowId, :flowVersion, :flowModel, :version)";

    private final String UPDATE_BY_BIZKEY_SQL = "update flow_model_runtime set flow_model =:flowModel, version =:version where t.valid_status = 1 and t.flow_id = :flowId and t.flow_version = :flowVersion and t.module_id = :moduleId";

    public FlowModelRuntime findByBizkey(String moduleId, String flowId, String flowVersion) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("moduleId", moduleId);
        params.put("flowId", flowId);
        params.put("flowVersion", flowVersion);
        try {
            return namedParameterJdbcTemplate.queryForObject(FIND_BY_BIZKEY_SQL, params, new BeanPropertyRowMapper<>(
                    FlowModelRuntime.class));
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void insert(Map<String, Object> params) {
        namedParameterJdbcTemplate.update(INSERT, params);
    }

    public void updateByBizkey(Map<String, Object> params) {
        namedParameterJdbcTemplate.update(UPDATE_BY_BIZKEY_SQL, params);
    }

}
