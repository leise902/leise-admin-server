package com.leise.provider.flow.controller;

import com.leise.flow.controller.ApiController;
import com.leise.flow.dto.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

/**
 * Created by JY-IT-D001 on 2018/7/10.
 */
@RestController
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping(value = {"/design", "/module", "/flowCenter"})
public class FlowCenterController extends ApiController {

    private final static Logger LOG = LoggerFactory.getLogger(FlowCenterController.class);

    @RequestMapping(value = "/{serviceId}/{version:.+}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult doExecute(@PathVariable String serviceId, @PathVariable String version, @RequestBody String requestBody)
            throws Exception {
        return super.doExecute(serviceId, version, requestBody);
    }
}
