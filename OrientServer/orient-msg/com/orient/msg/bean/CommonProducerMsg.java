package com.orient.msg.bean;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-15 15:12
 */
//@MessageProducerBinding(endPoint = "CommonExchange")
public class CommonProducerMsg {
    private String type;
    private String title;
    private String content;
    private Long timestamp;
    private Long userId;
    private String src;
    private String dest;
    private Boolean readed;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public Boolean getReaded() {
        return readed;
    }

    public void setReaded(Boolean readed) {
        this.readed = readed;
    }
}
