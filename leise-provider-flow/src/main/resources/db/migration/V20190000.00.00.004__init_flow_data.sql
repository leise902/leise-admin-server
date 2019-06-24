
/*==============================================================*/
/* Table: flow_data                                             */
/*==============================================================*/
create table flow_data
(
   id                   bigint(20) not null auto_increment comment 'id',
   data_code            varchar(64) not null comment '数据代码',
   data_name            varchar(128) not null comment '数据项名称',
   input_flag           boolean not null comment '是否输入项',
   output_flag          boolean not null comment '是否输出项',
   collect_flag         boolean not null comment '是否集合',
   collect_data_codes   varchar(2048) comment '集合数据代码(使用||分割)',
   flow_info_id         bigint(20) not null comment '流程ID',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
   valid_status         tinyint(1) not null default 1 comment '数据有效性',
   primary key (id)
)
auto_increment = 1;

alter table flow_data comment '流程数据定义表';

/*==============================================================*/
/* Index: IDX_UNIQUE_FLOW_DATA                                  */
/*==============================================================*/
create unique index IDX_UNIQUE_FLOW_DATA on flow_data
(
   flow_info_id,
   data_code
);
