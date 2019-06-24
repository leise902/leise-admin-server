/*==============================================================*/
/* Table: flow_biz_define                                         */
/*==============================================================*/
create table flow_biz_define
(
   id                   bigint(20) not null auto_increment comment 'id',
   module_id            varchar(64) not null comment '模块编号',
   flow_id              varchar(64) not null comment '流程编号（服务编号）',
   flow_version         varchar(10) not null comment '流程版本号（服务版本号）',
   flow_model           longtext not null comment '业务流程信息（与文件对应）',
   version              varchar(256) not null comment '文件版本号',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
   valid_status         tinyint(1) not null default 1 comment '数据有效性',
   primary key (id)
)
auto_increment = 100000;

alter table flow_biz_define comment '业务流程表（与业务流程配置文件对应）';

/*==============================================================*/
/* Index: IDX_UNIQUE_FLOW_BIZ_DEFINE                              */
/*==============================================================*/
create unique index IDX_UNIQUE_FLOW_BIZ_DEFINE on flow_biz_define
(
   module_id,
   flow_id,
   flow_version
);