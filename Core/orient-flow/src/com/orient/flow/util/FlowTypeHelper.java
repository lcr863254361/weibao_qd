package com.orient.flow.util;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.business.ProcessDefinitionBusiness;
import com.orient.flow.config.FlowType;
import com.orient.utils.UtilFactory;
import com.orient.workflow.WorkFlowConstants;
import com.orient.workflow.bean.DeployProperty;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * help to determine a pd's type
 *
 * @author Seraph
 *         2016-07-04 下午2:03
 */
@Component
public class FlowTypeHelper implements ApplicationListener<ContextRefreshedEvent> {

    private static final FlowTypeCache cache = new FlowTypeCache();

    static synchronized public void refresh() {
        ProcessDefinitionBusiness pdBusiness = (ProcessDefinitionBusiness) OrientContextLoaderListener
                .Appwac.getBean("processDefinitionBusiness");
        cache.auditFlowPdNames.clear();
        cache.auditFlowPdIds.clear();
        cache.mainAuditFlowPdIds.clear();
        cache.collabFlowPdIds.clear();
        List<ProcessDefinition> allPds = pdBusiness.getAllPrcessDefinitions();
        for (ProcessDefinition pd : allPds) {
            ProcessDefinitionImpl processDefinitionImpl = (ProcessDefinitionImpl) pd;
            List<DeployProperty> deployProperties = pdBusiness.getProcessDefinitionProperty(pd);
            //协同流程
            deployProperties.stream().filter(deployProperty -> deployProperty.getObjName().equals(WorkFlowConstants.USERPARA)).forEach(deployProperty -> {
                if (deployProperty.getKey().equals(WorkFlowConstants.AUDIT_FLOW)) {
                    cache.auditFlowPdNames.add(pd.getName());
                    cache.auditFlowPdIds.add(pd.getId());
                    String imgName = processDefinitionImpl.getImageResourceName();
                    if (imgName.split(WorkFlowConstants.PROCESS_MAIN_SUB_CONNECTER).length == 1) {
                        cache.mainAuditFlowPdIds.add(pd.getId());
                    }
                } else {
                    //协同流程
                    cache.collabFlowPdIds.add(pd.getId());
                }
            });
        }
    }

    static synchronized public FlowType getFlowType(String pdName) {
        boolean isAuditFlow = cache.auditFlowPdNames.contains(pdName);
        if (isAuditFlow) {
            return FlowType.Audit;
        } else {
            return FlowType.Collab;
        }
    }


    static synchronized public List<String> getAuditFlowPdIds() {
        return cache.auditFlowPdIds;
    }

    static synchronized public List<String> getMainAuditFlowPdIds() {
        return cache.mainAuditFlowPdIds;
    }

    static synchronized public List<String> getCollabFlowPdIds() {
        return cache.collabFlowPdIds;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            return;
        }

        FlowTypeHelper.refresh();
    }


    private static class FlowTypeCache {
        //全集 包括 子流程
        private Set<String> auditFlowPdNames = UtilFactory.newHashSet();
        //所有审批流程
        private List<String> auditFlowPdIds = new ArrayList<>();
        //子集 只包含 主流程
        private List<String> mainAuditFlowPdIds = new ArrayList<>();
        private List<String> collabFlowPdIds = new ArrayList<>();
    }
}
