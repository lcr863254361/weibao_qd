package com.orient.weibao.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2020-09-25 15:55
 */
public class ZipFileUtils {
    //目的地Zip文件
    private String zipFileName;
    //源文件（待压缩的文件或文件夹）
    private String sourceFileName;
    public ZipFileUtils(String zipFileName, String sourceFileName){
        this.zipFileName=zipFileName;
        this.sourceFileName=sourceFileName;
    }
    public void zip() throws Exception{
        System.out.println("压缩中");
        File file=new File(zipFileName+".zip");
        if (!file.exists()){
            file.createNewFile();
        }
        //创建zip输出流
        ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipFileName+".zip"));
        //创建缓冲输出流
        BufferedOutputStream bos=new BufferedOutputStream(out);
        File sourceFile=new File(sourceFileName);
        //调用函数
        compress(out,bos,sourceFile,sourceFile.getName());
        bos.close();
        out.close();
        System.out.println("压缩完成！");

    }

    public void compress(ZipOutputStream out,BufferedOutputStream bos,File sourceFile,String base) throws Exception{
        //如果路径为目录（文件夹）
        if (sourceFile.isDirectory()){
            //取出文件夹中的文件（或子文件夹）
            File[] fileList=sourceFile.listFiles();
            if (fileList.length==0){
                //如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
                System.out.println(base+"/");
                out.putNextEntry(new ZipEntry(base+"/"));
            }else{
                //如果文件夹不为空，则递归调用compress方法，文件夹中的每一个文件（或文件夹）进行压缩
                for (int i=0;i<fileList.length;i++){
                    compress(out,bos,fileList[i],base+"/"+fileList[i].getName());
                }
            }
        }else{
            //如果不是目录（文件夹）,即为文件，则先写入目录进入点，之后将文件写入zip文件中
            out.putNextEntry(new ZipEntry(base));
            FileInputStream fos=new FileInputStream(sourceFile);
            BufferedInputStream bis=new BufferedInputStream(fos);
            int tag;
            System.out.println(base);
            //将源文件写入到zip文件中
             while ((tag=bis.read())!=-1){
                out.write(tag);
            }
            bis.close();
            fos.close();
        }
    }

}
