package com.leise.flow.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.leise.flow.enums.ActionPropertyEnum;

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
