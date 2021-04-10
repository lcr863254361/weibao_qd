package com.orient.download.bean.sparePartsBean;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-02-22 14:57
 */
public class ReplaceDeviceBean {

    // 可能不需要
    // 这个ID是需要的 绑定语音和照片用 更换人就是上传人
    private String id;
    // 旧的的设备Id
    private String oldDeviceId;
    // 新的设备的Id
    private String newDeviceId;
    // 内容描述
    private String desc;
    // 日期
    private String date;

    public ReplaceDeviceBean(String id, String oldDeviceId, String newDeviceId, String desc, String date) {
        this.id = id;
        this.oldDeviceId = oldDeviceId;
        this.newDeviceId = newDeviceId;
        this.desc = desc;
        this.date = date;
    }

    public ReplaceDeviceBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldDeviceId() {
        return oldDeviceId;
    }

    public void setOldDeviceId(String oldDeviceId) {
        this.oldDeviceId = oldDeviceId;
    }

    public String getNewDeviceId() {
        return newDeviceId;
    }

    public void setNewDeviceId(String newDeviceId) {
        this.newDeviceId = newDeviceId;
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

}
