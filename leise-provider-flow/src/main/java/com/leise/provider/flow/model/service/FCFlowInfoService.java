package com.leise.provider.flow.model.service;

import com.google.common.collect.Maps;
import com.leise.flow.model.service.FlowInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by JY-IT-D001 on 2018/7/16.
 */
@Repository
public class FCFlowInfoService extends FlowInfoService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String FIND_BY_ID_SQL = "select t.id as flowInfoId, t.flow_id as flowId, t.flow_name as flowName, t.flow_version as flowVersion, t.module_id as moduleId, t.request_url as requestUrl, t.status as status, t.create_time as createTime, t.modify_time as modifyTime from flow_info t  where t.id = :flowInfoId and t.valid_status = 1";

    private final String INSERT_SQL = "insert into flow_info ( flow_id, flow_name, flow_version, module_id, request_url) values (:flowId, :flowName, :flowVersion, :moduleId, :requestUrl)";

    private final String UPDATE_BY_ID_SQL = "update flow_info set flow_id = :flowId, flow_name = :flowName, flow_version = :flowVersion, module_id = :moduleId, request_url = :requestUrl where id = :flowInfoId and valid_status = 1";

    private final String SEARCH = "select t.id as flowInfoId, t.flow_id as flowId, t.flow_name as flowName, t.flow_version as flowVersion, t.module_id as moduleId, m.module_name as moduleName from flow_info t, module_info m  where m.module_id=t.module_id and t.valid_status = 1";

    public long insert(Map<String, Object> params) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT_SQL, new MapSqlParameterSource(params), keyHolder,
                new String[] { "id" });
        return keyHolder.getKey() == null ? -1 : keyHolder.getKey().longValue();
    }

    public void update(Map<String, Object> params) {
        namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, params);
    }

    public Map<String, Object> findById(long flowInfoId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("flowInfoId", flowInfoId);
        try {
            return namedParameterJdbcTemplate.queryForMap(FIND_BY_ID_SQL, params);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Map<String, Object>> search() {
        try {
            return namedParameterJdbcTemplate.queryForList(SEARCH, Maps.newHashMap());
        }
        catch (DataAccessException e) {
            return null;
        }
    }

}
