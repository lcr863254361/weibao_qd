package com.orient.sysmodel.service.form.impl;

import com.orient.sysmodel.dao.form.IFreemarkTemplateDao;
import com.orient.sysmodel.domain.form.FreemarkTemplateEntity;
import com.orient.sysmodel.service.BaseService;
import com.orient.sysmodel.service.form.IFreemarkTemplateService;
import com.orient.utils.FileOperator;
import com.orient.utils.PathTools;
import com.orient.utils.xml.Dom4jUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by enjoy on 2016/3/15 0015.
 */
@Service
public class FreemarkTemplateService extends BaseService<FreemarkTemplateEntity> implements IFreemarkTemplateService {

    @Autowired
    IFreemarkTemplateDao baseDao;

    @Override
    public IFreemarkTemplateDao getBaseDao() {
        return this.baseDao;
    }

    @Override
    public void initSystemTemplate() {
        deleteSystemTemplates();
        addTemplate();
    }

    @Override
    public void deleteSystemTemplates() {
        Criterion criterion = Restrictions.eq("canedit", 0l);
        list(criterion).stream().mapToLong(FreemarkTemplateEntity::getId).forEach(toDelId -> delete(toDelId));
    }

    @Override
    public void addTemplate() {
        StringBuffer initPath = new StringBuffer(PathTools.getClassPath()).append(File.separator)
                .append("com").append(File.separator)
                .append("orient").append(File.separator)
                .append("background").append(File.separator)
                .append("init").append(File.separator);
        String xml = FileOperator.readFile(initPath.toString() + "templates.xml");
        Document document = Dom4jUtil.loadXml(xml);
        Element root = document.getRootElement();
        List<Element> list = root.elements();
        list.forEach(element -> {
            String alias = element.attributeValue("alias");
            String name = element.attributeValue("name");
            String type = element.attributeValue("type");
            String templateDesc = element.attributeValue("templateDesc");
            String macroAlias = element.attributeValue("macroAlias");
            String fileName = alias + ".ftl";
            String html = FileOperator.readFile(initPath.toString() + fileName);
            FreemarkTemplateEntity freemarkTemplateEntity = new FreemarkTemplateEntity();
            freemarkTemplateEntity.setAlias(alias);
            freemarkTemplateEntity.setName(name);
            freemarkTemplateEntity.setType(type);
            freemarkTemplateEntity.setDesc(templateDesc);
            freemarkTemplateEntity.setMacroAlias(macroAlias);
            freemarkTemplateEntity.setHtml(html);
            freemarkTemplateEntity.setCanedit(0l);
            save(freemarkTemplateEntity);
        });
    }


}
