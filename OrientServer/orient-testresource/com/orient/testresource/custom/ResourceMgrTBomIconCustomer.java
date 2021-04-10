package com.orient.testresource.custom;

import com.orient.modeldata.bean.TBomNode;
import com.orient.modeldata.tbomHandle.annotation.NodeIcon;
import com.orient.modeldata.tbomHandle.icon.TbomIcon;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-10 10:05
 */
@NodeIcon(tbomName = "试验资源管理", order = 0)
@Component
public class ResourceMgrTBomIconCustomer implements TbomIcon {
    @Override
    public void setIconCls(List<TBomNode> nodes) {
        nodes.forEach(node -> {
            String nodeName = node.getText();
            String iconCls = "";
            if ("试验资源管理".equals(nodeName)) {
                iconCls = "icon-resources";
            } else if (nodeName.contains("设备台账管理")) {
                iconCls = "icon-resources-equiment";
            } else if ("设备计量管理".equals(nodeName)) {
                iconCls = "icon-resources-jljd";
            } else if ("试验件管理".equals(nodeName)) {
                iconCls = "icon-resources-test";
            } else if ("试验样品管理".equals(nodeName)) {
                iconCls = "icon-resources-samples";
            } else if ("试验人员管理".equals(nodeName)) {
                iconCls = "icon-resources-users";
            } else if ("试验团队管理".equals(nodeName)) {
                iconCls = "icon-resources-team";
            } else if ("试验环境管理".equals(nodeName)) {
                iconCls = "icon-resources-environment";
            } else if ("试验系统管理".equals(nodeName)) {
                iconCls = "icon-resources-system";
            } else if ("一般设备".equals(nodeName)) {
                iconCls = "icon-resources-normal";
            } else if ("特种设备".equals(nodeName)) {
                iconCls = "icon-resources-special";
            } else if ("计量设备".equals(nodeName)) {
                iconCls = "icon-resources-jlsb";
            }
            node.setIconCls(iconCls);
        });
    }
}
