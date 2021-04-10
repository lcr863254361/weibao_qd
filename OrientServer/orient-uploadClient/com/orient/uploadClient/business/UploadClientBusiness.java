package com.orient.uploadClient.business;

import com.orient.edm.init.FileServerConfig;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.service.file.FileService;
import com.orient.utils.FileOperator;
import com.orient.utils.xml.Dom4jUtil;
import com.orient.web.base.BaseBusiness;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */
@Component
public class UploadClientBusiness extends BaseBusiness {
    @Autowired
    protected FileService fileService;

    @Autowired
    FileServerConfig fileServerConfig;

    public boolean saveImportInfo(String userid, String xml){
        Document document = Dom4jUtil.loadXml(xml);
        Element root = document.getRootElement();
        Element data = root.element("data");
        String modelId = getAttributeValueByElementName(data, "modelid");
        String dataId = getAttributeValueByElementName(data,"dataid");
        String userId = getAttributeValueByElementName(data,"user_id");

        Element files = data.element("files");
        List<Element> fileList = files.elements("file");
        if ( fileList.size() == 0 )    return false;

        fileList.forEach(file ->{
            String fileUploadName = getAttributeValueByElementName(file,"fileUploadName");
            //CwmFile query = new CwmFile();
            String filelocation = File.separator + fileUploadName;
            List<CwmFile> rets = fileService.findFilesByFileLocation(filelocation);
            CwmFile cwmFile = rets.get(0);
//            cwmFile.setFilename(getAttributeValueByElementName(file,"fileName"));

            String date = getAttributeValueByElementName(file, "upload_date");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                cwmFile.setUploadDate(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

//            String finalName = fileUploadName.substring(fileUploadName.lastIndexOf(File.separator) + 1);
//            String realFileStoragePath = fileServerConfig.getFtpHome() + fileUploadName;
            //String filePath = realFileStoragePath.substring(0,realFileStoragePath.lastIndexOf(File.separator));
            //FileOperator.createFolds(filePath + File.separator);
//            File newFile = new File(realFileStoragePath);
//            cwmFile.setFilelocation(fileUploadName);
//            cwmFile.setFinalname(finalName);
//            cwmFile.setFilesize(newFile.length());
            cwmFile.setFilesecrecy(getAttributeValueByElementName(file,"fileGrade"));
            cwmFile.setFileMark(getAttributeValueByElementName(file,"fileMark"));

            cwmFile.setDataid(dataId);
            cwmFile.setTableid(modelId);
            cwmFile.setUploadUserId(userId);
            //cwmFile.setFileCatalog("common");入库时已设置为normal
            cwmFile.setUploadStatus(CwmFile.UPLOAD_STATUS_SUCCESS);

            fileService.updateFile(cwmFile);
        });
        return true;
    }

    public String getAttributeValueByElementName(Element element,String attribute) {
        return element.attributeValue(attribute);
    }

}
