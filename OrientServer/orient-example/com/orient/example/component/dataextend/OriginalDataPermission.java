package com.orient.example.component.dataextend;

import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.tbomHandle.annotation.NodePermission;
import com.orient.modeldata.tbomHandle.permission.CustomTbomPermission;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.utils.CommonTools;
import com.orient.web.model.BaseNode;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tbom节点权限定制示例
 *
 * @author Administrator
 * @create 2017-03-09 13:37
 */

@NodePermission(tbomName = "试验数据")
public class OriginalDataPermission implements CustomTbomPermission {

    @Autowired
    IRoleUtil roleEngine;

    @Autowired
    public IBusinessModelService businessModelService;

    @Override
    public List<BaseNode> doRemove(List<BaseNode> nodes) {
        //返回值
        List<BaseNode> retVal = new ArrayList<>();
        if (!CommonTools.isEmptyList(nodes)) {
            int level = ((TBomNode) nodes.get(0)).getLevel();
            if (4 == level) {
                retVal.addAll(filterFourthLevel(nodes));
            }
        }
        return retVal;
    }

    /**
     * @param nodes
     * @return 第四层节点权限定制
     */
    private Collection<? extends BaseNode> filterFourthLevel(List<BaseNode> nodes) {
        List<BaseNode> retVal = new ArrayList<>();
        String userId = UserContextUtil.getUserId();
        //获取当前登录用户的权限
        List<com.orient.sysmodel.operationinterface.IRole> roles = roleEngine.getRoleModel(false).getRolesOfUser(userId);
        List<String> roleNames = roles.stream().map(com.orient.sysmodel.operationinterface.IRole::getName).collect(Collectors.toList());
//        IBusinessModel testTaskModel = businessModelService.getBusinessModelBySName("T_SYRW", GtbsmanConfigInfo.SCHEMAID, EnumInter.BusinessModelEnum.Table);
//        nodes.forEach(tBomNode -> {
////            if (tBomNode.getText().equals("未导出") && roleNames.contains("测量系统技术负责人")) {
////                retVal.add(tBomNode);
////            } else if (tBomNode.getText().equals("涉密导入")) {
////                TBomStaticNode tBomStaticNode = (TBomStaticNode) tBomNode;
////                CustomerFilter customerFilter = new CustomerFilter("C_SYDH_" + testTaskModel.getId(), EnumInter.SqlOperation.Equal, "111");
////                tBomStaticNode.gettBomModels().get(0).getDefaultFilter().setChild(customerFilter);
////            }
//        });
        return retVal;
    }
}
