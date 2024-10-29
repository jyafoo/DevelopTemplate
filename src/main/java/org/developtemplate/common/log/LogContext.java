package org.developtemplate.common.log;

import org.developtemplate.common.annotation.SystemEventLog;

import org.developtemplate.common.enums.SystemLogTypeEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 日志上下文
 *
 * @author jiafu.li
 * @date 2024/10/28
 */
@Component
public class LogContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LogContext.applicationContext = applicationContext;
    }

    /**
     * 保存系统事件日志
     *
     * @param systemEventLog 系统事件日志对象，包含日志的详细信息
     * @param methodName 触发日志记录的方法名称，用于追踪日志来源
     * @param args 方法的参数数组，记录方法调用时的输入参数
     * @param result 方法的执行结果，记录方法调用后的输出结果
     */
    public static void saveLog(SystemEventLog systemEventLog, String methodName, Object[] args, Object result) {
        // 获取所有BaseLogStrategy类型的Bean，以便找到合适的日志处理策略
        Map<String, BaseLogStrategy> map = applicationContext.getBeansOfType(BaseLogStrategy.class);

        // 默认日志处理策略，用于处理未指定特殊日志类型的日志
        BaseLogStrategy defaultHandler = null;

        // 遍历所有日志处理策略，寻找匹配当前日志类型的策略
        for (Map.Entry<String, BaseLogStrategy> entry : map.entrySet()) {
            // 如果当前策略支持处理的日志类型包含当前日志的类型，则使用该策略保存日志
            if (entry.getValue().getLogType().contains(systemEventLog.value().getType())) {
                entry.getValue().saveLog(systemEventLog, methodName, args, result);
                return;
            }

            // 同时检查并记录默认的日志处理策略
            if (entry.getValue().getLogType().contains(SystemLogTypeEnum.DEFAULT.getType())) {
                defaultHandler = entry.getValue();
            }
        }

        // 断言默认处理策略不为空，确保至少有一种策略可以处理日志，因为设置了默认策略，所以这里不会有空的情况
        assert defaultHandler != null;

        // 使用默认日志处理策略保存日志
        defaultHandler.saveLog(systemEventLog, methodName, args, result);
    }

}
