package org.developtemplate.common.log;

import lombok.AllArgsConstructor;
import org.developtemplate.common.annotation.SystemEventLog;
import org.developtemplate.common.biz.UserContext;
import org.developtemplate.domain.entity.EventLogDO;
import org.developtemplate.service.SystemEventLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * 日志策略基类
 *
 * @author jiafu.li
 * @date 2024/10/29
 */
@Component
public abstract class BaseLogStrategy {

    public final SystemEventLogService systemEventLogService;

    protected BaseLogStrategy(SystemEventLogService systemEventLogService) {
        this.systemEventLogService = systemEventLogService;
    }

    protected static final Logger log = LoggerFactory.getLogger(BaseLogStrategy.class);

    /**
     * 获取log类型
     *
     * @return Set<Integer>
     */
    protected abstract Set<Integer> getLogType();


    /**
     * 设置log_item表属性值
     *
     * @param eventLogDO 日志对象
     * @param args       接口输入参数列表
     * @param result     接口处理完成后的返回值
     */
    protected abstract void buildLogItem(EventLogDO eventLogDO, Object[] args, Object result);

    /**
     * 构建事件日志对象
     * <p>
     * 该方法主要用于在系统事件发生后，构建一个事件日志对象，包括通用属性和特定的日志条目信息
     *
     * @param systemEventLog 系统事件日志对象，包含事件的基础信息
     * @param methodName     方法名，记录触发事件的方法名称
     * @param args           方法参数，记录触发事件时的方法参数值
     * @param result         方法执行结果，记录方法执行后的结果
     * @return 返回构建完成的事件日志对象
     */
    private EventLogDO buildLog(SystemEventLog systemEventLog, String methodName, Object[] args, Object result) {
        // 构建事件日志的通用属性
        EventLogDO eventLogDO = buildGenericProperties(systemEventLog, methodName, args);
        // 构建特定的日志条目
        buildLogItem(eventLogDO, args, result);
        // 返回构建完成的事件日志对象
        return eventLogDO;
    }


    /**
     * 构建事件日志的通用属性
     *
     * @param systemEventLog 系统事件日志注解，用于获取事件类型信息
     * @param methodName     触发事件的方法名，未在当前版本中使用
     * @param args           方法参数，未在当前版本中使用
     * @return 返回填充了通用属性的事件日志对象
     */
    private EventLogDO buildGenericProperties(SystemEventLog systemEventLog, String methodName, Object[] args) {
        EventLogDO eventLogDO = new EventLogDO();

        if (systemEventLog != null) {
            //注解上的描述
            eventLogDO.setEventType(systemEventLog.value().getType());
            eventLogDO.setEventTypeDesc(systemEventLog.value().getTypeName());
        }

        // todo 获取登录用户信息，这里写的不够优雅，有待优化；注：没有做拦截器，这里获取的用户id会为空
        try {
            String userIdStr = UserContext.getUserId();
            if (userIdStr != null && !userIdStr.isEmpty()) {
                eventLogDO.setCreateBy(UserContext.getUserName() != null ? UserContext.getRealName() : "");
            } else {
                eventLogDO.setCreateBy("");
            }
        } catch (NumberFormatException e) {
            // 处理解析异常
            log.error("解析用户ID异常:", e);
            eventLogDO.setCreateBy("");
        }

        eventLogDO.setMethod(methodName);
        eventLogDO.setParams(args.toString());
        eventLogDO.setCreateTime(new Date());
        eventLogDO.setItemList(new ArrayList<>());
        return eventLogDO;
    }

    /**
     * 持久化日志信息到数据库
     *
     * @param syslog     系统事件日志对象，用于记录日志信息
     * @param methodName 方法名称，记录被调用的方法名
     * @param args       方法参数，记录被调用方法的输入参数
     * @param result     方法执行结果，记录方法执行后的结果
     */
    public void saveLog(SystemEventLog syslog, String methodName, Object[] args, Object result) {
        // 调用保存日志的服务，构建并保存日志信息
        systemEventLogService.saveLog(buildLog(syslog, methodName, args, result));
    }

}
