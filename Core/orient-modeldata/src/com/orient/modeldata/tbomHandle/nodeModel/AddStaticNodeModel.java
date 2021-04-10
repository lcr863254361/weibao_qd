package com.orient.modeldata.tbomHandle.nodeModel;


import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.modeldata.bean.TBomModel;

import java.util.List;

/**
 * 增加静态Tbom节点绑定模型
 */
public interface AddStaticNodeModel {
    /**
     * 获取静态节点新增模型
     * @return
     */
    IBusinessModel getBusinessModel();

    /**
     * 获取模型绑定的表单模板名称
     * @return
     */
    String getTemplateName();

    /** 设置新增动态模型过滤条件
     *
     * @param bm
     * @return
     */
    CustomerFilter getCustomerFilter(IBusinessModel bm);

    /** 对生成后的静态节点的所有模型进行处理
     *
     * @param tBomModels
     */
    void customAllModels(List<TBomModel> tBomModels);
}
