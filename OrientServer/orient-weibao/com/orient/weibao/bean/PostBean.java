package com.orient.weibao.bean;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.weibao.constants.PropertyConstant;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-04-11 16:54
 */
public class PostBean {

    private String postName;
    private String postType;

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }
}
