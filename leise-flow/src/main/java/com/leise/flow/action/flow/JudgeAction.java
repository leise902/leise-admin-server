package com.leise.flow.action.flow;

import com.leise.flow.action.IJumpAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;

/**
 * Created by JY-IT-D001 on 2018/6/26.
 */
@Action(id = "JudgeAction", name = "判断条件", group = ActionGroupEnum.FLOW_JUMP)
public class JudgeAction implements IJumpAction {

    @Override
    public ActionResultEnum execute(FlowContext context) {
        return ActionResultEnum.valueOf((String) context.get("actionResult"));
    }

    @Override
    public JudgeAction clone() {
        try {
            return (JudgeAction) super.clone();
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
