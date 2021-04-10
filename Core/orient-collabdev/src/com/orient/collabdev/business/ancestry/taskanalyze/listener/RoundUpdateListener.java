package com.orient.collabdev.business.ancestry.taskanalyze.listener;

import com.google.common.collect.Table;
import com.orient.collabdev.business.ancestry.core.bean.INode;
import com.orient.collabdev.business.ancestry.core.bean.NodeState;
import com.orient.collabdev.business.ancestry.core.handler.StateAnalyzeHandler;
import com.orient.collabdev.business.ancestry.core.listener.AnalyzeEndListener;
import com.orient.collabdev.business.ancestry.taskanalyze.AncestryModelBusiness;
import com.orient.sysmodel.domain.collabdev.CollabNode;
import com.orient.sysmodel.domain.collabdev.CollabNodeDevStatus;
import com.orient.sysmodel.domain.collabdev.CollabRound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/5.
 */
@Component
public class RoundUpdateListener extends AnalyzeEndListener<String, NodeState> {
    @Autowired
    AncestryModelBusiness ancestryModelBusiness;

    @Override
    public void onAnalyzeEnd(String type, String planId, Map<String, INode<String, NodeState>> nodeMap, Map<String, NodeState> oriStateMap, Map<String, NodeState> destStateMap, Table<String, String, String> relations) {
        boolean allNewAndValid = true;
        for(NodeState nodeState : destStateMap.values()) {
            if(!new NodeState(NodeState.TS_NEW, NodeState.AS_VALID).equals(nodeState)) {
                allNewAndValid = false;
                break;
            }
        }
        if(allNewAndValid && StateAnalyzeHandler.TYPE_BASIC.equals(type)) {
            updateRound(planId);
        }
    }

    @Override
    public Integer getOrder() {
        return 1;
    }

    public void updateRound(String planId) {
        CollabNode plan = ancestryModelBusiness.getNodeById(planId);
        CollabRound latestRound = ancestryModelBusiness.getLatestRound(planId);
        Long startVersion = 1L;
        if(latestRound != null) {
            startVersion = latestRound.getEndVersion()+1;
        }
        Long minVersion = null;
        List<CollabNodeDevStatus> devStatuses = ancestryModelBusiness.getAllDevStateByPlan(planId, startVersion, null);
        for(CollabNodeDevStatus devStatus : devStatuses) {
            if(minVersion == null) {
                minVersion = devStatus.getPversion();
            }
            else {
                if(minVersion > devStatus.getPversion()) {
                    minVersion = devStatus.getPversion();
                }
            }
        }
        startVersion = minVersion;
        ancestryModelBusiness.saveRound(planId, startVersion, Long.valueOf(plan.getVersion()));
    }
}
