package org.developtemplate.common.log;


import com.alibaba.fastjson2.JSON;
import org.developtemplate.common.biz.UserContext;
import org.developtemplate.common.enums.SystemLogTypeEnum;
import org.developtemplate.domain.entity.EventLogDO;
import org.developtemplate.domain.entity.EventLogItemDO;
import org.developtemplate.domain.entity.UserDO;
import org.developtemplate.service.SystemEventLogService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户日志策略
 *
 * @author jiafu.li
 * @date 2024/10/29
 */
@Component
public class UserLogStrategy extends BaseLogStrategy{
    protected UserLogStrategy(SystemEventLogService systemEventLogService) {
        super(systemEventLogService);
    }

    @Override
    protected Set<Integer> getLogType() {
        return Set.of(SystemLogTypeEnum.USER_UPDATE.getType());
    }

    @Override
    protected void buildLogItem(EventLogDO eventLogDO, Object[] args, Object result) {
        log.info("=====>call SaveUserLog");
        List<EventLogItemDO> itemList = eventLogDO.getItemList();

        UserDO userDO = (UserDO) result;
        EventLogItemDO itemDO = EventLogItemDO.builder()
                .eventTarget(userDO.getId())
                .eventTargetName(userDO.getUserAccount())
                .logSnapshot("")
                .logData(JSON.toJSONString(userDO))
                .createBy(UserContext.getUserName())
                .createTime(new Date())
                .build();

        itemList.add(itemDO);
    }
}
