package com.leise.flow.action.operation;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;
import com.leise.flow.exception.ActionException;

/**
 * 
 * @author leise
 * @date 2018年6月29日 下午5:13:01
 * @classname: ListAddElementAction
 * @description: 添加集合数据
 */
@Action(id = "ListAddElementAction", name = "添加集合数据", group = ActionGroupEnum.OPERATION)
public class ListAddElementAction implements IAction {

    private final static Logger LOG = LoggerFactory.getLogger(ListAddElementAction.class);

    /** add addAll */
    @ActionProperty
    private String listOperationType;

    @ActionProperty
    private String listName;

    @ActionProperty
    private String itemName;

    @Override
    public ActionResultEnum execute(FlowContext context) {
        List list = context.get(this.listName) instanceof List ? (List) context.get(this.listName) : Lists
                .newArrayList();

        switch (this.listOperationType) {
            case "add":
                list.add(context.get(this.itemName));
                break;
            case "addAll":
                list.addAll((List) context.get(this.itemName));
                break;
            default:
                break;
        }
        context.put(this.listName, list);
        return ActionResultEnum.SUCCESS;
    }

    @Override
    public ListAddElementAction clone() {
        try {
            return (ListAddElementAction) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new ActionException(800000, "action clone inner error", e);
        }
    }

    @Override
    public void validate() {
        // 参数验证
        Assert.isTrue(StringUtils.isNotEmpty(this.listName), "配置项[listName]未进行配置，请检查配置信息");
        Assert.isTrue(StringUtils.isNotEmpty(this.itemName), "配置项[itemName]未进行配置，请检查配置信息");
        Assert.isTrue(StringUtils.isNotEmpty(this.listOperationType), "配置项[listOperationType]未进行配置，请检查配置信息");
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
