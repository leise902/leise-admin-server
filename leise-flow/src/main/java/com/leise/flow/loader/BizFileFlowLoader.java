/**
 * Copyright (C) 2006-2019 版权所有者为leise。
 * 
 * @title: LocalFlowLoader.java
 * @package com.leise.flow.loader
 * @author leise
 * @date 2019年6月29日 下午5:47:30
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package com.leise.flow.loader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.leise.flow.model.dto.FlowModel;
import com.leise.flow.model.service.FlowModelService;

/**
 * @author leise
 * @date 2019年6月29日 下午5:47:30
 * @classname: LocalFlowLoader
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Component
@ConditionalOnProperty(name = "leise.flow.load.mode", havingValue = "file")
public class BizFileFlowLoader implements FlowLoader {

    @Autowired
    private FlowModelService flowModelService;

    /* (非 Javadoc)
    * <p>title: getFlowModelList</p>
    * <p>description: </p>
    * @param moduleId
    * @return
    * @see com.leise.flow.loader.FlowLoader#getFlowModelList(java.lang.String)
    */

    @Override
    public List<FlowModel> getFlowModelList(String moduleId) {
        return flowModelService.buildFlowModelListFromFlie(moduleId);
    }

    /* (非 Javadoc)
    * <p>title: getFlowModel</p>
    * <p>description: </p>
    * @param moduleId
    * @param flowId
    * @param flowVersion
    * @return
    * @see com.leise.flow.loader.FlowLoader#getFlowModel(java.lang.String, java.lang.String, java.lang.String)
    */

    @Override
    public FlowModel getFlowModel(String moduleId, String flowId, String flowVersion) {
        return flowModelService.buildFlowModelFromFlie(moduleId, flowId, flowVersion);
    }

}
