package com.orient.sysmodel.domain.sys;
// default package

import java.util.Date;

import com.orient.sysmodel.domain.user.User;


/**
 * Message entity. @author MyEclipse Persistence Tools
 */
public class Message extends AbstractMessage implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public Message() {
    }

    
    /** full constructor */
    public Message(String read, User sendUser, Date sendDate, User fromUser, String title, String content, String remark) {
        super(read, sendUser, sendDate, fromUser, title, content, remark);        
    }
   
}
