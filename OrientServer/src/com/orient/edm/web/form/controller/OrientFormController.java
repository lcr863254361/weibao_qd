package com.orient.edm.web.form.controller;


import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.business.ModelFileBusiness;
import com.orient.modeldata.util.FileEncrypt;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.utils.BeanUtils;
import com.orient.utils.FileOperator;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.ExtComboboxData;
import com.orient.web.base.ExtComboboxResponseData;
import com.orient.web.form.model.FileModel;
import com.orient.web.form.service.IFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by enjoy on 2016/3/21 0021.
 */
@Controller
@RequestMapping("/orientForm")
public class OrientFormController {

    @Autowired
    IFormService formService;

    @Autowired
    FileServerConfig fileServerConfig;

    @Autowired
    ModelFileBusiness modelFileBusiness;


    @RequestMapping("getModelCombobox")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getModelCombobox(Integer startIndex, Integer maxResults, String filter, String id) {
        ExtComboboxResponseData<ExtComboboxData> retValue = formService.getModelComboboxCollection(startIndex, maxResults, filter, id);
        return retValue;
    }

    @RequestMapping("getEnumCombobox")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getEnumCombobox(String restrictionId) {
        ExtComboboxResponseData<ExtComboboxData> retValue = formService.getEnumComboboxCollection(restrictionId);
        return retValue;
    }


    @RequestMapping("download")
    public void download(String fileId, HttpServletRequest request,
                         HttpServletResponse response) {
        FileModel fileModel = formService.getUploadFilePath(fileId);
        if(FileEncrypt.NONE.equals(fileServerConfig.getDecrypt())) {
            FileOperator.downLoadFile(request, response, fileModel.getFilePath(), fileModel.getFilename());
        }
        else {
            File srcFile = new File(fileModel.getFilePath());
            File decFile = new File(fileModel.getFilePath()+".decrypt");
            //解密
            File temp = FileEncrypt.decrypt(srcFile, decFile, fileServerConfig.getDecrypt());
            if(temp == null) {
                return;
            }
            FileOperator.downLoadFile(request, response, temp.getAbsolutePath(), fileModel.getFilename());
            temp.delete();
        }
    }

    @RequestMapping("downloadByName")
    public void downloadByName(String fileName, HttpServletRequest request,
                               HttpServletResponse response) {
        if(FileEncrypt.NONE.equals(fileServerConfig.getDecrypt())) {
            FileOperator.downLoadFile(request, response, fileServerConfig.getFtpHome() + fileName, fileName);
        }
        else {
            File srcFile = new File(fileServerConfig.getFtpHome()+fileName);
            File decFile = new File(fileServerConfig.getFtpHome()+fileName+".decrypt");
            //解密
            File temp = FileEncrypt.decrypt(srcFile, decFile, fileServerConfig.getDecrypt());
            if(temp == null) {
                return;
            }
            FileOperator.downLoadFile(request, response, temp.getAbsolutePath(), fileName);
            temp.delete();
        }
    }

    @RequestMapping("videoDownload")
    public void videoDownload(String fileId, String fileName, String type, HttpServletRequest request, HttpServletResponse response) {
        String filePath = null;
        if(fileId!=null && !"".equals(fileId)) {
            CwmFile cwmFile = modelFileBusiness.getFileById(fileId);
            filePath = cwmFile.getFilelocation();
            fileName = cwmFile.getFinalname();
        }
        else {
            if(fileName.indexOf("\\") > -1) {
                filePath = fileName.substring(fileName.lastIndexOf("\\")+1);
            }
            else {
                filePath = fileName;
            }
        }
        if("ck".equals(type)) {
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".mp4";
            FileOperator.downLoadFile(request, response, fileServerConfig.getMp4Home() + File.separator + fileName, fileName);
        }
        else if("vlc".equals(type)) {
            FileOperator.downLoadFile(request, response, fileServerConfig.getFtpHome() + filePath, fileName);
        }
    }

    @RequestMapping("pngDownload")
    public void pngDownload(String fileId, String fileName, String type, HttpServletRequest request, HttpServletResponse response) {
        String filePath = null;
        if(fileId!=null && !"".equals(fileId)) {
            CwmFile cwmFile = modelFileBusiness.getFileById(fileId);
            filePath = cwmFile.getFilelocation();
            fileName = cwmFile.getFinalname();
        }
        else {
            if(fileName.indexOf("\\") > -1) {
                filePath = fileName.substring(fileName.lastIndexOf("\\")+1);
            }
            else {
                filePath = fileName;
            }
        }
        fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "." + type;
        FileOperator.downLoadFile(request, response, fileServerConfig.getMp4Home() + File.separator + fileName, fileName);
    }

    @RequestMapping("upload")
    @ResponseBody
    public AjaxResponseData<FileModel> upload(MultipartFile file, String fileCatalog) {
        AjaxResponseData<FileModel> responseData = new AjaxResponseData();
        FileModel fileModel = new FileModel();
        CwmFile savedFile = formService.saveUploadFile(file, fileCatalog);
        BeanUtils.copyProperties(fileModel, savedFile);
        responseData.setResults(fileModel);
        return responseData;
    }

}
