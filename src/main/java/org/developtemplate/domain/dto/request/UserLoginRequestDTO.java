package org.developtemplate.domain.dto.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求参数
 * @author jyafoo
 * @version 1.0
 * @since 2024/7/3
 */
@Data
public class UserLoginRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;
}
