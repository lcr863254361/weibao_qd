package com.orient.collabdev.business.version.aspect;

import com.orient.collabdev.business.processing.DevDataCopyBusiness;
import com.orient.collabdev.business.startup.PlanStartBusiness;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.business.version.ICollabVersionMng;
import com.orient.collabdev.constant.CollabDevConstants;
import com.orient.collabdev.constant.ManagerStatusEnum;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeWithRelation;
import com.orient.sysmodel.service.collabdev.ICollabNodeService;
import com.orient.sysmodel.service.collabdev.impl.CollabNodeWithRelationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-17 9:13 AM
 */
@Aspect
@Component
public class IncreaseNodeVersionAspect {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(IncreaseNodeVersionAspect.class);

    @Pointcut("execution(* com.orient.sysmodel.service.collabdev.impl.CollabNodeService.update(..)) ")
    public void updateNode() {

    }

    @Pointcut("execution(* com.orient.sysmodel.service.collabdev.impl.CollabNodeService.save(..)) ")
    public void saveNode() {

    }

    @Pointcut("execution(* com.orient.sysmodel.service.collabdev.impl.CollabNodeWithRelationService.update(..)) ")
    public void updateNodeWithRelation() {

    }

    @Around(value = "updateNode()")
    public void updateNode(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        CollabNode targetNode = (CollabNode) args[0];
        boolean versionChangeFlag = targetNode.getVersion().intValue() != targetNode.getLastVersion().intValue();
        if (versionChangeFlag) {

        }
        proceedingJoinPoint.proceed();
        if (versionChangeFlag) {
            //save dev status in plan
            collabVersionMng.saveDevStatuses(targetNode);
            //复制研发数据
            devDataCopyBusiness.copyDevData(targetNode.getId(), targetNode.getLastVersion(), targetNode.getVersion(), targetNode.getType());

        }
    }

    @Around(value = "saveNode()")
    public void saveNode(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        CollabNode targetNode = (CollabNode) args[0];
        proceedingJoinPoint.proceed();
        //如果项目进行中增加了一个计划，如果该计划的计划启动日期小于等于今天，则需要调用启动该计划的方法
        if (targetNode.getType().equals(CollabDevConstants.NODE_TYPE_PLAN)) {
            CollabDevNodeDTO projectNode = structureBusiness.getRootNode(targetNode.getId(), targetNode.getVersion());
            if (!projectNode.getStatus().equals(ManagerStatusEnum.UNSTART.toString())) {
                planStartBusiness.startPlansNotUnderApecct(projectNode.getId());
            }
        }
    }

    @Around(value = "updateNodeWithRelation()")
    public void updateNodeWithRelation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        CollabNodeWithRelation targetNode = (CollabNodeWithRelation) args[0];
        boolean versionChangeFlag = targetNode.getVersion().intValue() != targetNode.getLastVersion().intValue();
        if (versionChangeFlag) {

        }
        proceedingJoinPoint.proceed();
        if (versionChangeFlag) {

        }
    }

    @Autowired
    ICollabNodeService collabNodeService;

    @Autowired
    CollabNodeWithRelationService collabNodeWithRelationService;

    @Autowired
    ICollabVersionMng collabVersionMng;

    @Autowired
    DevDataCopyBusiness devDataCopyBusiness;

    @Autowired
    StructureBusiness structureBusiness;

    @Autowired
    PlanStartBusiness planStartBusiness;

}
