package com.orient.background.doctemplate.bean;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-07 15:44
 */
public class HyperLink implements Serializable {

    private String hyperLinkName;

    private String hyperLinkUrl;

    public String getHyperLinkName() {
        return hyperLinkName;
    }

    public void setHyperLinkName(String hyperLinkName) {
        this.hyperLinkName = hyperLinkName;
    }

    public String getHyperLinkUrl() {
        return hyperLinkUrl;
    }

    public void setHyperLinkUrl(String hyperLinkUrl) {
        this.hyperLinkUrl = hyperLinkUrl;
    }
}
