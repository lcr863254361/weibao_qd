package com.orient.background.business;

import com.orient.background.bean.CwmComponentModelEntityWrapper;
import com.orient.component.ComponentInterface;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.component.CwmComponentEntity;
import com.orient.sysmodel.domain.component.CwmComponentModelEntity;
import com.orient.sysmodel.service.component.IComponentBindService;
import com.orient.sysmodel.service.component.IComponentService;
import com.orient.utils.BeanUtils;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 组件绑定管理
 *
 * @author
 * @create java.text.SimpleDateFormat@4f76f1a0
 */
@Component
public class ComponentBindBusiness extends BaseHibernateBusiness<CwmComponentModelEntity> {

    @Autowired
    IComponentBindService componentBindService;

    @Autowired
    IComponentService componentService;

    @Override
    public IComponentBindService getBaseService() {
        return componentBindService;
    }

    public ExtGridData<CwmComponentModelEntityWrapper> spcialList(Integer page, Integer limit, CwmComponentModelEntity filter) {

        ExtGridData<CwmComponentModelEntity> originalResults = super.list(page, limit, filter);
        ExtGridData<CwmComponentModelEntityWrapper> retVal = new ExtGridData<>();
        retVal.setSuccess(originalResults.isSuccess());
        retVal.setTotalProperty(originalResults.getTotalProperty());
        List<CwmComponentModelEntityWrapper> changedList = dataChange(originalResults.getResults());
        retVal.setResults(changedList);
        return retVal;
    }


    private List<CwmComponentModelEntityWrapper> dataChange(List<CwmComponentModelEntity> results) {
        List<CwmComponentModelEntityWrapper> changedList = new ArrayList<>();
        results.forEach(cwmComponentModelEntity -> {
            CwmComponentEntity cwmComponentEntity = cwmComponentModelEntity.getBelongComponent();
            CwmComponentModelEntityWrapper cwmComponentModelEntityWrapper = new CwmComponentModelEntityWrapper();
            BeanUtils.copyProperties(cwmComponentModelEntityWrapper, cwmComponentModelEntity);
            if (null != cwmComponentEntity) {
                String className = cwmComponentEntity.getClassname();
                ComponentInterface componentInterface = (ComponentInterface) OrientContextLoaderListener.Appwac.getBean(className);
                String jsCLass = componentInterface.getDashBordExtClass();
                cwmComponentModelEntityWrapper.setComponentExtJsClass(jsCLass);
            }
            changedList.add(cwmComponentModelEntityWrapper);
        });
        return changedList;
    }

    public void save(CwmComponentModelEntity formValue, Long componentId) {
        CwmComponentModelEntity filter = new CwmComponentModelEntity();
        filter.setNodeId(formValue.getNodeId());
        /*ilter.setModelId(formValue.getModelId());
        filter.setDataId(formValue.getDataId());*/
        List<CwmComponentModelEntity> cwmComponentModelEntities = componentBindService.listBeansByExample(filter);
        if (cwmComponentModelEntities.size() > 0 && !cwmComponentModelEntities.get(0).getBelongComponent().getId().equals(componentId)) {
            if (null != componentId) {
                cwmComponentModelEntities.get(0).setBelongComponent(componentService.getById(componentId));
                componentBindService.update(cwmComponentModelEntities.get(0));
            } else {
                componentBindService.delete(cwmComponentModelEntities.get(0));
            }
        } else if(null != componentId){
            CwmComponentModelEntity newComponent = new CwmComponentModelEntity();
            newComponent.setNodeId(formValue.getNodeId());
           /* newComponent.setModelId(formValue.getModelId());
            newComponent.setDataId(formValue.getDataId());*/
            newComponent.setBelongComponent(componentService.getById(componentId));
            componentBindService.save(newComponent);
        }
    }

}
