package com.orient.devdataobj.business;

import com.orient.devdataobj.bean.DataObjectBean;
import com.orient.devdataobj.bean.HisDataObjBean;
import com.orient.devdatatype.business.DataSubTypeBusiness;
import com.orient.devdatatype.business.DataTypeBusiness;
import com.orient.sysmodel.domain.taskdata.*;
import com.orient.sysmodel.service.taskdata.IDataObjectOldVersionService;
import com.orient.sysmodel.service.taskdata.IDataObjectService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseBusiness;
import com.orient.web.base.ExtGridData;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 历史数据实例处理业务类
 *
 * @author mengbin
 * @create 2016-07-14 上午11:26
 */
@Component
public class HisDataObjectBusiness extends BaseBusiness {


    @Autowired
    IDataObjectOldVersionService dataObjectOldVersionService;

    @Autowired
    IDataObjectService dataObjectService;

    @Autowired
    DataTypeBusiness dataTypeBusiness;

    @Autowired
    DataSubTypeBusiness dataSubTypeBusiness;

    @Autowired
    DataObjectBusiness dataObjectBusiness;

    /**
     * 根据dataObjId查询出历史数据
     *
     * @param dataObjId
     * @return
     */
    public List<DataObjectOldVersionEntity> getRootHisDataObj(Integer dataObjId) {
        List<Criterion> filters = new ArrayList<>();
        filters.add(Restrictions.eq("dataobjectid", dataObjId));
        Order orders[] = new Order[1];
        orders[0] = Order.desc("id");
        return dataObjectOldVersionService.list(filters.toArray(new Criterion[0]), orders);
    }


    /**
     * 根据历史Id获取历史实例
     *
     * @param id
     * @return
     */
    public DataObjectOldVersionEntity getHisDataObj(Integer id) {
        return dataObjectOldVersionService.getById(id);
    }


    /**
     * 获取同一个历史版本的数据实例
     *
     * @param rootHisDataObj
     * @return
     */
    public List<DataObjectOldVersionEntity> getHisDataObjFamily(DataObjectOldVersionEntity rootHisDataObj) {
        List<DataObjectOldVersionEntity> family = new ArrayList<>();
        getChildrenHisDataObj(rootHisDataObj, family);
        return family;
    }

    /**
     * 递归查找子数据类型
     *
     * @param parentHisDataObj
     * @param family
     * @return
     */
    private boolean getChildrenHisDataObj(DataObjectOldVersionEntity parentHisDataObj, List<DataObjectOldVersionEntity> family) {
        List<Criterion> filters = new ArrayList<>();
        filters.add(Restrictions.eq("parentdataobjectid", parentHisDataObj.getDataobjectid()));
        filters.add(Restrictions.eq("version", parentHisDataObj.getVersion()));
        List<DataObjectOldVersionEntity> children = dataObjectOldVersionService.list(filters.toArray(new Criterion[0]));
        for (DataObjectOldVersionEntity child : children) {
            family.add(child);
            getChildrenHisDataObj(child, family);
        }
        return true;
    }


    /**
     * 删除历史数据,假删除
     *
     * @param dataObj
     * @return
     */
    public boolean deleteHisDataObj(Integer dataObj) {
        List<Criterion> filters = new ArrayList<>();
        filters.add(Restrictions.eq("dataobjectid", dataObj));
        List<DataObjectOldVersionEntity> deletedObjs = dataObjectOldVersionService.list(filters.toArray(new Criterion[0]));
        for (DataObjectOldVersionEntity obj : deletedObjs) {
            obj.setIsdeleted(1);
            dataObjectOldVersionService.update(obj);
        }
        return true;
    }

    public ExtGridData<HisDataObjBean> getSimpleHisDevDatas(String nodeId, String originalObjId, Integer isglobal) {
        ExtGridData<HisDataObjBean> retVal = new ExtGridData<>();
        List<HisDataObjBean> results = new ArrayList<>();
        List<DataObjectEntity> currentDataList = dataObjectService.list(Restrictions.eq("nodeId", nodeId), Restrictions.eq("originalObjId", Integer.valueOf(originalObjId)), Restrictions.eq("isglobal", isglobal));
        List<DataObjectOldVersionEntity> hisDataList = dataObjectOldVersionService.list(Restrictions.eq("nodeId", nodeId), Restrictions.eq("originalObjId", Integer.valueOf(originalObjId)), Restrictions.eq("isglobal", isglobal));
        try {
            if (!CommonTools.isEmptyList(hisDataList)) {
                for (DataObjectOldVersionEntity dataObjectOldVersionEntity : hisDataList) {
                    HisDataObjBean hisDataObjBean = new HisDataObjBean();
                    changeDataObjectEntityToDataObject(dataObjectOldVersionEntity, hisDataObjBean);
                    hisDataObjBean.setId(-hisDataObjBean.getId());
                    results.add(hisDataObjBean);
                }
            }
            if (!CommonTools.isEmptyList(currentDataList)) {
                for (DataObjectEntity dataObjectEntity : currentDataList) {
                    HisDataObjBean hisDataObjBean = new HisDataObjBean();
                    changeDataObjectEntityToDataObject(dataObjectEntity, hisDataObjBean);
                    results.add(hisDataObjBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        results.sort((o1,o2)->{
            return o1.getNodeVersion()-o2.getNodeVersion();
        } );
        retVal.setResults(results);
        retVal.setSuccess(true);
        retVal.setTotalProperty(results.size());
        return retVal;
    }

    public void changeDataObjectEntityToDataObject(DataObjectEntityBase loop, HisDataObjBean bean) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PropertyUtils.copyProperties(bean, loop);
        if (!StringUtil.isEmpty(loop.getCreaterid())) {
            bean.setCreateUser(roleEngine.getRoleModel(false).getUserById(loop.getCreaterid()).getAllName());
        }
        if (!StringUtil.isEmpty(loop.getModifierid())) {
            bean.setModifiedUser(roleEngine.getRoleModel(false).getUserById(loop.getModifierid()).getAllName());
        }
        if (bean.getIsref() == 8) {//复杂类型才有子节点
            bean.setLeaf(false);
        } else {
            bean.setLeaf(true);
        }
        String dataTypeId = bean.getDatatypeId();
        String dataSubTypeId = bean.getSubtypeid();
        if (bean.getParentdataobjectid() == 0) {
            DataTypeEntity dataType = dataTypeBusiness.getDataType(dataTypeId);
            bean.setDataTypeShowName(dataType.getDatatypename());
            if (dataType.getRank() == DataTypeEntity.Rank_EXTEND || dataType.getRank() == DataTypeEntity.Rank_BASE) {
                bean.setExtendsTypeRealName(dataType.getDatatype());
            }
        } else {
            DataSubTypeEntity dataSubEntity = dataSubTypeBusiness.getDataSubTypeEntityById(dataSubTypeId);
            if (bean.getIsref() == 1) {
                String baseDataType = dataSubEntity.getDatatype();
                bean.setDataTypeShowName(dataTypeBusiness.getNewestDataTypeByTypeCode(baseDataType).getDatatypename());
                bean.setExtendsTypeRealName(dataTypeBusiness.getNewestDataTypeByTypeCode(baseDataType).getDatatype());
            } else if (bean.getIsref() == 2) {//扩展类型
                String extendDataType = dataSubEntity.getDatatype();   //dataTypeId
                DataTypeEntity dataType = dataTypeBusiness.getDataType(extendDataType);
                bean.setDataTypeShowName(dataType.getDatatypename());
                bean.setExtendsTypeRealName(dataType.getDatatype());
            } else if (bean.getIsref() == 4) {//枚举
                String extendDataType = dataSubEntity.getDatatype();   //dataTypeId
                DataTypeEntity dataType = dataTypeBusiness.getDataType(extendDataType);
                bean.setDataTypeShowName(dataType.getDatatypename());
            } else if (bean.getIsref() == 8) {//复杂
                String extendDataType = dataSubEntity.getDatatype();   //dataTypeId
                DataTypeEntity dataType = dataTypeBusiness.getDataType(extendDataType);
                bean.setDataTypeShowName(dataType.getDatatypename());
            }
        }
        bean.setIconCls(getIconCls(bean));
    }

    private String getIconCls(DataObjectBean bean) {
        String retVal = "";
        if (bean.getIsref() == 1) {
            retVal = "icon-" + bean.getExtendsTypeRealName();
        } else if (bean.getIsref() == 2) {//扩展类型
            retVal = "icon-extendDataType";
        } else if (bean.getIsref() == 4) {//枚举
            retVal = "icon-enumDataType";
        } else if (bean.getIsref() == 8) {//复杂
            retVal = "icon-complexDataType";
        }
        return retVal;
    }

    public ExtGridData<HisDataObjBean> getComplexHisDevDatas(String nodeId, String originalObjId, String rootId, Integer isglobal) {
        ExtGridData<HisDataObjBean> retVal = new ExtGridData<>();
        List<HisDataObjBean> results = new ArrayList<>();
        if (rootId.equals("-1")) {
            return getSimpleHisDevDatas(nodeId, originalObjId, isglobal);
        } else {
            if (rootId.contains("-")) {
                DataObjectOldVersionEntity topHisDataObj = getHisDataObj(-Integer.valueOf(rootId));
                List<DataObjectOldVersionEntity> hisDataObjFamily = getHisDataObjFamily(topHisDataObj);
                try {
                    for (DataObjectOldVersionEntity dataObjectOldVersionEntity : hisDataObjFamily) {
                        HisDataObjBean hisDataObjBean = new HisDataObjBean();
                        changeDataObjectEntityToDataObject(dataObjectOldVersionEntity, hisDataObjBean);
                        hisDataObjBean.setId(-hisDataObjBean.getId());
                        hisDataObjBean.setNodeVersion(null);
                        hisDataObjBean.setVersion("");
                        results.add(hisDataObjBean);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                DataObjectEntity parentDataObj = dataObjectBusiness.getDataObjEntityById(Integer.valueOf(rootId));
                List<DataObjectEntity> childrenDataObj = dataObjectBusiness.getChildrenDataObj(parentDataObj);
                try {
                    for (DataObjectEntity dataObjectEntity : childrenDataObj) {
                        HisDataObjBean hisDataObjBean = new HisDataObjBean();
                        changeDataObjectEntityToDataObject(dataObjectEntity, hisDataObjBean);
                        hisDataObjBean.setNodeVersion(null);
                        hisDataObjBean.setVersion("");
                        results.add(hisDataObjBean);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        retVal.setResults(results);
        retVal.setSuccess(true);
        retVal.setTotalProperty(results.size());
        return retVal;
    }

}
