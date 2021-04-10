package com.orient.template.controller;

import com.orient.template.business.TemplateMngBusiness;
import com.orient.web.base.BaseController;
import com.orient.web.base.CommonResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * manage the template's import, export, deletion
 *
 * @author Seraph
 *         2016-09-21 下午4:38
 */
@Controller
@RequestMapping("/template/mng")
public class TemplateMngController extends BaseController{

    @RequestMapping("/delete")
    @ResponseBody
    public CommonResponseData deleteTemplate(String id){
        return this.templateMngBusiness.deleteTemplate(id);
    }

    @RequestMapping("/bmTemplate/create")
    @ResponseBody
    public CommonResponseData createBmTemplate(String modelName, String dataId, String schemaId, String name, String version, boolean privateTemp){
        return this.templateMngBusiness.createBmTemplate(modelName, dataId, schemaId, name, version, privateTemp);
    }

    @RequestMapping("/bmTemplate/import")
    @ResponseBody
    public CommonResponseData importBmTemplate(String modelName, String parentModelName, String parentDataId, String schemaId, String templateId, String rootName){
        return this.templateMngBusiness.importBmTemplate(parentModelName, parentDataId, templateId, rootName);
    }

    @Autowired
    private TemplateMngBusiness templateMngBusiness;
}
