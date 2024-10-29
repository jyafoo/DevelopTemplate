package org.developtemplate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.developtemplate.domain.entity.EventLogDO;

/**
 * 系统事件日志Mapper接口
 *
 * @author jiafu.li
 * @date 2024/10/29
 */
@Mapper
public interface SystemEventLogMapper extends BaseMapper<EventLogDO> {

}
