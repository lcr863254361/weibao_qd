package com.orient.pvm.bean.sync;

/**
 * Created by mengbin on 16/8/1.
 * Purpose:
 * Detail:
 */
public class Column {

    String headName , headCode, type, defaultType,isRequired,isBindPhoto;//defaultType为默认格式描述 0:任意格式 1:字符串 2:勾选 3:字符串加勾选
                                                            //isRequired为是否必填 必填 非必填
                                                            //isBindPhoto是否绑定照片 绑定 不绑定

    public String getIsBindPhoto() {
        return isBindPhoto;
    }

    public void setIsBindPhoto(String isBindPhoto) {
        this.isBindPhoto = isBindPhoto;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getHeadCode() {
        return headCode;
    }

    public void setHeadCode(String headCode) {
        this.headCode = headCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultType() {return defaultType;}

    public void setDefaultType(String defaultType) {this.defaultType = defaultType;}
}
