package com.leise.flow.action.encrypt;

import com.alibaba.fastjson.JSON;
import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;
import com.leise.flow.exception.ActionException;
import com.leise.flow.util.ActionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

import java.util.Arrays;

/**
 * Created by JY-IT-D001 on 2018/6/26.
 */
@Action(id = "Md5Action", name = "Md5加密", group = ActionGroupEnum.ENCRYPT)
public class Md5Action implements IAction {

    private final static Logger LOG = LoggerFactory.getLogger(Md5Action.class);

    @ActionProperty
    private String sourceNames;

    @ActionProperty
    private String encryptDataName;

    @Override
    public ActionResultEnum execute(FlowContext context) {

        String[] sourceNameArray = ActionUtils.split(this.sourceNames);
        StringBuilder strBuilder = new StringBuilder();
        Arrays.asList(sourceNameArray).forEach(sourceName -> {
            Object value = context.get(sourceName);
            strBuilder.append(JSON.toJSONString(value));
        });
        try {
            String encryptData = DigestUtils.md5DigestAsHex(strBuilder.toString().getBytes("utf-8"));
            context.put(this.encryptDataName, encryptData);
        } catch (Exception e) {
            LOG.error("@_@[数据库查询异常]:", e);
            throw new ActionException(600001, "数据MD5加密失败", e);
        }
        return ActionResultEnum.SUCCESS;
    }

    @Override
    public Md5Action clone() {
        try {
            return (Md5Action) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void validate() {
        // 参数验证
        Assert.isTrue(StringUtils.isNotEmpty(this.sourceNames), "配置项[sourceNames]未进行配置，请检查配置信息");
        Assert.isTrue(StringUtils.isNotEmpty(this.encryptDataName), "配置项[encryptDataName]未进行配置，请检查配置信息");
    }

    public String getSourceNames() {
        return sourceNames;
    }

    public void setSourceNames(String sourceNames) {
        this.sourceNames = sourceNames;
    }

    public String getEncryptDataName() {
        return encryptDataName;
    }

    public void setEncryptDataName(String encryptDataName) {
        this.encryptDataName = encryptDataName;
    }

}
