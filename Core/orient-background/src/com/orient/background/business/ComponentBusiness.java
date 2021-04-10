package com.orient.background.business;

import com.orient.background.bean.CwmComponentModelEntityWrapper;
import com.orient.component.ComponentInterface;
import com.orient.component.bean.ValidateComponentBean;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.component.CwmComponentEntity;
import com.orient.sysmodel.service.component.IComponentService;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 组件管理
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class ComponentBusiness extends BaseHibernateBusiness<CwmComponentEntity> {

    @Autowired
    IComponentService componentService;

    @Override
    public IComponentService getBaseService() {
        return componentService;
    }

    public String getComponentJSClass(Long componentId) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        CwmComponentEntity cwmComponentEntity = componentService.getById(componentId);
        String className = cwmComponentEntity.getClassname();
        ComponentInterface componentInterface = (ComponentInterface) OrientContextLoaderListener.Appwac.getBean(className);
        String jsCLass = componentInterface.getDashBordExtClass();
        return jsCLass;
    }

    /**
     * @param validateComponentBean
     * @return 验证组件
     */
    public String validateComponent(ValidateComponentBean validateComponentBean) {

        String retVal = "";
        if (null != validateComponentBean.getComponentId()) {
            CwmComponentEntity cwmComponentEntity = componentService.getById(validateComponentBean.getComponentId());
            String className = cwmComponentEntity.getClassname();
            ComponentInterface componentInterface = (ComponentInterface) OrientContextLoaderListener.Appwac.getBean(className);
            if (null != componentInterface) {
                retVal = componentInterface.validateComponent(validateComponentBean);
            }
        }
        return retVal;
    }

    @Override
    public void delete(Long[] toDelIds) {
        //重写 需要关联删除
        for (Long toDelId : toDelIds) {
            componentService.delete(toDelId);
        }
    }

    public ExtGridData<CwmComponentEntity> listByIds(List<Long> ids) {
        ExtGridData<CwmComponentEntity> retVal = new ExtGridData<>();

        List<CwmComponentEntity> originalResults;
        if(ids.size() == 0){
            retVal.setTotalProperty(0);
            originalResults = UtilFactory.newArrayList();
        }else{
            originalResults = this.componentService.getByIds(ids.toArray(new Long[]{}));
            retVal.setTotalProperty(originalResults.size());
        }

        retVal.setResults(originalResults);
        return retVal;
    }
}

