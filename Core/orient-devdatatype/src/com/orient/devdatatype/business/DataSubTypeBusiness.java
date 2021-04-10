package com.orient.devdatatype.business;

import com.orient.sysmodel.domain.taskdata.DataSubTypeEntity;
import com.orient.sysmodel.domain.taskdata.DataTypeEntity;
import com.orient.sysmodel.service.PageBean;
import com.orient.sysmodel.service.taskdata.IDataSubTypeEntityService;
import com.orient.sysmodel.service.taskdata.IDataTypeEntityService;
import com.orient.web.base.BaseBusiness;
import com.orient.web.util.UserContextUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author mengbin
 * @create 2016-07-04 下午7:27
 */
@Component
public class DataSubTypeBusiness extends BaseBusiness {

    @Autowired
    IDataSubTypeEntityService dataSubTypeEntityService;

    @Autowired
    IDataTypeEntityService dataTypeEntityService;

    @Autowired
    DataTypeBusiness dataTypeBusiness;

    public Integer count() {
        return dataSubTypeEntityService.count();
    }


    public List<DataSubTypeEntity> list(Integer page, Integer limit) {
        PageBean pageBean = new PageBean();
        pageBean.setRows(limit);
        pageBean.setPage(page);
        pageBean.addOrder(Order.asc("id"));
        return dataSubTypeEntityService.listByPage(pageBean);
    }

    public void delete(Long[] toDelIds) {
        dataSubTypeEntityService.delete(toDelIds);
    }

    public void save(DataSubTypeEntity formValue) {
        dataSubTypeEntityService.save(formValue);
    }

    public void update(DataSubTypeEntity formValue) {
        dataSubTypeEntityService.update(formValue);
    }


    /**
     * 根据dataTypeId 获取 最新版本DataSubType列表
     *
     * @param dataTypeId
     * @return
     */
    public List<DataSubTypeEntity> getAllNewestDataSubTypebyDataTypeId(String dataTypeId) {

        Criterion filter1 = Restrictions.eq("datatypeId", dataTypeId);
        Criterion filter2 = Restrictions.eq("isnewest", 1);


        List<DataSubTypeEntity> subTypes = dataSubTypeEntityService.list(filter1, filter2);
        return subTypes;
    }

    /**
     * 根据dataTypeId 获取DataSubType列表
     *
     * @param dataTypeId
     * @return
     */
    public List<DataSubTypeEntity> getAllDataSubTypebyDataTypeId(String dataTypeId) {

        Criterion[] filters = new Criterion[2];
        filters[0] = Restrictions.eq("datatypeId", dataTypeId);
        filters[1] = Restrictions.eq("status", 2);
        Order orders[] = new Order[1];
        orders[0] = Order.asc("ordernumber");
        List<DataSubTypeEntity> subTypes = dataSubTypeEntityService.list(filters, orders);
        return subTypes;

    }


    /**
     * 子数据类型升级
     *
     * @param oldDataSubType 原来的子数据类型
     * @param newDataTypeId  新的子数据类型的
     * @return 新的子数据类型
     */
    public DataSubTypeEntity upVersion(DataSubTypeEntity oldDataSubType, String newDataTypeId) {

        DataSubTypeEntity newdst = new DataSubTypeEntity();
        newdst.setDatatypeId(newDataTypeId);
        newdst.setCreatetime(new Date());
        newdst.setDatasubname(oldDataSubType.getDatasubname());
        newdst.setDatatype(oldDataSubType.getDatatype());

        newdst.setDimension(oldDataSubType.getDimension());
        newdst.setFileid(oldDataSubType.getFileid());

        newdst.setIsnewest(1);
        newdst.setIsref(oldDataSubType.getIsref());
        newdst.setOrdernumber(oldDataSubType.getOrdernumber());
        newdst.setStatus(oldDataSubType.getStatus());
        newdst.setUnit(oldDataSubType.getUnit());
        newdst.setUserid(oldDataSubType.getUserid());
        newdst.setValue(oldDataSubType.getValue());
        newdst.setVersion(oldDataSubType.getVersion() + 1);
        newdst.setSubtypecode(oldDataSubType.getSubtypecode());
        dataSubTypeEntityService.save(newdst);
        oldDataSubType.setIsnewest(0);
        dataSubTypeEntityService.update(oldDataSubType);
        return newdst;
    }


    /**
     * 根据子类型编码 获取最新版的DataSubType
     *
     * @param subTypeCode
     * @return
     */
    public DataSubTypeEntity getNewestDataSubTypeByCode(String subTypeCode) {
        Criterion filter1 = Restrictions.eq("subtypecode", subTypeCode);
        Criterion filter2 = Restrictions.eq("isnewest", 1);
        List<DataSubTypeEntity> subTypes = dataSubTypeEntityService.list(filter1, filter2);
        if (subTypes.size() >= 1) {
            return subTypes.get(0);
        }
        return null;
    }


    /**
     * 创建新的DataSubType ,已经包括了升级版本
     *
     * @param dataSubType
     * @return
     */
    @Deprecated
    public boolean createNewDataSubType(DataSubTypeEntity dataSubType) {

        if (checkReName(dataSubType)) {
            return false;
        }
        String dataTypeId = dataSubType.getDatatypeId();
        DataTypeEntity newDataTypeEntity = dataTypeBusiness.upVersion(dataTypeId);

        DataSubTypeEntity newdst = new DataSubTypeEntity();
        newdst.setDatatypeId(newDataTypeEntity.getId());
        newdst.setCreatetime(new Date());
        newdst.setDatasubname(dataSubType.getDatasubname());
        newdst.setDatatype(dataSubType.getDatatype());
        newdst.setDimension(dataSubType.getDimension());
        newdst.setFileid(dataSubType.getFileid());
        newdst.setIsnewest(1);
        newdst.setIsref(dataSubType.getIsref());
        newdst.setOrdernumber(dataSubTypeEntityService.getNextOrderNum());
        newdst.setStatus(2);
        newdst.setUnit(dataSubType.getUnit());
        newdst.setUserid(UserContextUtil.getCurrentUser().getId());
        newdst.setValue(dataSubType.getValue());
        newdst.setVersion(1);
        newdst.setSubtypecode(Calendar.getInstance().getTimeInMillis() + "");
        newdst.setDimension("1");
        dataSubTypeEntityService.save(newdst);

        return true;
    }


    /**
     * ,调用前应先升级好版本
     *
     * @param dataSubType
     * @param parentDataType
     * @return
     */
    public boolean createNewDataSubType(DataSubTypeEntity dataSubType, DataTypeEntity parentDataType) {

        DataSubTypeEntity newdst = new DataSubTypeEntity();
        newdst.setDatatypeId(parentDataType.getId());
        newdst.setCreatetime(new Date());
        newdst.setDatasubname(dataSubType.getDatasubname());
        newdst.setDatatype(dataSubType.getDatatype());
        newdst.setDimension(dataSubType.getDimension());
        newdst.setFileid(dataSubType.getFileid());
        newdst.setIsnewest(1);
        newdst.setIsref(dataSubType.getIsref());
        newdst.setOrdernumber(dataSubTypeEntityService.count(Restrictions.eq("datatypeId", parentDataType.getId())) + 1);
        newdst.setStatus(2);
        newdst.setUnit(dataSubType.getUnit());
        newdst.setUserid(UserContextUtil.getCurrentUser().getId());
        newdst.setValue(dataSubType.getValue());
        newdst.setVersion(1);
        newdst.setSubtypecode(Calendar.getInstance().getTimeInMillis() + "");
        newdst.setDimension("1");
        dataSubTypeEntityService.save(newdst);
        return true;
    }


    /**
     * ,调用前应先升级好版本
     *
     * @param dataSubType
     * @param parentDataType 更新版本后需要挂接的父DataType
     * @return
     */
    public DataSubTypeEntity updateDataSubType(DataSubTypeEntity dataSubType, DataTypeEntity parentDataType) {

        String dataSubTypeCode = dataSubType.getSubtypecode();

        DataSubTypeEntity modifieddst = getNewestDataSubTypeByCode(dataSubTypeCode);
        copySimpleProperty(dataSubType, modifieddst);
        modifieddst.setDatatypeId(parentDataType.getId());
        dataSubTypeEntityService.update(modifieddst);
        return modifieddst;
    }


    /**
     * 更新DataSubType,已经包括了升级版本
     *
     * @param dataSubType 需要更新的子数据类型
     * @return
     */
    @Deprecated
    public DataSubTypeEntity updateDataSubType(DataSubTypeEntity dataSubType) {

        if (checkReName(dataSubType)) {
            return null;
        }

        String dataTypeId = dataSubType.getDatatypeId();
        DataTypeEntity newDataTypeEntity = dataTypeBusiness.upVersion(dataTypeId);

        String dataSubTypeCode = dataSubType.getSubtypecode();

        DataSubTypeEntity modifieddst = getNewestDataSubTypeByCode(dataSubTypeCode);

        copySimpleProperty(dataSubType, modifieddst);
        modifieddst.setDatatypeId(newDataTypeEntity.getId());
        dataSubTypeEntityService.update(modifieddst);
        return modifieddst;
    }


    /**
     * 删除DataSubType.已经包括了升级版本
     *
     * @param dataSubType
     * @return 返回版本升级后的DataTypeEntity
     */
    @Deprecated
    public DataTypeEntity deleteDataSubType(DataSubTypeEntity dataSubType) {

        String dataTypeId = dataSubType.getDatatypeId();
        DataTypeEntity newDataTypeEntity = dataTypeBusiness.upVersion(dataTypeId);

        String dataSubTypeCode = dataSubType.getSubtypecode();
        DataSubTypeEntity doDel = getNewestDataSubTypeByCode(dataSubTypeCode);
        dataSubTypeEntityService.delete(doDel);

        return newDataTypeEntity;
    }


    /**
     * 删除子数据类型,不包含升级,调用前应先升级好版本
     *
     * @param dataSubType
     * @param parentDateType
     * @return
     */
    public boolean deleteDataSubType(DataSubTypeEntity dataSubType, DataTypeEntity parentDateType) {
        String dataSubTypeCode = dataSubType.getSubtypecode();
        DataSubTypeEntity doDel = getNewestDataSubTypeByCode(dataSubTypeCode);
        dataSubTypeEntityService.delete(doDel);

        return true;
    }

    /**
     * 拷贝属性值,除去 ID, DataSubTypeCode , Version , OrderNumber不拷贝
     * 设置新的createTime,UserID
     *
     * @param src
     * @param dist
     * @return
     */
    public boolean copySimpleProperty(DataSubTypeEntity src, DataSubTypeEntity dist) {


        dist.setCreatetime(new Date());
        dist.setDatasubname(src.getDatasubname());
        dist.setDatatype(src.getDatatype());

        dist.setDimension(src.getDimension());
        dist.setFileid(src.getFileid());
        dist.setIsref(src.getIsref());
        dist.setStatus(2);
        dist.setUnit(src.getUnit());
        dist.setUserid(UserContextUtil.getCurrentUser().getId());
        dist.setValue(src.getValue());
        return true;

    }

    /**
     * 校验同一个复杂类型的下是否有重名子数据类型
     *
     * @param dataSubTypeEntity
     * @return true 说明已经重名
     */
    public boolean checkReName(DataSubTypeEntity dataSubTypeEntity) {

        String dataTypeId = dataSubTypeEntity.getDatatypeId();
        //  List<DataSubTypeEntity>  allDataSubTypes = this.getAllNewestDataSubTypebyDataTypeId(dataTypeId);

        //获取当时版本下所有
        List<DataSubTypeEntity> allDataSubTypes = this.getAllDataSubTypebyDataTypeId(dataTypeId);


        for (DataSubTypeEntity loop : allDataSubTypes) {
            //类型相同,名称相同,编码不同,则说明重名了
            if (loop.getDatasubname().equals(dataSubTypeEntity.getDatasubname()) &&
                    !loop.getSubtypecode().equals(dataSubTypeEntity.getSubtypecode()) &&
                    loop.getDatatype().equals(dataSubTypeEntity.getDatatype())) {
                return true;
            }
        }
        return false;

    }

    /**
     * 获取父DataType
     *
     * @param dataSubType
     * @return
     */
    public DataTypeEntity getParentDataType(DataSubTypeEntity dataSubType) {

        if (dataSubType.getDatatypeId() != null && !dataSubType.getDatatypeId().equals("")) {
            return dataTypeEntityService.getById(dataSubType.getDatatypeId());
        }
        return null;

    }

    public DataSubTypeEntity getDataSubTypeEntityById(String dataSubTypeId) {

        return dataSubTypeEntityService.getById(dataSubTypeId);
    }


}
