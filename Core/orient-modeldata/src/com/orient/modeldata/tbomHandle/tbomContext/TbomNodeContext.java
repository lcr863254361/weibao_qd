package com.orient.modeldata.tbomHandle.tbomContext;

import com.orient.web.model.BaseNode;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.modeldata.tbomHandle.tbomStrategy.TbomNodeStrategy;

import java.util.List;

/**
 * 获取Tbom策略的容器
 *
 * @author enjoy
 * @createTime 2016-05-21 11:08
 */
public class TbomNodeContext {

    private TbomNodeStrategy tbomNodeStrategy;

    public TbomNodeContext(TbomNodeStrategy tbomNodeStrategy) {
        this.tbomNodeStrategy = tbomNodeStrategy;
    }

    /**
     * 获取子节点信息
     *
     * @param getTbomNodesEventParam 父节点信息描述
     * @return
     */
    public List<BaseNode> getSonTbomNodes(GetTbomNodesEventParam getTbomNodesEventParam) {
        return tbomNodeStrategy.getSonTbomNodes(getTbomNodesEventParam);
    }
}

