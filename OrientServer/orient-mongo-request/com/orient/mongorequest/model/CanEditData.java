package com.orient.mongorequest.model;

import java.io.Serializable;

public class CanEditData implements Serializable {

    private String versionNumber;
    private String responseCode;

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

}
