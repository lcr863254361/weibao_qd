/**
 * Right.java
 * com.orient.sysmodel.domain.role
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-4-12 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.domain.role;


import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:Right
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @Date 2012-4-12		上午11:16:34
 * @see
 * @since Ver 1.1
 */
public class AbstractRight extends com.orient.sysmodel.domain.BaseBean {


    String filter = "";//过滤条件

    String userFilter = "";//用户表过滤

    private List<String> listColIds = new ArrayList<>();

    private List<String> addColIds = new ArrayList<>();

    private List<String> modifyColIds = new ArrayList<>();

    private List<String> detailColIds = new ArrayList<>();

    private List<String> exportColIds = new ArrayList<>();

    private List<Long> btnTypeIds = new ArrayList<>();

    private Boolean isModelRightSet = false;

    public Boolean getModelRightSet() {
        return isModelRightSet;
    }

    public void setModelRightSet(Boolean modelRightSet) {
        isModelRightSet = modelRightSet;
    }

    public List<String> getListColIds() {
        return listColIds;
    }

    public void setListColIds(List<String> listColIds) {
        this.listColIds = listColIds;
    }

    public List<String> getAddColIds() {
        return addColIds;
    }

    public void setAddColIds(List<String> addColIds) {
        this.addColIds = addColIds;
    }

    public List<String> getModifyColIds() {
        return modifyColIds;
    }

    public void setModifyColIds(List<String> modifyColIds) {
        this.modifyColIds = modifyColIds;
    }

    public List<String> getDetailColIds() {
        return detailColIds;
    }

    public void setDetailColIds(List<String> detailColIds) {
        this.detailColIds = detailColIds;
    }

    public List<String> getExportColIds() {
        return exportColIds;
    }

    public void setExportColIds(List<String> exportColIds) {
        this.exportColIds = exportColIds;
    }

    public List<Long> getBtnTypeIds() {
        return btnTypeIds;
    }

    public void setBtnTypeIds(List<Long> btnTypeIds) {
        this.btnTypeIds = btnTypeIds;
    }

    public AbstractRight() {
    }

    /**
     * filter
     *
     * @return the filter
     * @since CodingExample Ver 1.0
     */

    public String getFilter() {
        return filter;
    }

    /**
     * filter
     *
     * @param filter the filter to set
     * @since CodingExample Ver 1.0
     */

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getUserFilter() {
        return userFilter;
    }

    public void setUserFilter(String userFilter) {
        this.userFilter = userFilter;
    }
}

