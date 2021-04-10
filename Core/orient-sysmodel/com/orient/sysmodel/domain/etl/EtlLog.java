package com.orient.sysmodel.domain.etl;
// default package

import java.sql.Clob;
import java.util.Date;



/**
 * EtlLogId entity. @author MyEclipse Persistence Tools
 */
public class EtlLog extends AbstractEtlLog implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public EtlLog() {
    }

    
    /** full constructor */
    public EtlLog(String id, String logInfo, String userId, Date startTime, String tableId, String status, String dataAmount, String fileName, String tableDisname, Date endTime, String rightData, String wrongData, Date time1, Date time2, Long isDelete, EtlJob job, String jobResult, String tableName, String fileSize, Clob logddl) {
        super(id, logInfo, userId, startTime, tableId, status, dataAmount, fileName, tableDisname, endTime, rightData, wrongData, time1, time2, isDelete, job, jobResult, tableName, fileSize, logddl);        
    }
   
}
