package com.orient.web.base;

import java.io.Serializable;

/**
 * Created by enjoy on 2016/3/21 0021
 */
public class ExtComboboxData extends CommonResponseData implements Serializable {
    private String id;

    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ExtComboboxData(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public ExtComboboxData(){

    }

}
