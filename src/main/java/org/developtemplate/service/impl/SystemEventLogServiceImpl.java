package org.developtemplate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.developtemplate.domain.entity.EventLogDO;
import org.developtemplate.domain.entity.EventLogItemDO;
import org.developtemplate.mapper.SystemEventLogItemMapper;
import org.developtemplate.mapper.SystemEventLogMapper;
import org.developtemplate.service.SystemEventLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统事件日志服务接口实现类
 *
 * @author jiafu.li
 * @date 2024/10/29
 */
@Service("systemEventLogServiceImpl")
@AllArgsConstructor
public class SystemEventLogServiceImpl extends ServiceImpl<SystemEventLogMapper, EventLogDO> implements SystemEventLogService {
    private final SystemEventLogItemMapper systemEventLogItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLog(EventLogDO eventLogEntity) {
        save(eventLogEntity);
        if (CollectionUtils.isNotEmpty(eventLogEntity.getItemList())) {
            for(EventLogItemDO item : eventLogEntity.getItemList()){
                item.setEventLogId(eventLogEntity.getId());
                systemEventLogItemMapper.insert(item);
            }
        }
    }
}
