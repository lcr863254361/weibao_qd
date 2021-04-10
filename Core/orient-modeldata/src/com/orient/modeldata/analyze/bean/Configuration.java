package com.orient.modeldata.analyze.bean;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-27 16:54
 */
public class Configuration {
    private String filePath;
    private String jarPath;
    private String hasHead;
    private String startLine;
    private String endLine;
    private String rowSplit;
    private String columnSplit;
    private String analyzeType;
    private String tableName;
    private Map<String, ColMapDesc> relationMap;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getHasHead() {
        return hasHead;
    }

    public void setHasHead(String hasHead) {
        this.hasHead = hasHead;
    }

    public String getStartLine() {
        return startLine;
    }

    public void setStartLine(String startLine) {
        this.startLine = startLine;
    }

    public String getEndLine() {
        return endLine;
    }

    public void setEndLine(String endLine) {
        this.endLine = endLine;
    }

    public String getRowSplit() {
        return rowSplit;
    }

    public void setRowSplit(String rowSplit) {
        this.rowSplit = rowSplit;
    }

    public String getColumnSplit() {
        return columnSplit;
    }

    public void setColumnSplit(String columnSplit) {
        this.columnSplit = columnSplit;
    }

    public String getAnalyzeType() {
        return analyzeType;
    }

    public void setAnalyzeType(String analyzeType) {
        this.analyzeType = analyzeType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, ColMapDesc> getRelationMap() {
        return relationMap;
    }

    public void setRelationMap(Map<String, ColMapDesc> relationMap) {
        this.relationMap = relationMap;
    }
}
