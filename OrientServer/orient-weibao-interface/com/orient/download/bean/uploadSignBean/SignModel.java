package com.orient.download.bean.uploadSignBean;

/**
 * 签署的模型
 *
 * Author WangJie
 * Created on 2019/3/26.
 */
public class SignModel {
    private String id;
    private String userId;
    // "table" or "flow"
    private String type;
    // 签署的目标的Id
    private String targetId;
    // 结果为"success"或者"fail"
    private String result;
    // 驳回理由
    private String  reason;
    // 创建日期
    private String createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
