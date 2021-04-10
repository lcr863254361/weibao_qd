package com.orient.modeldata.fileHandle.decorator;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.fileHandle.bean.FileHandleTarget;
import com.orient.sysmodel.domain.file.CwmFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * office文件转pdf文件（因为预览office准备采用jqueryMedia替换PageOffice）
 *
 * Created by GNY on 2018/4/27
 */
@Component
public class Office2PDFDecorator extends ModelFileDecorator {

    @Autowired
    FileServerConfig fileServerConfig;

    @Override
    public void doHandleFile(FileHandleTarget fileHandleTarget) {
        super.doHandleFile(fileHandleTarget);
        CwmFile fileDesc = fileHandleTarget.getFileDesc();
        String fileName = fileDesc.getFinalname();
        //只有office类型的文件才转为pdf，pdf用于文件预览
        if (fileName.endsWith(".doc") || fileName.endsWith(".docx") || fileName.endsWith(".xls")
                || fileName.endsWith(".xlsx") || fileName.endsWith(".ppt") || fileName.endsWith(".pptx")) {
            //获取上传文件在服务器上的相对路径--Modifiedby ZhangSheng 2018.9.3
            String location = fileDesc.getFilelocation();
            String realFileStoragePath = fileServerConfig.getFtpHome() + location;
            try {
                Document doc = new Document(realFileStoragePath);
                doc.save(fileServerConfig.getFtpHome() + location.substring(0, fileName.lastIndexOf(".")) + ".pdf", SaveFormat.PDF);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
