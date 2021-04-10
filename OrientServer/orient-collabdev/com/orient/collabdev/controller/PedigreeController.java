package com.orient.collabdev.controller;

import com.orient.collabdev.business.ancestry.taskanalyze.context.AdjustNodeAnalyzeContext;
import com.orient.collabdev.business.ancestry.taskanalyze.context.CopyNodeAnalyzeContext;
import com.orient.collabdev.business.common.pedigree.PedigreeBusiness;
import com.orient.collabdev.business.structure.StructureBusiness;
import com.orient.collabdev.model.CollabDevNodeDTO;
import com.orient.collabdev.model.PedigreeDTO;
import com.orient.collabdev.model.PedigreeRelationVO;
import com.orient.collabdev.model.PedigreeVO;
import com.orient.sysmodel.domain.collabdev.CollabNodeRelation;
import com.orient.utils.exception.OrientBaseAjaxException;
import com.orient.web.base.AjaxResponseData;
import com.orient.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2018-08-04 10:53 AM
 */
@Controller
@RequestMapping("/pedigree")
public class PedigreeController extends BaseController {

    @Autowired
    PedigreeBusiness pedigreeBusiness;

    @Autowired
    StructureBusiness structureBusiness;

    @RequestMapping("/mxClient")
    @ResponseBody
    public AjaxResponseData<PedigreeVO> getPedigree(String nodeId, Integer nodeVersion, Boolean queryStatus,
                                                    String queryType) {
        AjaxResponseData<PedigreeVO> retVal = new AjaxResponseData<>();
        if (null == nodeVersion) {
            CollabDevNodeDTO node = structureBusiness.getNode(nodeId, nodeVersion);
            if (null == node) {
                throw new OrientBaseAjaxException("", "节点已被删除");
            }
            nodeVersion = node.getVersion();
        }
        PedigreeDTO pedigreeDTO = pedigreeBusiness.getPedigree(nodeId, nodeVersion, queryStatus, queryType);
        PedigreeVO pedigreeVO = PedigreeVO.converDTOToVO(pedigreeDTO);
        retVal.setResults(pedigreeVO);
        return retVal;
    }

    @RequestMapping("/relations")
    @ResponseBody
    @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public AjaxResponseData<Boolean> savePedigree(@RequestBody List<PedigreeRelationVO> relations, String nodeId) {
        AjaxResponseData<Boolean> retVal = new AjaxResponseData<>();
        //get latest node version
        CollabDevNodeDTO node = structureBusiness.getNode(nodeId, null);
        if (null == node) {
            throw new OrientBaseAjaxException("", "节点已被删除");
        }
        Integer latestNodeVersion = node.getVersion();
        //transfer to DVO
        List<CollabNodeRelation> collabNodeRelations = new ArrayList<>();
        relations.forEach(relationVO -> {
            CollabNodeRelation collabNodeRelation = new CollabNodeRelation();
            collabNodeRelation.setSrcDevNodeId(relationVO.getSrcId());
            collabNodeRelation.setDestDevNodeId(relationVO.getDestId());
            collabNodeRelations.add(collabNodeRelation);
        });
        pedigreeBusiness.saveRelations(collabNodeRelations, nodeId, latestNodeVersion);

        //sync dev status in plan
        CopyNodeAnalyzeContext copyContext = new CopyNodeAnalyzeContext();
        copyContext.copyTaskDevStatesInPlan(nodeId);
        AdjustNodeAnalyzeContext adjustContext = new AdjustNodeAnalyzeContext();
        adjustContext.adjustStateAnalyze(nodeId);

        retVal.setResults(true);
        retVal.setMsg("保存成功");
        return retVal;
    }
}
