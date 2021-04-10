package com.orient.modeldata.analyze.processor;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-24 9:36
 */
public abstract class Processor {
    private int colNum;
    private String fileColName;
    private String mappedColName;

    public abstract void process(List<String> rawData, Map<String, Object> destMap);

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public String getFileColName() {
        return fileColName;
    }

    public void setFileColName(String fileColName) {
        this.fileColName = fileColName;
    }

    public String getMappedColName() {
        return mappedColName;
    }

    public void setMappedColName(String mappedColName) {
        this.mappedColName = mappedColName;
    }
}
