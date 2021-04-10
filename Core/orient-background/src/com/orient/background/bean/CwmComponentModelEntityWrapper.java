package com.orient.background.bean;

import com.orient.sysmodel.domain.component.CwmComponentModelEntity;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class CwmComponentModelEntityWrapper extends CwmComponentModelEntity {

    private String componentExtJsClass;

    public String getComponentExtJsClass() {
        return componentExtJsClass;
    }

    public void setComponentExtJsClass(String componentExtJsClass) {
        this.componentExtJsClass = componentExtJsClass;
    }
}
