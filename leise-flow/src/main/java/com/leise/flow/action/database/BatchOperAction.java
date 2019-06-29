package com.leise.flow.action.database;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.leise.flow.action.IJdbcAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;
import com.leise.flow.exception.ActionException;
import com.leise.flow.util.ActionUtils;

/**
 * 
 * @author leise
 * @date 2018年6月29日 下午5:05:52
 * @classname: BatchOperAction
 * @description: 数据库批量操作
 */
@Action(id = "BatchOperAction", name = "批量操作", group = ActionGroupEnum.DATABASE)
@Transactional(rollbackFor = { ActionException.class, RuntimeException.class, Exception.class })
public class BatchOperAction implements IJdbcAction {

    private final static Logger LOG = LoggerFactory.getLogger(BatchOperAction.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @ActionProperty
    private String listName;

    @ActionProperty
    private String operSql;

    @ActionProperty
    private String newTransation;

    @Override
    public ActionResultEnum execute(FlowContext context) {

        List<Map<String, Object>> paramValueList = (List<Map<String, Object>>) context.get(this.listName);

        // 获取SQL输入参数
        SqlParameterSource[] paramValues = ActionUtils.parseBatchSqlParams(this.operSql, paramValueList);
        LOG.info("[输入参数数量]:{}", paramValues.length);

        // 执行SQL语句并对结果进行处理
        try {
            LOG.info("[执行sql]:{}", this.operSql);
            int[] operCount = namedParameterJdbcTemplate.batchUpdate(this.operSql, paramValues);
            LOG.info("[执行操作记录数量]:{}", operCount);
            if (operCount.length == 0) {
                return ActionResultEnum.NODATA;
            }
        }
        catch (DataAccessException e) {
            LOG.error("@_@[数据库操作异常]:", e);
            throw new ActionException(800003, "数据库操作异常", e);
        }
        catch (Exception e) {
            LOG.error("@_@[其他类型异常]:", e);
            throw new ActionException(800004, "数据库操作未知异常", e);
        }

        return ActionResultEnum.SUCCESS;
    }

    public String getOperSql() {
        return operSql;
    }

    public void setOperSql(String operSql) {
        this.operSql = operSql;
    }

    public String getNewTransation() {
        return newTransation;
    }

    public void setNewTransation(String newTransation) {
        this.newTransation = newTransation;
    }

    @Override
    public BatchOperAction clone() {
        try {
            return (BatchOperAction) super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void validate() {
        // 参数验证
        Assert.isTrue(StringUtils.isNotEmpty(this.operSql), "配置项[operSql]未进行配置，请检查配置信息");
        Assert.isTrue(StringUtils.isNotEmpty(this.listName), "配置项[listName]未进行配置，请检查配置信息");
    }
}
