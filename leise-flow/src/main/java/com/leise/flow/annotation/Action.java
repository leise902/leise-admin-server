package com.leise.flow.annotation;

import com.leise.flow.enums.ActionGroupEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 
 * @author leise
 * @date 2018年6月29日 下午5:26:48
 * @classname: Action
 * @description: 标注在执行操作或动作上的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Component
public @interface Action {

    /**
     * 组件标识
     */
    String id();

    /**
     * 组件名称
     */
    String name();

    /**
     * 组件分组
     */
    ActionGroupEnum group() default ActionGroupEnum.CUSTOMIZED;

}
