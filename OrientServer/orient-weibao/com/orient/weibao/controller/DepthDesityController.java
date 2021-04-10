package com.orient.weibao.controller;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.utils.ExcelUtil.reader.ExcelReader;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.web.base.BaseController;
import com.orient.weibao.business.DepthDesityBusiness;
import com.orient.weibao.utils.ExcelImport;
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
import java.util.*;

@Controller
@RequestMapping("/depthDesity")
public class DepthDesityController extends BaseController {

    @Autowired
    DepthDesityBusiness depthDesityBusiness;
    @Autowired
    ExcelImport excelImport;

    /**
     * 导入深度密度数据
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("importDepthDesityData")
    @ResponseBody
    public Map<String, Object> importDepthDesityData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> retVal = null;
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
                String sufix = fileName.substring(fileName.lastIndexOf(".") + 1);
                if ("xls".equals(sufix) || "xlsx".equals(sufix)) {
//                    File dst = new File(fileName);
//                    file.transferTo(dst);
                    //导入的文件若是xlsx后缀，则为true,否则为false
//                    boolean after2007=sufix.equals("xlsx");
//                    ExcelReader excelReader = new ExcelReader();
                    File excelFile = new File(fileName);
//                    InputStream inputStream = new FileInputStream(excelFile);
//                    TableEntity excelEntity = excelReader.readFile(inputStream, after2007);
                    String depthDesityTypeId = request.getParameter("depthDesityTypeId");
                    retVal = depthDesityBusiness.importDepthDesityData(file, depthDesityTypeId);
                    excelFile.delete();
                } else {
                    retVal = new HashMap<>();
                    retVal.put("success", false);
                    retVal.put("msg", "目前仅支持.xls,.xlsx文件格式");
                }
            }
        }
        return retVal;
    }

    /**
     * 导出深度密度数据
     * @param exportAll
     * @param toExportIds
     * @param depthDesityTypeId
     * @param seaAreaName
     * @param response
     */
    @RequestMapping("exportDepthDesityData")
    @ResponseBody
    public void exportDepthDesityData(boolean exportAll, String toExportIds, String depthDesityTypeId,String seaAreaName, HttpServletResponse response) {
        String filePath = depthDesityBusiness.exportDepthDesityData(exportAll, toExportIds, depthDesityTypeId,seaAreaName);
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        try {
            response.setContentType("aplication/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
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
