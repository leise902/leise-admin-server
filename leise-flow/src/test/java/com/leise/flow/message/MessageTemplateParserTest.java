package com.leise.flow.message;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@RunWith(SpringRunner.class)
public class MessageTemplateParserTest {

    private final static Logger LOG = LoggerFactory.getLogger(MessageTemplateParserTest.class);

    private final static String DATA_TYPE_STRING = "string";

    private final static String DATA_TYPE_DATE = "date";

    private final static String DATA_TYPE_NUMBER = "number";

    private final static String DATA_TYPE_ARRAY = "array";

    private final static String DATA_TYPE_OBJECT = "object";

    private final static String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";

    @Test
    public void test1() {

        MessageData data = new MessageData();

        List<DataElement> dataElements = Lists.newArrayList();
        DataElement ele = new DataElement();
        ele.setDataName("string");
        ele.setDataRef("a");
        ele.setDataType("string");
        dataElements.add(ele);
        data.setDataElements(dataElements);
        LOG.info("data=========================>: {}", JSON.toJSONString(data));

    }

    @Test
    public void test3() {

        Map<String, Object> context = Maps.newHashMap();
        context.put("reqId", "3213901831eaqjdkaljd");
        context.put("reqTime", new Date());
        Map<String, Object> record = Maps.newHashMap();
        record.put("a", "111");
        record.put("b", "222");
        record.put("c", "333");
        context.put("d", record);

        List<Map<String, Object>> records = new ArrayList<>();
        records.add(record);
        records.add(record);
        records.add(record);
        context.put("e", records);

        LOG.info("context==============>: {}",
                JSON.toJSONString(context, SerializerFeature.DisableCircularReferenceDetect));

        try {
            InputStream in = this.getClass().getResourceAsStream("100001Req.json");
            String messageTemplate = IOUtils.toString(in, "utf-8");
            LOG.info("messageTemplate: {}", messageTemplate);
            MessageData messageData = JSON.parseObject(messageTemplate, MessageData.class);

            Map<String, Object> result = Maps.newHashMap();
            long begin = System.currentTimeMillis();
            result = this.format(context, messageData.getDataElements(), result);
            long end = System.currentTimeMillis();
            LOG.info("result==============>: {}, cost:{}", JSON.toJSONString(result), end - begin);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void test4() {

        try {
            InputStream sampleIn = this.getClass().getResourceAsStream("sample.json");
            String message = IOUtils.toString(sampleIn, "utf-8");
            LOG.info("message: {}", message);
            Map<String, Object> dataMap = JSON.parseObject(message);
            InputStream in = this.getClass().getResourceAsStream("100001Res.json");
            String messageTemplate = IOUtils.toString(in, "utf-8");
            LOG.info("messageTemplate: {}", messageTemplate);
            MessageData messageData = JSON.parseObject(messageTemplate, MessageData.class);

            Map<String, Object> result = Maps.newHashMap();
            long begin = System.currentTimeMillis();
            result = this.unformat(dataMap, messageData.getDataElements(), result);
            long end = System.currentTimeMillis();
            LOG.info("result==============>: {}, cost:{}", JSON.toJSONString(result), end - begin);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Map<String, Object> format(Map<String, Object> context, List<DataElement> dataElements,
            Map<String, Object> result) {

        for (DataElement dataElement : dataElements) {
            String dataName = dataElement.getDataName();
            String dataType = dataElement.getDataType();
            String dataRef = dataElement.getDataRef();
            if (DATA_TYPE_STRING.equalsIgnoreCase(dataType)) {
                String dataValue = (String) context.get(dataRef);
                result.put(dataName, dataValue);
            } else if (DATA_TYPE_DATE.equalsIgnoreCase(dataType)) {
                String dataFormat = dataElement.getDataFormat() == null ? DATE_DEFAULT_FORMAT : dataElement
                        .getDataFormat();
                if (context.get(dataRef) != null) {
                    String dataValue = new DateTime(context.get(dataRef)).toString(dataFormat);
                    result.put(dataName, dataValue);
                } else {
                    result.put(dataName, null);
                }
            } else if (DATA_TYPE_NUMBER.equalsIgnoreCase(dataType)) {
                if (context.get(dataRef) != null) {
                    BigDecimal dataValue = new BigDecimal((String) context.get(dataRef));
                    result.put(dataName, dataValue);
                } else {
                    result.put(dataName, null);
                }
            } else if (DATA_TYPE_OBJECT.equalsIgnoreCase(dataType)) {
                List<DataElement> elements = dataElement.getElements();
                Map<String, Object> dataValue = (Map<String, Object>) context.get(dataRef);
                if (CollectionUtils.isEmpty(elements)) {
                    result.put(dataName, dataValue);
                } else {
                    Map<String, Object> valueMap = Maps.newHashMap();
                    valueMap = format(dataValue, elements, valueMap);
                    result.put(dataName, valueMap);
                }
            } else if (DATA_TYPE_ARRAY.equalsIgnoreCase(dataType)) {
                List<DataElement> elements = dataElement.getElements();
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) context.get(dataRef);
                if (CollectionUtils.isEmpty(elements)) {
                    result.put(dataName, mapList);
                } else {
                    List<Map<String, Object>> mapListResult = Lists.newArrayList();
                    for (Map<String, Object> valueMap : mapList) {
                        Map<String, Object> newVauleMap = Maps.newHashMap();
                        newVauleMap = format(valueMap, elements, newVauleMap);
                        mapListResult.add(newVauleMap);
                    }
                    result.put(dataName, mapListResult);
                }
            } else {

            }

        }

        return result;
    }

    public Map<String, Object> unformat(Map<String, Object> dataMap, List<DataElement> dataElements,
            Map<String, Object> result) {

        for (DataElement dataElement : dataElements) {
            String dataName = dataElement.getDataName();
            String dataType = dataElement.getDataType();
            String dataRef = dataElement.getDataRef();
            if (DATA_TYPE_STRING.equalsIgnoreCase(dataType)) {
                String dataValue = (String) dataMap.get(dataName);
                result.put(dataRef, dataValue);
            } else if (DATA_TYPE_DATE.equalsIgnoreCase(dataType)) {
                String dataFormat = dataElement.getDataFormat() == null ? DATE_DEFAULT_FORMAT : dataElement
                        .getDataFormat();
                if (dataMap.get(dataName) != null) {
                    String dataValue = new DateTime(dataMap.get(dataName)).toString(dataFormat);
                    result.put(dataRef, dataValue);
                } else {
                    result.put(dataRef, null);
                }
            } else if (DATA_TYPE_NUMBER.equalsIgnoreCase(dataType)) {
                if (dataMap.get(dataName) != null) {
                    BigDecimal dataValue = new BigDecimal((String) dataMap.get(dataName));
                    result.put(dataRef, dataValue);
                } else {
                    result.put(dataRef, null);
                }
            } else if (DATA_TYPE_OBJECT.equalsIgnoreCase(dataType)) {
                List<DataElement> elements = dataElement.getElements();
                Map<String, Object> tempDataMap = (Map<String, Object>) dataMap.get(dataName);
                if (CollectionUtils.isEmpty(elements)) {
                    result.putAll(dataMap);
                } else {
                    Map<String, Object> valueMap = Maps.newHashMap();
                    valueMap = unformat(tempDataMap, elements, valueMap);
                    result.putAll(valueMap);
                }
            } else if (DATA_TYPE_ARRAY.equalsIgnoreCase(dataType)) {
                List<DataElement> elements = dataElement.getElements();
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) dataMap.get(dataName);
                if (CollectionUtils.isEmpty(elements)) {
                    result.put(dataRef, mapList);
                } else {
                    List<Map<String, Object>> mapListResult = Lists.newArrayList();
                    for (Map<String, Object> valueMap : mapList) {
                        Map<String, Object> newVauleMap = Maps.newHashMap();
                        newVauleMap = unformat(valueMap, elements, newVauleMap);
                        mapListResult.add(newVauleMap);
                    }
                    result.put(dataRef, mapListResult);
                }
            } else {

            }

        }

        return result;
    }
}
