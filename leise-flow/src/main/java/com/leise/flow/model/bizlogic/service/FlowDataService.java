package com.leise.flow.model.bizlogic.service;

import com.google.common.collect.Maps;
import com.leise.flow.model.bizlogic.entity.FlowData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by JY-IT-D001 on 2018/7/2.
 */
@Repository
public class FlowDataService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SEARCH_BY_FLOW_INFO_ID_SQL = "select id, flow_info_id, data_code, data_name, input_flag, output_flag, collect_flag, collect_data_codes from flow_data t where t.valid_status = 1 and t.flow_info_id = :flowInfoId";

    public List<FlowData> searchByFlowInfoId(long flowInfoId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("flowInfoId", flowInfoId);
        return namedParameterJdbcTemplate.query(SEARCH_BY_FLOW_INFO_ID_SQL, params, new BeanPropertyRowMapper<>(
                FlowData.class));
    }

}
