package com.leise.flow.annotation;

import com.leise.flow.enums.ActionPropertyEnum;

import java.lang.annotation.*;

/**
 * Created by JY-IT-D001 on 2018/6/27.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ActionProperty {

    String defaultValue() default "";

    ActionPropertyEnum type() default ActionPropertyEnum.TEXT;

    String[] choices() default {};

}
