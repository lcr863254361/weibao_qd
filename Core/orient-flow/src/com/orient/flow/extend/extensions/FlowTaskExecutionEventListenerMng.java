package com.orient.flow.extend.extensions;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.flow.config.FlowType;
import com.orient.flow.extend.ExtensionManager;
import com.orient.flow.extend.annotation.FlowTaskExecutionEventMarker;
import com.orient.flow.extend.process.Extension;
import com.orient.flow.util.FlowTypeHelper;
import com.orient.sysmodel.domain.flow.TaskDataRelation;
import com.orient.sysmodel.service.flow.IFlowDataRelationService;
import com.orient.sysmodel.service.flow.ITaskDataRelationService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.utils.UtilFactory;
import com.orient.workflow.WorkFlowConstants;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.orient.flow.config.FlowConfig.FLOW_TASK_STRATEGY_DELIMITER;

/**
 * Flow Task Execution Event Listener Manager
 *
 * @author Seraph
 *         2016-07-01 上午9:05
 */
@Component
public class FlowTaskExecutionEventListenerMng {

    public List<FlowTaskExecutionEventListener> getListenersForTask(String piId, String pdName, String flowTaskName) {

        List<Extension> allExtensions = extensionManager.getExtensions(FlowTaskExecutionEventListener.class);
        List<Extension> appliedExtensions = new ArrayList<>();
        for (Extension extension : allExtensions) {
            FlowTaskExecutionEventMarker marker = (FlowTaskExecutionEventMarker) extension.getCustomAnnotation();
            if (marker == null) {
                continue;
            }

            if (marker.flowTypes().length > 0) {
                FlowType curFlowType = FlowTypeHelper.getFlowType(pdName);
                boolean flowTypeApplied = false;
                for (FlowType flowType : marker.flowTypes()) {
                    if (curFlowType == flowType) {
                        flowTypeApplied = true;
                        break;
                    }
                }

                if (!flowTypeApplied) {
                    continue;
                }
            }

            extension.setOrder(marker.order());
            //flow名称 转化为真实值
            String flow = getRealFlowName(marker);
            if ("".equals(flow) || flow.equals(pdName)) {
                if (marker.tasks().length == 0) {
                    boolean exceptTask = false;
                    for (String task : marker.exceptTasks()) {
                        if (task.equals(flowTaskName)) {
                            exceptTask = true;
                            continue;
                        }
                    }

                    if (!exceptTask) {
                        appliedExtensions.add(extension);
                    }
                    continue;
                }

                for (String task : marker.tasks()) {
                    if (task.equals(flowTaskName)) {
                        appliedExtensions.add(extension);
                    }
                }
            }
        }

        List<String> userConfigedListenerBeanNames = UtilFactory.newArrayList();
        List<TaskDataRelation> taskDataRelations = this.taskDataRelationService.list(Restrictions.eq("piId", piId), Restrictions.eq("taskName", flowTaskName));
        for (TaskDataRelation taskDataRelation : taskDataRelations) {
            if (!CommonTools.isNullString(taskDataRelation.getStrategy())) {
                userConfigedListenerBeanNames.addAll(Arrays.asList(taskDataRelation.getStrategy().split(FLOW_TASK_STRATEGY_DELIMITER)));
            }
        }

        Collections.sort(appliedExtensions);
        List<String> listenerBeanNames = UtilFactory.newArrayList();
        for (Extension extension : appliedExtensions) {
            String beanName = extension.getBeanName();
            if (userConfigedListenerBeanNames.contains(beanName)) {
                continue;
            }
            listenerBeanNames.add(beanName);
        }
        listenerBeanNames.addAll(userConfigedListenerBeanNames);

        List<FlowTaskExecutionEventListener> listeners = UtilFactory.newArrayList();
        for (String beanName : listenerBeanNames) {
            FlowTaskExecutionEventListener listenerBean = (FlowTaskExecutionEventListener) OrientContextLoaderListener.Appwac.getBean(beanName);
            listeners.add(listenerBean);
        }
        return listeners;
    }

    private String getRealFlowName(FlowTaskExecutionEventMarker marker) {
        String flow = marker.flow();
        if (!StringUtil.isEmpty(flow) && flow.indexOf("$$") != -1) {
            String[] flowDesc = flow.split("\\u0024\\u0024");
            if (flowDesc.length == 2) {
                try {
                    Class configClass = Class.forName(flowDesc[0]);
                    flow = CommonTools.Obj2String(configClass.getDeclaredField(flowDesc[1]).get(configClass));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        //去除版本信息
        if(flow.indexOf(WorkFlowConstants.PROCESS_NAME_VERSION_CONNECTER) != -1){
            flow = flow.substring(0,flow.indexOf(WorkFlowConstants.PROCESS_NAME_VERSION_CONNECTER));
        }
        return flow;
    }

    static public FlowTaskExecutionEventListenerMng getInstance() {
        return MngHolder.mng;
    }

    private static class MngHolder {
        private static final FlowTaskExecutionEventListenerMng mng = compute();

        private static FlowTaskExecutionEventListenerMng compute() {
            return (FlowTaskExecutionEventListenerMng) OrientContextLoaderListener.Appwac
                    .getBean("flowTaskExecutionEventListenerMng");
        }
    }

    @Autowired
    private IFlowDataRelationService flowDataRelationService;

    @Autowired
    private ITaskDataRelationService taskDataRelationService;

    @Autowired
    private ExtensionManager extensionManager;
}
