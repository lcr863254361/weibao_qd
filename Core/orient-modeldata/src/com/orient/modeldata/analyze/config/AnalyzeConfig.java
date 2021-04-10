package com.orient.modeldata.analyze.config;

import com.orient.modeldata.analyze.bean.ColMapDesc;
import com.orient.modeldata.analyze.bean.Configuration;
import com.orient.modeldata.analyze.strategy.AnalyzeStrategy;

import java.io.File;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-23 15:29
 */
public abstract class AnalyzeConfig {
    private File file;
    private boolean hasHead = true;
    private int startLine = 0;
    private int endLine = Integer.MAX_VALUE;
    private boolean isPreview = false;
    private boolean isFileOutput = false;
    private boolean isDBInput = false;

    private String tableName;
    private Map<String, ColMapDesc> relationMap;

    public abstract AnalyzeStrategy buildAnalyzeStrategy();

    public AnalyzeConfig(Configuration config) {
        //filePath
        String filePath = config.getFilePath();
        this.file = new File(filePath);
        //hasHead
        String hasHead = config.getHasHead();
        if (hasHead != null && ("1".equals(hasHead) || "true".equals(hasHead))) {
            this.hasHead = true;
        } else {
            this.hasHead = false;
        }
        //startLine
        String startLine = config.getStartLine();
        if (startLine != null && !"".equals(startLine)) {
            this.startLine = Integer.valueOf(startLine) - 1;
        } else {
            this.startLine = 0;
        }
        //endLine
        String endLine = config.getEndLine();
        if (endLine!=null && !"".equals(endLine) && !"-1".equals(endLine)) {
            this.endLine = Integer.valueOf(endLine) - 1;
        } else {
            this.endLine = Integer.MAX_VALUE;
        }
        //analyzeType
        String analyzeType = config.getAnalyzeType();
        if("preview".equals(analyzeType)) {
            this.isPreview = true;
            this.endLine = this.startLine + 24;
        }
        else if("fileOutput".equals(analyzeType)) {
            this.isFileOutput = true;
        }
        else if("dbInput".equals(analyzeType)) {
            this.isDBInput = true;
        }
        //tableName
        String tableName = config.getTableName();
        if(tableName != null && !"".equals(tableName)) {
            this.tableName = tableName;
        }
        //relationMap
        Map<String, ColMapDesc> relationMap = config.getRelationMap();
        if(relationMap != null && relationMap.size() > 0) {
            this.relationMap = relationMap;
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isHasHead() {
        return hasHead;
    }

    public void setHasHead(boolean hasHead) {
        this.hasHead = hasHead;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
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

    public boolean isPreview() {
        return isPreview;
    }

    public void setIsPreview(boolean isPreview) {
        this.isPreview = isPreview;
    }

    public boolean isFileOutput() {
        return isFileOutput;
    }

    public void setIsFileOutput(boolean isFileOutput) {
        this.isFileOutput = isFileOutput;
    }

    public boolean isDBInput() {
        return isDBInput;
    }

    public void setIsDBInput(boolean isDBInput) {
        this.isDBInput = isDBInput;
    }
}
