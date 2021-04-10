package com.orient.alarm.model;

import java.io.Serializable;

public class AlarmUserRelation implements Serializable {

    public static final String TO_SEND_USER = "0";

    public static final String TO_COPY_USER = "1";

    private String type;

    public AlarmUserRelation() {
    }

    public AlarmUserRelation(String type) {
        this.type = type;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
