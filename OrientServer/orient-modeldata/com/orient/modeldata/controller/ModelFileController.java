package com.orient.modeldata.controller;

import com.orient.edm.init.FileServerConfig;
import com.orient.modeldata.business.ModelFileBusiness;
import com.orient.modeldata.fileHandle.OrientModelFileHandle;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.service.file.FileService;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.CommonResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.form.model.FileModel;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * 数据管理控制层
 *
 * @author enjoy
 * @creare 2016-04-01 9:45
 */
@Controller
@RequestMapping("/modelFile")
public class ModelFileController {


    @Autowired
    ModelFileBusiness modelFileBusiness;

    @Autowired
    OrientModelFileHandle orientModelFileHandle;

    @Autowired
    FileService fileService;

    @Autowired
    FileServerConfig fileServerConfig;

    /**
     * 获取文件列表
     *
     * @param modelId
     * @param dataId
     * @param fileGroupId
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public ExtGridData<FileModel> list(String modelId, String dataId, String fileGroupId, Integer page, Integer limit) {
        ExtGridData<FileModel> retVal = new ExtGridData<>();
        retVal.setTotalProperty(modelFileBusiness.count(modelId, dataId, fileGroupId));
        retVal.setResults(modelFileBusiness.list(modelId, dataId, fileGroupId, page, limit));
        return retVal;
    }

    @RequestMapping("getFileInfoById")
    @ResponseBody
    public ExtGridData<FileModel> getFileInfoById(String fileId) {
        ExtGridData<FileModel> retVal = new ExtGridData<>();
        retVal.setTotalProperty(1);
        retVal.setResults(modelFileBusiness.getFileInfoById(fileId));
        return retVal;
    }

    @RequestMapping("create")
    @ResponseBody
    public AjaxResponseData<FileModel> create(MultipartFile file, String modelId, String dataId, String fileCatalog, String desc, String secrecy) {
        AjaxResponseData<FileModel> responseData = new AjaxResponseData();
        FileModel fileModel = new FileModel();
        CwmFile savedFile = modelFileBusiness.saveUploadFile(file, modelId, dataId, fileCatalog, desc, secrecy);
        //文件处理
        orientModelFileHandle.doHandleModelFile(modelId, dataId, savedFile);
        try {
            BeanUtils.copyProperties(fileModel, savedFile);
            responseData.setResults(fileModel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return responseData;
    }

    /**
     * 删除表格
     *
     * @param toDelIds
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public CommonResponseData delete(String[] toDelIds) {
        CommonResponseData retVal = new CommonResponseData();
        modelFileBusiness.delete(toDelIds);
        retVal.setMsg("删除成功");
        return retVal;
    }

   /* @RequestMapping("preview")
    public String preview(HttpServletRequest request, String fileId) {
        //获取文件信息
        String fileJspPath = modelFileBusiness.getPreviewFileJsp(fileId, request);
        return "redirect:" + PageOfficeLink.openWindow(request, fileJspPath, "width=800px;height=800px;");
    }*/

    @RequestMapping("isConverCompleted")
    @ResponseBody
    public AjaxResponseData<Boolean> isConverCompleted(String fileId) {
        Boolean retVal = modelFileBusiness.checkConverCompleted(fileId);
        return new AjaxResponseData<>(retVal);
    }

    /**
     * 跳转到/app/views/file/PDFPreviewByJqueryMedia.jsp
     * create by gny 2018-4-27
     *
     * @param fileId
     * @return
     */
    @RequestMapping("/preview")
    public ModelAndView preview(String fileId) {
        return modelFileBusiness.preview(fileId);
    }

    /**
     * 获取pdf文件的文件流
     * create by gny 2018-4-27
     *
     * @param fileId
     * @param response
     */
    @RequestMapping("/showPDF")
    public void showWorkItemPhoto(String fileId, HttpServletResponse response) {
        OutputStream os;
        CwmFile cwmFile = fileService.findFileById(fileId);
        String fileName = cwmFile.getFilelocation();
        String filePath;
        if (fileName.endsWith(".pdf")) {
            filePath = fileServerConfig.getFtpHome() + fileName;
        } else {
            filePath = fileServerConfig.getFtpHome() + fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
        }
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(filePath));
            os = response.getOutputStream();
            int cnt = 0;
            byte[] bytes = new byte[1024 * 4];
            while ((cnt = fis.read(bytes)) != -1) {
                os.write(bytes, 0, cnt);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到/app/views/file/uploadFile.jsp
     * create by gny 2018-4-27
     *
     * @return
     */
    @RequestMapping("/addFile")
    public ModelAndView addFile(String modelId, String dataId) {
        ModelAndView retVal = new ModelAndView();
        retVal.setViewName("/app/views/file/h5FileUpload.jsp");
        retVal.addObject("modelId", modelId);
        retVal.addObject("dataId", dataId);
        return retVal;
    }

}
