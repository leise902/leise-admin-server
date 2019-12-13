package com.leise.flow.action;

import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionResultEnum;

/**
 * @author leise
 * @date 2018年6月29日 下午4:59:45
 * @classname: IAction
 * @description: 执行动作的接口类
 */
public interface IAction extends Cloneable {

    /**
     * @title: execute
     * @author leise
     * @description: 执行动作
     * @date 2018年6月29日 下午4:57:43
     * @param context
     * @return
     * @throws
     */
    ActionResultEnum execute(FlowContext context);

    /**
     * @title: clone
     * @author leise
     * @description: 克隆动作
     * @date 2018年6月29日 下午5:00:24
     * @return
     * @throws
     */
    IAction clone();

    /**
     * @title: validate
     * @author leise
     * @description: 验证动作要素
     * @date 2018年6月29日 下午5:00:24
     * @return
     * @throws
     */
    void validate();
}
