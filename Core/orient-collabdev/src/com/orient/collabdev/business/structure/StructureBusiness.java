package com.orient.collabdev.business.structure;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.collabdev.business.common.annotation.VersionStatus;
import com.orient.collabdev.business.structure.query.IStructureQuery;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.constant.VersionStatusEnum;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.collabdev.util.DTOConverTool;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.sysmodel.service.collabdev.ICollabNodeWithRelationService;
import com.orient.web.base.BaseBusiness;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.Table;
import static com.orient.config.ConfigInfo.COLLAB_SCHEMA_ID;

/**
 * 最新结构处理层
 *
 * @author panduanduan
 * @create 2018-07-27 9:13 AM
 */
@Service
public class StructureBusiness extends BaseBusiness {

    /**
     * @return get root dirs
     */
    protected List<CollabDevNodeDTO> getRootDirs() {
        List<CollabDevNodeDTO> retVal = new ArrayList<>();
        IBusinessModel dirBm = businessModelService.getBusinessModelBySName(CollabDevConstants.DIRECTORY, COLLAB_SCHEMA_ID, Table);
        String dirBmiId = dirBm.getId();
        List<Map<String, String>> oriDatas = orientSqlEngine.getBmService().createModelQuery(dirBm).list();
        if (oriDatas.size() == 0) {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("ID", "-1");
            dataMap.put("NAME_" + dirBm.getId(), "默认分类");
            orientSqlEngine.getBmService().insertModelData(dirBm, dataMap);
            oriDatas.add(dataMap);
        }
        oriDatas.forEach(dataMap -> retVal.add(DTOConverTool.converDirToNodeDTO(dataMap, dirBmiId)));
        return retVal;
    }

    /**
     * get structure from any nodeId and any version
     *
     * @param nodeId
     * @param nodeVersion
     * @return current structure or history structure
     */
    public List<CollabDevNodeDTO> getSonNodes(String nodeId, Integer nodeVersion) {
        IStructureQuery structureQuery = getStructureQuery(nodeId, nodeVersion);
        return structureQuery.getSonNodes(nodeId, nodeVersion);
    }

    /**
     * get parent node from any nodeId and any version
     *
     * @param nodeId
     * @param nodeVersion
     * @return
     */
    public CollabDevNodeDTO getParentNode(String nodeId, Integer nodeVersion) {
        IStructureQuery structureQuery = getStructureQuery(nodeId, nodeVersion);
        return structureQuery.getParentNode(nodeId, nodeVersion);
    }

    /**
     * get root node (project node desc) from any nodeId and any version
     *
     * @param nodeId
     * @param nodeVersion
     * @return
     */
    public CollabDevNodeDTO getRootNode(String nodeId, Integer nodeVersion) {
        IStructureQuery structureQuery = getStructureQuery(nodeId, nodeVersion);
        return structureQuery.getRootNode(nodeId, nodeVersion);
    }

    /**
     * get node
     *
     * @param nodeId
     * @param nodeVersion
     * @return
     */
    public CollabDevNodeDTO getNode(String nodeId, Integer nodeVersion) {
        IStructureQuery structureQuery = getStructureQuery(nodeId, nodeVersion);
        return structureQuery.getNode(nodeId, nodeVersion);
    }

    /**
     * get all parent node (parent node not include dir node) from any nodeId and any version
     *
     * @param nodeId
     * @param nodeVersion
     * @return
     */
    public List<CollabDevNodeDTO> getAllParentNode(String nodeId, Integer nodeVersion) {
        IStructureQuery structureQuery = getStructureQuery(nodeId, nodeVersion);
        return structureQuery.getAllParentNode(nodeId, nodeVersion);
    }

    /**
     * @param nodeId
     * @param nodeVersion
     * @return
     */
    private IStructureQuery getStructureQuery(String nodeId, Integer nodeVersion) {
        IStructureQuery retVal = null;
        boolean isHistory = isHistoryNode(nodeId, nodeVersion);
        VersionStatusEnum versionStatusEnum = isHistory ? VersionStatusEnum.HISTORY : VersionStatusEnum.LATEST;
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(IStructureQuery.class);
        for (String beanName : beanNames) {
            IStructureQuery structureQuery = OrientContextLoaderListener.Appwac.getBean(beanName, IStructureQuery.class);
            Class operateClass = structureQuery.getClass();
            VersionStatus classAnnotation = (VersionStatus) operateClass.getAnnotation(VersionStatus.class);
            VersionStatusEnum annotationStatus = classAnnotation.status();
            if (annotationStatus == versionStatusEnum) {
                retVal = structureQuery;
            }
        }
        return retVal;
    }


    /**
     * judge if the special node is in history table
     *
     * @param nodeId
     * @param nodeVersion
     * @return
     */
    protected boolean isHistoryNode(String nodeId, Integer nodeVersion) {
        Criterion idCriterion = Restrictions.eq("id", nodeId);
        if (null != nodeVersion) {
            return collabNodeService.count(idCriterion, Restrictions.eq("version", nodeVersion)) == 0;
        } else
            return collabNodeService.count(idCriterion) == 0;
    }

    @Autowired
    public ICollabNodeService collabNodeService;

    @Autowired
    public ICollabNodeWithRelationService collabNodeWithRelationService;
}
