package com.orient.background.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.sysmodel.domain.doc.CwmDocColumnScopeEntity;
import com.orient.sysmodel.domain.doc.CwmDocHandlerEntity;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.doc.IDocHandlerScopeService;
import com.orient.sysmodel.service.doc.IDocHandlerService;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class DocHandlerScopeBusiness extends BaseHibernateBusiness<CwmDocColumnScopeEntity> {

    @Autowired
    IDocHandlerScopeService docHandlerScopeService;

    @Autowired
    IDocHandlerService docHandlerService;

    @Override
    public IDocHandlerScopeService getBaseService() {
        return docHandlerScopeService;
    }

    public ExtGridData<CwmDocColumnScopeEntity> listSpecial(Integer page, Integer limit, CwmDocColumnScopeEntity filter, Long belongHandler) {
        ExtGridData<CwmDocColumnScopeEntity> retVal = new ExtGridData<>();
        PageBean pageBean = new PageBean();
        pageBean.setRows(null == limit ? Integer.MAX_VALUE : limit);
        pageBean.setPage(null == page ? -1 : page);
        pageBean.setExampleFilter(filter);
        pageBean.addOrder(Order.asc("id"));
        pageBean.addCriterion(Restrictions.eq("belongDocHandler.id", belongHandler));
        List<CwmDocColumnScopeEntity> queryResult = getBaseService().listByPage(pageBean);
        retVal.setTotalProperty(pageBean.getTotalCount());
        retVal.setResults(queryResult);
        return retVal;
    }

    public void saveSpecial(CwmDocColumnScopeEntity formValue, Long belongHandler) {
        CwmDocHandlerEntity docHandlerEntity = docHandlerService.getById(belongHandler);
        formValue.setBelongDocHandler(docHandlerEntity);
        super.save(formValue);
    }

    public Map<String, CwmDocColumnScopeEntity> getDefaultColumnHandler(Map<String, EnumInter> columnNameAndType) {
        List<String> columnTypes = columnNameAndType.values().stream().map(enumInter -> enumInter.toString()).collect(Collectors.toList());
        List<CwmDocColumnScopeEntity> docColumnScopeEntities = docHandlerScopeService.list(Restrictions.in("columnType", columnTypes));
        Map<String, CwmDocColumnScopeEntity> retVal = new LinkedHashMap<>();
        columnNameAndType.forEach((key, enumInter) -> {
            CwmDocColumnScopeEntity docColumnScopeEntity = docColumnScopeEntities.stream().filter(docColumnScopeEntitie -> docColumnScopeEntitie.getColumnType().equals(enumInter.toString())).findFirst().get();
            retVal.put(key, docColumnScopeEntity);
        });
        return retVal;
    }
}
