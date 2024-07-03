package org.developtemplate.domain.dto.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户更新请求参数
 * @author jyafoo
 * @version 1.0
 * @since 2024/7/3
 */
@Data
public class UserUpdateRequestDTO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    @Serial
    private static final long serialVersionUID = 1L;
}
