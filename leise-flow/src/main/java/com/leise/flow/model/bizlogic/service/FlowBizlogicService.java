package com.leise.flow.model.bizlogic.service;

import com.google.common.collect.Maps;
import com.leise.flow.model.bizlogic.entity.FlowBizlogic;
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
public class FlowBizlogicService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String FIND_BY_FLOW_INFO_ID_SQL = "select t.id, t.flow_info_id, t.bizlogic, t.valid_status, t.create_time, t.modify_time from flow_bizlogic t where t.valid_status = 1 and t.flow_info_id = :flowInfoId";

    public FlowBizlogic findByFlowInfoId(long flowInfoId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("flowInfoId", flowInfoId);
        try {
            return namedParameterJdbcTemplate.queryForObject(FIND_BY_FLOW_INFO_ID_SQL, params,
                    new BeanPropertyRowMapper<>(FlowBizlogic.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
