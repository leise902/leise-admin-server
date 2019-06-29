package com.leise.flow.action.flow;

import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;

/**
 * @author leise
 * @date 2018年6月29日 下午5:10:12
 * @classname: StartAction
 * @description: 开始动作
 */
@Action(id = "startAction", name = "开始", group = ActionGroupEnum.FLOW_START)
public class StartAction implements IAction {

    @Override
    public ActionResultEnum execute(FlowContext context) {
        return ActionResultEnum.SUCCESS;
    }

    @Override
    public StartAction clone() {
        try {
            return (StartAction) super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void validate() {

    }
}
