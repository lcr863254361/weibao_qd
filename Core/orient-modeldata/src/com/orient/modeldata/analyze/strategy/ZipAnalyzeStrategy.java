package com.orient.modeldata.analyze.strategy;

import com.orient.modeldata.analyze.AnalyzeContext;
import com.orient.modeldata.analyze.bean.Configuration;
import com.orient.modeldata.analyze.config.ZipAnalyzeConfig;
import com.orient.utils.FileOperator;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-08 10:41
 */
public class ZipAnalyzeStrategy extends AnalyzeStrategy {
    private String rowSplit = "\r\n";
    private String columnSplit = "\t";
    private List<AnalyzeContext> analyzeContexts = new ArrayList<>();

    public ZipAnalyzeStrategy(ZipAnalyzeConfig config) {
        super(config);
        this.rowSplit = config.getRowSplit();
        this.columnSplit = config.getColumnSplit();

        try {
            String unzipPath = System.getProperty("user.dir")+File.separator+System.currentTimeMillis();
            FileOperator.unZip(config.getFile().getAbsolutePath(), unzipPath);
            List<String> files = new FileOperator().getAllFileWithPath(unzipPath);
            for(String file : files) {
                Configuration conf = (Configuration) BeanUtils.cloneBean(config.getConfig());
                conf.setFilePath(file);
                this.analyzeContexts.add(new AnalyzeContext(conf));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> doHeadAnalyze() {
        if(analyzeContexts.size() > 0) {
            return analyzeContexts.get(0).doHeadAnalyze();
        }
        else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Map<String, Object>> doContentAnalyze() {
        List<Map<String, Object>> retList = new ArrayList<>();
        for(AnalyzeContext analyzeContext : analyzeContexts) {
            retList.addAll(analyzeContext.doContentAnalyze());
        }
        return retList;
    }
}
