package com.orient.web.base.OrientEventBus;

import com.orient.web.base.CommonResponseData;

/**
 * Created by Administrator on 2016/12/22.
 */
public class CommonResponseDataExt extends CommonResponseData {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
