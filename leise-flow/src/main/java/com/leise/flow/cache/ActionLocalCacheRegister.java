package com.leise.flow.cache;

import com.google.common.collect.Maps;
import com.leise.flow.action.IAction;

import java.util.Map;

/**
 * Created by JY-IT-D001 on 2018/6/14.
 */
public class ActionLocalCacheRegister {

    public static final Map<String, Map<String, Object>> actionPropertyCache = Maps.newConcurrentMap();

    public static final Map<String, IAction> actionCache = Maps.newConcurrentMap();

}
