package com.leise.flow.enums;

/**
 * Created by JY-IT-D001 on 2018/6/28.
 */
public enum ActionGroupEnum {
    /** 数据库 */ 
    DATABASE, 
    /** 流程开始 */ 
    FLOW_START, 
    /** 流程结束 */ 
    FLOW_END, 
    /** 流程跳转 */ 
    FLOW_JUMP, 
    FILE, CACHE, COMMU, OPERATION, VALIDATE, ENCRYPT,
    /** 应用系统自定义统一使用 */
    CUSTOMIZED
}
