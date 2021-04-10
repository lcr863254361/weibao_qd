package com.orient.history.core.binddata.model;

import com.orient.history.core.binddata.handler.IBindDataHandler;

/**
 * Created by Administrator on 2016/9/28 0028.
 * 如果任务中绑定了freemarker 模板
 */
public class BindFreemarkerData extends TaskBindData {

    public BindFreemarkerData() {
        super(IBindDataHandler.BIND_TYPE_FREEMARKER);
    }

    //数据 + 展现
    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
