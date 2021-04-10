package com.orient.modeldata.tbomHandle.nodeModel;


import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.modeldata.bean.TBomDynamicNode;
import com.orient.modeldata.bean.TBomModel;
import com.orient.modeldata.bean.TBomNode;

import java.util.List;
import java.util.Map;

/**
 * TBom动态节点递归定制
 */
public interface BuildRecurrentlyDynamicNode {

    /** 父节点为动态节点时获取其过滤条件
     *
     * @param bm
     * @param fatherNode
     * @return
     */
    List<CustomerFilter> getDynamicFatherNodeFilters(IBusinessModel bm, TBomNode fatherNode);

    /** 父节点为静态节点时获取其过滤条件
     *
     * @param bm
     * @param fatherNode
     * @return
     */
    List<CustomerFilter> getStaticFatherNodeFilters(IBusinessModel bm, TBomNode fatherNode);

    /**
     * 获取动态节点名称
     * @param bm
     * @param resultNode
     * @return
     */
    String getNodeText(IBusinessModel bm, Map<String, Object> resultNode);

    /** 设置动态模型过滤条件
     * @param refBm
     * @param dynamicNode
     * @param results
     * @return
     */
    CustomerFilter getCustomerFilter(IBusinessModel refBm, TBomDynamicNode dynamicNode, List<Map<String, Object>> results);

    /** 对生成后的静态节点的所有模型进行处理
     *
     * @param tBomModels
     */
    void customAllModels(List<TBomModel> tBomModels);
}
