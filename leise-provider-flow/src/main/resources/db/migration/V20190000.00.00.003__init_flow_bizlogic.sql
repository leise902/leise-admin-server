/*==============================================================*/
/* Table: flow_bizlogic                                         */
/*==============================================================*/
create table flow_bizlogic
(
   id                   bigint(20) not null auto_increment comment 'id',
   bizlogic             longtext not null comment '业务流程信息',
   flow_info_id         bigint(20) not null comment '流程ID',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
   valid_status         tinyint(1) not null default 1 comment '数据有效性',
   primary key (id)
)
auto_increment = 100000;

alter table flow_bizlogic comment '流程业务定义表';

/*==============================================================*/
/* Index: IDX_UNIQUE_FLOW_BIZLOGIC                              */
/*==============================================================*/
create unique index IDX_UNIQUE_FLOW_BIZLOGIC on flow_bizlogic
(
   flow_info_id
);