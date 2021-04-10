package com.orient.collabdev.business.common.pedigree.event.listener;

import com.orient.collabdev.business.common.pedigree.event.GetPedigreeEvent;
import com.orient.collabdev.business.common.pedigree.event.GetPedigreeEventParam;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.model.PedigreeNodeRelation;
import com.orient.sysmodel.domain.collabdev.CollabNodeHisRelation;
import com.orient.sysmodel.domain.collabdev.CollabNodeRelation;
import com.orient.sysmodel.service.collabdev.ICollabNodeHisRelationService;
import com.orient.sysmodel.service.collabdev.ICollabNodeRelationService;
import com.orient.utils.BeanUtils;
import com.orient.utils.CommonTools;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取谱系节点间上下游关系
 *
 * @author panduanduan
 * @create 2018-08-04 2:37 PM
 */
@Component
public class GetPedigreeRelations extends OrientEventListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        if (!isOrientEvent(eventType)) {
            return false;
        }
        return eventType == GetPedigreeEvent.class || GetPedigreeEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        OrientEvent orientEvent = (OrientEvent) applicationEvent;
        GetPedigreeEventParam eventParam = (GetPedigreeEventParam) orientEvent.getParams();
        String planNodeId = eventParam.getNodeId();
        Integer planNodeVersion = eventParam.getNodeVersion();
        //get relation
        //在CollabNodeRelation表中，通过planNodeId获取到最大的计划版本
        Integer maxPlanVersion = getMaxVersionFromCollabNodeRelation(planNodeId);
        if (maxPlanVersion != null) {
            if (planNodeVersion >= maxPlanVersion) {
                //如果待查询的计划版本比最大计划版本maxPlanVersion大，则在CollabNodeRelation表中通过planNodeId查询
                List<CollabNodeRelation> collabNodeRelationList = collabNodeRelationService.list(Restrictions.eq("pid", planNodeId));
                eventParam.getPedigreeDTO().setRelations(converToDTO(collabNodeRelationList));
            } else {
                //否则通过planNodeId和待查询planNodeVersion在CollabNodeHisRelation表中找到比planNodeVersion小但是最接近的版本号
                Integer secondMaxVersion = getSecondMaxVersionFromCollabNodeHisRelation(planNodeId, planNodeVersion);
                if (secondMaxVersion != null) {
                    List<CollabNodeHisRelation> hisRelations = collabNodeHisRelationService.list(Restrictions.eq("pid", planNodeId)
                            , Restrictions.eq("pversion", secondMaxVersion));
                    eventParam.getPedigreeDTO().setRelations(converToDTO(hisRelations));
                } else {
                    eventParam.getPedigreeDTO().setRelations(new ArrayList<>());
                }
            }
        }
    }

    private Integer getSecondMaxVersionFromCollabNodeHisRelation(String planNodeId, Integer toComparePlanVersion) {
        List<CollabNodeHisRelation> collabNodeHisRelations = collabNodeHisRelationService.list(new Criterion[]{Restrictions.eq("pid", planNodeId), Restrictions.le("pversion", toComparePlanVersion)}, Order.asc("pversion"));
        if (!CommonTools.isEmptyList(collabNodeHisRelations)) {
            return collabNodeHisRelations.get(0).getPversion();
        }
        return null;
        // String hql = "select max(pversion) from CollabNodeHisRelation where pversion<= " + toCompareVersion + " and pid='" + planNodeId + "'";
        //return (Integer) collabNodeHisRelationService.queryHQL(hql);
    }

    private Integer getMaxVersionFromCollabNodeRelation(String planNodeId) {
        //任务之间没有连线前，通过planNodeId查询到的结果为空，需要做空指针判断
        List<CollabNodeRelation> collabNodeRelations = collabNodeRelationService.list(Restrictions.eq("pid", planNodeId), Order.asc("pversion"));
        if (!CommonTools.isEmptyList(collabNodeRelations)) {
            return collabNodeRelations.get(0).getPversion();
        }
        return null;
    }

    private <T> List<PedigreeNodeRelation> converToDTO(List<T> source) {
        List<PedigreeNodeRelation> retVal = new ArrayList<>();
        source.forEach(sb -> {
            PedigreeNodeRelation pedigreeNodeRelation = new PedigreeNodeRelation();
            BeanUtils.copyProperties(pedigreeNodeRelation, sb);
            retVal.add(pedigreeNodeRelation);
        });
        return retVal;
    }

    @Autowired
    StructureBusiness structureBusiness;

    @Autowired
    ICollabNodeRelationService collabNodeRelationService;

    @Autowired
    ICollabNodeHisRelationService collabNodeHisRelationService;
}
