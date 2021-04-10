package com.orient.history.core.binddata.model;

import com.orient.history.core.binddata.handler.IBindDataHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class BindExtraData extends TaskBindData {

    public BindExtraData(String taskBindType) {
        super(IBindDataHandler.BIND_TYPE_EXTRADATA);
    }

    //自定义参数
    private Map<String, String> extraMap = new HashMap<>();

    public Map<String, String> getExtraMap() {
        return extraMap;
    }

    public void setExtraMap(Map<String, String> extraMap) {
        this.extraMap = extraMap;
    }
}
