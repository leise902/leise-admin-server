package com.leise.flow.loader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.leise.flow.model.dto.FlowModel;
import com.leise.flow.model.service.FlowModelService;

@Component
@Profile("prod")
public class ProductEnvFlowLoader implements FlowLoader {

    @Autowired
    private FlowModelService flowModelService;

    @Override
    public List<FlowModel> getFlowModelList(String moduleId) {
        return flowModelService.buildFlowModelListFromFlie(moduleId);
    }

    @Override
    public FlowModel getFlowModel(String moduleId, String flowId, String flowVersion) {
        return flowModelService.buildFlowModelFromFlie(moduleId, flowId, flowVersion);
    }

}
