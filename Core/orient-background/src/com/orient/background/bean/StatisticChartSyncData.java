package com.orient.background.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2017-04-08 10:18 AM
 */
public class StatisticChartSyncData implements Serializable {

    private List<StatisticChartSyncBean> update = new ArrayList<>();
    private List<StatisticChartSyncBean> create = new ArrayList<>();
    private List<StatisticChartSyncBean> remove = new ArrayList<>();

    public List<StatisticChartSyncBean> getUpdate() {
        return update;
    }

    public void setUpdate(List<StatisticChartSyncBean> update) {
        this.update = update;
    }

    public List<StatisticChartSyncBean> getCreate() {
        return create;
    }

    public void setCreate(List<StatisticChartSyncBean> create) {
        this.create = create;
    }

    public List<StatisticChartSyncBean> getRemove() {
        return remove;
    }

    public void setRemove(List<StatisticChartSyncBean> remove) {
        this.remove = remove;
    }
}
