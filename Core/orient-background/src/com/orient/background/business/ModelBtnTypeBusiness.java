package com.orient.background.business;

import com.orient.sysmodel.domain.form.ModelBtnTypeEntity;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.form.IModelBtnTypeService;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtComboboxData;
import com.orient.web.base.ExtComboboxResponseData;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 模型表格按钮业务层
 *
 * @author enjoy
 * @creare 2016-04-09 10:47
 */
@Component
public class ModelBtnTypeBusiness extends BaseHibernateBusiness<ModelBtnTypeEntity> {

    @Autowired
    IModelBtnTypeService modelBtnTypeService;

    @Override
    public IModelBtnTypeService getBaseService() {
        return this.modelBtnTypeService;
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
        List<ModelBtnTypeEntity> queryList = modelBtnTypeService.listByPage(pageBean);
        retVal.setTotalProperty(pageBean.getTotalCount());
        queryList.forEach(modelBtnTypeEntity -> {
            ExtComboboxData combobox = new ExtComboboxData();
            combobox.setId(modelBtnTypeEntity.getId().toString());
            combobox.setValue(modelBtnTypeEntity.getName());
            retVal.getResults().add(combobox);
        });
        return retVal;
    }
}
