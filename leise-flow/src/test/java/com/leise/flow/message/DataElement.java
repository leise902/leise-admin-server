package com.leise.flow.message;

import java.util.List;

public class DataElement {

    private String dataName;

    private String dataType;

    private String dataFormat;

    private String dataRef;

    private String dataDesc;
    
    private List<DataElement> elements;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getDataRef() {
        return dataRef;
    }

    public void setDataRef(String dataRef) {
        this.dataRef = dataRef;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public List<DataElement> getElements() {
        return elements;
    }

    public void setElements(List<DataElement> elements) {
        this.elements = elements;
    }

}
