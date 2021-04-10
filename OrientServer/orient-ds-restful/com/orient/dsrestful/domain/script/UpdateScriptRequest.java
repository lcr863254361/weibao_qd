package com.orient.dsrestful.domain.script;

/**
 * Created by GNY on 2018/3/27
 */
public class UpdateScriptRequest extends AddScriptRequest {

    private long id;
    private boolean enable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
