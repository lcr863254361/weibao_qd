package com.orient.collab.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * CalendarEvent
 *
 * @author Seraph
 *         2016-08-29 下午3:26
 */
public class CalendarEvent {

    public static final String TYPE_PLAN = "1";
    public static final String TYPE_COLLAB = "2";
    public static final String TYPE_AUDIT = "3";
    public static final String TYPE_DATA = "4";

    /**
     * id,建议以类型和db中id拼接,以免重复
     */
    private String id;

    /**
     * 类型id,取值见上方常量
     */
    private String tid;

    /**
     * 显示名称
     */
    private String title;

    /**
     * 开始时间
     */
    private Date start;

    /**
     * 结束时间
     */
    private Date end;

    /**
     * 备注
     */
    private String notes;

    /**
     * 关联模型英文名
     */
    private String mn;

    /**
     * 关联数据ID
     */
    private String di;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getEnd() {
        return null == end ? start : end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMn() {
        return mn;
    }

    public void setMn(String mn) {
        this.mn = mn;
    }

    public String getDi() {
        return di;
    }

    public void setDi(String di) {
        this.di = di;
    }

}
