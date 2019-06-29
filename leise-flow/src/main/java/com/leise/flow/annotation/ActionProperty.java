package com.leise.flow.annotation;

import com.leise.flow.enums.ActionPropertyEnum;

import java.lang.annotation.*;

/**
 * 
 * @author leise
 * @date 2018年6月29日 下午5:27:25
 * @classname: ActionProperty
 * @description: TODO
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
