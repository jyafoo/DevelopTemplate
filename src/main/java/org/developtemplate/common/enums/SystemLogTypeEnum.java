package org.developtemplate.common.enums;

/**
 * 系统日志类型枚举
 *
 * @author jiafu.li
 * @date 2024/10/28
 */
public enum SystemLogTypeEnum {

    // ======================系统默认=======================
    DEFAULT(-1, "默认"),
    OTHER_OPERATION(99, "其他操作"),

    // ======================用户操作=======================
    USER_UPDATE(11, "用户信息修改");


    private final int type;
    private final String typeName;

    SystemLogTypeEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }
}
