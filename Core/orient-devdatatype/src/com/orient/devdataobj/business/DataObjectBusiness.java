package com.orient.devdataobj.business;

import com.orient.devdataobj.bean.DataObjectBean;
import com.orient.devdataobj.util.DataObjVersionUtil;
import com.orient.devdatatype.business.DataSubTypeBusiness;
import com.orient.devdatatype.business.DataTypeBusiness;
import com.orient.sysmodel.domain.taskdata.*;
import com.orient.sysmodel.service.taskdata.IDataObjectOldVersionService;
import com.orient.sysmodel.service.taskdata.IDataObjectService;
import com.orient.sysmodel.service.taskdata.impl.DataSubTypeEntityService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.base.BaseBusiness;
import com.orient.web.util.UserContextUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 当前数据实例
 *
 * @author mengbin
 * @create 2016-07-13 下午4:39
 */
@Component
public class DataObjectBusiness extends BaseBusiness {

    @Autowired
    IDataObjectService dataObjectService;

    @Autowired
    IDataObjectOldVersionService dataObjectOldVersionService;

    @Autowired
    DataTypeBusiness dataTypeBusiness;

    @Autowired
    DataSubTypeEntityService dataSubTypeEntityService;

    @Autowired
    DataSubTypeBusiness dataSubTypeBusiness;

    @Autowired
    HisDataObjectBusiness hisDataObjectBusiness;

    /**
     * 获取当前数据实例:
     * 1.如果查询的是临时数据，过滤条件不需要节点版本
     * 2.如果查询的是自身数据，如果通过节点id和待查询节点版本无法找到数据，则获取最接近待查询节点版本的版本号，再查询一次
     *
     * @param nodeId      绑定的节点ID
     * @param nodeVersion 绑定的节点版本
     * @param isOnlyRoot  isOnlyRoot  是否只获取根节点
     * @param globalFlag  1:查询全局的  2:查询私有的  3:查询所有的
     * @return
     */
    public List<DataObjectEntity> getAllCurrentDataObject(String nodeId, Integer nodeVersion, int globalFlag, boolean isOnlyRoot) {
        if (StringUtil.isEmpty(nodeId) || nodeVersion == null) {
            return new ArrayList<>();
        }
        List<Criterion> filters = new ArrayList<>();
        filters.add(Restrictions.eq("nodeId", nodeId));
        SimpleExpression nodeVersionFilter = Restrictions.eq("nodeVersion", nodeVersion);
        if (globalFlag == 1) {
            filters.add(Restrictions.eq("isglobal", 1));
            filters.add(nodeVersionFilter);
        } else if (globalFlag == 2) {
            filters.add(Restrictions.eq("isglobal", 0));
        }
        if (isOnlyRoot) {
            filters.add(Restrictions.eq("parentdataobjectid", 0));
        }
        Order orders[] = new Order[1];
        orders[0] = Order.desc("ordernumber");
        List<DataObjectEntity> list = dataObjectService.list(filters.toArray(new Criterion[0]), orders);
        if (list.size() > 0) {
            return list;
        } else { //如果通过当前节点版本无法找到研发数据，查找最接近当前节点版本号的版本数据
            List<DataObjectEntity> dataObjectEntityList = dataObjectService.list(new Criterion[]{Restrictions.eq("nodeId", nodeId), Restrictions.le("nodeVersion", nodeVersion)}, Order.desc("nodeVersion"));
            if (CommonTools.isEmptyList(dataObjectEntityList)) {
                return new ArrayList<>();
            } else {
                //获取最接近查询节点版本号的版本号
                Integer secondMaxNodeVersion = dataObjectEntityList.get(0).getNodeVersion();
                //过滤条件的节点版本号改为接近查询节点版本号的版本号
                if (filters.contains(nodeVersionFilter)) {
                    filters.remove(nodeVersionFilter);
                }
                filters.add(Restrictions.eq("nodeVersion", secondMaxNodeVersion));
                return dataObjectService.list(filters.toArray(new Criterion[0]), orders);
            }
        }
    }

    /**
     * 获取下一层子数据实例
     *
     * @param parentDataObj
     * @return
     */
    public List<DataObjectEntity> getChildrenDataObj(DataObjectEntity parentDataObj) {

        List<DataObjectEntity> children = new ArrayList<>();
        dataObjectService.getChildDataObjEntity(parentDataObj, children);
        return children;
    }

    /**
     * 创建顶层的数据实例
     *
     * @param newDataObj
     * @return 返回创建后的实例
     */
    public List<DataObjectEntity> createTopDataObj(DataObjectEntity newDataObj) {
        List<DataObjectEntity> newDataObjList = new ArrayList<>();
        newDataObj.setParentdataobjectid(0);
        newDataObj.setSubtypeparentid("0");
        newDataObj.setVersion("0.0.0.0");
        newDataObj.setCreaterid(UserContextUtil.getCurrentUser().getId());
        newDataObj.setCreatetime(new Date());
        newDataObj.setModifytime(new Date());
        newDataObj.setModifierid(UserContextUtil.getCurrentUser().getId());
        newDataObj.setOrdernumber(dataObjectService.count(Restrictions.eq("nodeId", newDataObj.getNodeId()), Restrictions.eq("nodeVersion", newDataObj.getNodeVersion())) + 1);
        dataObjectService.save(newDataObj);
        newDataObj.setOriginalObjId(newDataObj.getId());
        dataObjectService.update(newDataObj);
        //如果是复杂数据类型,则需要创建子数据实例
        createSubDataObj(newDataObj, newDataObjList);
        newDataObjList.add(newDataObj);
        return newDataObjList;
    }

    /**
     * 递归创建子数据实例
     *
     * @param parentDataObj
     * @param newDataObjList
     * @return
     */
    public boolean createSubDataObj(DataObjectEntity parentDataObj, List<DataObjectEntity> newDataObjList) {
        //只有复杂数据类型才需要创建子数据实例
        if (parentDataObj.getIsref() != 8) {
            return true;
        }
        String dataTypeId = parentDataObj.getDatatypeId();
        List<DataSubTypeEntity> dataSubTypes = dataSubTypeBusiness.getAllDataSubTypebyDataTypeId(dataTypeId);
        int loop = 1;
        for (DataSubTypeEntity dataSubTypeEntity : dataSubTypes) {
            DataObjectEntity childDataObj = new DataObjectEntity();
            copyPObjProperty2Child(parentDataObj, childDataObj);
            childDataObj.setParentdataobjectid(parentDataObj.getId());
            childDataObj.setDatatypeId(dataSubTypeEntity.getDatatypeId());
            childDataObj.setDataobjectname(dataSubTypeEntity.getDatasubname());
            childDataObj.setIsref(dataSubTypeEntity.getIsref());
            childDataObj.setOrdernumber(loop++);
            childDataObj.setSubtypeid(dataSubTypeEntity.getId());
            childDataObj.setUnit(dataSubTypeEntity.getUnit());
            dataObjectService.save(childDataObj);
            childDataObj.setOriginalObjId(childDataObj.getId());
            String subTypeParentId = parentDataObj.getSubtypeparentid() + "," + childDataObj.getId();
            childDataObj.setSubtypeparentid(subTypeParentId);
            dataObjectService.update(childDataObj);
            //递归下面的子数据类型
            createSubDataObj(childDataObj, newDataObjList);
            newDataObjList.add(childDataObj);
        }
        return true;
    }

    /**
     * 将父数据实例的数据拷贝至子数据实例,主要用处创建实例时,子实例的数据必需与父实例的数据一致
     *
     * @param parentDataObj
     * @param childDataObj
     * @return
     */
    private boolean copyPObjProperty2Child(DataObjectEntity parentDataObj, DataObjectEntity childDataObj) {
        childDataObj.setCreaterid(parentDataObj.getCreaterid());
        childDataObj.setModifierid(parentDataObj.getModifierid());
        childDataObj.setCreatetime(parentDataObj.getCreatetime());
        childDataObj.setModifytime(parentDataObj.getModifytime());
        childDataObj.setNodeId(parentDataObj.getNodeId());
        childDataObj.setNodeVersion(parentDataObj.getNodeVersion());
        childDataObj.setVersion(parentDataObj.getVersion());
        childDataObj.setIsglobal(parentDataObj.getIsglobal());
        return true;
    }

    /**
     * 数据实例修改后,更新数据,已经包括版本升级
     *
     * @param modifieredDataObjs
     * @return
     * @throws Exception
     */
    public boolean updateDateObj(List<DataObjectEntity> modifieredDataObjs) throws Exception {
        for (DataObjectEntity loopDataObj : modifieredDataObjs) {
            DataObjectEntity newDataObj = dataObjectService.getById(loopDataObj.getId());
            int compareVal = DataObjVersionUtil.versionCompare(newDataObj.getVersion(), loopDataObj.getVersion());
            if (compareVal == 0) { //版本未升级过,先升级
                upVersion(loopDataObj);
                newDataObj = dataObjectService.getById(loopDataObj.getId());
            }
            newDataObj.setValue(loopDataObj.getValue());
            newDataObj.setFileid(loopDataObj.getFileid());
            newDataObj.setDataobjectname(loopDataObj.getDataobjectname());
            newDataObj.setModifytime(new Date());
            newDataObj.setModifierid(UserContextUtil.getCurrentUser().getId());
            newDataObj.setUnit(loopDataObj.getUnit());
            newDataObj.setDescription(loopDataObj.getDescription());
            dataObjectService.update(newDataObj);
        }
        return true;
    }

    /**
     * 删除所有的数据实例,并将历史表中的数据设置为删除
     *
     * @param modifieredDataObjs
     * @return
     */
    public boolean deleteDataObj(List<DataObjectEntity> modifieredDataObjs) {
        for (DataObjectEntity loopDataObj : modifieredDataObjs) {
            hisDataObjectBusiness.deleteHisDataObj(loopDataObj.getId());
            dataObjectService.delete(loopDataObj);
        }
        return true;
    }

    /**
     * 将目前dataObj的整个家族都进行"数据提交升级"
     *
     * @param dataObj
     * @return 老的ID与新的数据Id的Mapping
     * @throws Exception
     */
    private boolean upVersion(DataObjectEntity dataObj) throws Exception {
        DataObjectEntity topDataObj = dataObjectService.getTopDataObjEntity(dataObj);
        List<DataObjectEntity> dataObjFamily = dataObjectService.getFamilyDataObjEntity(topDataObj);
        //先将原来的DataObj移动至历史表,再升级版本号及修改人,修改时间属性
        for (DataObjectEntity loop : dataObjFamily) {
            moveToHistory(loop);
            loop.setVersion(DataObjVersionUtil.dataSubmitUpVersion(loop.getVersion()));
            loop.setModifierid(UserContextUtil.getCurrentUser().getId());
            loop.setModifytime(new Date());
            dataObjectService.update(loop);
        }
        return true;
    }

    /**
     * 将DataObj 移动至历史表中
     *
     * @param oldDataObject
     * @return
     */
    public boolean moveToHistory(DataObjectEntity oldDataObject) {
        DataObjectOldVersionEntity hisDataObj = new DataObjectOldVersionEntity();
        hisDataObj.setDataobjectid(oldDataObject.getId());
        hisDataObj.setParentdataobjectid(oldDataObject.getParentdataobjectid());
        hisDataObj.setIsref(oldDataObject.getIsref());
        hisDataObj.setSubtypeid(oldDataObject.getSubtypeid());
        hisDataObj.setSubtypeparentid(oldDataObject.getSubtypeparentid());
        hisDataObj.setDataobjectname(oldDataObject.getDataobjectname());
        hisDataObj.setVersion(oldDataObject.getVersion());
        hisDataObj.setValue(oldDataObject.getValue());
        hisDataObj.setIsglobal(oldDataObject.getIsglobal());
        hisDataObj.setCreaterid(oldDataObject.getCreaterid());
        hisDataObj.setCreatetime(oldDataObject.getCreatetime());
        hisDataObj.setNodeId(oldDataObject.getNodeId());
        hisDataObj.setNodeVersion(oldDataObject.getNodeVersion());
        hisDataObj.setModifierid(oldDataObject.getModifierid());
        hisDataObj.setModifytime(oldDataObject.getModifytime());
        hisDataObj.setDimension(oldDataObject.getDimension());
        hisDataObj.setOrdernumber(oldDataObject.getOrdernumber());
        hisDataObj.setUnit(oldDataObject.getUnit());
        hisDataObj.setDescription(oldDataObject.getDescription());
        hisDataObj.setDatatypeId(oldDataObject.getDatatypeId());
        hisDataObj.setFileid(oldDataObject.getFileid());
        hisDataObj.setCreateBy(oldDataObject.getCreateBy());
        hisDataObj.setOriginalObjId(oldDataObject.getOriginalObjId());
        dataObjectOldVersionService.save(hisDataObj);
        return true;
    }

    /**
     * 交换两者的顺序
     *
     * @param dataObj1
     * @param dataObj2
     * @return
     */
    public boolean swapOderNumber(DataObjectEntity dataObj1, DataObjectEntity dataObj2) {
        int swap = dataObj1.getOrdernumber();
        dataObj1.setOrdernumber(dataObj2.getOrdernumber());
        dataObj2.setOrdernumber(swap);
        dataObjectService.update(dataObj1);
        dataObjectService.update(dataObj2);
        return true;
    }

    /**
     * 任务提交后,数据版本升级
     *
     * @param dataObj
     * @return
     */
    public boolean jobSubmitUpVersion(DataObjectEntity dataObj) {
        DataObjectEntity topDataObj = dataObjectService.getTopDataObjEntity(dataObj);
        List<DataObjectEntity> dataObjFamily = dataObjectService.getFamilyDataObjEntity(topDataObj);

        //先将原来的DataObj移动至历史表,再升级版本号及修改人,修改时间属性
        for (DataObjectEntity loop : dataObjFamily) {
            moveToHistory(loop);
            loop.setVersion(DataObjVersionUtil.jobSubmitUpVersion(loop.getVersion()));
            loop.setModifierid(UserContextUtil.getCurrentUser().getId());
            loop.setModifytime(new Date());
            dataObjectService.update(loop);
        }
        return true;
    }

    /**
     * 数据任务提交后,版本升级
     *
     * @param dataObj
     * @return
     */
    public boolean dataTaskSubmitUpVersion(DataObjectEntity dataObj) {
        DataObjectEntity topDataObj = dataObjectService.getTopDataObjEntity(dataObj);
        List<DataObjectEntity> dataObjFamily = dataObjectService.getFamilyDataObjEntity(topDataObj);
        //先将原来的DataObj移动至历史表,再升级版本号及修改人,修改时间属性
        for (DataObjectEntity loop : dataObjFamily) {
            moveToHistory(loop);
            loop.setVersion(DataObjVersionUtil.dataTaskSubmit(loop.getVersion()));
            loop.setModifierid(UserContextUtil.getCurrentUser().getId());
            loop.setModifytime(new Date());
            dataObjectService.update(loop);
        }
        return true;
    }

    public DataObjectEntity getDataObjEntityById(Integer Id) {
        return dataObjectService.getById(Id);
    }

    public List<DataObjectBean> dataChange(List<DataObjectEntity> objs) {
        List<DataObjectBean> resluts = new ArrayList<>();
        try {
            for (DataObjectEntity loop : objs) {
                DataObjectBean bean = new DataObjectBean();
                changeDataObjectEntityToDataObject(loop, bean);
                resluts.add(bean);
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            throw new OrientBaseAjaxException(e.getMessage(), e.getMessage());
        }
        return resluts;
    }

    public void changeDataObjectEntityToDataObject(DataObjectEntityBase loop, DataObjectBean bean) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
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

}
