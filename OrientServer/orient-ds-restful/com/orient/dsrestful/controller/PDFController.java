package com.orient.dsrestful.controller;

import com.orient.edm.init.FileServerConfig;
import com.orient.sysmodel.domain.file.CwmFile;
import com.orient.sysmodel.service.file.FileService;
import com.orient.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * Created by GNY on 2018/4/25
 */
@Controller
@RequestMapping("/test")
public class PDFController extends BaseController {

    @Autowired
    FileService fileService;

    @Autowired
    FileServerConfig fileServerConfig;

    @RequestMapping("/previewPDF")
    public ModelAndView previewPDF(String fileId) {
        ModelAndView retVal = new ModelAndView();
        retVal.addObject("fileId", fileId);
        retVal.setViewName("/app/views/PDFView.jsp");
        return retVal;
    }

    @RequestMapping("/showPDF")
    public void showWorkItemPhoto(String fileId, HttpServletResponse response) {
        OutputStream os;
        CwmFile cwmFile = fileService.findFileById(fileId);
        String fileName = cwmFile.getFilelocation();
        String filePath = null;
        if (fileName.endsWith(".pdf")) {
            filePath = fileServerConfig.getFtpHome() + fileName;
        } else {
            filePath = fileServerConfig.getFtpHome()+fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
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

}
