package org.developtemplate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统事件日志详细信息实体类
 *
 * @author jiafu.li
 * @date 2024/10/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@TableName("event_log_item")
public class EventLogItemDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 事件日志的ID，用于关联事件日志表
     * <p>
     * 通过外键约束与事件日志表关联
     */
    @TableField(value = "event_log_id")
    private Long eventLogId;

    /**
     * 事件目标的ID，表示触发事件的目标对象
     * <p>
     * 用于记录事件影响的具体对象
     */
    @TableField(value = "event_target")
    private Long eventTarget;

    /**
     * 事件目标的名称，用于记录事件影响对象的名称
     * <p>
     * 提供更直观的日志信息
     */
    @TableField(value = "event_target_name")
    private String eventTargetName;

    /**
     * 日志快照，记录事件发生时的状态或数据的快照
     * <p>
     * 用于后续分析和回溯
     */
    @TableField(value = "log_snapshot")
    private String logSnapshot;

    /**
     * 日志数据，详细记录事件的信息和数据
     * <p>
     * 包含事件的详细描述和相关数据
     */
    @TableField(value = "log_data")
    private String logData;

    /**
     * 创建者的用户名，记录创建日志的用户
     * <p>
     * 用于追踪和审计
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间，记录日志创建的时间
     * <p>
     * 用于排序和时间范围的查询
     */
    @TableField(value = "create_time")
    private Date createTime;
}
