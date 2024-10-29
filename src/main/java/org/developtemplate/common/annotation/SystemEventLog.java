package org.developtemplate.common.annotation;

import org.developtemplate.common.enums.SystemLogTypeEnum;

import java.lang.annotation.*;

/**
 * 系日志注解
 *
 * @author jiafu.li
 * @date 2024/10/28
 */
@Target(ElementType.METHOD)           // 指定方法级别的元数据
@Retention(RetentionPolicy.RUNTIME)   // 表示该注解会在运行时被保留，可以被反射机制访问
@Documented                           // 表示该注解是文档化的，可以被javadoc工具提取
public @interface SystemEventLog {
    SystemLogTypeEnum value() default SystemLogTypeEnum.DEFAULT;
}
