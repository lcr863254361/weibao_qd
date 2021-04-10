package com.orient.history.core.binddata.model;

import com.orient.history.core.binddata.handler.IBindDataHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class BindOpinionData extends TaskBindData {

    public BindOpinionData() {
        super(IBindDataHandler.BIND_TYPE_OPINIONDATA);
    }

    private Map<String, String> opinions = new HashMap<>();

    public Map<String, String> getOpinions() {
        return opinions;
    }

    public void setOpinions(Map<String, String> opinions) {
        this.opinions = opinions;
    }
}
