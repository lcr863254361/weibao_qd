package com.orient.modeldata.dataanalyze.Strategy;

import com.orient.utils.ExcelUtil.reader.TableEntity;

import java.io.File;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-08 10:33
 */
public interface AnalyzeStrategy {
    TableEntity doAnalyzeData(File file);
}
