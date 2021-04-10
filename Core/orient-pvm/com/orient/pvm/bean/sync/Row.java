package com.orient.pvm.bean.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbin on 16/8/1.
 * Purpose:
 * Detail:
 */
public class Row {
    String dataId;
    List<Check> checks = new ArrayList<>();

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks = checks;
    }
}
