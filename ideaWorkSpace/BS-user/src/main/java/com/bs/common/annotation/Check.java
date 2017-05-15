package com.bs.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登陆校验、手机号校验、认证校验
 * Created by fusj on 15/12/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Check {

    /**
     * 是否需要登陆校验
     */
    boolean loginCheck() default true;

    /**
     * 是否需要手机号校验
     * @return
     */
    boolean phoneCheck() default false;

    /**
     * 是否需要认证校验
     * @return
     */
    boolean statusCheck() default false;
}
