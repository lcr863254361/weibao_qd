package com.orient.collab.business;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.collab.business.strategy.FunctionModule;
import com.orient.collab.business.strategy.ProjectTreeNodeStrategy;
import com.orient.collab.config.CollabConstants;
import com.orient.collab.model.Project;
import com.orient.collab.model.ProjectTreeNode;
import com.orient.collab.model.TreeDeleteResult;
import com.orient.utils.CommonTools;
import com.orient.utils.Pair;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.web.base.BaseBusiness;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.collab.config.CollabConstants.NODE_ID_SPLIT;
import static com.orient.collab.config.CollabConstants.STATUS_MAPPING;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * the project tree business
 *
 * @author Seraph
 *         2016-07-06 上午11:35
 */
@Service
public class ProjectTreeBusiness extends BaseBusiness {

    public List<ProjectTreeNode> getNextLayerNodes(String functionModule, String modelName, String dataId) {

        if (modelName.startsWith("\"")) {//ext's bug?
            modelName = modelName.substring(1, modelName.length() - 1);
        }

        ProjectTreeNodeStrategy nodeStrategy = ProjectTreeNodeStrategy.fromString(modelName);
        if (nodeStrategy == null) {
            return UtilFactory.newArrayList();
        }
        return nodeStrategy.getNextLayerNodes(FunctionModule.fromString(functionModule), dataId);
    }

    public Map<String, String> getNodeModelInfo(String modelName) {
        Map<String, String> retV = UtilFactory.newHashMap();
        IBusinessModel bm = this.businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, Table);
        retV.put("modelId", bm.getId());
        retV.put("schemaId", bm.getSchema().getId());
        return retV;
    }

    public TreeDeleteResult deleteNode(String modelName, String dataId) {

        ProjectTreeNodeStrategy nodeStrategy = ProjectTreeNodeStrategy.fromString(modelName);
        return nodeStrategy.deleteNode(dataId);
    }

    public ProjectTreeNode getTreeNodeInfo(String modelName, String dataId) {
        IBusinessModel curBm = this.businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, Table);
        curBm.setReserve_filter(" AND ID = '" + dataId + "'");
        List<Map<String, Object>> oriDatas = this.orientSqlEngine.getBmService().createModelQuery(curBm).list();
        assert oriDatas.size() == 1;

        ProjectTreeNode node = new ProjectTreeNode();
        node.setModelName(modelName);
        node.setModelId(curBm.getId());
        node.setDataId(dataId);
        node.setId(modelName + NODE_ID_SPLIT + node.getDataId());
        node.setText(CommonTools.Obj2String(oriDatas.get(0).get("NAME_" + curBm.getId())));
        return node;
    }

    public ProjectTreeNode getParentTreeNodeInfo(String modelName, String dataId) {
        IBusinessModel curBm = this.businessModelService.getBusinessModelBySName(modelName, COLLAB_SCHEMA_ID, Table);
        curBm.setReserve_filter(" AND ID = '" + dataId + "'");
        List<Map<String, String>> oriDatas = this.orientSqlEngine.getBmService().createModelQuery(curBm).list();

        ProjectTreeNodeStrategy strategy = ProjectTreeNodeStrategy.fromString(modelName);
        Pair<ProjectTreeNodeStrategy, Map<String, String>> parentNodeInfo = strategy.getParentNodeData(oriDatas.get(0));

        String parentBmName = parentNodeInfo.fst.toString();
        IBusinessModel parentBm = this.businessModelService.getBusinessModelBySName(parentBmName, COLLAB_SCHEMA_ID, Table);
        ProjectTreeNode parentNode = new ProjectTreeNode();
        parentNode.setModelName(parentBmName);
        parentNode.setModelId(parentBm.getId());
        parentNode.setDataId(CommonTools.Obj2String(parentNodeInfo.snd.get("ID")));
        parentNode.setId(parentBmName + NODE_ID_SPLIT + parentNode.getDataId());
        parentNode.setText(CommonTools.Obj2String(parentNodeInfo.snd.get("NAME_" + parentBm.getId())));
        return parentNode;
    }

    public String getOriginalAssigneeId(String modelId, String dataId) {
        IBusinessModel bm = this.businessModelService.getBusinessModelById(modelId, EnumInter.BusinessModelEnum.Table);
        return CommonTools.Obj2String(this.orientSqlEngine.getBmService().createModelQuery(bm).findById(dataId).get("PRINCIPAL_" + modelId));
    }

    public List<Project> listPrjAsGrid(String dirId) {
        List<String> allDirs = getSonDirs(dirId, true);
        List<Project> prjs = orientSqlEngine.getTypeMappingBmService().get(Project.class, new CustomerFilter("parDirId", EnumInter.SqlOperation.In, CommonTools.list2String(allDirs)));
        //转化为显示值
        prjs.forEach(project -> {
            String principal = project.getPrincipal();
            if (!StringUtil.isEmpty(principal)) {
                project.setPrincipal(roleEngine.getRoleModel(false).getUserById(principal).getAllName());
            }
            project.setStatus(STATUS_MAPPING.get(project.getStatus()));
        });
        return prjs;
    }

    public List<String> getSonDirs(String curDirId, Boolean cascade) {

        List<String> retVal = new ArrayList<>();
        ProjectTreeNodeStrategy nodeStrategy = ProjectTreeNodeStrategy.fromString(CollabConstants.DIRECTORY);
        retVal.add(curDirId);
        List<ProjectTreeNode> dirSubNodes = nodeStrategy.getSubNodesOfType(CollabConstants.DIRECTORY, curDirId, CollabConstants.DIRECTORY);
        for (ProjectTreeNode dirSubNode : dirSubNodes) {
            retVal.add(dirSubNode.getDataId());
            getSonDirs(dirSubNode.getDataId(), cascade);
        }
        return retVal;
    }
}
