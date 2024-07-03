package org.developtemplate.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录响应参数
 * @author jyafoo
 * @version 1.0
 * @since 2024/7/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDTO implements Serializable {
    /**
     * 用户Token
     */
    private String token;
    @Serial
    private static final long serialVersionUID = 1L;
}
