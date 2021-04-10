package com.orient.sysman.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/21.
 */
public class FuncBean implements Serializable {
    private String id;
    private String code;
    private String text;
    private String name;
    private String pid;
    private String url;
    private String js;
    private Boolean hasChildrens = false;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String icon;
    private Long position;

    public String getTbomFlg() {
        return tbomFlg;
    }

    public void setTbomFlg(String tbomFlg) {
        this.tbomFlg = tbomFlg;
    }

    private String tbomFlg;
    private String notes;

    public String getAddFlg() {
        return addFlg;
    }

    public void setAddFlg(String addFlg) {
        this.addFlg = addFlg;
    }

    private String addFlg;//是否可以添加, 1．可以添加 0．不可以添加

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    private String delFlg;//是否可以删除, 1.可以删除0.不可以删除

    public String getEditFlg() {
        return editFlg;
    }

    public void setEditFlg(String editFlg) {
        this.editFlg = editFlg;
    }

    private String editFlg;//是否可以编辑, 1.可以编辑0.不可以编辑

    private String iconCls;
    private Boolean expanded = true;
    private Boolean leaf = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean getHasChildrens() {
        return hasChildrens;
    }

    public void setHasChildrens(Boolean hasChildrens) {
        this.hasChildrens = hasChildrens;
    }

    //由于通用Ajax返回值中数据key为results 故 替换children至 results
    private List<FuncBean> results = new ArrayList<>();

    public List<FuncBean> getResults() {
        return results;
    }

    public void setResults(List<FuncBean> results) {
        this.results = results;
    }
}
