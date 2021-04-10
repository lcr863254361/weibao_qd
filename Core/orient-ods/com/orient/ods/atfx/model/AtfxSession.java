/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.ods.atfx.model;

import org.asam.ods.AoSession;

import java.util.Date;

/**
 * Created by mengbin on 16/3/18.
 * Purpose: 为了节约内存,共享各客户端访问文件,被连接池管理
 * Detail:  封装Atfx文件的访问的Session,并管理
 */
public class AtfxSession {

    /**
     * 暂时设置为20分钟,session自动关闭
     */
    public static long sessionTime= 1000*60*20;

    /**
     * 访问文件的Session
     */
    private AoSession aoSession;

    /**
     * 上次访问Session的时间
     */
    private Date  lastAccessTime;

    /**
     * AoEnvirment node
     */
    private AoEnvirmentNode rootODSNode;



    public AoEnvirmentNode getRootODSNode() {
        lastAccessTime = new Date();
        return rootODSNode;
    }

    public void setRootODSNode(AoEnvirmentNode rootODSNode) {
        lastAccessTime = new Date();
        this.rootODSNode = rootODSNode;
    }

    public AtfxSession(AoSession aoSession) {
        this.aoSession = aoSession;
        lastAccessTime = new Date();
    }

    public AoSession getAoSession() {
        lastAccessTime = new Date();
        return aoSession;
    }



    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }
}
