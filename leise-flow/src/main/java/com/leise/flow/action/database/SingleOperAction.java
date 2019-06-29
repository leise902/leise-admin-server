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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author leise
 * @date 2018年6月29日 下午5:07:41
 * @classname: SingleOperAction
 * @description: 数据库单笔操作
 */
@Action(id = "singleOperAction", name = "单笔操作", group = ActionGroupEnum.DATABASE)
@Transactional(rollbackFor = { ActionException.class, RuntimeException.class, Exception.class })
public class SingleOperAction implements IJdbcAction {

    private final static Logger LOG = LoggerFactory.getLogger(SingleOperAction.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @ActionProperty
    private String operSql;

    @ActionProperty
    private String newTransation;

    @Override
    public ActionResultEnum execute(FlowContext context) {

        // 获取SQL输入参数
        Map<String, Object> inParamValueMap = ActionUtils.parseSqlParams(this.operSql, context);
        LOG.info("[输入参数[:{}", inParamValueMap);

        // 执行SQL语句并对结果进行处理
        try {
            LOG.info("[执行sql]:{}", this.operSql);
            int operCount = namedParameterJdbcTemplate.update(this.operSql, inParamValueMap);
            LOG.info("[执行操作记录数量]:{}", operCount);
            if (operCount == 0) {
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
    public SingleOperAction clone() {
        try {
            return (SingleOperAction) super.clone();
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
    }
}
