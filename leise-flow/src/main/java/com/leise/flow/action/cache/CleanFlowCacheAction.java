package com.leise.flow.action.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.cache.FlowCacheKey;
import com.leise.flow.cache.FlowLocalCacheRegister;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;

/**
 * @author leise
 * @date 2018年6月29日 下午5:04:20
 * @classname: CleanFlowCacheAction
 * @description: 清除本地流程缓存数据
 */
@Action(id = "clearFlowCacheAction", name = "清除流程缓存", group = ActionGroupEnum.CACHE)
public class CleanFlowCacheAction implements IAction {

    // TODO
    @Autowired
    private FlowLocalCacheRegister flowLocalCacheRegister;

    @ActionProperty
    private String moduleIdField;

    @ActionProperty
    private String flowIdField;

    @ActionProperty
    private String flowVersionField;

    @Override
    public ActionResultEnum execute(FlowContext context) {
        String moduleId = (String) context.get(this.moduleIdField);
        String flowId = (String) context.get(this.flowIdField);
        String flowVersion = (String) context.get(this.flowVersionField);
        Assert.isTrue(StringUtils.isNoneEmpty(new String[] { moduleId, flowId, flowVersion }), "请检查缓存KEY数据项, 存在为空的参数");
        FlowCacheKey flowCacheKey = new FlowCacheKey(moduleId, flowId, flowVersion);
        flowLocalCacheRegister.flowLoadingCache.invalidate(flowCacheKey);
        return ActionResultEnum.SUCCESS;
    }

    @Override
    public CleanFlowCacheAction clone() {
        try {
            return (CleanFlowCacheAction) super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void validate() {
        // 参数验证
        Assert.isTrue(StringUtils.isNotEmpty(this.moduleIdField), "配置项[moduleId]未进行配置，请检查配置信息");
        Assert.isTrue(StringUtils.isNotEmpty(this.flowIdField), "配置项[flowId]未进行配置，请检查配置信息");
        Assert.isTrue(StringUtils.isNotEmpty(this.flowVersionField), "配置项[flowVersion]未进行配置，请检查配置信息");
    }

    public String getModuleIdField() {
        return moduleIdField;
    }

    public void setModuleIdField(String moduleIdField) {
        this.moduleIdField = moduleIdField;
    }

    public String getFlowIdField() {
        return flowIdField;
    }

    public void setFlowIdField(String flowIdField) {
        this.flowIdField = flowIdField;
    }

    public String getFlowVersionField() {
        return flowVersionField;
    }

    public void setFlowVersionField(String flowVersionField) {
        this.flowVersionField = flowVersionField;
    }
}
