package com.leise.flow.action.flow;

import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;

/**
 * Created by JY-IT-D001 on 2018/6/26.
 */
@Action(id = "endAction", name = "结束", group = ActionGroupEnum.FLOW_END)
public class EndAction implements IAction {

    @Override
    public ActionResultEnum execute(FlowContext context) {
        return ActionResultEnum.SUCCESS;
    }

    @Override
    public EndAction clone() {
        try {
            return (EndAction) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public void validate() {

    }
}
