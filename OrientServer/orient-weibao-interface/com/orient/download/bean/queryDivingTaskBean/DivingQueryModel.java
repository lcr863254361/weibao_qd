package com.orient.download.bean.queryDivingTaskBean;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * 潜次模型
 */
public class DivingQueryModel {

    private List<DiveNumModel> diveNumModels= UtilFactory.newArrayList();

    public List<DiveNumModel> getDiveNumModels() {
        return diveNumModels;
    }

    public void setDiveNumModels(List<DiveNumModel> diveNumModels) {
        this.diveNumModels = diveNumModels;
    }
}
