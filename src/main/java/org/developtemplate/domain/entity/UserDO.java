package org.developtemplate.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.developtemplate.domain.base.BaseDO;


import java.io.Serializable;


/**
 * 用户
 * @author jyafoo
 * @version 1.0
 * @since 2024/7/2
 */
@TableName(value ="user")
@Data
public class UserDO extends BaseDO implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户角色：user/admin
     */
    private String userRole;

    // /**
    //  * 创建时间
    //  */
    // private Date createTime;
    //
    // /**
    //  * 更新时间
    //  */
    // private Date updateTime;
    //
    // /**
    //  * 是否删除
    //  */
    // @TableLogic
    // private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}