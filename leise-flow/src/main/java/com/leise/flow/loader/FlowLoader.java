package com.leise.flow.loader;

import java.util.List;

import com.leise.flow.model.dto.FlowModel;

public interface FlowLoader {

    List<FlowModel> getFlowModelList(String moduleId);

    FlowModel getFlowModel(String moduleId, String flowId, String flowVersion);

}
