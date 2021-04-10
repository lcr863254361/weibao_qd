package com.orient.background.business;

import com.orient.sysmodel.domain.form.FreemarkTemplateEntity;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.form.IFreemarkTemplateService;
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
 * Created by enjoy on 2016/3/24 0024.
 */
@Component
public class FreemarkTemplateBusiness extends BaseHibernateBusiness<FreemarkTemplateEntity> {

    @Autowired
    IFreemarkTemplateService freemarkTemplateService;

    @Override
    public IFreemarkTemplateService getBaseService() {
        return freemarkTemplateService;
    }

    public ExtComboboxResponseData<ExtComboboxData> getFreemarkerTemplateCombobox(Integer startIndex, Integer maxResults, String filter) {
        ExtComboboxResponseData<ExtComboboxData> retVal = new ExtComboboxResponseData<>();
        PageBean pageBean = new PageBean();
        if (!StringUtil.isEmpty(filter)) {
            pageBean.addCriterion(Restrictions.like("name", "%" + filter + "%"));
        }
        pageBean.addCriterion(Restrictions.eq("type", "主表模板"));
        pageBean.setRows(maxResults);
        pageBean.setPage(startIndex / pageBean.getRows() + 1);
        pageBean.addOrder(Order.asc("id"));
        List<FreemarkTemplateEntity> queryList = freemarkTemplateService.listByPage(pageBean);
        retVal.setTotalProperty(pageBean.getTotalCount());
        queryList.forEach(freemarkTemplateEntity -> {
            ExtComboboxData combobox = new ExtComboboxData();
            combobox.setId(freemarkTemplateEntity.getId().toString());
            combobox.setValue(freemarkTemplateEntity.getName());
            retVal.getResults().add(combobox);
        });
        return retVal;
    }

    public void initSystemTemplate() {
        freemarkTemplateService.initSystemTemplate();
    }
}
