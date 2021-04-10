package com.orient.sysman.bean;

import com.orient.sysmodel.domain.sys.CwmSysToolsGroupEntity;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-10-29 14:39
 */
public class CwmSysToolsGroupEntityWrapper extends CwmSysToolsGroupEntity {

    private String iconCls = "icon-toolGroup";

    private Boolean leaf = true;

    public String getIconCls() {
        return iconCls;
    }

    public Boolean getLeaf() {
        return leaf;
    }
}
