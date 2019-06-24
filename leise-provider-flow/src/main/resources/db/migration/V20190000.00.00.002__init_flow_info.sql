create table if not exists flow_info
(
   id                   bigint(20) not null auto_increment comment 'id',
   module_id            varchar(64) not null comment '模块编号',
   flow_id              varchar(64) not null comment '流程编号（服务编号）',
   flow_name            varchar(512) not null comment '流程名称（服务名称）',
   flow_version         varchar(10) not null comment '流程版本号（服务版本号）',
   request_url          varchar(256) not null comment '请求地址（格式为"/控制器前缀路径/流程编号/流程版本号）',
   status               varchar(32) not null default 'UNKNOW' comment '流程状态',
   create_time          timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   modify_time          timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
   valid_status         boolean not null default 1 comment '数据有效性',
   primary key (id)
)
auto_increment = 100000;

alter table flow_info comment '流程信息表';

/*==============================================================*/
/* Index: IDX_UNIQUE_FLOW_INFO                                  */
/*==============================================================*/
create unique index IDX_UNIQUE_FLOW_INFO on flow_info
(
   module_id,
   flow_id,
   flow_version
);