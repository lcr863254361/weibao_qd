package com.orient.example.component.dataextend;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.sqlengine.extend.ModelDataOperate;
import com.orient.sqlengine.extend.annotation.ModelOperateExtend;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/24 0024.
 */
@ModelOperateExtend(modelNames = "*", schemaName = "数据模型")
public class ModelDataOperateExample implements ModelDataOperate {

    @Override
    public void beforeAdd(IBusinessModel bm, Map<String, String> dataMap) {
        System.err.println("通用新增前处理");
    }

    @Override
    public void afterAdd(IBusinessModel bm, Map<String, String> dataMap, String id) {
        System.err.println("通用新增后处理：" + id);
    }

    @Override
    public void beforeDelete(IBusinessModel bm, String dataIds) {
        System.err.println("通用删除前处理");
    }

    @Override
    public void afterDelete(IBusinessModel bm, String dataIds) {
        System.err.println("通用删除后处理");
    }

    @Override
    public void beforeDeleteCascade(IBusinessModel bm, String dataIds) {
        System.err.println("通用级联删除前处理");
    }

    @Override
    public void afterDeleteCascade(IBusinessModel bm, String dataIds) {
        System.err.println("通用级联删出后处理");
    }

    @Override
    public void beforeUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId) {
        System.err.println("通用修改前处理");
    }

    @Override
    public void afterUpdate(IBusinessModel bm, Map<String, String> dataMap, String dataId, Boolean result) {
        System.err.println("通用修改后处理：" + result);
    }
}
