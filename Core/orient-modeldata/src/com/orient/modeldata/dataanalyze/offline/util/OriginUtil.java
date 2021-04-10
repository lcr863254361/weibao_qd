package com.orient.modeldata.dataanalyze.offline.util;

import com.csvreader.CsvWriter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-07 14:31
 */
public class OriginUtil {
    private IBusinessModel mainModel;
    private List<Map> oriDataList;
    private String name;
    private String path;

    private List<String> colNames = new ArrayList<>();
    private List<String> heads = new ArrayList<>();
    private List<List<String>> dataList = new ArrayList<>();

    public OriginUtil(IBusinessModel mainModel, List<Map> oriDataList, String ftpHome) {
        this.mainModel = mainModel;
        this.oriDataList = oriDataList;

        this.name = ""+System.currentTimeMillis();
        this.path = ftpHome + this.name;
        File file = new File(this.path);
        if (!file.exists()) {
            file.mkdir();
        }

        for(IBusinessColumn col : mainModel.getAllBcCols()) {
            this.colNames.add(col.getS_column_name());
            this.heads.add(col.getDisplay_name());
        }
    }

    public void readData() {
        for(Map<String, Object> data : oriDataList) {
            List<String> list = new ArrayList<>();
            for(String colName : colNames) {
                Object obj = data.get(colName);
                list.add(CommonTools.Obj2String(obj));
            }
            this.dataList.add(list);
        }
    }

    public String generateZipFile() {
        generateCsvFile(this.path+File.separator+mainModel.getS_table_name()+".csv");
        try {
            FileOperator.zip(this.path, this.path+".zip", "");
            FileOperator.delFoldsWithChilds(this.path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return this.name+".zip";
    }

    private void generateCsvFile(String csvPath) {
        try {
            CsvWriter writer = new CsvWriter(csvPath, ',', Charset.forName("GBK"));
            for(String col : this.heads) {
                writer.write(col);
            }
            writer.endRecord();

            for(List<String> data : this.dataList) {
                for(String col : data) {
                    writer.write(col);
                }
                writer.endRecord();
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
