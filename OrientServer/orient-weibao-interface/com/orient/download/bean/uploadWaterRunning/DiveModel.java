package com.orient.download.bean.uploadWaterRunning;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Author WangJie
 * Created on 2018/10/15.
 */
public class DiveModel {
    private String id;
    private String date;
    //任务名称
    private String title;
    private String persons;
    private List<RecordModel> records;

    public DiveModel(String id, String date, String title, String persons, List<RecordModel> records) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.persons = persons;
        this.records = records;
    }

    public DiveModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<RecordModel> getRecords() {
        return records;
    }

    public void setRecords(List<RecordModel> records) {
        this.records = records;
    }

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

}
