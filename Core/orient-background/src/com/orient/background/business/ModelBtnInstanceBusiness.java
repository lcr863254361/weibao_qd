package com.orient.background.business;

import com.orient.background.bean.ModelBtnInstanceWrapper;
import com.orient.sysmodel.domain.form.ModelBtnInstanceEntity;
import com.orient.sysmodel.domain.form.ModelBtnTypeEntity;
import com.orient.sysmodel.domain.form.ModelFormViewEntity;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.form.IModelBtnInstanceService;
import com.orient.sysmodel.service.form.IModelBtnTypeService;
import com.orient.sysmodel.service.form.IModelFormViewService;
import com.orient.utils.CommonTools;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseHibernateBusiness;
import com.orient.web.base.ExtGridData;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 模型按钮实例Business
 *
 * @author enjoy
 * @creare 2016-04-11 13:59
 */
@Component
public class ModelBtnInstanceBusiness extends BaseHibernateBusiness<ModelBtnInstanceEntity> {

    @Autowired
    IModelBtnInstanceService modelBtnInstanceService;

    @Autowired
    IModelBtnTypeService modelBtnTypeService;

    @Autowired
    IModelFormViewService modelFormViewService;

    @Override
    public IModelBtnInstanceService getBaseService() {
        return modelBtnInstanceService;
    }

    public ExtGridData<ModelBtnInstanceEntity> list(Integer page, Integer limit, ModelBtnInstanceEntity filter) {
        ExtGridData<ModelBtnInstanceEntity> retVal = new ExtGridData<>();
        PageBean pageBean = new PageBean();
        pageBean.setRows(limit);
        pageBean.setPage(page);
        pageBean.setExampleFilter(filter);
        pageBean.addOrder(Order.asc("id"));
        List<ModelBtnInstanceEntity> queryResult = modelBtnInstanceService.listByPage(pageBean);
        retVal.setTotalProperty(pageBean.getTotalCount());
        retVal.setResults(queryResult);
        dataChange(queryResult);
        return retVal;
    }

    /**
     * 转化外键
     *
     * @param dataList
     */
    private void dataChange(List<ModelBtnInstanceEntity> dataList) {
        //减少查询次数
        List<String> btnTypeIds = dataList.stream().filter(modelBtnInstanceEntity -> modelBtnInstanceEntity.getBtnTypeId() != null).map(ModelBtnInstanceEntity::getBtnTypeId).collect(Collectors.toList());
        List<String> bindFormViewIds = dataList.stream().filter(modelBtnInstanceEntity -> modelBtnInstanceEntity.getFormViewId() != null).map(ModelBtnInstanceEntity::getFormViewId).collect(Collectors.toList());
        List<ModelBtnTypeEntity> modelBtnTypeEntities = new ArrayList<>();
        List<ModelFormViewEntity> modelFormViewEntitys = new ArrayList<>();
        if (btnTypeIds.size() > 0) {
            List<Long> seriIds = new ArrayList<>();
            btnTypeIds.forEach(btnTypeId -> seriIds.add(Long.valueOf(btnTypeId)));
            modelBtnTypeEntities = modelBtnTypeService.list(Restrictions.in("id", seriIds));
        }
        if (bindFormViewIds.size() > 0) {
            List<Long> seriIds = new ArrayList<>();
            bindFormViewIds.forEach(bindFormViewId -> seriIds.add(Long.valueOf(bindFormViewId)));
            modelFormViewEntitys = modelFormViewService.list(Restrictions.in("id", seriIds));
        }
        final List<ModelBtnTypeEntity> finalModelBtnTypeEntities = modelBtnTypeEntities;
        final List<ModelFormViewEntity> finalModelFormViewEntitys = modelFormViewEntitys;
        //遍历替换
        dataList.forEach(modelBtnInstanceEntity -> {
            String btnTypeId = modelBtnInstanceEntity.getBtnTypeId();
            String formViewId = modelBtnInstanceEntity.getFormViewId();
            if (null != btnTypeId) {

                if(finalModelBtnTypeEntities.stream().filter(modelBtnTypeEntity -> modelBtnTypeEntity.getId().equals(Long.valueOf(btnTypeId))).count() > 0){
                    ModelBtnTypeEntity bindModelBtnTypeEntity = finalModelBtnTypeEntities.stream().filter(modelBtnTypeEntity -> modelBtnTypeEntity.getId().equals(Long.valueOf(btnTypeId))).findFirst().get();
                    Map<String, String> showMap = new HashMap<String, String>() {{
                        put("id", btnTypeId.toString());
                        put("value", bindModelBtnTypeEntity.getName());
                    }};
                    modelBtnInstanceEntity.setBtnTypeId(JsonUtil.toJson(showMap));
                }
            }
            if (null != formViewId) {

                if(finalModelFormViewEntitys.stream().filter(modelFormViewEntity -> modelFormViewEntity.getId().equals(Long.valueOf(formViewId))).count() > 0){
                    ModelFormViewEntity bindModelFormViewEntity = finalModelFormViewEntitys.stream().filter(modelFormViewEntity -> modelFormViewEntity.getId().equals(Long.valueOf(formViewId))).findFirst().get();
                    Map<String, String> showMap = new HashMap<String, String>() {{
                        put("id", formViewId.toString());
                        put("value", bindModelFormViewEntity.getName());
                    }};
                    modelBtnInstanceEntity.setFormViewId(JsonUtil.toJson(showMap));
                }
            }
        });
    }

    /**
     * 根据过滤ID 获取过滤以及反向过滤的数据集合
     *
     * @param filter
     * @return
     */
    public Map<String, ExtGridData<ModelBtnInstanceEntity>> getModelBtnInstanceData(Long[] filter) {
        List<Long> filterList = CommonTools.arrayToList(filter);
        //准备约束条件
        Criterion selectCriterion = Restrictions.in("id", filter);
        Criterion unSelectCriterion = Restrictions.not(selectCriterion);
        //获取数据条数
        Integer selectedCount = modelBtnInstanceService.count(selectCriterion);
        Integer unSelectedCount = modelBtnInstanceService.count(unSelectCriterion);
        //获取具体数据
        List<ModelBtnInstanceEntity> selectedData = modelBtnInstanceService.list(selectCriterion);
        //排序
        selectedData.sort((ModelBtnInstanceEntity e1, ModelBtnInstanceEntity e2) ->
                        filterList.indexOf(e1.getId()) - filterList.indexOf(e2.getId())
        );
        dataChange(selectedData);
        List<ModelBtnInstanceEntity> unSelectedData = modelBtnInstanceService.list(unSelectCriterion,Order.asc("id"));
        dataChange(unSelectedData);
        //准备返回值
        ExtGridData<ModelBtnInstanceEntity> selectedGridData = new ExtGridData<>();
        selectedGridData.setTotalProperty(selectedCount);
        selectedGridData.setResults(selectedData);
        ExtGridData<ModelBtnInstanceEntity> unSelectedGridData = new ExtGridData<>();
        unSelectedGridData.setTotalProperty(unSelectedCount);
        unSelectedGridData.setResults(unSelectedData);
        //以Map结构返回
        Map<String, ExtGridData<ModelBtnInstanceEntity>> retVaL = new HashMap<String, ExtGridData<ModelBtnInstanceEntity>>() {{
            put("selected", selectedGridData);
            put("unselected", unSelectedGridData);
        }};
        return retVaL;
    }

    /**
     * 获取包装后的模型实例信息
     *
     * @param criterion
     * @return
     */
    public List<ModelBtnInstanceWrapper> listByCriterion(Criterion criterion) {
        List<ModelBtnInstanceWrapper> retVal = new ArrayList<>();
        modelBtnInstanceService.list(criterion, Order.asc("id")).forEach(modelBtnInstance -> {
            ModelBtnInstanceWrapper modelBtnInstanceWrapper = new ModelBtnInstanceWrapper();
            Long modelTypeId = StringUtil.isEmpty(modelBtnInstance.getBtnTypeId()) ? -1l : Long.valueOf(modelBtnInstance.getBtnTypeId());
            ModelBtnTypeEntity modelBtnTypeEntity = modelBtnTypeService.getById(modelTypeId);
            modelBtnInstanceWrapper.setModelBtnInstanceEntity(modelBtnInstance);
            modelBtnInstanceWrapper.setBelongModelBtnType(modelBtnTypeEntity);
            retVal.add(modelBtnInstanceWrapper);
        });
        return retVal;
    }

}
