package com.orient.sysman.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/21.
 */
public class CheckFuncBean extends FuncBean {

    private List<CheckFuncBean> children = new ArrayList<>();

    private Boolean checked = false;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<CheckFuncBean> getChildren() {
        return children;
    }

    public void setChildren(List<CheckFuncBean> children) {
        this.children = children;
        children.forEach(checkFuncBean -> {
            super.getResults().add(checkFuncBean);
        });
    }
}
