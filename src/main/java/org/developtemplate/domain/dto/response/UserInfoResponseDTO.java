package org.developtemplate.domain.dto.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息响应参数
 * @author jyafoo
 * @version 1.0
 * @since 2024/7/3
 */
@Data
public class UserInfoResponseDTO implements Serializable {
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
