package com.orient.background.business;

import com.orient.background.bean.ModelFormViewEntityWrapper;
import com.orient.background.bean.OrientTreeColumnDesc;
import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.sysmodel.domain.form.FreemarkTemplateEntity;
import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.form.IFreemarkTemplateService;
import com.orient.sysmodel.service.form.IModelFormViewService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtComboboxData;
import com.orient.web.base.ExtComboboxResponseData;
import com.orient.web.base.ExtGridData;
import com.orient.web.form.engine.FreemarkEngine;
import com.orient.web.modelDesc.column.ColumnDesc;
import com.orient.web.modelDesc.operator.builder.impl.ColumnDescBuilder;
import freemarker.template.TemplateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by enjoy on 2016/3/24 0024.
 */
@Component
public class ModelFormViewBusiness extends BaseHibernateBusiness<ModelFormViewEntity> {

    @Autowired
    IModelFormViewService modelFormViewService;

    @Autowired
    IFreemarkTemplateService freemarkTemplateService;

    @Autowired
    FreemarkEngine freemarkEngine;

    @Autowired
    ColumnDescBuilder formItemBuilder;

    @Override
    public IModelFormViewService getBaseService() {
        return modelFormViewService;
    }

    public ExtGridData<ModelFormViewEntityWrapper> listByCustomer(Integer page, Integer limit, ModelFormViewEntity filter) {
        ExtGridData<ModelFormViewEntityWrapper> retVal = new ExtGridData<>();
        filter.setIsvalid(1l);
        PageBean pageBean = new PageBean();
        pageBean.setRows(limit);
        pageBean.setPage(page);
        pageBean.setExampleFilter(filter);
        List<ModelFormViewEntity> queryData = modelFormViewService.listByPage(pageBean);
        List<ModelFormViewEntityWrapper> results = dataChange(queryData);
        retVal.setResults(results);
        return retVal;
    }

    private List<ModelFormViewEntityWrapper> dataChange(List<ModelFormViewEntity> originalValue) {
        List<ModelFormViewEntityWrapper> retVal = new ArrayList<>();
        originalValue.forEach(modelFormViewEntity -> {
            ModelFormViewEntityWrapper loopItem = new ModelFormViewEntityWrapper();
            BeanUtils.copyProperties(loopItem, modelFormViewEntity);
            Long modelId = modelFormViewEntity.getModelid();
            if (null != modelId) {
                String modelDisplayName = businessModelService.getBusinessModelById(modelId.toString(), EnumInter.BusinessModelEnum.Table).getDisplay_name();
                loopItem.setModelid_display(modelDisplayName);
            }
            retVal.add(loopItem);
        });
        return retVal;
    }

    /**
     * 获取模型字段描述 用户表单构建选择
     *
     * @param modelID
     * @return
     */
    public List<ColumnDesc> getModelColumn(String modelID) {
        List<ColumnDesc> retVal = new ArrayList<>();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelID, EnumInter.BusinessModelEnum.Table);
        if (null != businessModel) {
            businessModel.getAllBcCols().forEach(iBusinessColumn -> {
                ColumnDesc orientExtColumn = new OrientTreeColumnDesc().init(iBusinessColumn);
                retVal.add(orientExtColumn);
            });
        }
        return retVal;
    }

    /**
     * 构建前台表单元素
     *
     * @param modelID 模型ID
     * @return
     */
    public List<ColumnDesc> getFormItems(String modelID) {
        List<ColumnDesc> retVal = new ArrayList<>();
        IBusinessModel businessModel = businessModelService.getBusinessModelById(modelID, EnumInter.BusinessModelEnum.Table);
        if (null != businessModel) {
            businessModel.getAllBcCols().forEach(iBusinessColumn -> {
                ColumnDesc formItem = formItemBuilder.buildColumnDesc(iBusinessColumn);
                retVal.add(formItem);
            });
        }
        return retVal;
    }


    /**
     * 返回字段的前段描述
     *
     * @param modelId
     * @param templateId
     * @return
     */
    public Map<String, String> initColumnControls(Long modelId, Long templateId) {
        //返回值
        Map<String, String> retVal = new HashMap<>();
        //获取表单模板描述
        FreemarkTemplateEntity freemarkTemplateEntity = freemarkTemplateService.getById(templateId);
        //获取宏描述
        if (null != freemarkTemplateEntity.getMacroAlias()) {
            FreemarkTemplateEntity macroTemplateEntity = freemarkTemplateService.getByProperties("alias", freemarkTemplateEntity.getMacroAlias());
            //获取模型
            IBusinessModel businessModel = businessModelService.getBusinessModelById(modelId.toString(), EnumInter.BusinessModelEnum.Table);
            if (null != businessModel && null != macroTemplateEntity) {
                businessModel.getAllBcCols().forEach(iBusinessColumn -> {
                    ColumnDesc formItem = formItemBuilder.buildColumnDesc(iBusinessColumn);
                    Map<String, Object> data = new HashMap<>();
                    data.put("field", formItem);
                    try {
                        retVal.put(formItem.getDbName(),
                                freemarkEngine.parseByStringTemplate(data, macroTemplateEntity.getHtml()
                                        + "<@input field=field/>"));
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        return retVal;
    }

    public ExtComboboxResponseData<ExtComboboxData> getModelComboboxCollection(Integer startIndex, Integer maxResults, String filter, String id) {
        ExtComboboxResponseData<ExtComboboxData> retVal = new ExtComboboxResponseData<>();
        PageBean pageBean = new PageBean();
        if (!StringUtil.isEmpty(filter)) {
            pageBean.addCriterion(Restrictions.like("name", "%" + filter + "%"));
        }
        pageBean.setRows(maxResults);
        pageBean.setPage(startIndex / pageBean.getRows() + 1);
        pageBean.addOrder(Order.asc("id"));
        List<ModelFormViewEntity> queryList = modelFormViewService.listByPage(pageBean);
        retVal.setTotalProperty(pageBean.getTotalCount());
        queryList.forEach(modelFormViewEntity -> {
            ExtComboboxData combobox = new ExtComboboxData();
            combobox.setId(modelFormViewEntity.getId().toString());
            combobox.setValue(modelFormViewEntity.getName());
            retVal.getResults().add(combobox);
        });
        return retVal;
    }

    public void doClearData() {
        List<ModelFormViewEntity> toDelData = modelFormViewService.list().stream().filter(modelFormViewEntity -> null == getBusinessModelById(modelFormViewEntity.getModelid().toString())).collect(Collectors.toList());
        if (!CommonTools.isEmptyList(toDelData)) {
            toDelData.forEach(modelFormViewEntity -> modelFormViewService.delete(modelFormViewEntity));
        }
    }

    @Override
    public void delete(Long[] toDelIds) {
        for (Long toDelId : toDelIds) {
            ModelFormViewEntity modelFormViewEntity = modelFormViewService.getById(toDelId);
            modelFormViewEntity.setIsvalid(0l);
            modelFormViewService.save(modelFormViewEntity);
        }
    }

    public ModelFormViewEntity findById(Long formViewId) {
        return modelFormViewService.getById(formViewId);
    }


    public List<ModelFormViewEntity> getByIds(List<Long> ids) {
        if (CommonTools.isEmptyList(ids)) {
            return new ArrayList<>();
        }
        return modelFormViewService.list(Restrictions.in("id", ids));
    }
}

