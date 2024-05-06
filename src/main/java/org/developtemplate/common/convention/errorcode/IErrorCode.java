package org.developtemplate.common.convention.errorcode;

/**
 * 平台错误码接口
 *
 * @author JIA
 * @since 2024/5/8
 */

public interface IErrorCode {
    /**
     * 错误码
     */
    String code();

    /**
     * 错误信息
     */
    String message();
}
