package com.orient.flow.extend.extensions;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.config.FlowType;
import com.orient.flow.extend.ExtensionManager;
import com.orient.flow.extend.annotation.FlowDecisionEventMarker;
import com.orient.flow.extend.process.Extension;
import com.orient.flow.util.FlowTypeHelper;
import com.orient.utils.UtilFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Flow Task Execution Event Listener Manager
 *
 * @author Seraph
 *         2016-07-01 上午9:05
 */
@Component
public class FlowDecisionEventListenerMng {

    public List<FlowDecisionEventListener> getListenersForDecision(String piId, String pdName, String decisionName) {

        List<Extension> allExtensions = extensionManager.getExtensions(FlowDecisionEventListener.class);
        List<Extension> appliedExtensions = new ArrayList<>();
        allExtensions.forEach(extension -> {
            FlowDecisionEventMarker marker = (FlowDecisionEventMarker) extension.getCustomAnnotation();
            if (marker != null) {
                if (marker.flowTypes().length > 0) {
                    FlowType curFlowType = FlowTypeHelper.getFlowType(pdName);
                    boolean flowTypeApplied = false;
                    //校验流程类型是否符合
                    for (FlowType flowType : marker.flowTypes()) {
                        if (curFlowType == flowType) {
                            flowTypeApplied = true;
                            break;
                        }
                    }
                    if (flowTypeApplied) {
                        extension.setOrder(marker.order());
                        //排除判断节点信息
                        if ("".equals(marker.flow()) || marker.flow().equals(pdName)) {
                            if (marker.decisions().length == 0) {
                                boolean exceptDecisions = false;
                                for (String task : marker.exceptDecisions()) {
                                    if (task.equals(decisionName)) {
                                        exceptDecisions = true;
                                        continue;
                                    }
                                }
                                if (!exceptDecisions) {
                                    appliedExtensions.add(extension);
                                }
                            }
                            //名称匹配
                            for (String task : marker.decisions()) {
                                if (task.equals(decisionName)) {
                                    appliedExtensions.add(extension);
                                }
                            }
                        }
                    }
                }
            }
        });
        Collections.sort(appliedExtensions);
        List<FlowDecisionEventListener> listeners = UtilFactory.newArrayList();
        appliedExtensions.forEach(extension -> {
            FlowDecisionEventListener listenerBean = (FlowDecisionEventListener) OrientContextLoaderListener.Appwac.getBean(extension.getBeanName());
            listeners.add(listenerBean);
        });
        return listeners;
    }

    static public FlowDecisionEventListenerMng getInstance() {
        return MngHolder.mng;
    }

    private static class MngHolder {
        private static final FlowDecisionEventListenerMng mng = compute();

        private static FlowDecisionEventListenerMng compute() {
            return (FlowDecisionEventListenerMng) OrientContextLoaderListener.Appwac
                    .getBean("flowDecisionEventListenerMng");
        }
    }

    @Autowired
    private ExtensionManager extensionManager;
}
