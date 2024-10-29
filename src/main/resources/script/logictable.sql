drop table if exists user;
CREATE TABLE user
(
    id            BIGINT       NOT NULL,
    user_account   VARCHAR(255) NOT NULL,
    user_password  VARCHAR(255) NOT NULL,
    user_name      VARCHAR(255) not null default '用户',
    user_avatar    VARCHAR(255) not null DEFAULT 'https://cdn.acmer.cn/img/20230921162607.png',
    user_role      VARCHAR(50)  NOT NULL DEFAULT 'user',

    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT(2)   NOT NULL DEFAULT '0' COMMENT '删除标志：0未删除；1已删除',

    primary key (id),
    unique (user_account)
)ENGINE = InnoDB COMMENT ='用户信息表';


-- 系统事件日志表
DROP TABLE IF EXISTS `event_log`;
CREATE TABLE `event_log`
(
    `id`              BIGINT        NOT NULL AUTO_INCREMENT             COMMENT 'id',
    `event_type`      INT           NOT NULL DEFAULT 0 COMMENT '事件类型',
    `event_type_desc` VARCHAR(64)   NOT NULL DEFAULT '' COMMENT '事件类型描述',
    `method`          VARCHAR(256)  NOT NULL DEFAULT '' COMMENT '请求方法',
    `params`          VARCHAR(4096) NOT NULL DEFAULT '' COMMENT '请求参数',

    `create_by`       VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT ='各种事件日志表';

-- 事件日志明细表
DROP TABLE IF EXISTS `event_log_item`;
CREATE TABLE `event_log_item`
(
    `id`                BIGINT        NOT NULL AUTO_INCREMENT            COMMENT 'id',
    `event_log_id`      BIGINT        NOT NULL DEFAULT 0 COMMENT '事件ID',
    `log_snapshot`      VARCHAR(4096) NOT NULL DEFAULT '' COMMENT '事件发生前数据',
    `log_data`          VARCHAR(4096) NOT NULL DEFAULT '' COMMENT '事件发生后数据',
    `event_target`      BIGINT        NOT NULL DEFAULT 0 COMMENT '事件影响者ID',
    `event_target_name` VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '事件影响者',

    `create_by`         VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time`       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
)ENGINE = InnoDB COMMENT ='事件日志明细表';