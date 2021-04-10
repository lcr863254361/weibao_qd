package com.orient.sysmodel.domain.etl;

import java.sql.Clob;

// default package



/**
 * EtlTranslator entity. @author MyEclipse Persistence Tools
 */
public class EtlTranslator extends AbstractEtlTranslator implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public EtlTranslator() {
    }

    
    /** full constructor */
    public EtlTranslator(String tablename, String tableid, EtlScript script, String tablecolumn, String tablesysname, Clob translator) {
        super(tablename, tableid, script, tablecolumn, tablesysname, translator);        
    }
   
}
