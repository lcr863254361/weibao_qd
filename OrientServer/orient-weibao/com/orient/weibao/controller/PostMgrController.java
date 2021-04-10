package com.orient.weibao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orient.log.annotion.Action;
import com.orient.utils.ExcelUtil.reader.ExcelReader;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.weibao.business.PostMgrBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-01-10 8:54
 */
@Controller
@RequestMapping("/PostMgrController")
public class PostMgrController extends BaseController {

    @Autowired
    PostMgrBusiness postMgrBusiness;

    @RequestMapping("deletePostData")
    @ResponseBody
    public AjaxResponseData deletePostData(String id) {
        AjaxResponseData retVal = new AjaxResponseData();
        postMgrBusiness.deletePostData(id);
        retVal.setSuccess(true);
        return retVal;
    }

    /**
     * 批量导入岗位
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("importPostData")
    @ResponseBody
    public void importPostData(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String fileName = null;
        //解析器解析request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //先判断request中是否包涵multipart类型的数据
        if (multipartResolver.isMultipart(request)) {
            //再将request中的数据转化成multipart类型的数据
            MultipartHttpServletRequest mutiRequest = (MultipartHttpServletRequest) request;
            Iterator iterator = mutiRequest.getFileNames();
            if (iterator.hasNext()) {
                MultipartFile file = mutiRequest.getFile((String) iterator.next());
                fileName = file.getOriginalFilename();
                File dst = new File(fileName);
                try {
                    file.transferTo(dst);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        ExcelReader excelReader=new ExcelReader();
        File excelFile=new File(fileName);
        InputStream inputStream=new FileInputStream(excelFile);
        boolean after2007=fileName.substring(fileName.length()-4).equals("xlsx");
        TableEntity excelEntity=excelReader.readFile(inputStream, after2007);
        Map<String,Object> retVal=postMgrBusiness.importPostData(excelEntity);
        try {
            response.setContentType("text/html");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), retVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        excelFile.delete();
    }

    /**
     * 导出岗位数据
     * @param exportAll
     * @param toExportIds
     * @param response
     */
    @RequestMapping("exportPostData")
    @ResponseBody
    public void exportPostData(boolean exportAll, String toExportIds, HttpServletResponse response) {
        postMgrBusiness.exportPostData(exportAll, toExportIds);
        try {
            response.setContentType("aplication/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("posts.xls", "UTF-8"));
            BufferedInputStream in = new BufferedInputStream(new FileInputStream("posts.xls"));
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
