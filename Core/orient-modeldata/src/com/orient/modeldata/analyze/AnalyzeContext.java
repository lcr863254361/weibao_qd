package com.orient.modeldata.analyze;

import com.orient.modeldata.analyze.bean.Configuration;
import com.orient.modeldata.analyze.config.*;
import com.orient.modeldata.analyze.bean.ColMapDesc;
import com.orient.modeldata.analyze.strategy.AnalyzeStrategy;
import com.orient.utils.FileOperator;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-08 10:42
 */
public class AnalyzeContext {
    private AnalyzeConfig analyzeConfig;

    private AnalyzeStrategy analyzeStrategy;

    private List<String> head;

    private List<Map<String, Object>> content;

    /*
    conf示例：{
        filePath: "D:/ftpHome/xxx.txt",
        hasHead: "true",
        startLine: "10",
        endLine: "100",
        rowSplit: "\r\n",
        columnSplit: "\t",
        analyzeType: "preview",
        tableName: "T_PRODUCT_110"
    }
    relationMap示例：{
        "C_NAME_120": {
            fileColName: "产品",
            timeFormat: null,
            defalutValue: null,
            standardFileColName: null,
            dbColName: "C_NAME_120",
            dbColType: "C_Simple"
        },
        "C_PRODUCT_TIME_120": {
            fileColName: "生产时间",
            timeFormat: "yyyy-MM-dd hh:mm:ss.SSS",
            defalutValue: null,
            standardFileColName: null,
            dbColName: "C_PRODUCT_TIME_120",
            dbColType: "C_DateTime"
        },
        "T_FACTORY_110_ID": {
            fileColName: "所属工厂",
            timeFormat: null,
            defalutValue: "1",
            standardFileColName: null,
            dbColName: "T_FACTORY_110_ID",
            dbColType: "C_Relation"
        }
    }
     */
    public AnalyzeContext(Configuration config) throws FileNotFoundException {
        String jarPath = config.getJarPath();
        if(jarPath!=null && !"".equals(jarPath)) {
            if(!FileOperator.isFileExist(jarPath)) {
                throw new FileNotFoundException("文件未找到："+jarPath);
            }
            this.analyzeConfig = new JarBasedAnalyzeConfig(config);
        }
        else {
            String filePath = config.getFilePath();
            if(!FileOperator.isFileExist(filePath)) {
                throw new FileNotFoundException("文件未找到："+filePath);
            }
            String subfix = FileOperator.getSuffix(filePath);
            if("xls".equals(subfix) || "xlsx".equals(subfix)) {
                this.analyzeConfig = new ExcelAnalyzeConfig(config);
            }
            else if("csv".equals(subfix)) {
                this.analyzeConfig = new CsvAnalyzeConfig(config);
            }
            else if("txt".equals(subfix)) {
                this.analyzeConfig = new TxtAnalyzeConfig(config);
            }
            else if("zip".equals(subfix)) {
                this.analyzeConfig = new ZipAnalyzeConfig(config);
            }
        }

        this.analyzeStrategy = this.analyzeConfig.buildAnalyzeStrategy();
    }

    public List<String> doHeadAnalyze() {
        if(this.head == null) {
            this.head = this.analyzeStrategy.doHeadAnalyze();
        }
        return this.head;
    }

    public List<Map<String, Object>> doContentAnalyze() {
        if(this.content == null) {
            this.content = this.analyzeStrategy.doContentAnalyze();
        }
        return this.content;
    }
}
