package com.leise.flow.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.leise.flow.model.entity.ModuleInfo;
import com.leise.flow.model.service.ModuleInfoService;

/**
 * Created by JY-IT-D001 on 2018/6/29.
 */
@Component
@Order(0)
@Deprecated
public class ModuleInitializer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ModuleInitializer.class);

    @Value(value = "${spring.application.name}")
    private String moduleId;

    @Autowired
    private ModuleInfoService moduleInfoService;

    @Override
    public void run(String... args) throws Exception {
        Assert.notNull(moduleId, "请配置[spring.application.name]");
        LOG.info("初始化模块信息开始......模块Id:[{}]......", this.moduleId);
        ModuleInfo moduleInfo = moduleInfoService.findByModuleId(moduleId);
        if (null == moduleInfo) {
            moduleInfoService.insert(moduleId);
            LOG.info("添加模块信息完毕");
        }
        LOG.info("初始化模块信息结束......模块Id:[{}].....", this.moduleId);
    }

}
