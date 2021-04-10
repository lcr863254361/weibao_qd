package com.orient.sysman.bussiness;

import com.orient.edm.init.FileServerConfig;
import com.orient.utils.CommonTools;
import com.orient.utils.FileOperator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by qjs on 2017/1/19.
 * 用来备份日志的业务类
 */
public class FileExportService {
    private boolean isExportExcel = false;
    private List messageList = new ArrayList();
    private String rootPath = "";//保存导出日志文件的根路径 现在在项目根路径下 fileServerConfig.getFtpHome() + File.separator + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    private String subPath = "";
    private String rootName = "";
    private String retFileName = "";
    public FileExportService(boolean isExportExcel,List messageList,String subPath,String rootName,FileServerConfig fileServerConfig,boolean isAll)
    {
        this.isExportExcel = isExportExcel;
        this.messageList = messageList;
        this.subPath = subPath;
        if(isAll) {
            this.rootPath = fileServerConfig.getFtpHome() + File.separator + "系统日志备份" + File.separator + this.subPath.substring(0,4) + File.separator +
                    this.subPath.substring(4,6) + File.separator + this.subPath.substring(6,8) + File.separator + "压缩";//CommonTools.getRootPath()+"/"+"file"+"/"+this.subPath;
        } else {
            this.rootPath = fileServerConfig.getFtpHome() + File.separator + "系统日志备份" + File.separator + this.subPath.substring(0,4) + File.separator +
                    this.subPath.substring(4,6) + File.separator + this.subPath.substring(6,8);//CommonTools.getRootPath()+"/"+"file"+"/"+this.subPath;
        }
        //File fileDir = new File(fileServerConfig.getFtpHome() + File.separator + "系统日志备份");//new File(CommonTools.getRootPath()+"/"+"file");
        FileOperator.createFolds(rootPath + File.separator);
//        if(!fileDir.exists())
//        {
//            fileDir.mkdir();
//        }
        this.rootName = rootName;
    }


    public String getRootPath() {
        return rootPath;
    }


    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getRetFileName() {
        return retFileName;
    }

    /**
     * @func:导出文件
     * @return true/false
     */
    public boolean exportFile()
    {
        if(!"".endsWith(this.subPath))
        {
            File file = new File(rootPath);
            if(!file.exists())
            {
                file.mkdir();
            }
        }
        if(this.isExportExcel)	//保存为Excel文件
        {
            return saveExcelFile(rootPath,this.messageList,this.rootName);
        }
        else					//保存为Txt文件
        {
            return saveTxtFile(rootPath,this.messageList,this.rootName);
        }
    }

    /**
     * @func 用于保存Txt文件
     * @param path
     * @param valueList
     * @return true/false
     */
    private boolean saveTxtFile(String path,List valueList,String name)
    {
        String fileMsg = "";
        for(int i=0;i<valueList.size();i++)
        {
            String oneMsg = "";
            Map mapValue = (Map)valueList.get(i);
            Iterator iterator = mapValue.entrySet().iterator();
            while(iterator.hasNext())
            {
                Map.Entry<String, String> next = (Map.Entry<String, String>)iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                if("".equals(oneMsg))
                {
                    oneMsg = "["+key+","+value+"]";
                }
                else
                {
                    oneMsg = oneMsg + " ["+key+","+value+"]";
                }
            }
            if("".equals(fileMsg))
            {
                fileMsg = oneMsg;
            }
            else
            {
                fileMsg = fileMsg + "\r\n" +oneMsg;
            }
        }
        try
        {
            retFileName = path + "/" + name + ".txt";
            File file = new File(retFileName);
            FileWriter fw = new FileWriter(file);
            fw.write(fileMsg);
            fw.flush();
            fw.close();
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @func 用于保存Excel文件
     * @param path
     * @param valueList
     * @return
     */
    private boolean saveExcelFile(String path,List valueList,String name)
    {
        HSSFWorkbook workbook = new HSSFWorkbook();//excel文件对象
        HSSFSheet createSheet = createSheet(workbook,name);
        for(int i=0;i<valueList.size();i++)//获取每一条数据
        {
            HSSFRow typeRow = null;
            if(i == 0)
            {
                typeRow = createRow(createSheet,i);
            }
            HSSFRow createRow = createRow(createSheet,i+1);
            Map mapValue = (Map)valueList.get(i);
            Iterator iterator = mapValue.entrySet().iterator();
            int j = 0;
            while(iterator.hasNext())//获取每一个数据
            {
                Map.Entry<String, String> next = (Map.Entry<String, String>)iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                if(i == 0)
                {
                    createCell(typeRow,i,j,key,value);
                }
                createCell(createRow,i+1,j,key,value);
                j++;
            }
        }
        try {

            retFileName = path + "/" + name + ".xls";
            FileOutputStream out = new FileOutputStream(retFileName);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    private HSSFSheet createSheet(HSSFWorkbook workbook,String name)
    {
        return workbook.createSheet(name);
    }

    private HSSFRow createRow(HSSFSheet sheet,int row)
    {
        return sheet.createRow(row);
    }

    private boolean createCell(HSSFRow row,int rownum,int colnum,String key,String value)
    {
        if(rownum == 0)
        {
            HSSFCell createCell = row.createCell(colnum);
            createCell.setCellValue(key);
        }
        else
        {
            HSSFCell createCell = row.createCell(colnum);
            createCell.setCellValue(value);
        }
        return true;
    }

    public boolean zipFile(String from)
    {
        //进行文件压缩
        try
        {
            if(this.isExportExcel)
            {
                FileOperator.zip(from, from+"(xls).zip", "");
            }
            else
            {
                FileOperator.zip(from, from+"(txt).zip", "");
            }
            FileOperator.delFoldsWithChilds(from);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
}
