package org.developtemplate.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.developtemplate.common.annotation.SystemEventLog;
import org.developtemplate.common.log.LogContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 系统日志切面类
 *
 * @author jiafu.li
 * @date 2024/10/28
 */
@Aspect
@Component
public class SystemEventLogAspect {

    /**
     * 配置切点方法，用于匹配带有特定注解的方法
     * <p>Event
     * 拦截所有标注了 @SystemLog 注解的方法
     */
    @Pointcut("@annotation(org.developtemplate.common.annotation.SystemEventLog)")
    public void logPointCut() {
        // logPointCut
    }

    /**
     * 环绕通知方法，用于在方法执行前后进行操作
     *
     * @param point 切入点对象，提供了关于目标方法的信息
     * @return 目标方法的执行结果
     * @throws Throwable 如果目标方法执行过程中抛出异常，本方法也会抛出
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 执行目标方法并获取其返回结果
        Object result = point.proceed();
        // 将目标方法的执行信息和结果保存到日志中
        this.saveSysLog(point, result);

        return result;
    }

    /**
     * 保存系统日志
     *
     * @param joinPoint 切入点，包含被拦截方法的信息
     * @param result 被拦截方法的返回结果
     */
    private void saveSysLog(ProceedingJoinPoint joinPoint, Object result) {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法上的系统事件日志注解
        SystemEventLog syslog = signature.getMethod().getAnnotation(SystemEventLog.class);

        // 请求的方法名
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
        // 请求的参数
        Object[] args = joinPoint.getArgs();

        // 保存系统日志
        LogContext.saveLog(syslog, methodName, args, result);
    }
}
