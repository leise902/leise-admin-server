package com.leise.flow.action.database;

import com.leise.flow.action.IJdbcAction;
import com.leise.flow.util.ActionUtils;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;
import com.leise.flow.exception.ActionException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by JY-IT-D001 on 2018/6/26.
 */
@Action(id = "singleQueryAction", name = "单笔查询", group = ActionGroupEnum.DATABASE)
public class SingleQueryAction implements IJdbcAction {

    private final static Logger LOG = LoggerFactory.getLogger(SingleQueryAction.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @ActionProperty
    private String querySql;

    @ActionProperty
    private String outParamNames;

    @Override
    public ActionResultEnum execute(FlowContext context) {

        // 获取SQL输入参数
        Map<String, Object> inParamValueMap = ActionUtils.parseSqlParams(this.querySql, context);
        LOG.info("[输入参数]:{}", inParamValueMap);

        // 执行SQL语句并对结果进行输出
        String[] outParamNameArray = ActionUtils.split(this.outParamNames);
        try {
            LOG.info("[执行sql]:{}", this.querySql);
            Map<String, Object> outParamValueMap = namedParameterJdbcTemplate.queryForMap(this.querySql,
                    inParamValueMap);
            Map<String, Object> result = ActionUtils.filterParamsToMap(outParamValueMap, outParamNameArray);
            LOG.info("[输出参数结果]:{}", result);
            context.putAll(result);
        }
        catch (EmptyResultDataAccessException e) {
            LOG.warn("!!![没有查询到数据记录]");
            return ActionResultEnum.NODATA;
        }
        catch (DataAccessException e) {
            LOG.error("@_@[数据库查询异常]:", e);
            throw new ActionException(800001, "数据库查询异常", e);
        }
        catch (Exception e) {
            LOG.error("@_@[其他类型异常]:", e);
            throw new ActionException(800002, "数据库查询未知异常", e);
        }
        return ActionResultEnum.SUCCESS;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public String getOutParamNames() {
        return outParamNames;
    }

    public void setOutParamNames(String outParamNames) {
        this.outParamNames = outParamNames;
    }

    @Override
    public SingleQueryAction clone() {
        try {
            return (SingleQueryAction) super.clone();
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
        Assert.isTrue(StringUtils.isNotEmpty(this.querySql), "配置项[querySql]未进行配置，请检查配置信息");
    }
}
