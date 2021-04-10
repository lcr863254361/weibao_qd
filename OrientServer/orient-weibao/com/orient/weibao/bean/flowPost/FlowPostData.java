package com.orient.weibao.bean.flowPost;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FlowPostData implements Serializable {

    private List<Field> fields;
    private List<Column> columns;
    private List<Map> content;

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Map> getContent() {
        return content;
    }

    public void setContent(List<Map> content) {
        this.content = content;
    }
}
