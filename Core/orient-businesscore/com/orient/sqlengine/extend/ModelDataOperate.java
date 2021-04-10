package com.orient.sqlengine.extend;

import com.orient.businessmodel.bean.IBusinessModel;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
public interface ModelDataOperate {

    void beforeAdd(IBusinessModel bm, Map<String, String> dataMap);

    void afterAdd(IBusinessModel bm, Map<String, String> dataMap, String id);

    void beforeDelete(IBusinessModel bm, String dataIds);

    void afterDelete(IBusinessModel bm, String dataIds);

    void beforeDeleteCascade(IBusinessModel bm, String dataIds);

    void afterDeleteCascade(IBusinessModel bm, String dataIds);

    void beforeUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId);

    void afterUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, Boolean result);
}
