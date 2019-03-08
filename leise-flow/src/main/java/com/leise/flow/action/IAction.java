package com.leise.flow.action;

import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionResultEnum;

/**
 * Created by JY-IT-D001 on 2018/6/26.
 */
public interface IAction extends Cloneable {

    ActionResultEnum execute(FlowContext context);

    IAction clone();

    void validate();
}
