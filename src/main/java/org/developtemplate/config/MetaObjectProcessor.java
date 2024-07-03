package org.developtemplate.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义元对象处理器（字段填充）
 *
 */
@Primary
@Component(value = "metaObjectProcessor")
public class MetaObjectProcessor implements MetaObjectHandler {
    /**
     * 插入时字段填充
     * @param metaObject 元数据对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        strictInsertFill(metaObject, "createTime",Date::new, Date.class);
        strictInsertFill(metaObject, "updateTime",Date::new, Date.class);
        strictInsertFill(metaObject, "isDelete", () -> 0, Integer.class);
    }

    /**
     * 更新时字段填充
     * @param metaObject 元数据对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        strictInsertFill(metaObject, "updateTime", Date::new, Date.class);
    }
}
