package com.orient.mongorequest.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by GNY on 2018/6/13
 */
public class MatrixDataPlotUtil {

    private static final String TABLE = "数据表"; // 表格名称
    private String directory;//文件路径

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public MatrixDataPlotUtil(String directory){
        this.directory = directory;
    }

    public void init() throws IOException {
        String dataFolder = directory ;
        new File(dataFolder).mkdirs();
    }

    /**
     * 制导数据分析写XML
     * @param tableName 表名
     * @param rowCount 记录数
     * @param typeList 每个列类型
     * @param pathList 每列对应数据.dat
     * @param colList 列集合
     */
    public  void writeXml (String tableName,Integer rowCount,List<String> typeList,List<String> pathList,List<String> colList) throws IOException{
        Document doc = DocumentHelper.createDocument();
        Element rootEL = doc.addElement("edmpost");
        rootEL.addAttribute("version", "1.0");
        rootEL.addAttribute("name",tableName);
        Element scripting = rootEL.addElement("scripting");
        scripting.setText("muParser");
        Element windownumEL = rootEL.addElement("windownum");
        windownumEL.setText("1");
        Element tableEL = rootEL.addElement("table");
        tableEL.addAttribute("name", TABLE);
        Element dataEL = tableEL.addElement("data");
        dataEL.addAttribute("rowcount", String.valueOf(rowCount));
        dataEL.addAttribute("columncount", String.valueOf(colList.size()));
        for(int i = 0;i<pathList.size();i++){
            Element column = dataEL.addElement("column");
            column.addAttribute("readonly","0");
            column.addAttribute("type", "double");
            column.addAttribute("name",colList.get(i));
            column.addAttribute("file", pathList.get(i));
        }
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        XMLWriter xmlWriter = new XMLWriter(new BufferedOutputStream(
                new FileOutputStream(directory+File.separator+tableName+".xml")), format);
        xmlWriter.write(doc);
        xmlWriter.close();
    }

}
