package com.orient.download.bean.sparePartsBean;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-22 15:41
 */
public class TroubleDeviceBean {
    // 可能不需要
    // 这个ID是需要的 绑定语音和照片用
    private String id;
    // 绑定的设备ID,设备ID/单元格Id
    private String refId;
    // 内容描述
    private String desc;
    // 日期
    private String date;
    //用来区分上传的是故障记录还是入所检验记录
    //"1"  故障记录；"2"入所检验； "3"单元格故障
    private String type;
    //是否包含需要下载的录音文件
    private boolean containAudio=false;
    //是否包含需要下载的照片
    private boolean containPhoto=false;

    public TroubleDeviceBean(String id, String refId, String desc, String date) {
        this.id = id;
        this.refId = refId;
        this.desc = desc;
        this.date = date;
    }

    public TroubleDeviceBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isContainAudio() {
        return containAudio;
    }

    public void setContainAudio(boolean containAudio) {
        this.containAudio = containAudio;
    }

    public boolean isContainPhoto() {
        return containPhoto;
    }

    public void setContainPhoto(boolean containPhoto) {
        this.containPhoto = containPhoto;
    }
}
