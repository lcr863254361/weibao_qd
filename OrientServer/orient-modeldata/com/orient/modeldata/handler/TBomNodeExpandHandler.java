package com.orient.modeldata.handler;

import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.tbomHandle.annotation.NodeHandle;
import com.orient.modeldata.tbomHandle.handle.TbomHandle;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.operationinterface.ITbom;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.utils.CommonTools;
import com.orient.web.model.BaseNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-06-01 15:15
 */
@NodeHandle(order = 1,tbomName = "*")
public class TBomNodeExpandHandler implements TbomHandle {
    @Autowired
    @Qualifier("RoleEngine")
    private IRoleUtil roleEngine;

    @Override
    public void handleTreeNodes(List<BaseNode> nodes, TBomNode fatherNode) {
        IRoleModel roleModel = roleEngine.getRoleModel(false);
        String tbomId;
        if(fatherNode == null && !CommonTools.isEmptyList(nodes)) {
            //根节点 则子节点肯定是静态节点
            tbomId = ((TBomNode)nodes.get(0)).getTbomId();
        }else{
            tbomId = fatherNode.getTbomId();
        }
        ITbom tbom = roleModel.getTbomById(tbomId);
        int expLevel = 1;
        String expandLevel = tbom.getExpandLevel();
        if(expandLevel!=null && !"".equals(expandLevel)) {
            expLevel = Integer.valueOf(expandLevel);
        }
        for(BaseNode baseNode : nodes) {
            TBomNode tBomNode = (TBomNode) baseNode;
            if(tBomNode.getLevel() < expLevel) {
                tBomNode.setExpanded(true);
            }
            else {
                tBomNode.setExpanded(false);
            }
        }
    }
}
