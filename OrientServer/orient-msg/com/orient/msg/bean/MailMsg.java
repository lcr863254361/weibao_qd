package com.orient.msg.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
public class MailMsg implements Serializable {
    //收件人
    private String to;
    //抄送人
    private String[] copys;
    //主题
    private String subject;
    //正文
    private String text;
    //附件
    private String filePath;


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String[] getCopys() {
        return copys;
    }

    public void setCopys(String[] copys) {
        this.copys = copys;
    }
}
