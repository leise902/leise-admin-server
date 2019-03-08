package com.leise.flow.parser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.leise.flow.cache.FlowLink;
import com.leise.flow.cache.FlowMetaData;

/**
 * Created by JY-IT-D001 on 2018/7/4.
 */
public class FlowLinkParser {

    private static final Logger LOG = LoggerFactory.getLogger(FlowLinkParser.class);

    public static void parse(List<FlowLink> flowLinks, FlowMetaData metaData) {

        List<FlowLink> list = Lists.newArrayList();
        for (Object object : flowLinks) {
            Map map = (Map) object;
            Integer from = (Integer) map.get("from");
            Integer to = (Integer) map.get("to");
            // TODO 看看前端怎么把condition加到属性中
            String condition = (String) map.get("text");
            String linkDesc = (String) map.get("linkDesc");
            FlowLink flowLink = new FlowLink(from, to, condition, linkDesc);
            list.add(flowLink);
        }
        Map<Integer, List<FlowLink>> flowLinkMap = list.stream().collect(Collectors.groupingBy(FlowLink::getFrom));
        metaData.setFlowLinks(flowLinkMap);
    }
}
