package com.orient.web.base;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.metamodel.metadomain.Column;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.sqlengine.api.ISqlEngine;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.sysmodel.service.IBaseService;
import com.orient.sysmodel.service.PageBean;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.ReflectUtil;
import com.orient.utils.StringUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("baseHibernateDao")
public class BaseHibernateBusiness<M> {

    @Autowired
    protected ISqlEngine orientSqlEngine;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Autowired
    protected IRoleUtil roleEngine;

    @Autowired
    protected MetaUtil metaEngine;

    @Autowired
    protected MetaDAOFactory metaDaoFactory;

    @Autowired
    protected IBaseService baseService;

    public IBaseService getBaseService() {
        return this.baseService;
    }

    public ExtGridData<M> list(Integer page, Integer limit, M filter, Criterion... criterions) {
        ExtGridData<M> retVal = new ExtGridData<>();
        PageBean pageBean = new PageBean();
        pageBean.setRows(null == limit ? Integer.MAX_VALUE : limit);
        pageBean.setPage(null == page ? -1 : page);
        pageBean.setExampleFilter(filter);
        pageBean.addOrder(Order.asc("id"));
        pageBean.getCriterions().addAll(CommonTools.arrayToList(criterions));
        List<M> queryResult = getBaseService().listByPage(pageBean);
        retVal.setTotalProperty(pageBean.getTotalCount());
        retVal.setResults(queryResult);
        return retVal;
    }

    public void update(M formValue) {
        getBaseService().merge(formValue);
    }

    public void save(M formValue) {
        getBaseService().save(formValue);
    }

    public void delete(Long[] toDelIds) {
        getBaseService().delete(toDelIds);
    }

    /**
     * 从前端传递的对象中一般不包含关联属性，故只更新对象中基本成员变量的信息
     *
     * @param formValue
     * @param idProperty
     */
    public void updateBasicAttr(M formValue, String idProperty) {
        idProperty = StringUtil.isEmpty(idProperty) ? "id" : idProperty;
        Serializable idValue = (Serializable) ReflectUtil.getFieldValue(formValue, idProperty);
        M originalData = (M) getBaseService().getById(idValue);
        BeanUtils.copyNotNullProperties(originalData, formValue);
        update(originalData);

    }

    /**
     * 针对hibernate设置cascade注解后级联删除
     *
     * @param toDelIds
     * @param cascade  是否级联删除
     */
    public void delete(Long[] toDelIds, boolean cascade) {
        if (cascade && null != toDelIds) {
            for (Long toDelId : toDelIds) {
                getBaseService().delete(toDelId);
            }
        } else {//采用hql删除
            getBaseService().delete(toDelIds);
        }
    }

    protected IBusinessModel getBusinessModelById(String modelId) {
        IBusinessModel retVal = null;
        try {
            retVal = businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    /**
     * @param bm      业务模型
     * @param dataMap 业务数据
     * @return 获取主键显示值
     */
    protected String getDisplayData(IBusinessModel bm, Map<String, String> dataMap) {
        Map PKColumns = bm.getMatrix().getMainTable().getPkColumns();
        String retVal = "";
        if (null != PKColumns && null != dataMap) {
            List<String> items = new ArrayList<>();
            PKColumns.forEach((key, value) -> {
                Column column = (Column) value;
                String item = dataMap.get(column.getColumnName());
                if (!StringUtil.isEmpty(item)) {
                    items.add(item);
                }
            });
            retVal = CommonTools.list2String(items);
        }
        return retVal;
    }

}

