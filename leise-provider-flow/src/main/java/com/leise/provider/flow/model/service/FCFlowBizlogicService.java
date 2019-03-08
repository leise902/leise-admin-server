package com.leise.provider.flow.model.service;

import com.leise.flow.model.bizlogic.service.FlowBizlogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by JY-IT-D001 on 2018/7/16.
 */
@Repository
public class FCFlowBizlogicService extends FlowBizlogicService{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String INSERT_SQL = "insert into flow_bizlogic ( flow_info_id, bizlogic ) values (:flowInfoId, :bizlogic )";

    private final String UPDATE_BY_FLOW_INFO_ID_SQL = "update flow_bizlogic set bizlogic = :bizlogic where flow_info_id = :flowInfoId and valid_status = 1";
    
    public void insert(Map<String, Object> params) {
        namedParameterJdbcTemplate.update(INSERT_SQL, new MapSqlParameterSource(params));
    }

    public void saveOrUpdate(Map<String, Object> params) {
        int count = namedParameterJdbcTemplate.update(UPDATE_BY_FLOW_INFO_ID_SQL, params);
        if(count == 0){
            insert(params);
        }
    }
}
