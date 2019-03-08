package com.leise.flow.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import com.google.common.collect.Maps;
import com.leise.flow.context.FlowContext;
import com.leise.flow.exception.ActionException;

/**
 * Created by JY-IT-D001 on 2018/7/20.
 */
public class ActionUtils {

    private final static Logger LOG = LoggerFactory.getLogger(ActionUtils.class);

    private final static String PARAMS_SEPARATOR = "||";

    public final static String[] split(String params) {
        return StringUtils.split(params, PARAMS_SEPARATOR);
    }

    public static Map<String, Object> parseSqlParams(String sql, FlowContext context) {
        return parseSqlParams(sql, context, false);
    }

    public final static Map<String, Object> parseSqlParams(String sql, FlowContext context, boolean convertNullToEmpty) {
        ParsedSql parseSql = NamedParameterUtils.parseSqlStatement(sql);
        List<SqlParameter> sqlParameterList = NamedParameterUtils.buildSqlParameterList(parseSql,
                new MapSqlParameterSource());
        Map<String, Object> paramsMap = Maps.newHashMap();
        if (CollectionUtils.isEmpty(sqlParameterList)) {
            return paramsMap;
        }
        Boolean isThrowException = Boolean.FALSE;
        for (SqlParameter sqlParameter : sqlParameterList) {
            String paramName = sqlParameter.getName();
            if (!context.containsKey(paramName)) {
                LOG.error("参数[{}]在context中未定义，请检查数据定义相关配置", paramName);
                isThrowException = Boolean.TRUE;
                continue;
            }
            Object paramValue = context.get(paramName);
            if (paramValue instanceof String) {
                String paramValueStr = (String) paramValue;
                if (StringUtils.isEmpty(paramValueStr) || StringUtils.equalsIgnoreCase(paramValueStr, "null")) {
                    paramValue = null;
                }
            }
            paramsMap.put(paramName, paramValue);
        }

        if (isThrowException) {
            LOG.error("[解析Sql]:{}", sql);
            throw new ActionException("参数不正确，请检查参数列表");
        }
        return paramsMap;
    }

    public static SqlParameterSource[] parseBatchSqlParams(String sql, List<Map<String, Object>> paramValueList) {

        if (CollectionUtils.isEmpty(paramValueList)) {
            LOG.warn("参数值列表为空，请检查参数值列表");
            return null;
        }
        ParsedSql parseSql = NamedParameterUtils.parseSqlStatement(sql);
        List<SqlParameter> sqlParameterList = NamedParameterUtils.buildSqlParameterList(parseSql,
                new MapSqlParameterSource());
        if (CollectionUtils.isEmpty(sqlParameterList)) {
            return null;
        }
        String[] containParamNames = sqlParameterList.stream().toArray(String[]::new);
        List<Map<String, Object>> list = filterParamsToList(paramValueList, containParamNames);
        SqlParameterSource[] sources = SqlParameterSourceUtils.createBatch(list);
        return sources;
    }

    public final static Map<String, Object> filterParamsToMap(Map<String, Object> paramsMap, String[] containParamNames) {
        return paramsMap.keySet().stream()
                .filter(key -> Arrays.asList(containParamNames).contains(key) && paramsMap.get(key) != null)
                .collect(Collectors.toMap(key -> key, key -> paramsMap.get(key)));
    }

    public final static List<Map<String, Object>> filterParamsToList(List<Map<String, Object>> dataList,
            String[] containParamNames) {
        return dataList.stream().map(map -> filterParamsToMap(map, containParamNames)).collect(Collectors.toList());
    }

}
