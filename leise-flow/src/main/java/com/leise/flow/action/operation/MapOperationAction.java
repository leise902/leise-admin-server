package com.leise.flow.action.operation;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;
import com.leise.flow.exception.ActionException;
import com.leise.flow.util.ActionUtils;

/**
 * Created by JY-IT-D001 on 2018/7/26.
 */
@Action(id = "MapOperationAction", name = "操作MAP集合", group = ActionGroupEnum.OPERATION)
public class MapOperationAction implements IAction {

    @ActionProperty
    private String mapOperationType;

    @ActionProperty
    private String mapName;

    @ActionProperty
    private String keyNames;

    @Override
    public ActionResultEnum execute(FlowContext context) {

        String[] keys = ActionUtils.split(keyNames);
        Assert.isTrue(keys.length > 0, "无法找到对应的key，请检查配置信息");
        Map<String, Object> map = context.get(this.mapName) instanceof Map ? (Map) context.get(this.mapName) : Maps
                .newHashMap();
        switch (this.mapOperationType) {
            case "put":
                for (int i = 0; i < keys.length; i++) {
                    map.put(keys[i], context.get(keys[i]));
                }
                context.put(this.mapName, map);
                break;
            case "remove":
                for (int i = 0; i < keys.length; i++) {
                    map.remove(keys[i]);
                }
                context.put(this.mapName, map);
                break;
            case "get":
                for (int i = 0; i < keys.length; i++) {
                    context.put(keys[i], map.get(keys[i]));
                }
                break;
            case "putAll":
                for (int i = 0; i < keys.length; i++) {
                    map.putAll((Map) context.get(keys[i]));
                }
                context.put(this.mapName, map);
                break;
            default:
                return ActionResultEnum.SUCCESS;
        }
        return ActionResultEnum.SUCCESS;
    }

    @Override
    public MapOperationAction clone() {
        try {
            return (MapOperationAction) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ActionException(800000, "action clone inner error", e);
        }
    }

    @Override
    public void validate() {
        // 参数验证
        Assert.isTrue(StringUtils.isNotEmpty(this.mapName), "配置项[mapName]未进行配置，请检查配置信息");
        Assert.isTrue(StringUtils.isNotEmpty(this.keyNames), "配置项[keyNames]未进行配置，请检查配置信息");
        Assert.isTrue(ActionUtils.split(keyNames).length > 0, "无法找到对应的key，请检查配置信息");
    }

    public String getMapOperationType() {
        return mapOperationType;
    }

    public void setMapOperationType(String mapOperationType) {
        this.mapOperationType = mapOperationType;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getKeyNames() {
        return keyNames;
    }

    public void setKeyNames(String keyNames) {
        this.keyNames = keyNames;
    }
}
