package com.orient.modeldata.dataanalyze.analyzeContext;

import com.orient.modeldata.dataanalyze.Strategy.AnalyzeStrategy;
import com.orient.modeldata.dataanalyze.Strategy.impl.ExcelAnalyzeStrategy;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.FileOperator;

import java.io.File;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-08 10:42
 */
public class AnalyzeContext {

    private AnalyzeStrategy analyzeStrategy;

    private File file;

    public AnalyzeContext(File file) {
        this.file = file;
        //获取上传文件后缀
        String fileSuffix = FileOperator.getSuffix(file.getName());
        if ("xls".equals(fileSuffix) || "xlsx".equals(fileSuffix)) {
            analyzeStrategy = new ExcelAnalyzeStrategy();
        }
    }

    public TableEntity doAnalyzeFile() {
        return this.analyzeStrategy.doAnalyzeData(file);
    }
}
