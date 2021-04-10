package com.orient.webservice.collab.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-09-26 16:30
 */
public class TaskInfo {
    private List<CollabTask> tasks = new ArrayList<>();

    public List<CollabTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<CollabTask> tasks) {
        this.tasks = tasks;
    }
}
