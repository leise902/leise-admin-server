package com.leise.flow.action.database;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.leise.flow.action.IJdbcAction;
import com.leise.flow.util.ActionUtils;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by JY-IT-D001 on 2018/6/26.
 */
@Action(id = "pageQueryAction", name = "分页查询", group = ActionGroupEnum.DATABASE)
public class PageQueryAction implements IJdbcAction {

    private final static Logger LOG = LoggerFactory.getLogger(PageQueryAction.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @ActionProperty
    private String pageSql;

    @ActionProperty
    private String countSql;

    @ActionProperty
    private String queryCount;

    @ActionProperty
    private String outParamNames;

    @ActionProperty
    private String listName;

    private int pageNum = 1;

    private int pageSize = 10;

    private final static long LIMIT_SIZE = 300000L;

    @Override
    public ActionResultEnum execute(FlowContext context) {

        Integer pageNum = Optional.ofNullable((Integer) context.get("pageNum")).orElse(this.pageNum);
        Integer pageSize = Optional.ofNullable((Integer) context.get("pageSize")).orElse(this.pageSize);

        Integer limit = (pageNum - 1) * pageSize;
        context.put("limit", limit);
        context.put("offset", pageSize);

        Long queryLimitSize = Long.valueOf(pageNum * pageSize);
        List<Map<String, Object>> dataList = Lists.newArrayList();
        Long total = 0L;
        if (LIMIT_SIZE <= queryLimitSize) {
            String tempPageSql = this.pageSql.replaceFirst("select|Select", "select sql_calc_found_rows");
            String tempCountSql = "select found_rows()";
            dataList = this.queryDataList(context, tempPageSql);
            total = namedParameterJdbcTemplate.queryForObject(tempCountSql, Maps.newHashMap(), Long.class);
            context.put("total", total);
            if (total <= 0) {
                LOG.warn("o_0[没有查询到数据记录]");
                return ActionResultEnum.NODATA;
            }
        } else {
            if (Boolean.parseBoolean(this.queryCount)) {
                total = this.queryTotal(context, this.countSql);
                context.put("total", total);
                if (total <= 0) {
                    LOG.warn("o_0[没有查询到数据记录]");
                    return ActionResultEnum.NODATA;
                }
            }
            dataList = this.queryDataList(context, this.pageSql);
        }

        if (CollectionUtils.isEmpty(dataList)) {
            LOG.warn("o_0[没有查询到数据记录]");
            return ActionResultEnum.NODATA;
        }

        // 转换输出参数
        String[] outParamNameArray = ActionUtils.split(this.outParamNames);
        List<Map<String, Object>> result = ActionUtils.filterParamsToList(dataList, outParamNameArray);
        context.put(this.listName, result);

        return ActionResultEnum.SUCCESS;
    }

    private Long queryTotal(FlowContext context, String countSql) {
        Map<String, Object> inParamValueCountMap = ActionUtils.parseSqlParams(countSql, context, true);
        // 查询总记录数
        LOG.info("[查询记录数sql]:{}, [输入参数]:{}", this.countSql, inParamValueCountMap);
        Long total = namedParameterJdbcTemplate.queryForObject(this.countSql, inParamValueCountMap, Long.class);
        LOG.info("[查询记录数]:{}", total);
        return total;
    }

    private List<Map<String, Object>> queryDataList(FlowContext context, String pageSql) {
        // 获取SQL输入参数
        Map<String, Object> inParamValueMap = ActionUtils.parseSqlParams(pageSql, context, true);
        LOG.info("[查询分页数据sql]:{}, [输入参数]:{}", pageSql, inParamValueMap);
        List<Map<String, Object>> dataList = namedParameterJdbcTemplate.queryForList(pageSql, inParamValueMap);
        LOG.debug("[查询分页数据结果]:{}", JSON.toJSONString(dataList));
        return dataList;
    }

    public String getOutParamNames() {
        return outParamNames;
    }

    public void setOutParamNames(String outParamNames) {
        this.outParamNames = outParamNames;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageSql() {
        return pageSql;
    }

    public void setPageSql(String pageSql) {
        this.pageSql = pageSql;
    }

    public void setCountSql(String countSql) {
        this.countSql = countSql;
    }

    public void setQueryCount(String queryCount) {
        this.queryCount = queryCount;
    }

    @Override
    public PageQueryAction clone() {
        try {
            return (PageQueryAction) super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void validate() {
        // 参数验证
        Assert.isTrue(StringUtils.isNotEmpty(this.outParamNames), "配置项[outParamNames]未进行配置，请检查配置信息");
        if (Boolean.parseBoolean(this.queryCount)) {
            Assert.isTrue(StringUtils.isNotEmpty(this.countSql), "配置项[countSql]未进行配置，请检查配置信息");
        }
        Assert.isTrue(StringUtils.isNotEmpty(this.listName), "配置项[listName]未进行配置，请检查配置信息");
    }
}
