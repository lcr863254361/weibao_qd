package com.orient.sysmodel.domain.report;

// default package



/**
 * ReportItemsId entity. @author MyEclipse Persistence Tools
 */
public class ReportItems extends AbstractReportItems implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public ReportItems() {
    }

    
    /** full constructor */
    public ReportItems(String id, Reports report, String tableId, String columnName, String relations, String type) {
        super(id, report, tableId, columnName, relations, type);        
    }
   
}
