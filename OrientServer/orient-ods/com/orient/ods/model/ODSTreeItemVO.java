package com.orient.ods.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-06-30 10:20
 */
public class ODSTreeItemVO implements Serializable {

    private String id;

    private String text;

    private String thumb;

    private Boolean leaf;

    private List<ODSTreeItemVO> children = new ArrayList<>();

    public ODSTreeItemVO(String id, String text, String thumb, Boolean leaf) {
        this.id = id;
        this.text = text;
        this.thumb = thumb;
        this.leaf = leaf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public List<ODSTreeItemVO> getChildren() {
        return children;
    }

    public void setChildren(List<ODSTreeItemVO> children) {
        this.children = children;
    }
}
