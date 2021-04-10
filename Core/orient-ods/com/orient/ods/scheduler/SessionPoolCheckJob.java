/*
 * Copyright (c) 2016. Orient Company
 *
 */

package com.orient.ods.scheduler;

import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.ods.atfx.business.AtfxFileMangrBusiness;
import com.orient.ods.atfx.model.AtfxSession;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.Date;

/**
 * Created by mengbin on 16/3/23.
 * Purpose:
 * Detail:
 */
public class SessionPoolCheckJob implements Job {

    private AtfxFileMangrBusiness atfxFileMangrBusiness;



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.print("SessionPoolCheckJob executed");

        for ( String filePath : AtfxFileMangrBusiness.aoSessionPool.keySet()){
            AtfxSession session = AtfxFileMangrBusiness.aoSessionPool.get(filePath);
            Date lastVist =session.getLastAccessTime();
            if(lastVist.getTime()+AtfxSession.sessionTime<new Date().getTime()){
                AtfxFileMangrBusiness.closeFile(filePath);
            }
          

        }

    }
}
