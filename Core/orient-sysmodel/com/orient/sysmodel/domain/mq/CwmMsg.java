package com.orient.sysmodel.domain.mq;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-15 14:43
 */
@Entity
@Table(name = "CWM_MSG")
public class CwmMsg {
    public static final String TYPE_COLLAB_FEEDBACK = "feedback";

    private Long id;
    private String title;
    private String content;
    private String data;
    private Long timestamp;
    private Long userId;
    private String type;
    private String src;
    private String dest;
    private Boolean readed;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_CWM_MSG")})
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "DATA")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Basic
    @Column(name = "TIMESTAMP")
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "SRC")
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Basic
    @Column(name = "DEST")
    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    @Basic
    @Column(name = "READED")
    public Boolean getReaded() {
        return readed;
    }

    public void setReaded(Boolean readed) {
        this.readed = readed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CwmMsg cwmMsg = (CwmMsg) o;

        if (id != null ? !id.equals(cwmMsg.id) : cwmMsg.id != null) return false;
        if (title != null ? !title.equals(cwmMsg.title) : cwmMsg.title != null) return false;
        if (content != null ? !content.equals(cwmMsg.content) : cwmMsg.content != null) return false;
        if (timestamp != null ? !timestamp.equals(cwmMsg.timestamp) : cwmMsg.timestamp != null) return false;
        if (userId != null ? !userId.equals(cwmMsg.userId) : cwmMsg.userId != null) return false;
        if (type != null ? !type.equals(cwmMsg.type) : cwmMsg.type != null) return false;
        if (src != null ? !src.equals(cwmMsg.src) : cwmMsg.src != null) return false;
        if (dest != null ? !dest.equals(cwmMsg.dest) : cwmMsg.dest != null) return false;
        if (readed != null ? !readed.equals(cwmMsg.readed) : cwmMsg.readed != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (src != null ? src.hashCode() : 0);
        result = 31 * result + (dest != null ? dest.hashCode() : 0);
        result = 31 * result + (readed != null ? readed.hashCode() : 0);
        return result;
    }
}
