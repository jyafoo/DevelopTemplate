package org.developtemplate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.developtemplate.domain.entity.EventLogItemDO;

/**
 * 系统事件日志明细Mapper接口
 *
 * @author jiafu.li
 * @date 2024/10/29
 */
@Mapper
public interface SystemEventLogItemMapper extends BaseMapper<EventLogItemDO> {
}
