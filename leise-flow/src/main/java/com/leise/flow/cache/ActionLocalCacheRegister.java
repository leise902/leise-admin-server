package com.leise.flow.cache;

import com.google.common.collect.Maps;
import com.leise.flow.action.IAction;

import java.util.Map;

/**
 * @author leise
 * @date 2019年11月25日 下午6:01:34
 * @classname: ActionLocalCacheRegister
 * @description: TODO(这里用一句话描述这个类的作用)
 */
public class ActionLocalCacheRegister {

    public static final Map<String, Map<String, Object>> ACTION_PROPERTY_CACHE = Maps.newConcurrentMap();

    public static final Map<String, IAction> ACTION_CACHE = Maps.newConcurrentMap();

}
