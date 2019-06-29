package com.leise.flow.action.operation;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;
import com.leise.flow.exception.ActionException;

/**
 * 
 * @author leise
 * @date 2018年6月29日 下午5:12:03
 * @classname: GenUuidAction
 * @description: 生成UUID流水号
 */
@Action(id = "GenUuidAction", name = "生成UUID", group = ActionGroupEnum.OPERATION)
public class GenUuidAction implements IAction {

    @ActionProperty
    private String uuidField;

    @Override
    public ActionResultEnum execute(FlowContext context) {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        context.put(this.uuidField, uuid);
        return ActionResultEnum.SUCCESS;
    }

    @Override
    public IAction clone() {
        try {
            return (GenUuidAction) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new ActionException(800000, "action clone inner error", e);
        }
    }

    @Override
    public void validate() {
        // 参数验证
        Assert.isTrue(StringUtils.isNotEmpty(this.uuidField), "配置项[uuidField]未进行配置，请检查配置信息");
    }

    public String getUuidField() {
        return uuidField;
    }

    public void setUuidField(String uuidField) {
        this.uuidField = uuidField;
    }
}
