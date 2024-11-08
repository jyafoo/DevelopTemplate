package org.developtemplate.service;

import org.developtemplate.domain.entity.EventLogDO;

/**
 * 系统事件日志服务接口
 * <p>
 * 用于定义保存系统事件日志的相关操作
 *
 * @author jiafu.li
 * @date 2024/10/29
 */
public interface SystemEventLogService {

    /**
     * 保存用户后台管理操作日志,将日志信息持久化
     * <p>
     *
     * @param eventLogDO 事件日志实体，包含日志的相关信息
     */
    void saveLog(EventLogDO eventLogDO);

}
