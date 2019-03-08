package com.leise.provider.auth.controller;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.leise.flow.controller.ApiController;
import com.leise.flow.dto.JsonResult;

/**
 * Created by JY-IT-D001 on 2018/7/10.
 */
@RestController
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping(value = { "/auth", "/branch", "/user" })
public class AuthController extends ApiController {

    @RequestMapping(value = "/{serviceId}/{version:.+}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult doExecute(@PathVariable String serviceId, @PathVariable String version,
            @RequestBody String requestBody) throws Exception {
        return super.doExecute(serviceId, version, requestBody);
    }
}
