package com.orient.modeldata.bean;

import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.web.util.TDMParamter;

import java.io.Serializable;

/**
 * tbom绑定模型描述
 *
 * @author enjoy
 * @createTime 2016-05-23 13:33
 */
public class TBomModel implements Serializable {

    private String modelId;

    private String modelName;

    private CustomerFilter defaultFilter;

    //0:数据类 1:视图
    private String type;

    private String templateId;

    //模板绑定JS类路径
    private String templateJS;

    private Boolean usePage = true;

    private Integer pageSize = TDMParamter.getPageSize();

    public TBomModel() {
    }

    public TBomModel(String modelId, String modelName, String type) {
        this.modelName = modelName;
        this.modelId = modelId;
        this.type = type;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public CustomerFilter getDefaultFilter() {
        return defaultFilter;
    }

    public void setDefaultFilter(CustomerFilter defaultFilter) {
        this.defaultFilter = defaultFilter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateJS() {
        return templateJS;
    }

    public void setTemplateJS(String templateJS) {
        this.templateJS = templateJS;
    }

    public Boolean getUsePage() {
        return usePage;
    }

    public void setUsePage(Boolean usePage) {
        this.usePage = usePage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
