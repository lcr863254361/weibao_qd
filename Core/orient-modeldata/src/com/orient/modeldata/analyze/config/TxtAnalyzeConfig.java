package com.orient.modeldata.analyze.config;

import com.orient.modeldata.analyze.bean.ColMapDesc;
import com.orient.modeldata.analyze.bean.Configuration;
import com.orient.modeldata.analyze.strategy.AnalyzeStrategy;
import com.orient.modeldata.analyze.strategy.CsvAnalyzeStrategy;
import com.orient.modeldata.analyze.strategy.TxtAnalyzeStrategy;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-23 16:28
 */
public class TxtAnalyzeConfig extends AnalyzeConfig {
    private String rowSplit = "\r\n";
    private String columnSplit = "\t";

    public TxtAnalyzeConfig(Configuration config) {
        super(config);

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
        return new TxtAnalyzeStrategy(this);
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
