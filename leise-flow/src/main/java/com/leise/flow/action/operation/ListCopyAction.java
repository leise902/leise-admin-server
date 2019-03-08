package com.leise.flow.action.operation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.alibaba.fastjson.util.IOUtils;
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
@Action(id = "ListCopyAction", name = "复制集合数据", group = ActionGroupEnum.OPERATION)
public class ListCopyAction implements IAction {

    @ActionProperty
    private String sourceNames;

    @ActionProperty
    private String targetNames;

    @Override
    public ActionResultEnum execute(FlowContext context) {
        String[] sources = ActionUtils.split(this.sourceNames);
        String[] targets = ActionUtils.split(this.targetNames);
        Assert.isTrue(sources.length == targets.length, "源数据与目标数据个数不一致，请检查配置信息");
        for (int i = 0; i < sources.length; i++) {
            Object sourceValue = context.get(sources[i]);
            Object targetValue = deepCopy((List<? extends Object>) sourceValue);
            context.put(targets[i], targetValue);
        }
        return ActionResultEnum.SUCCESS;
    }

    private <T> List<T> deepCopy(List<T> source) {
        List<T> target = null;
        ByteArrayOutputStream byteOut = null;
        ObjectOutputStream objectOut = null;
        ByteArrayInputStream byteIn = null;
        ObjectInputStream objectIn = null;

        try {
            byteOut = new ByteArrayOutputStream();
            objectOut = new ObjectOutputStream(byteOut);
            objectOut.writeObject(source);
            byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            objectIn = new ObjectInputStream(byteIn);
            target = (List<T>) objectIn.readObject();
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {

        } finally {
            IOUtils.close(byteOut);
            IOUtils.close(objectOut);
            IOUtils.close(byteIn);
            IOUtils.close(objectIn);
        }
        return target;
    }

    public String getSourceNames() {
        return sourceNames;
    }

    public void setSourceNames(String sourceNames) {
        this.sourceNames = sourceNames;
    }

    public String getTargetNames() {
        return targetNames;
    }

    public void setTargetNames(String targetNames) {
        this.targetNames = targetNames;
    }

    @Override
    public ListCopyAction clone() {
        try {
            return (ListCopyAction) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ActionException(800000, "action clone inner error", e);
        }
    }

    @Override
    public void validate() {
        // 参数验证
        Assert.isTrue(StringUtils.isNotEmpty(this.sourceNames), "配置项[sourceNames]未进行配置，请检查配置信息");
        Assert.isTrue(StringUtils.isNotEmpty(this.targetNames), "配置项[targetNames]未进行配置，请检查配置信息");
    }
}
