package com.orient.background.business;

import com.orient.sysmodel.domain.doc.CwmDocHandlerEntity;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.doc.IDocHandlerService;
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
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class DocHandlerBusiness extends BaseHibernateBusiness<CwmDocHandlerEntity> {

    @Autowired
    IDocHandlerService docHandlerService;

    @Override
    public IDocHandlerService getBaseService() {
        return docHandlerService;
    }

    public void deleteSpecial(Long[] toDelIds) {
        //delete cascade
        for (Long toDelId : toDelIds) {
            CwmDocHandlerEntity docHandlerEntity = docHandlerService.getById(toDelId);
            docHandlerService.delete(docHandlerEntity);
        }
    }

    public ExtComboboxResponseData<ExtComboboxData> getDocHandlerCombobox(Integer startIndex, Integer maxResults, String filter, String id) {

        ExtComboboxResponseData<ExtComboboxData> retVal = new ExtComboboxResponseData<>();
        PageBean pageBean = new PageBean();
        if (!StringUtil.isEmpty(filter)) {
            pageBean.addCriterion(Restrictions.like("showName", "%" + filter + "%"));
        }
        pageBean.setRows(maxResults);
        pageBean.setPage(startIndex / pageBean.getRows() + 1);
        pageBean.addOrder(Order.asc("id"));
        List<CwmDocHandlerEntity> queryList = docHandlerService.listByPage(pageBean);
        retVal.setTotalProperty(pageBean.getTotalCount());
        queryList.forEach(modelFormViewEntity -> {
            ExtComboboxData combobox = new ExtComboboxData();
            combobox.setId(modelFormViewEntity.getId().toString());
            combobox.setValue(modelFormViewEntity.getShowName());
            retVal.getResults().add(combobox);
        });
        return retVal;
    }
}
