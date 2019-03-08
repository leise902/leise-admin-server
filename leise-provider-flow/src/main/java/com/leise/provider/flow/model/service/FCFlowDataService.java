package com.leise.provider.flow.model.service;

import com.google.common.collect.Maps;
import com.leise.flow.model.bizlogic.entity.FlowData;
import com.leise.flow.model.bizlogic.service.FlowDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by JY-IT-D001 on 2018/7/19.
 */
@Repository
public class FCFlowDataService extends FlowDataService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SEARCH_PAGE_TOTAL_BY_FLOW_INFO_ID_SQL = "select count(*) from flow_data t where t.valid_status = 1 and t.flow_info_id = :flowInfoId";

    private final String SEARCH_PAGE_BY_FLOW_INFO_ID_SQL = "select id, flow_info_id, data_code, data_name, input_flag, output_flag, collect_flag, collect_data_codes from flow_data t where t.valid_status = 1 and t.flow_info_id = :flowInfoId limit :limit, :offset";

    private final String INSERT_SQL = "insert into flow_data (flow_info_id, data_code, data_name, input_flag, output_flag, collect_flag, collect_data_codes) values (:flowInfoId, :dataCode, :dataName, :inputFlag, :outputFlag, :collectFlag, :collectDataCodes)";

    private final String UPDATE_BY_ID_SQL = "update flow_data set data_code = :dataCode, data_name = :dataName, input_flag = :inputFlag, output_flag = :outputFlag, collect_flag = :collectFlag, collect_data_codes = :collectDataCodes where id = :flowDataId and valid_status = 1";

    private final String DELETE_BY_ID_SQL = "delete from flow_data where id = :flowDataId";

    private final String FIND_BY_ID = "select * from flow_data where id = :flowDataId";

    public List<FlowData> searchPageByFlowInfoId(long flowInfoId, int pageNum, int pageSize){
        Map<String, Object> params = Maps.newHashMap();
        params.put("flowInfoId", flowInfoId);
        params.put("limit", (pageNum-1)*pageSize);
        params.put("offset", pageSize);
        return namedParameterJdbcTemplate.query(SEARCH_PAGE_BY_FLOW_INFO_ID_SQL, params, new BeanPropertyRowMapper<>(FlowData.class));
    }

    public Long searchPageTotalByFlowInfoId(long flowInfoId){
        Map<String, Object> params = Maps.newHashMap();
        params.put("flowInfoId", flowInfoId);
        return namedParameterJdbcTemplate.queryForObject(SEARCH_PAGE_TOTAL_BY_FLOW_INFO_ID_SQL, params, Long.class);
    }

    public FlowData findById(long flowDataId) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("flowDataId", flowDataId);
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, params, new BeanPropertyRowMapper<>(FlowData.class));
    }

    public void insert(Map params) {
        namedParameterJdbcTemplate.update(INSERT_SQL, params);
    }

    public int updateById(Map params) {
        int count = namedParameterJdbcTemplate.update(UPDATE_BY_ID_SQL, params);
        return count;
    }

    public int deleteById(long flowDataId) {
        Map<String, Long> params = Maps.newHashMap();
        params.put("flowDataId", flowDataId);
        int count = namedParameterJdbcTemplate.update(DELETE_BY_ID_SQL, params);
        return count;
    }
}
