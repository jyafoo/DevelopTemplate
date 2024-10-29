package org.developtemplate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jiafu.li
 * @date 2024/10/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@TableName("event_log")
public class EventLogDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 事件类型
     * <p>
     * 用于区分不同的事件
     */
    @TableField(value = "event_type")
    private Integer eventType;

    /**
     * 事件类型描述
     * <p>
     * 对事件类型的描述，便于理解事件内容
     */
    @TableField(value = "event_type_desc")
    private String eventTypeDesc;

    /**
     * 请求方法
     * <p>
     * 记录触发事件的请求方法，以便于追踪和审计
     */
    @TableField(value = "method")
    private String method;

    /**
     * 请求参数
     * <p>
     * 记录触发事件的请求参数，以便于追踪和审计
     */
    @TableField(value = "params")
    private String params;

    /**
     * 创建者
     * <p>
     * 记录创建日志的用户信息
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     * <p>
     * 记录日志创建的时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 事件日志条目列表
     * <p>
     * 用于存储与该事件相关的详细日志项，非数据库字段
     */
    @TableField(exist = false)
    private List<EventLogItemDO> itemList = new ArrayList<>();
}
