package com.leise.flow.action.operation;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.leise.flow.action.IAction;
import com.leise.flow.annotation.Action;
import com.leise.flow.annotation.ActionProperty;
import com.leise.flow.context.FlowContext;
import com.leise.flow.enums.ActionGroupEnum;
import com.leise.flow.enums.ActionResultEnum;
import com.leise.flow.exception.ActionException;

/**
 * Created by JY-IT-D001 on 2018/8/27.
 */
@Action(id = "GenSerialNoAction", name = "生成流水号", group = ActionGroupEnum.OPERATION)
public class GenSerialNoAction implements IAction {

    @ActionProperty
    private String prefix;

    @ActionProperty
    private String dateFormat = "yyyyMMddHHmmss";

    @ActionProperty
    private String randomCount = "6";

    @ActionProperty
    private String serialNoField;

    @Override
    public ActionResultEnum execute(FlowContext context) {
        String now = DateTime.now().toString(this.dateFormat);
        StringBuffer sbuffer = new StringBuffer();
        if (StringUtils.isNotEmpty(this.prefix)) {
            sbuffer.append(prefix);
        }
        sbuffer.append(now);
        String randomNum = RandomStringUtils.randomAlphanumeric(Integer.parseInt(this.randomCount));
        sbuffer.append(randomNum);
        context.put(this.serialNoField, sbuffer.toString());
        return ActionResultEnum.SUCCESS;
    }

    @Override
    public IAction clone() {
        try {
            return (GenSerialNoAction) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ActionException(800000, "action clone inner error", e);
        }
    }

    @Override
    public void validate() {
        // 参数验证
        Assert.isTrue(StringUtils.isNotEmpty(this.serialNoField), "配置项[serialNoField]未进行配置，请检查配置信息");
        Assert.isTrue(StringUtils.isNotEmpty(this.dateFormat), "配置项[dateFormat]未进行配置，请检查配置信息");
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        if (StringUtils.isNotEmpty(dateFormat)) {
            this.dateFormat = dateFormat;
        }
    }

    public String getRandomCount() {
        return randomCount;
    }

    public void setRandomCount(String randomCount) {
        if (StringUtils.isNotEmpty(randomCount)) {
            this.randomCount = randomCount;
        }
    }

    public String getSerialNoField() {
        return serialNoField;
    }

    public void setSerialNoField(String serialNoField) {
        this.serialNoField = serialNoField;
    }

}
