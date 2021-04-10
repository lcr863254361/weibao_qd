package com.orient.collabdev.business.version.type;

import com.orient.businessmodel.bean.IBusinessModel;

import java.util.Map;

/**
 * 桥接模式 减少继承类的数量·
 *
 * @author panduanduan
 * @create 2018-07-28 10:17 AM
 */
public interface ICRUDVersionMng {

    void doCreate(IBusinessModel bm, Map<String, String> dataMap, String id);

    void doUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId);

    void doDelete(IBusinessModel bm, String dataIds);
}
