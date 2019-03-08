package com.leise.flow.action.operation;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;
import com.leise.flow.exception.ActionException;

/**
 * Created by JY-IT-D001 on 2018/7/26.
 */
@Action(id = "ListForeachAction", name = "循环集合数据", group = ActionGroupEnum.OPERATION)
public class ListForeachAction implements IAction {

    private final static Logger LOG = LoggerFactory.getLogger(ListForeachAction.class);

    @ActionProperty
    private String listName;

    @ActionProperty
    private String itemName;

    @Override
    public ActionResultEnum execute(FlowContext context) {
        List<?> list = (List<?>) context.get(this.listName);
        if (CollectionUtils.isEmpty(list)) {
            LOG.warn("需要循环处理的集合数据为空");
            return ActionResultEnum.NODATA;
        }
        Object itemValue = list.get(0);
        context.put(this.itemName, itemValue);
        list.remove(0);
        return ActionResultEnum.SUCCESS;
    }

    @Override
    public ListForeachAction clone() {
        try {
            return (ListForeachAction) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ActionException(800000, "action clone inner error", e);
        }
    }

    @Override
    public void validate() {
        // 参数验证
        Assert.isTrue(StringUtils.isNotEmpty(this.listName), "配置项[listName]未进行配置，请检查配置信息");
        Assert.isTrue(StringUtils.isNotEmpty(this.itemName), "配置项[itemName]未进行配置，请检查配置信息");
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
