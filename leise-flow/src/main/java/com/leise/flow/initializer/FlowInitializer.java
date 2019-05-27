package com.leise.flow.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.leise.flow.cache.FlowLocalCacheRegister;

/**
 * Created by JY-IT-D001 on 2018/6/29.
 */
@Component
@Order(2)
public class FlowInitializer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(FlowInitializer.class);

    @Autowired
    private FlowLocalCacheRegister flowLocalCacheRegister;

    @Value(value = "${spring.application.name}")
    private String moduleId;

    @Override
    public void run(String... args) throws Exception {
        Assert.notNull(moduleId, "请配置[spring.application.name]");
        LOG.info("初始化流程配置开始......模块Id:[{}]......", this.moduleId);
        flowLocalCacheRegister.initFlowLocalCache(this.moduleId);
        LOG.info("初始化流程配置结束......模块Id:[{}].....", this.moduleId);
    }

}
