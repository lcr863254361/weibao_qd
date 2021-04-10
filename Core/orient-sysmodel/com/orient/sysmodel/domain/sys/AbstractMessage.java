package com.orient.sysmodel.domain.sys;
// default package

import java.util.Date;

import com.orient.sysmodel.domain.user.User;


/**
 * AbstractMessage entity provides the base persistence definition of the Message entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractMessage extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String read;//信息是否读过, 0表示已读，1表示未读
     //private String sendUserId;//收件人
     private User sendUser;
     private Date sendDate;//发件时间
     //private String fromUserId;//发件人
     private User fromUser;
     private String title;//信息标题
     private String content;//信息内容
     private String remark;//备注


    // Constructors

    /** default constructor */
    public AbstractMessage() {
    }

    
    /** full constructor */
    public AbstractMessage(String read, User sendUser, Date sendDate, User fromUser, String title, String content, String remark) {
        this.read = read;
        this.sendUser = sendUser;
        this.sendDate = sendDate;
        this.fromUser = fromUser;
        this.title = title;
        this.content = content;
        this.remark = remark;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getRead() {
        return this.read;
    }
    
    public void setRead(String read) {
        this.read = read;
    }

    public Date getSendDate() {
        return this.sendDate;
    }
    
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    

    public User getSendUser() {
		return sendUser;
	}


	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}


	public User getFromUser() {
		return fromUser;
	}


	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}


	public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   








}