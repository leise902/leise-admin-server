package com.leise.flow.annotation;

import com.leise.flow.enums.ActionGroupEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by JY-IT-D001 on 2018/6/26.
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
