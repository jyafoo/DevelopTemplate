package org.developtemplate.common.convention.exception;


import org.developtemplate.common.convention.errorcode.BaseErrorCode;
import org.developtemplate.common.convention.errorcode.IErrorCode;

/**
 * 客户端异常类
 *
 * @author JIA
 * @version 1.0
 * @since 2024/3/9
 */

public class ClientException extends AbstractException{
    public ClientException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public ClientException(String message) {
        this(message, null, BaseErrorCode.CLIENT_ERROR);
    }

    public ClientException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ClientException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
