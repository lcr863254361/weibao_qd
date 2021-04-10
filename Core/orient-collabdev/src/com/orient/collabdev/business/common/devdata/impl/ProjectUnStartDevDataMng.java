package com.orient.collabdev.business.common.devdata.impl;

import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.common.devdata.IDevDataMng;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.devdataobj.bean.DataObjectBean;
import com.orient.devdataobj.business.DataObjectBusiness;
import com.orient.sysmodel.domain.taskdata.DataObjectEntity;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 项目没有启动情况下，研发数据的增删改处理类
 * @Author GNY
 * @Date 2018/8/16 16:07
 * @Version 1.0
 **/
@MngStatus(status = ManagerStatusEnum.UNSTART)
@Component
public class ProjectUnStartDevDataMng implements IDevDataMng {

    @Autowired
    DataObjectBusiness dataObjectBusiness;

    /**
     * 项目没有在运行中，直接保存研发数据就即可
     *
     * @param data
     * @param nodeId
     * @param nodeVersion
     * @return
     */
    @Override
    public boolean createDataObj(Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion) {
        List<DataObjectBean> dataObjs = data.get("data");
        boolean bSuc = true;
        try {
            for (DataObjectBean dataObj : dataObjs) {
                DataObjectEntity newDataObjEntity = new DataObjectEntity();
                PropertyUtils.copyProperties(newDataObjEntity, dataObj);
                newDataObjEntity.setNodeId(nodeId);
                newDataObjEntity.setNodeVersion(nodeVersion);
                if (dataObjectBusiness.createTopDataObj(newDataObjEntity).size() == 0) {
                    bSuc = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return bSuc;
    }

    @Override
    public boolean updateDataObj(Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion) {
        List<DataObjectBean> dataObjs = data.get("data");
        List<DataObjectEntity> modifiedObjs = new ArrayList<>();
        boolean bSuc;
        try {
            for (DataObjectBean dataObj : dataObjs) {
                DataObjectEntity modifiedObj = new DataObjectEntity();
                PropertyUtils.copyProperties(modifiedObj, dataObj);
                modifiedObjs.add(modifiedObj);
            }
            bSuc = dataObjectBusiness.updateDateObj(modifiedObjs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return bSuc;
    }

    @Override
    public boolean deleteDataObj(Map<String, List<DataObjectBean>> data, String nodeId, Integer nodeVersion) {
        List<DataObjectBean> dataObjs = data.get("data");
        List<DataObjectEntity> deletedObjs = new ArrayList<>();
        boolean bSuc;
        try {
            for (DataObjectBean dataObj : dataObjs) {
                DataObjectEntity modifiedObj = new DataObjectEntity();
                PropertyUtils.copyProperties(modifiedObj, dataObj);
                deletedObjs.add(modifiedObj);
            }
            bSuc = dataObjectBusiness.deleteDataObj(deletedObjs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return bSuc;
    }

}
