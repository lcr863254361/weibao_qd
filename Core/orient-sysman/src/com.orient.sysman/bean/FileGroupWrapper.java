package com.orient.sysman.bean;

import com.orient.sysmodel.domain.file.CwmFileGroupEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件分组包装类
 * 增加树形节点相关属性
 *
 * @author enjoy
 * @creare 2016-04-30 11:22
 */
public class FileGroupWrapper extends CwmFileGroupEntity implements Serializable {

    private Boolean leaf = true;

    private String parentId;

    private Boolean expanded = true;

    private String iconCls = "icon-fileGroup";


    public FileGroupWrapper() {
    }

    public FileGroupWrapper(CwmFileGroupEntity cwmFileGroupEntity) {
        this.setId(cwmFileGroupEntity.getId());
        this.setGroupName(cwmFileGroupEntity.getGroupName());
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    //由于通用Ajax返回值中数据key为results 故 替换children至 results
    private List<FuncBean> results = new ArrayList<>();

    public List<FuncBean> getResults() {
        return results;
    }

    public void setResults(List<FuncBean> results) {
        this.results = results;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public String getIconCls() {
        return iconCls;
    }
}
