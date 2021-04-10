package com.orient.modeldata.analyze.config;

import com.orient.modeldata.analyze.bean.ColMapDesc;
import com.orient.modeldata.analyze.bean.Configuration;
import com.orient.modeldata.analyze.strategy.AnalyzeStrategy;
import com.orient.modeldata.analyze.strategy.ExcelAnalyzeStrategy;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-23 16:28
 */
public class ExcelAnalyzeConfig extends AnalyzeConfig {

    public ExcelAnalyzeConfig(Configuration config) {
        super(config);
    }

    @Override
    public AnalyzeStrategy buildAnalyzeStrategy() {
        return new ExcelAnalyzeStrategy(this);
    }
}
