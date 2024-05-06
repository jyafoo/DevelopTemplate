package org.developtemplate.common.convention.errorcode;

/**
 * 基础错误码类
 *
 */

public enum BaseErrorCode implements IErrorCode{
    // ========== 正确执行后的返回 ==========
    OK("00000", "一切 ok"),


    // ========== 一级宏观错误码 客户端错误 ==========
    CLIENT_ERROR("A0001", "用户端错误"),

    // ========== 二级宏观错误码 用户注册错误 ==========
    USER_REGISTRATION_ERROR("A0100", "用户注册错误"),
    USERNAME_VALIDATION_FAILED_ERROR("A0110", "用户名校验失败"),
    USERNAME_ALREADY_EXISTS_ERROR("A0111", "用户名已存在"),
    USERNAME_CONTAINS_SENSITIVE_WORDS_ERROR("A0112", "用户名包含敏感词"),
    USERNAME_CONTAINS_SPECIAL_CHARACTERS_ERROR("A0113", "用户名包含特殊字符"),
    PASSWORD_VALIDATION_FAILED_ERROR("A0120", "密码校验失败"),
    PASSWORD_LENGTH_TOO_SHORT_ERROR("A0121", "密码长度不够"),
    PASSWORD_STRENGTH_NOT_ENOUGH_ERROR("A0122", "密码强度不够"),
    USER_BASIC_INFORMATION_VALIDATION_FAILED_ERROR("A0150", "用户基本信息校验失败"),
    MOBILE_FORMAT_VALIDATION_FAILED_ERROR("A0151", "手机格式校验失败"),
    ADDRESS_FORMAT_VALIDATION_FAILED_ERROR("A0152", "地址格式校验失败"),
    EMAIL_FORMAT_VALIDATION_FAILED_ERROR("A0153", "邮箱格式校验失败"),


    // ========== 一级宏观错误码 系统执行出错 ==========
    SERVICE_ERROR("B0001", "系统执行出错"),

    // ========== 二级宏观错误码 系统执行超时 ==========
    SYSTEM_EXECUTION_TIMEOUT_ERROR("B0100", "系统执行超时"),


    // ========== 一级宏观错误码 调用第三方服务出错 ==========
    REMOTE_ERROR("C0001", "调用第三方服务出错"),

    // ========= 二级宏观错误码 中间件服务出错 ==========
    MIDDLEWARE_SERVICE_ERROR("C0100", "中间件服务出错");


    private final String code;

    private final String message;

    BaseErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
