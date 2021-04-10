package com.orient.modeldata.eventListener;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.modeldata.bean.TBomDynamicNode;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.bean.TBomStaticNode;
import com.orient.modeldata.event.GetTbomNodesEvent;
import com.orient.modeldata.eventParam.GetTbomNodesEventParam;
import com.orient.modeldata.tbomHandle.annotation.NodePermission;
import com.orient.modeldata.tbomHandle.permission.CustomTbomPermission;
import com.orient.sysmodel.domain.tbom.DynamicTbomRole;
import com.orient.sysmodel.domain.tbom.TbomRole;
import com.orient.sysmodel.operationinterface.IRole;
import com.orient.sysmodel.roleengine.impl.RoleUtilImpl;
import com.orient.sysmodel.service.tbom.TbomService;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import com.orient.web.base.OrientEventBus.OrientEvent;
import com.orient.web.base.OrientEventBus.OrientEventListener;
import com.orient.web.model.BaseNode;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qjs on 2017/2/11.
 * TBom节点图标监听器
 */
@Component
public class PermissionFilterListener extends OrientEventListener {

    @Autowired
    TbomService tbomService;

    @Autowired
    protected RoleUtilImpl roleEngine;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return isOrientEvent(eventType) && (eventType == GetTbomNodesEvent.class || GetTbomNodesEvent.class.isAssignableFrom(eventType));
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (super.isAbord(event)) {
            return;
        }
        OrientEvent orientEvent = (OrientEvent) event;
        //获取事件参数
        GetTbomNodesEventParam param = (GetTbomNodesEventParam) orientEvent.getParams();
        //get AllNodes
        List<BaseNode> result = param.getTbomNodes();
        //collect dynamic and static node id
        List<String> dynamicNodeId = new ArrayList<>();
        List<String> staticNodeId = new ArrayList<>();
        result.forEach(baseNode -> {
            TBomNode tBomNode = (TBomNode) baseNode;
            if (tBomNode.getNodeType().equals(TBomNode.DYNAMIC_NODE)) {
                TBomDynamicNode tBomDynamicNode = (TBomDynamicNode) tBomNode;
                dynamicNodeId.add(tBomDynamicNode.getDynamicId());
            } else {
                staticNodeId.add(tBomNode.getStaticDbId());
            }
        });
        List<IRole> roles = roleEngine.getRoleModel(false).getRolesOfUser(UserContextUtil.getUserId());
        List<String> currentRoleIds = roles.stream().map(IRole::getId).collect(Collectors.toList());
        List<BaseNode> toRemoveNodes = new ArrayList<>();
        //filter by role
        dynamicNodeId.forEach(dnId -> {
            List<DynamicTbomRole> dynamicTbomRoles = tbomService.getDynamicTbomRoleDAO().findByProperty("nodeId", dnId);
            if (!CommonTools.isEmptyList(dynamicNodeId)) {
                List<String> permissionedRoleIds = new ArrayList<>();
                dynamicTbomRoles.forEach(dynamicTbomRole -> {
                    String roleIds = CommonTools.null2String(dynamicTbomRole.getRoleId());
                    if (!StringUtil.isEmpty(roleIds)) {
                        permissionedRoleIds.addAll(CommonTools.arrayToList(roleIds.split(",")));
                    }
                });
                if (!CommonTools.isEmptyList(permissionedRoleIds) && !CommonTools.hasSameItem(currentRoleIds, permissionedRoleIds)) {
                    toRemoveNodes.addAll(result.stream().filter(baseNode -> baseNode instanceof TBomDynamicNode && ((TBomDynamicNode) baseNode).getDynamicId().equals(dnId)).collect(Collectors.toList()));
                }
            }
        });
        staticNodeId.forEach(snId -> {
            List<TbomRole> staticTbomRoles = tbomService.getTbomRoleDAO().findByProperty("nodeId", snId);
            if (!CommonTools.isEmptyList(staticNodeId)) {
                List<String> permissionedRoleIds = new ArrayList<>();
                staticTbomRoles.forEach(staticTbomRole -> {
                    String roleIds = CommonTools.null2String(staticTbomRole.getRoleId());
                    if (!StringUtil.isEmpty(roleIds)) {
                        permissionedRoleIds.addAll(CommonTools.arrayToList(roleIds.split(",")));
                    }
                });
                if (!CommonTools.isEmptyList(permissionedRoleIds) && !CommonTools.hasSameItem(currentRoleIds, permissionedRoleIds)) {
                    toRemoveNodes.addAll(result.stream().filter(baseNode -> baseNode instanceof TBomStaticNode && ((TBomStaticNode) baseNode).getStaticDbId().equals(snId)).collect(Collectors.toList()));
                }
            }
        });
        result.removeAll(toRemoveNodes);
        //filter by custom
        if (!CommonTools.isEmptyList(result)) {
            List<BaseNode> customRemoveItems = new ArrayList<>();
            //get current tbom name
            String currentTbomId = ((TBomNode) result.get(0)).getTbomId();
            String currentTbomName = roleEngine.getRoleModel(false).getTbomById(currentTbomId).getName();
            //get the right custom permission filter of tbom
            String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(CustomTbomPermission.class);
            for (String beanName : beanNames) {
                CustomTbomPermission customTbomPermission = (CustomTbomPermission) OrientContextLoaderListener.Appwac.getBean(beanName);
                Class validatorClass = customTbomPermission.getClass();
                NodePermission nodePermission = (NodePermission) validatorClass.getAnnotation(NodePermission.class);
                String tbomName = nodePermission.tbomName();
                if (tbomName.equals(currentTbomName)) {
                    List<BaseNode> tmpItems = customTbomPermission.doRemove(result);
                    if (!CommonTools.isEmptyList(tmpItems)) {
                        customRemoveItems.addAll(tmpItems);
                    }
                }
            }
            if (!CommonTools.isEmptyList(customRemoveItems)) {
                result.removeAll(customRemoveItems);
            }
        }
    }

}
