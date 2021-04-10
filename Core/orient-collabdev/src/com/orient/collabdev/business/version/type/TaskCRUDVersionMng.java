package com.orient.collabdev.business.version.type;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.version.status.IVersionModifyer;
import com.orient.collabdev.constant.CollabDevConstants;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-07-28 10:23 AM
 */
public class TaskCRUDVersionMng extends AbstructCRUDVersionMng {

    public TaskCRUDVersionMng(IVersionModifyer versionModifyer) {
        super.versionModifyer = versionModifyer;
    }

    @Override
    public void doCreate(IBusinessModel bm, Map<String, String> dataMap, String id) {
        super.versionModifyer.influentByCreate(bm, dataMap, id);
    }

    @Override
    public void doUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId) {
        super.versionModifyer.influentByUpdate(bm, dataMap, dataId, CollabDevConstants.NODE_TYPE_TASK);
    }

    @Override
    public void doDelete(IBusinessModel bm, String dataIds) {
        super.versionModifyer.influentByDelete(bm, dataIds, CollabDevConstants.NODE_TYPE_TASK);
    }
}
