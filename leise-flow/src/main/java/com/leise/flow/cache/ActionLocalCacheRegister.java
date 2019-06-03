package com.leise.flow.cache;

import com.google.common.collect.Maps;
import com.leise.flow.action.IAction;

import java.util.Map;

/**
 * Created by JY-IT-D001 on 2018/6/14.
 */
public class ActionLocalCacheRegister {

    public static final Map<String, Map<String, Object>> ACTION_PROPERTY_CACHE = Maps.newConcurrentMap();

    public static final Map<String, IAction> ACTION_CACHE = Maps.newConcurrentMap();

}
