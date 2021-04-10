package com.orient.weibao.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-05-15 11:11
 */
@Service
public class PrintImage {
    private static final Logger logger= LoggerFactory.getLogger(PrintImage.class);
    public void drawImage(String fileName){
        try{
            //设置打印格式
            DocFlavor dof=null;
            if (fileName.endsWith(".gif")){
                dof=DocFlavor.INPUT_STREAM.GIF;
            }else if (fileName.endsWith(".jpg")){
                dof=DocFlavor.INPUT_STREAM.JPEG;
            }else if (fileName.endsWith(".png")){
                dof=DocFlavor.INPUT_STREAM.PNG;
            }
            //查找所有的可用的打印服务，定位默认的打印服务
            PrintService ps= PrintServiceLookup.lookupDefaultPrintService();
            //构建打印请求属性集
            PrintRequestAttributeSet pras=new HashPrintRequestAttributeSet();
            pras.add(OrientationRequested.PORTRAIT);
            pras.add(new Copies(1));
            pras.add(PrintQuality.HIGH);
            DocAttributeSet das=new HashDocAttributeSet();

            //设置打印纸张的大小（以毫米为单位）
            das.add(new MediaPrintableArea(0,0,400,300,MediaPrintableArea.MM));
            //构造待打印的文件流
            FileInputStream fin=new FileInputStream(fileName);

            Doc doc=new SimpleDoc(fin,dof,das);
            //创建打印作业
            DocPrintJob job=ps.createPrintJob();

            job.print(doc,pras);
            fin.close();
            logger.info("打印成功！文件："+fileName+"数量为："+1);
        }catch (IOException ie){
            ie.printStackTrace();
        }catch (PrintException pe){
            pe.printStackTrace();
        }
    }
}
