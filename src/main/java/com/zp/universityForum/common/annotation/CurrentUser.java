package com.zp.universityForum.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解类: 主要用于标注后通过AOP获取已登录的用户ID
 * Retention: 元注解 并标明了此注解将于运行时使用
 * Target: 顾名思义 标明此注解可用于哪些成分
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
