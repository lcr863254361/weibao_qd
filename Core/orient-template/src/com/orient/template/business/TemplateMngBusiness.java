package com.orient.template.business;

import com.orient.collab.config.CollabConstants;
import com.orient.sysmodel.domain.template.CollabTemplate;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.sysmodel.service.template.impl.CollabTemplateNodeService;
import com.orient.sysmodel.service.template.impl.CollabTemplateService;
import com.orient.template.business.core.TemplateEngine;
import com.orient.template.model.CollabTemplateImportExtraInfo;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.CommonResponseData;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * TemplateMngBusiness
 *
 * @author Seraph
 *         2016-09-13 上午10:33
 */
@Component
public class TemplateMngBusiness extends BaseBusiness {

    public CommonResponseData deleteTemplate(String id) {
        CollabTemplateNode collabTemplateNode = this.collabTemplateNodeService.get(Restrictions.eq(CollabTemplateNode.TEMPLATE_ID, id),
                Restrictions.eq(CollabTemplateNode.ROOT, true));
        this.collabTemplateNodeService.delete(collabTemplateNode);
        this.collabTemplateService.delete(Long.valueOf(id));
        return new CommonResponseData(true, "");
    }

    public CommonResponseData createBmTemplate(String modelName, String dataId, String schemaId, String name, String version, boolean privateTemp) {
        CommonResponseData retV = new CommonResponseData(true, "");
        CollabTemplate collabTemplate = new CollabTemplate();
        collabTemplate.setName(name);
        collabTemplate.setVersion(version);
        Class<? extends Serializable> typeCls = this.orientSqlEngine.getTypeMappingBmService().getModelBindClass(modelName, schemaId, false);
        collabTemplate.setType(typeCls.getName());
        collabTemplate.setExportTime(new Timestamp(new Date().getTime()));
        collabTemplate.setExportUserId(UserContextUtil.getUserId());
        collabTemplate.setPrivateTemp(privateTemp);
        this.collabTemplateService.save(collabTemplate);

        Serializable root = this.orientSqlEngine.getTypeMappingBmService().getById(typeCls, dataId);
        this.templateEngine.doExport(root, collabTemplate);
        return retV;
    }

    public AjaxResponseData<CollabTemplateNode> importBmTemplate(String parentModelName, String parentDataId, String templateId, String rootName) {
        String schemaId = CollabConstants.COLLAB_SCHEMA_ID;

        Class<?> typeCls = this.orientSqlEngine.getTypeMappingBmService().getModelBindClass(parentModelName, schemaId, false);
        Object mountNode = this.orientSqlEngine.getTypeMappingBmService().getById(typeCls, parentDataId);
        CollabTemplateNode collabTemplateNode = this.collabTemplateNodeService.get(Restrictions.eq(CollabTemplateNode.TEMPLATE_ID, templateId),
                Restrictions.eq(CollabTemplateNode.ROOT, true));
        CollabTemplateImportExtraInfo extraInfo = new CollabTemplateImportExtraInfo();
        extraInfo.setMountNode(mountNode);
        extraInfo.setNewRootNodeName(rootName);
        this.templateEngine.doImport(collabTemplateNode, extraInfo);
        AjaxResponseData<CollabTemplateNode> retV = new AjaxResponseData(collabTemplateNode);
        return retV;
    }

    @Autowired
    private CollabTemplateService collabTemplateService;
    @Autowired
    private CollabTemplateNodeService collabTemplateNodeService;
    @Autowired
    private TemplateEngine templateEngine;

}
