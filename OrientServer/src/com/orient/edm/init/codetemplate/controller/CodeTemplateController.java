package com.orient.edm.init.codetemplate.controller;

import com.orient.edm.init.codetemplate.bean.CodeGeneraterBean;
import com.orient.edm.init.codetemplate.business.CodeGenerateBusiness;
import com.orient.utils.BeanUtils;
import com.orient.utils.FileOperator;
import com.orient.web.base.ExtComboboxData;
import com.orient.web.base.ExtComboboxResponseData;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-06-25 13:38
 */
@Controller
@RequestMapping("/codeTemplate")
public class CodeTemplateController {

    @Autowired
    CodeGenerateBusiness codeGenerateBusiness;

    /**
     *
     * @param startIndex
     * @param maxResults
     * @param filter
     * @return 获取当前系统所有Hibernate映射的Bean
     */
    @RequestMapping("getHibernateBeanList")
    @ResponseBody
    public ExtComboboxResponseData<ExtComboboxData> getHibernateBeanList(Integer startIndex, Integer maxResults, String filter){
        ExtComboboxResponseData<ExtComboboxData> retValue = new ExtComboboxResponseData<>();
        List<String> hibernateBeanClasses = BeanUtils.getAnnotationClasses("com.orient",javax.persistence.Entity.class,filter);
        List<ExtComboboxData> dataList = new ArrayList<>();
        hibernateBeanClasses.forEach(className -> {
            ExtComboboxData extComboboxData = new ExtComboboxData();
            extComboboxData.setId(className);
            extComboboxData.setValue(className.substring(className.lastIndexOf(".")+1));
            dataList.add(extComboboxData);
        });
        retValue.setResults(dataList);
        retValue.setTotalProperty(dataList.size());
        return retValue;
    }

    @RequestMapping("doGemerateCode")
    @ResponseBody
    public CommonResponseData doGemerateCode(CodeGeneraterBean codeGeneraterBean){
        CommonResponseData retVal = new CommonResponseData();
        String zipPath = codeGenerateBusiness.doGemerateCode(codeGeneraterBean);
        retVal.setMsg(zipPath);
        return retVal;
    }

    @RequestMapping("download")
    public void download(String zipName, String moduleName,HttpServletRequest request,
                         HttpServletResponse response) {
        String zipPath = codeGenerateBusiness.getZipPath(zipName);
        FileOperator.downLoadFile(request, response, zipPath, moduleName+".zip");
    }

}
