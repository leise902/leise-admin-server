package com.leise.flow.initializer;

import com.google.common.collect.Maps;
import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.cache.ActionLocalCacheRegister;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by JY-IT-D001 on 2018/6/28.
 */
@Component
@Order(1)
public class ActionInitializer implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ActionInitializer.class);

    @Autowired
    private List<IAction> actionList;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("初始化组件配置开始......");
        if (CollectionUtils.isEmpty(actionList)) {
            LOG.warn("没有可用的组件列表......");
            return;
        }
        actionList.forEach(iAction -> {
            Map<String, Object> actionPropertyMap = Maps.newHashMap();
            Class<?> clazz = null;
            if (AopUtils.isAopProxy(iAction) || AopUtils.isJdkDynamicProxy(iAction)) {
                clazz = AopUtils.getTargetClass(iAction);
            } else {
                clazz = iAction.getClass();
            }

            Action action = AnnotationUtils.findAnnotation(clazz, Action.class);
            String actionId = action.id();
            String actionName = action.name();
            String actionGroup = action.group().toString();
            actionPropertyMap.put("actionId", actionId);
            actionPropertyMap.put("actionName", actionName);
            actionPropertyMap.put("text", actionName);
            actionPropertyMap.put("category", actionGroup);
            actionPropertyMap.put("groupId", actionGroup.indexOf("FLOW") != -1 ? "FLOW" : actionGroup);
            actionPropertyMap.put("refClass", clazz.getName());
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ActionProperty.class)) {
                    String fieldName = field.getName();
                    actionPropertyMap.put(fieldName, StringUtils.EMPTY);
                }
            }
            ActionLocalCacheRegister.actionPropertyCache.put(clazz.getName(), actionPropertyMap);
            ActionLocalCacheRegister.actionCache.put(clazz.getName(), iAction);
            LOG.info("组件{}初始化完毕", clazz.getName());
        });

        LOG.info("初始化组件配置结束......");

    }
}
