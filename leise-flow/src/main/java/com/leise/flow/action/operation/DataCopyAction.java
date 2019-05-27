package com.leise.flow.action.operation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;
import com.leise.flow.exception.ActionException;
import com.leise.flow.util.ActionUtils;

/**
 * Created by JY-IT-D001 on 2018/7/26.
 */
@Action(id = "DataCopyAction", name = "复制数据", group = ActionGroupEnum.OPERATION)
public class DataCopyAction implements IAction {

    @ActionProperty
    private String sourceNames;

    @ActionProperty
    private String targetNames;

    @Override
    public ActionResultEnum execute(FlowContext context) {
        String[] sources = ActionUtils.split(this.sourceNames);
        String[] targets = ActionUtils.split(this.targetNames);
        Assert.isTrue(sources.length == targets.length, "源数据与目标数据个数不一致，请检查配置信息");
        for (int i = 0; i < sources.length; i++) {
            context.put(targets[i], context.get(sources[i]));
        }
        return ActionResultEnum.SUCCESS;
    }

    public String getSourceNames() {
        return sourceNames;
    }

    public void setSourceNames(String sourceNames) {
        this.sourceNames = sourceNames;
    }

    public String getTargetNames() {
        return targetNames;
    }

    public void setTargetNames(String targetNames) {
        this.targetNames = targetNames;
    }

    @Override
    public DataCopyAction clone() {
        try {
            return (DataCopyAction) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new ActionException(800000, "action clone inner error", e);
        }
    }

    @Override
    public void validate() {
        // 参数验证
        Assert.isTrue(StringUtils.isNotEmpty(this.sourceNames), "配置项[sourceNames]未进行配置，请检查配置信息");
        Assert.isTrue(StringUtils.isNotEmpty(this.targetNames), "配置项[targetNames]未进行配置，请检查配置信息");
    }
}
