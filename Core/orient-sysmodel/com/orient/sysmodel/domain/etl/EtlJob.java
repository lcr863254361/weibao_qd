package com.orient.sysmodel.domain.etl;
// default package

import java.sql.Clob;
import java.util.Date;


/**
 * EtlJob entity. @author MyEclipse Persistence Tools
 */
public class EtlJob extends AbstractEtlJob implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public EtlJob() {
    }

    
    /** full constructor */
    public EtlJob(String userName, Date jobTime, String status, Clob ddl, Clob dml, Clob loadsql) {
        super(userName, jobTime, status, ddl, dml, loadsql);        
    }
   
}
