package com.orient.download.bean.uploadCheckInstBean;

/**
 * 上传文件的返回结果
 *
 * Author WangJie
 * Created on 2019/3/13.
 */
public class FileResultModel {
    private String id;
    private String type;

    public FileResultModel(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
