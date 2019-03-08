package com.leise.flow.controller.advice;

import com.leise.flow.dto.JsonResult;
import com.leise.flow.exception.ActionException;
import com.leise.flow.exception.FlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * Created by JY-IT-D001 on 2018/7/20.
 */
@ControllerAdvice
public class ApiControllerAdivce {

    private final static Logger LOG = LoggerFactory.getLogger(ApiControllerAdivce.class);

    @ExceptionHandler(FlowException.class)
    @ResponseBody
    public JsonResult handleException(FlowException e) {
        LOG.error("@_@[流程发生异常]:", e);
        return new JsonResult(e.getErrorCode(), e.getErrorMessage(), null);
    }

    @ExceptionHandler(ActionException.class)
    @ResponseBody
    public JsonResult handleException(ActionException e) {
        LOG.error("@_@[组件发生异常]:", e);
        return new JsonResult(e.getErrorCode(), e.getErrorMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public JsonResult handleException(IllegalArgumentException e) {
        LOG.error("@_@[参数发生异常]:", e);
        return new JsonResult(555555, "参数发生异常", null);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public JsonResult handleException(SQLException e) {
        LOG.error("@_@[数据库发生异常]:", e);
        return new JsonResult(e.getErrorCode(), e.getMessage(), null);
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public JsonResult handleException(DataAccessException e) {
        LOG.error("@_@[数据库发生异常]:", e);
        return new JsonResult(200000, e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult handleException(Exception e) {
        LOG.error("@_@[系统其它异常]:", e);
        return new JsonResult(-1, e.getMessage(), null);
    }

}
