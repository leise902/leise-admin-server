/*==============================================================*/
/* Table: module_info                                           */
/*==============================================================*/
create table if not exists module_info
(
   id                   bigint(20) not null auto_increment comment 'id',
   module_id            varchar(64) not null comment '模块编号',
   module_name          varchar(256) not null comment '模块名称',
   module_desc          varchar(1024) comment '模块描述',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
   valid_status         boolean not null default 1 comment '数据有效性',
   primary key (id)
)
auto_increment = 100000;

alter table module_info comment '模块信息表';

/*==============================================================*/
/* Index: IDX_UNIQUE_MODULE_INFO                                */
/*==============================================================*/
create unique index IDX_UNIQUE_MODULE_INFO on module_info
(
   module_id
);