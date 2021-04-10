package com.orient.modeldata.dataanalyze.Strategy.impl;

import com.orient.modeldata.dataanalyze.Strategy.AnalyzeStrategy;
import com.orient.utils.ExcelUtil.reader.ExcelReader;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.FileOperator;

import java.io.File;
import java.io.FileInputStream;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-08 10:41
 */
public class ExcelAnalyzeStrategy implements AnalyzeStrategy {

    @Override
    public TableEntity doAnalyzeData(File file) {
        TableEntity retVal = new TableEntity();
        ExcelReader excelReader = new ExcelReader();
        //是否是2007以后的excel文件
        Boolean after2007 = FileOperator.getSuffix(file.getName()).equals("xlsx") ? true : false;
        //读取文件
        try {
            retVal = excelReader.readFile(new FileInputStream(file), after2007);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
