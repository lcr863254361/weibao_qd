package com.orient.template.business.core.support;

/**
 * for registering template support with related node type
 *
 * @author Seraph
 *         2016-09-13 上午9:51
 */
public interface TemplateSupportRegistry {

    void registerTemplateSupport(String nodeType, TemplateSupport templateSupport);

    TemplateSupport getTemplateSupport(String nodeType);
}
