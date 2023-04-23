DROP TABLE IF EXISTS vcs_db.vcs_user ;
DROP TABLE IF EXISTS vcs_db.user_log_operation;
DROP TABLE IF EXISTS vcs_db.vcs_device;
DROP TABLE IF EXISTS vcs_db.vcs_log;
DROP TABLE IF EXISTS vcs_db.vcs_nvehicle;
DROP TABLE IF EXISTS vcs_db.vcs_record;
DROP TABLE IF EXISTS vcs_db.vcs_remind;
DROP TABLE IF EXISTS vcs_db.vcs_rfid;
DROP TABLE IF EXISTS vcs_db.vcs_warning;
create table vcs_user
(
    id           bigint auto_increment comment '用户ID' primary key,
    sid          varchar(20)                         null comment '用户学号',
    mail         varchar(20) default 'unknow@qq.com' not null comment '用户邮箱',
    password     varchar(512)                        not null comment '用户密码',
    phone        varchar(11)                         not null comment '用户手机号',
    role         tinyint     default 0               not null comment '用户角色',
    is_deleted   tinyint     default 0               not null comment '是否删除',
    created_time datetime     default CURRENT_TIMESTAMP    not null comment '创建时间',
    updated_time datetime     default CURRENT_TIMESTAMP    not null comment '更新时间'
) comment '用户' collate = utf8mb4_unicode_ci;

create table user_log_operation
(
    log_id       bigint not null comment '日志ID',
    user_id      bigint not null comment '用户ID',
    created_time datetime default CURRENT_TIMESTAMP  not null comment '操作时间',
    primary key (log_id, user_id)
) comment '日志关联' collate = utf8mb4_unicode_ci;


create table vcs_device
(
    id           bigint            not null comment '设备ID' primary key,
    dName    varchar(20)          not null comment '设备类型',
    longitude    varchar(50)          not null comment '经度',
    latitude     varchar(50)          not null comment '纬度',
    is_valid     tinyint default 0 not null comment '是否有效',
    is_deleted   tinyint default 0 not null comment '是否删除'
) comment '设备' collate = utf8mb4_unicode_ci;

create table vcs_log
(
    id           bigint      not null comment '日志ID'
        primary key,
    storage_path varchar(50) not null comment '存储地址',
    record_range varchar(50) not null comment '记录时间范围'
) comment '日志存储' collate = utf8mb4_unicode_ci;
    
create table vcs_nvehicle
(
    id           bigint            not null comment '车辆ID'
        primary key,
    car_number   varchar(10)       not null comment '车牌号',
    state        int     default 0 not null comment '车辆状态',
    use_range    varchar(50)       not null comment '正常使用时段',
    is_deleted   tinyint default 0 not null comment '是否删除',
    created_time datetime     default CURRENT_TIMESTAMP    not null comment '创建时间',
    updated_time datetime     default CURRENT_TIMESTAMP    null comment '更新时间',
    user_id      bigint            not null comment '用户ID'
) comment '车辆' collate = utf8mb4_unicode_ci;

create table vcs_record
(
    id           bigint        not null comment '记录ID'
        primary key,
    type         int default 0 not null comment '记录类型',
    created_time datetime     default CURRENT_TIMESTAMP    not null comment '创建时间',
    device_id    bigint        not null comment '设备ID',
    nvehicle_id  bigint        not null comment '车辆ID'
) comment '记录' collate = utf8mb4_unicode_ci;

create table vcs_remind
(
    id             bigint       not null comment '提醒ID'
        primary key,
    user_id        bigint       not null comment '用户ID',
    remind_user_id bigint       not null comment '被提醒用户ID',
    remind_content varchar(255) not null comment '提醒内容',
    remind_time    datetime     default CURRENT_TIMESTAMP    not null comment '提醒时间'
) comment '提醒' collate = utf8mb4_unicode_ci;

create table vcs_rfid
(
    id           bigint            not null comment '信息ID'
        primary key,
    check_code   varchar(40)       not null comment '校验码',
    valid_date   datetime     default CURRENT_TIMESTAMP    not null comment '有效期',
    is_valid     tinyint default 0 not null comment '是否有效',
    created_time datetime     default CURRENT_TIMESTAMP    not null comment '创建时间',
    updated_time datetime     default CURRENT_TIMESTAMP    null comment '更新时间',
    user_id      bigint            not null comment '用户ID',
    nvehicle_id  bigint            not null comment '车辆ID'
) comment 'RFID信息' collate = utf8mb4_unicode_ci;

create table vcs_warning
(
    id              bigint       not null comment '预警ID'
        primary key,
    warning_content varchar(255) not null comment '预警内容',
    created_time    datetime     default CURRENT_TIMESTAMP    not null comment '预警时间',
    user_id         bigint       not null comment '用户ID',
    nvehicle_id     bigint       not null comment '车辆ID',
    device_id       bigint       not null comment '设备ID'
) comment '预警记录' collate = utf8mb4_unicode_ci;
