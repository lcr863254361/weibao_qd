package com.orient.modeldata.tbomHandle.permission;

import com.orient.web.model.BaseNode;

import java.util.List;

/**
 * 定制Tbom权限
 *
 * @author DuanDuan Pan
 * @create 2017-03-09 11:31
 */
public interface CustomTbomPermission {

    /**
     * 1.可过滤出不符合定制业务的节点,挑选出不符合当前业务节点，作为返回值。
     * 2.可定制Tbom节点关联模型数据过滤条件，如本来的过滤条件为 id in (1,2,3),可以追加过滤条件
     *
     * @param nodes 准备返回前端的Tbom节点描述
     * @return 不符合定制业务的，待删除的Tbom节点描述
     */
    List<BaseNode> doRemove(List<BaseNode> nodes);
}
