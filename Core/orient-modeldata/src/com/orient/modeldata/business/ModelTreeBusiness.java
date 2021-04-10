package com.orient.modeldata.business;

import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.modeldata.bean.SchemaNodeVO;
import com.orient.modeldata.bean.SchemaWrapper;
import com.orient.modeldata.bean.TBomTree;
import com.orient.sysmodel.operationinterface.*;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.BaseBusiness;
import com.orient.web.util.UserContextUtil;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Tbom业务处理
 *
 * @author enjoy
 * @createTime 2016-05-17 9:12
 */
@Component
public class ModelTreeBusiness extends BaseBusiness {

    /**
     * @return 根据用户ID 以及 功能点ID 获取schema集合
     */
    public List<SchemaWrapper> getUserSchemaList(Long functionId) {
        List<SchemaWrapper> schemaList = new ArrayList<>();
        //获取当前用户ID
        String userId = UserContextUtil.getUserId();
        //获取角色缓存信息
        IRoleModel roleModel = roleEngine.getRoleModel(false);
        //获取用户对象
        IUser user = roleModel.getUserById(userId);
        //获取该用户所拥有的schema信息
        Map<String, ISchema> allSchemas = getSchemasByUser(user);
        Iterator iterSch = allSchemas.entrySet().iterator();
        //遍历 包装前台交互类
        while (iterSch.hasNext()) {
            Map.Entry entry = (Map.Entry) iterSch.next();
            ISchema val = (ISchema) entry.getValue();
            SchemaWrapper schema = new SchemaWrapper();
            BeanUtils.copyProperties(schema, val);
            schemaList.add(schema);
        }
        //根据功能点过滤
        if (null != functionId) {
            filterSchema(userId, functionId, schemaList);
        }
        return schemaList;
    }

    /**
     * 去除没有tbom的schema
     *
     * @param userId
     * @param functionId
     * @param schemaList
     */
    private void filterSchema(String userId, Long functionId, List<SchemaWrapper> schemaList) {
        List<SchemaWrapper> copySchemaList = new ArrayList<>();
        //拷贝
        copySchemaList.addAll(schemaList);
        //循环过滤
        copySchemaList.forEach(schema -> {
            List<TBomTree> tBomTreeList = getAllEBomTree(userId, functionId, schema.getId());
            if (CommonTools.isEmptyList(tBomTreeList)) {
                schemaList.remove(schema);
            }
        });
    }

    /**
     * @param user
     * @return 根据用户信息 获取 其拥有权限的schema集合
     */
    private Map<String, ISchema> getSchemasByUser(IUser user) {
        Map<String, ISchema> allSchema = new LinkedHashMap<>();
        //获取当前用户所拥有的角色信息
        List<IRole> roleList = roleEngine.getRoleModel(false).getRolesOfUser(
                user.getId());
        //根据角色 整合 schema集合
        roleList.forEach(iRole -> {
            List<ISchema> allsch = iRole.getAllSchemas();
            for (ISchema schema : allsch) {
                allSchema.put(schema.getId(), schema);
            }
        });
        return allSchema;
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
     * @param schemaId
     * @param node
     * @return 获取schema下面所有模型树形化信息
     */
    public List<SchemaNodeVO> getNodesBySchemaId(String schemaId, String node) {
        List<SchemaNodeVO> retVal = new ArrayList<>();
        if ("root".equals(node) && !StringUtil.isEmpty(schemaId)) {
            ISchema schema = metaEngine.getMeta(false).getISchemaById(schemaId);
            List<ITable> tables = schema.getAllTables();
            List<ITable> sortedTables = tables.stream().sorted((t1,t2)->t1.getOrder().intValue() - t2.getOrder().intValue()).collect(Collectors.toList());
            sortedTables.forEach(table -> {
                SchemaNodeVO schemaNodeVO = new SchemaNodeVO();
                schemaNodeVO.setModelId(table.getId());
                schemaNodeVO.setExpanded(true);
                schemaNodeVO.setLeaf(true);
                schemaNodeVO.setText(table.getDisplayName());
                schemaNodeVO.setIconCls("icon-model");
                retVal.add(schemaNodeVO);
            });
        }
        return retVal;
    }
}
