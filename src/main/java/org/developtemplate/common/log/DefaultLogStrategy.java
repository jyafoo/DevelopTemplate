package org.developtemplate.common.log;

import org.apache.commons.compress.utils.Sets;
import org.developtemplate.common.enums.SystemLogTypeEnum;
import org.developtemplate.domain.entity.EventLogDO;
import org.developtemplate.service.SystemEventLogService;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 默认日志策略
 *
 * @author jiafu.li
 * @date 2024/10/29
 */
@Component
public class DefaultLogStrategy extends BaseLogStrategy {

    protected DefaultLogStrategy(SystemEventLogService systemEventLogService) {
        super(systemEventLogService);
    }

    @Override
    protected Set<Integer> getLogType() {
        return Sets.newHashSet(SystemLogTypeEnum.DEFAULT.getType());
    }

    @Override
    protected void buildLogItem(EventLogDO eventLogDO, Object[] args, Object result) {
        log.info("=====>call DefaultLogHandler");
    }
}
