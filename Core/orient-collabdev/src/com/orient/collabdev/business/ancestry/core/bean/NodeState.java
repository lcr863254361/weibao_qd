package com.orient.collabdev.business.ancestry.core.bean;

/**
 * Created by karry on 18-8-30.
 */
public class NodeState {
    public static final String TS_OUTDATE = "已过时";
    public static final String TS_WAIT = "待更新";
    public static final String TS_NEW = "最新";

    public static final String AS_INVALID = "无效";
    public static final String AS_VALID = "有效";

    private String techState;
    private String approveState = "有效";

    public NodeState(String techState, String approveState) {
        this.techState = techState;
        this.approveState = approveState;
    }

    public String getTechState() {
        return techState;
    }

    public void setTechState(String techState) {
        this.techState = techState;
    }

    public String getApproveState() {
        return approveState;
    }

    public void setApproveState(String approveState) {
        this.approveState = approveState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeState)) return false;

        NodeState nodeState = (NodeState) o;

        if (techState != null ? !techState.equals(nodeState.techState) : nodeState.techState != null) return false;
        return !(approveState != null ? !approveState.equals(nodeState.approveState) : nodeState.approveState != null);

    }

    @Override
    public int hashCode() {
        int result = techState != null ? techState.hashCode() : 0;
        result = 31 * result + (approveState != null ? approveState.hashCode() : 0);
        return result;
    }
}
