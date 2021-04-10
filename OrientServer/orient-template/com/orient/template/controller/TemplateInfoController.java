package com.orient.template.controller;

import com.orient.sysmodel.domain.template.CollabTemplate;
import com.orient.sysmodel.operationinterface.IUser;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.template.business.TemplateInfoBusiness;
import com.orient.template.model.CollabTemplateMngTreeNode;
import com.orient.template.model.TemplateInfo;
import com.orient.utils.UtilFactory;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import com.orient.web.base.ExtGridData;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.lang.mutable.MutableLong;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * TemplateInfoController
 *
 * @author Seraph
 *         2016-09-21 下午4:13
 */
@Controller
@RequestMapping("/templateInfo")
public class TemplateInfoController extends BaseController {

    @RequestMapping(value = "/nextLayerNodes")
    @ResponseBody
    public AjaxResponseData<List<CollabTemplateMngTreeNode>> getNextLayerTreeNodes(String parDataId, String node) {
        return new AjaxResponseData(templateInfoBusiness.getNextLayerTreeNodes(parDataId));
    }

    @RequestMapping("/bmTemplates/my")
    @ResponseBody
    public ExtGridData<TemplateInfo> getMyBmTemplates(Integer page, Integer limit, String modelName, String schemaId){
        MutableLong totalCnt = new MutableLong();

        List<CollabTemplate> collabTemplates = this.templateInfoBusiness.getMyTemplates(UserContextUtil.getUserId(), modelName, schemaId, page, limit, totalCnt);

        List<TemplateInfo> templateInfos = convertCollabTemplatesToTemplateInfo(collabTemplates);
        ExtGridData<TemplateInfo> retV = new ExtGridData<>(templateInfos, (Long) totalCnt.getValue());
        return retV;
    }

    @RequestMapping("/bmTemplates")
    @ResponseBody
    public ExtGridData<TemplateInfo> getBmTemplates(Integer page, Integer limit, String name, String creator, String modelName, String schemaId){

        MutableLong totalCnt = new MutableLong();
        List<CollabTemplate> collabTemplates = this.templateInfoBusiness.getBmTemplates(page, limit, totalCnt, name, creator, modelName, schemaId);

        List<TemplateInfo> templateInfos = convertCollabTemplatesToTemplateInfo(collabTemplates);
        ExtGridData<TemplateInfo> retV = new ExtGridData<>(templateInfos, (Long) totalCnt.getValue());
        return retV;
    }

    private List<TemplateInfo> convertCollabTemplatesToTemplateInfo(List<CollabTemplate> collabTemplates){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        List<TemplateInfo> templateInfos = UtilFactory.newArrayList();
        for(CollabTemplate collabTemplate : collabTemplates){
            TemplateInfo templateInfo = new TemplateInfo();
            BeanUtils.copyProperties(collabTemplate, templateInfo);
            IUser auditUser = roleEngine.getRoleModel(false).getUserById(collabTemplate.getAuditUserId());
            templateInfo.setAuditUserDisplayName(auditUser == null? "" : auditUser.getAllName());

            IUser createUser = roleEngine.getRoleModel(false).getUserById(collabTemplate.getExportUserId());
            templateInfo.setCreateUserDisplayName(createUser == null? "" : createUser.getAllName());

            if(collabTemplate.getExportTime() != null){
                templateInfo.setCreateTimeValue(sdf.format(collabTemplate.getExportTime()));
            }
            templateInfos.add(templateInfo);
        }

        return templateInfos;
    }

    @Autowired
    private TemplateInfoBusiness templateInfoBusiness;

    @Autowired
    protected IRoleUtil roleEngine;
}
