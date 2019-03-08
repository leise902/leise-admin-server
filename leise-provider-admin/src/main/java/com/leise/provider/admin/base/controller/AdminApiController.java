/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: LTAdminController.java
 * @package com.leise.provider.admin.base.controller
 * @author leise
 * @date 2016年5月9日 下午2:27:21
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package com.leise.provider.admin.base.controller;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.leise.core.base.controller.ApiContoller;
import com.leise.core.base.dto.ResponseMsg;

/**
 * @classname: AdminApiController
 * @description: Admin API的统一控制器
 */

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RestController
@RequestMapping(value = "/admin")
public class AdminApiController extends ApiContoller {

    @RequestMapping(value = "/{serviceId}/{version:.+}", method = RequestMethod.POST)
    @ResponseBody
    public <T> ResponseEntity<ResponseMsg<T>> doExecute(@PathVariable String serviceId,
            @PathVariable String version, @RequestBody String requestBody) throws Exception {

        return super.doExecute(serviceId, version, requestBody);
    }

}
