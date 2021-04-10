package com.orient.sysmodel.domain.report;
// default package

import java.util.Date;

import com.orient.metamodel.metadomain.Schema;


/**
 * Reports entity. @author MyEclipse Persistence Tools
 */
public class Reports extends AbstractReports implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public Reports() {
    }

    
    /** full constructor */
    public Reports(Schema schema, String tableId, String viewsId, String columnId, String content, String filepath, String pid, Boolean type, Boolean orders, String createUser, Date createTime, String name, String filterjson, String dataEntry) {
        super(schema, tableId, viewsId, columnId, content, filepath, pid, type, orders, createUser, createTime, name, filterjson, dataEntry);        
    }
   
}
