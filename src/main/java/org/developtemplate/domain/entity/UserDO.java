package org.developtemplate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.developtemplate.domain.base.BaseDO;


import java.io.Serializable;


/**
 * 用户
 * @author jyafoo
 * @version 1.0
 * @since 2024/7/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@TableName(value ="user")
public class UserDO extends BaseDO implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账号
     */
    @TableField(value = "user_account")
    private String userAccount;

    /**
     * 密码
     */
    @TableField(value = "user_password")
    private String userPassword;

    /**
     * 用户昵称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户头像
     */
    @TableField(value = "user_avatar")
    private String userAvatar;

    /**
     * 用户角色：user/admin
     */
    @TableField(value = "user_role")
    private String userRole;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}