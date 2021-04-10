package com.orient.sysman.bussiness;

import com.orient.sysmodel.domain.sys.CwmSysToolsEntity;
import com.orient.sysmodel.service.IBaseService;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.sys.IToolGroupService;
import com.orient.sysmodel.service.sys.IToolService;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 工具管理
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class ToolBusiness extends BaseHibernateBusiness<CwmSysToolsEntity> {

    @Autowired
    IToolService toolService;

    @Autowired
    IToolGroupService toolGroupService;

    @Override
    public IToolService getBaseService() {
        return toolService;
    }

    public ExtGridData<CwmSysToolsEntity> list(Integer page, Integer limit, CwmSysToolsEntity filter) {

        Criterion relationCriterion = Restrictions.eq("belongGroup.id", filter.getGroupId());
        ExtGridData<CwmSysToolsEntity> retVal = new ExtGridData<>();
        PageBean pageBean = new PageBean();
        pageBean.addCriterion(relationCriterion);
        pageBean.setRows(limit);
        pageBean.setPage(page);
        pageBean.setExampleFilter(filter);
        List<CwmSysToolsEntity> queryResult = toolService.listByPage(pageBean);
        retVal.setTotalProperty(pageBean.getTotalCount());
        retVal.setResults(queryResult);
        return retVal;
    }

    public void save(CwmSysToolsEntity formValue) {
        formValue.setBelongGroup(toolGroupService.getById(formValue.getGroupId()));
        toolService.save(formValue);
    }

    public void update(CwmSysToolsEntity formValue) {
        formValue.setBelongGroup(toolGroupService.getById(formValue.getGroupId()));
        toolService.update(formValue);
    }
}
