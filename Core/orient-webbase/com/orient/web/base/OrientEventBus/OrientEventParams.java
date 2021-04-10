package com.orient.web.base.OrientEventBus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mengbin on 16/3/24.
 * Purpose: OrientEvent中传入的参数基类
 * Detail:
 */
public class OrientEventParams implements Serializable {

    /**
     * Event是否还要执行的标志
     */
    protected boolean bAboard = false;

    /**
     * 多个事件监听器之间的数据传输
     */
    protected Map<String, Object> flowParams = new HashMap<>();

    public boolean isbAboard() {
        return bAboard;
    }

    public void setbAboard(boolean bAboard) {
        this.bAboard = bAboard;
    }

    public void setFlowParams(String key, Object obj) {
        flowParams.put(key, obj);
    }

    public <T> T getFlowParams(String key,Class<T> cls) {
        return (T) flowParams.get(key);
    }

    public Object getFlowParams(String key) {
        return flowParams.get(key);
    }

    public String errorMsg = "";

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
