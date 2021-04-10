package com.orient.template.model;

import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.utils.UtilFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * a model used in template preview
 *
 * @author Seraph
 *         2016-09-27 上午11:08
 */
public class CollabTemplatePreviewNode {

    public static final String PREVIEW_TYPE_TREE = "tree";
    public static final String PREVIEW_TYPE_COMP = "comp";
    public static final String PREVIEW_TYPE_CUSTOM = "custom";

    public CollabTemplatePreviewNode(CollabTemplateNode collabTemplateNode, String previewType){
        this.id = String.valueOf(collabTemplateNode.getId());
        this.data = collabTemplateNode.getData();
        this.previewType = previewType;
    }

    private String id;
    private String text;
    private Object data;
    private String previewType;
    private Map<String, Object> extraInfo = UtilFactory.newHashMap();
    private List<CompInfo> compInfos = UtilFactory.newArrayList();
    private String iconCls;

    public String getId() {
        return id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addExtraInfo(String key, Object info){
        this.extraInfo.put(key, info);
    }

    public String getPreviewType() {
        return previewType;
    }

    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    public List<CompInfo> getCompInfos() {
        return compInfos;
    }

    public void addCompInfos(CompInfo compInfo) {
        this.compInfos.add(compInfo);
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    static public class CompInfo {

        public CompInfo(long nodeId, String type, boolean relationComp){
            this.type = type;
            this.nodeId = nodeId;
            this.relationComp = relationComp;
        }

        private String type;
        private long nodeId;
        private boolean relationComp;

        public long getNodeId() {
            return nodeId;
        }

        public boolean isRelationComp() {
            return relationComp;
        }

        public String getType() {
            return type;
        }
    }
}
