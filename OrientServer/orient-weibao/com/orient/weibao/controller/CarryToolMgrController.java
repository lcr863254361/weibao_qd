package com.orient.weibao.controller;

import com.orient.web.base.BaseController;
import com.orient.weibao.business.CarryToolMgrBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/carryToolMgr")
public class CarryToolMgrController extends BaseController {

    @Autowired
    CarryToolMgrBusiness carryToolMgrBusiness;

    /**
     * 导入携带的作业工具
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("importCarryToolData")
    @ResponseBody
    public Map<String, Object> importCarryToolData(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                    File excelFile = new File(fileName);
                    String isCabinOrOut = request.getParameter("isCabinOrOut");
                    retVal = carryToolMgrBusiness.importCarryToolData(file, isCabinOrOut);
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
     * 批量导出舱内舱外携带的作业工具
     * @param exportAll
     * @param toExportIds
     * @param isCabinOrOut
     * @param response
     */
    @RequestMapping("exportCarryToolData")
    @ResponseBody
    public void exportCarryToolData(boolean exportAll, String toExportIds, String isCabinOrOut, HttpServletResponse response) {
        String filePath = carryToolMgrBusiness.exportCarryToolData(exportAll, toExportIds, isCabinOrOut);
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
