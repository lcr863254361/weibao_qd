package com.orient.sysmodel.domain.mq;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * represent a failed msg send via mq
 *
 * @author Seraph
 *         2016-12-01 下午2:05
 */
@Entity
@Table(name = "CWM_FAILED_MSG")
public class FailedMsg {

    public FailedMsg(){

    }

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, precision = -127)
    @GeneratedValue(generator = "sequence")
    @GenericGenerator(name = "sequence", strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_FAILED_MSG")})
    private Long id;

    @Basic
    @Column(name = "ERROR_CODE")
    private String errorCode;

    @Basic
    @Column(name = "ERROR_TEXT")
    private String errorText;

    @Basic
    @Column(name = "SEND_TIME")
    private Timestamp sendTime;

    @Lob
    @Column(name = "MESSAGE_BODY")
    private byte[] messageBody;

    @Basic
    @Column(name = "CORRELATION")
    private String correlation;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }


    public String getCorrelation() {
        return correlation;
    }

    public void setCorrelation(String correlation) {
        this.correlation = correlation;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }
}
