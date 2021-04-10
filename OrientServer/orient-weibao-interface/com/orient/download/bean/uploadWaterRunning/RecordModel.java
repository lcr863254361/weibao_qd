package com.orient.download.bean.uploadWaterRunning;


import java.util.Date;

/**
 * Author WangJie
 * Created on 2018/10/15.
 */
public class RecordModel {
    private String id;
    // 深度
    private double depth;
    // 绝对时间
    private String date;
    // 主要记录的内容
    private String content;
    // 关联的用户的Id
    private String userId;
    //记录名称
    private String recordName;

    public RecordModel(String id, double depth, String date, String content, String userId) {
        this.id = id;
        this.depth = depth;
        this.date = date;
        this.content = content;
        this.userId = userId;
    }

    public RecordModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }
}
