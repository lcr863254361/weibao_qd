package com.orient.download.bean.queryDivingTaskBean;

import com.orient.utils.UtilFactory;

import java.util.List;

/**
 * 潜次模型
 */
public class DiveNumModel {
    private String id;
    private String name="";
    private DiveDetailModel diveDetailModel;
    private List<TableSimpleModel> tableSimpleModels= UtilFactory.newArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableSimpleModel> getTableSimpleModels() {
        return tableSimpleModels;
    }

    public DiveDetailModel getDiveDetailModel() {
        return diveDetailModel;
    }

    public void setDiveDetailModel(DiveDetailModel diveDetailModel) {
        this.diveDetailModel = diveDetailModel;
    }

    public void setTableSimpleModels(List<TableSimpleModel> tableSimpleModels) {
        this.tableSimpleModels = tableSimpleModels;

    }
}
