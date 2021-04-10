package com.orient.modeldata.analyze.config;

import com.orient.modeldata.analyze.bean.Configuration;
import com.orient.modeldata.analyze.strategy.AnalyzeStrategy;
import com.orient.modeldata.analyze.strategy.ZipAnalyzeStrategy;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-23 16:28
 */
public class ZipAnalyzeConfig extends AnalyzeConfig {
    private Configuration config;
    private String rowSplit;
    private String columnSplit;

    public ZipAnalyzeConfig(Configuration config) {
        super(config);
        this.config = config;
        //rowSplit
        String rowSplit = config.getRowSplit();
        if(rowSplit!=null && !"".equals(rowSplit)) {
            this.rowSplit = rowSplit;
        }
        else {
            this.rowSplit = "\r\n";
        }
        //columnSplit
        String columnSplit = config.getColumnSplit();
        if(columnSplit!=null && !"".equals(columnSplit)) {
            this.columnSplit = columnSplit;
        }
        else {
            this.columnSplit = "\t";
        }
    }

    @Override
    public AnalyzeStrategy buildAnalyzeStrategy() {
        return new ZipAnalyzeStrategy(this);
    }

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
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
}
