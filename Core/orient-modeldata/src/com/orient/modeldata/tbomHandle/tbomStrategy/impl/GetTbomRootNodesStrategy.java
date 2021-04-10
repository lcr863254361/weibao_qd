package com.orient.modeldata.tbomHandle.tbomStrategy.impl;

import com.orient.modeldata.bean.*;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.modeldata.tbomHandle.tbomStrategy.TbomNodeStrategy;
import com.orient.modeldata.tbomHandle.template.TbomStaticNodeBuilder;
import com.orient.sysmodel.domain.tbom.TbomDir;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.operationinterface.IRoleFunctionTbom;
import com.orient.sysmodel.operationinterface.ITbom;
import com.orient.sysmodel.operationinterface.ITbomDir;
import com.orient.sysmodel.roleengine.impl.RoleUtilImpl;
import com.orient.web.model.BaseNode;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 获取Tbom根节点描述
 *
 * @author enjoy
 * @createTime 2016-05-22 10:38
 */

@Component
public class GetTbomRootNodesStrategy implements TbomNodeStrategy {

    @Autowired
    protected RoleUtilImpl roleEngine;

    @Autowired
    protected TbomStaticNodeBuilder tbomStaticNodeBuilder;

    @Override
    public List<BaseNode> getSonTbomNodes(GetTbomNodesEventParam getTbomNodesEventParam) {
        //返回值
        List<BaseNode> retVal = new ArrayList<>();
        //获取当前登录用户ID
        String userId = UserContextUtil.getUserId();
        //
        Long belongFunctionId = getTbomNodesEventParam.getBelongFunctionId();
        String schemaId = getTbomNodesEventParam.getSchemaId();
        //获取当前用户所有可见Tbom信息
        List<TBomTree> tboms = getAllEBomTree(userId, belongFunctionId, schemaId);
        //排序
        List<TBomTree> sortedTboms  = tboms.stream().sorted((tbom1,tbom2)-> tbom1.getOrderSign().intValue() - tbom2.getOrderSign().intValue()).collect(Collectors.toList());
        //转化为前端节点对象
        sortedTboms.forEach(tBomTree -> {
            BaseNode rootNode = getTreeRootNode(tBomTree.getId());
            retVal.add(rootNode);
        });
        return retVal;
    }

    /**
     * @param userId     用户ID
     * @param functionId 功能点ID
     * @param schemaId   schema ID
     * @return 获取功能点下tbom集合
     */
    public List<TBomTree> getAllEBomTree(String userId, long functionId, String schemaId) {
        Map<String, ITbomDir> treeMap = new LinkedHashMap<>();
        //获取缓存信息
        List<IRole> RoleList = roleEngine.getRoleModel(false).getRolesOfUser(userId);
        //遍历
        RoleList.forEach(iRole -> {
            //获取功能点描述
            List<IRoleFunctionTbom> allfunctionbomTrees = iRole
                    .getAllFunctionTboms();
            //根据功能点ID过滤
            allfunctionbomTrees.stream().filter(roleFunctionTbom -> roleFunctionTbom.getFunction().getFunctionid() == functionId).forEach(roleFunctionTbom -> treeMap.put(roleFunctionTbom.getTbomDir().getId(), roleFunctionTbom.getTbomDir()));
        });
        List<TBomTree> treeList = new ArrayList<>();
        Iterator<String> iter = treeMap.keySet().iterator();
        //遍历准备数据
        while (iter.hasNext()) {
            String EBomId = iter.next();
            //获取tbom描述
            ITbomDir bom = treeMap.get(EBomId);
            if (bom.getSchemaid().equalsIgnoreCase(schemaId)) {
                TBomTree ebomTree = new TBomTree();
                ebomTree.setId(EBomId);
                ebomTree.setText(bom.getName());
                ebomTree.setOrderSign(bom.getOrder_sign());
                treeList.add(ebomTree);
            }
        }
        return treeList;
    }

    /**
     * @param treeId tbom ID
     * @return 根据tbomId 获取Tbom根节点信息
     */
    public BaseNode getTreeRootNode(String treeId) {

        final TBomNode[] treenode = {new TBomNode()};
        //获取当前用户ID
        String userId = UserContextUtil.getUserId();
        //获取当前用户的角色信息
        List<IRole> roleList = roleEngine.getRoleModel(false).getRolesOfUser(userId);
        // 根据EDM_EBomTree_Async得到ITbomDir
        final ITbomDir[] bomDir = {new TbomDir()};
        //初始化tbom
        roleList.forEach(iRole -> {
            if (bomDir[0].getId() == null) {
                ITbomDir rootTbom = iRole.getTbomDirById(treeId);
                if (null != rootTbom) {
                    bomDir[0] = rootTbom;
                }
            }
        });
        ITbomDir tbom = bomDir[0];
        //准备Tbom根节点
        roleList.forEach(iRole -> {
            //获取当前角色所有的Tbom
            List<ITbom> tbomrootNodes = iRole.getAllTboms();
            tbomrootNodes.forEach(treeroot -> {
                //如果与传进来的tbom匹配
                if (treeroot.getName().equalsIgnoreCase(tbom.getName())
                        && treeroot.getSchema().getId().equalsIgnoreCase(tbom.getSchemaid())) {
                    //初始化节点信息
                    TBomStaticNodeMaterial tBomStaticNodeMaterial = new TBomStaticNodeMaterial(treeroot.getId(), treeroot.getId(), null, treeroot);
                    treenode[0] = tbomStaticNodeBuilder.builderTBomNode(tBomStaticNodeMaterial).get(0);
                }
            });
        });
        return treenode[0];
    }
}

