package com.orient.collabdev.business.common.pedigree;

import com.orient.collabdev.business.common.annotation.MngStatus;
import com.orient.collabdev.business.common.pedigree.event.GetPedigreeEvent;
import com.orient.collabdev.business.common.pedigree.event.GetPedigreeEventParam;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.business.version.ICollabVersionMng;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.collabdev.model.PedigreeDTO;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeHisRelation;
import com.orient.sysmodel.domain.collabdev.CollabNodeRelation;
import com.orient.sysmodel.service.collabdev.ICollabNodeRelationService;
import com.orient.sysmodel.service.collabdev.impl.CollabNodeHisRelationService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.base.BaseBusiness;
import org.hibernate.criterion.Restrictions;
import org.jboss.util.graph.Edge;
import org.jboss.util.graph.Graph;
import org.jboss.util.graph.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 11:30 AM
 */
@Component
public class PedigreeBusiness extends BaseBusiness {

    public PedigreeDTO getPedigree(String nodeId, Integer nodeVersion, Boolean queryStatus, String queryType) {
        GetPedigreeEventParam getPedigreeEventParam = new GetPedigreeEventParam(nodeId, nodeVersion, queryStatus, queryType);
        GetPedigreeEvent getGridModelDescEvent = new GetPedigreeEvent(this, getPedigreeEventParam);
        OrientContextLoaderListener.Appwac.publishEvent(getGridModelDescEvent);
        return getPedigreeEventParam.getPedigreeDTO();
    }

    /**
     * @param relations
     */
    public void saveRelations(List<CollabNodeRelation> relations, String nodeId, Integer nodeVersion) {
        //check has cycle
        if (hasCycle(relations)) {
            throw new OrientBaseAjaxException("", "谱系中存在循环依赖，请删除循环依赖并再次保存");
        } else {
            //save to DB
            IPedigreeMng pedigreeMng = getPedigreeMng(nodeId, nodeVersion);
            if (null == pedigreeMng) {
                throw new OrientBaseAjaxException("", "项目状态异常，请联系管理员");
            } else {
                //only latest node version can modify relations
                pedigreeMng.saveRelations(relations, nodeId, nodeVersion);
            }
        }
    }

    /**
     * @param nodeId
     * @param nodeVersion
     * @return
     */
    private IPedigreeMng getPedigreeMng(String nodeId, Integer nodeVersion) {
        IPedigreeMng retVal = null;
        CollabDevNodeDTO rootNode = structureBusiness.getRootNode(nodeId, nodeVersion);
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(IPedigreeMng.class);
        for (String beanName : beanNames) {
            IPedigreeMng pedigreeMng = OrientContextLoaderListener.Appwac.getBean(beanName, IPedigreeMng.class);
            Class operateClass = pedigreeMng.getClass();
            MngStatus classAnnotation = (MngStatus) operateClass.getAnnotation(MngStatus.class);
            ManagerStatusEnum annotationStatus = classAnnotation.status();
            if (annotationStatus == ManagerStatusEnum.fromString(rootNode.getStatus())) {
                retVal = pedigreeMng;
            }
        }
        return retVal;
    }

    public List<CollabNodeRelation> saveToHistory(String planNodeId, String rootId, Integer nextVersion) {
        //通过planNodeId找到CollabNodeRelation表中的记录，保存到CollabNodeHisRelation表中
        List<CollabNodeRelation> collabNodeRelations = collabNodeRelationService.list(Restrictions.eq("pid", planNodeId));
        if (!CommonTools.isEmptyList(collabNodeRelations)) {
            collabNodeRelations.forEach(collabNodeRelation -> {
                CollabNodeHisRelation collabNodeHisRelation = new CollabNodeHisRelation();
                BeanUtils.copyProperties(collabNodeHisRelation, collabNodeRelation);
                collabNodeHisRelationService.save(collabNodeHisRelation);
                collabNodeRelation.setRid(rootId);
                collabNodeRelation.setRversion(nextVersion);
                collabNodeRelationService.update(collabNodeRelation);
            });
        }
        return collabNodeRelations;
       /* List<CollabNodeRelation> collabNodeRelations = collabNodeRelationService.list(Restrictions.eq("rid", rootId), Restrictions.eq("rversion", rootVersion));
        if (collabNodeHisRelationService.count(Restrictions.eq("rid", rootId), Restrictions.eq("rversion", rootVersion)) == 0) {
            collabNodeRelations.forEach(collabNodeRelation -> {
                CollabNodeHisRelation collabNodeHisRelation = new CollabNodeHisRelation();
                BeanUtils.copyProperties(collabNodeHisRelation, collabNodeRelation);
                collabNodeHisRelationService.save(collabNodeHisRelation);
                collabNodeRelation.setRid(rootId);
                collabNodeRelation.setRversion(nextVersion);
                collabNodeRelationService.update(collabNodeRelation);
            });
        }
        return collabNodeRelations;*/
    }

    public void removeNodes(List<CollabNode> taskNode, Boolean savenToHistory) {
        if (!CommonTools.isEmptyList(taskNode)) {
            CollabDevNodeDTO rootNode = structureBusiness.getRootNode(taskNode.get(0).getId(), null);
            CollabDevNodeDTO parentNode = structureBusiness.getParentNode(taskNode.get(0).getId(), null);
            List<CollabNodeRelation> relations;
            if (savenToHistory) {
                //判断历史表中是否已经存在当前版本谱系关系
                Integer nextVersion = collabVersionMng.getNextVersion(rootNode.getId());
                relations = saveToHistory(parentNode.getId(), rootNode.getId(), nextVersion);

            } else {
                relations = collabNodeRelationService.list(Restrictions.eq("rid", rootNode.getId()), Restrictions.eq("rversion", rootNode.getVersion()));
            }
            //过滤出受影响的边
            List<String> taskNodeIds = taskNode.stream().map(CollabNode::getId).collect(Collectors.toList());
            List<CollabNodeRelation> existRelations = relations.stream().filter(collabNodeRelation -> taskNodeIds.contains(collabNodeRelation.getSrcDevNodeId())
                    || taskNodeIds.contains(collabNodeRelation.getDestDevNodeId())).collect(Collectors.toList());
            existRelations.forEach(existRelation -> collabNodeRelationService.delete(existRelation));
        }
    }


    /**
     * 判断是否存在循环依赖
     *
     * @param relations
     * @return
     */
    private boolean hasCycle(List<CollabNodeRelation> relations) {
        Graph graphEx = new Graph<>();
        Set<String> vertexs = new HashSet<>();
        Map<String, Vertex> idToVertexMap = new HashMap<>();
        relations.forEach(relation -> {
            vertexs.add(relation.getSrcDevNodeId());
            vertexs.add(relation.getDestDevNodeId());
        });
        vertexs.forEach(nodeId -> {
            Vertex<String> vertex = new Vertex<>(nodeId, nodeId);
            graphEx.addVertex(vertex);
            idToVertexMap.put(nodeId, vertex);
        });
        relations.forEach(relation -> {
            graphEx.addEdge(idToVertexMap.get(relation.getSrcDevNodeId()), idToVertexMap.get(relation.getDestDevNodeId()), 0);
        });
        Edge<String>[] cycle = graphEx.findCycles();
        if (cycle.length > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Autowired
    StructureBusiness structureBusiness;

    @Autowired
    ICollabNodeRelationService collabNodeRelationService;

    @Autowired
    CollabNodeHisRelationService collabNodeHisRelationService;

    @Autowired
    ICollabVersionMng collabVersionMng;
}
