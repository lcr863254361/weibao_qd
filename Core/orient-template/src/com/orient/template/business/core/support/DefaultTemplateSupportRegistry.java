package com.orient.template.business.core.support;

import com.orient.collab.model.Project;
import com.orient.template.business.core.support.impl.ProjectTemplateSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * a default implementation of {@link TemplateSupportRegistry}
 *
 * @author Seraph
 *         2016-09-13 上午9:53
 */
public class DefaultTemplateSupportRegistry implements TemplateSupportRegistry{

    @Override
    public void registerTemplateSupport(String nodeType, TemplateSupport templateSupport) {
        templateSupportMap.put(nodeType, templateSupport);
    }

    @Override
    public TemplateSupport getTemplateSupport(String nodeType) {
        return templateSupportMap.get(nodeType);
    }

    public void setTemplateSupportMap(Map<String, TemplateSupport> templateSupportMap){
        this.templateSupportMap.putAll(templateSupportMap);
    }

    private Map<String, TemplateSupport> templateSupportMap = new ConcurrentHashMap<>(32);
}
