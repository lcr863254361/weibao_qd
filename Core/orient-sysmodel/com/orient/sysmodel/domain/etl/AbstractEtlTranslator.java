package com.orient.sysmodel.domain.etl;

import java.sql.Clob;

// default package



/**
 * AbstractEtlTranslator entity provides the base persistence definition of the EtlTranslator entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractEtlTranslator extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String tablename;//导入数据表的显示名称
     private String tableid;//导入数据类的ID
     //private String scriptid;//数据导入脚本ID
     private EtlScript script;
     private String tablecolumn;//导入表字段名称
     private String tablesysname;//导入数据表的真实名称(S_TABLE_NAME)
     private Clob translator;//导入数据转换和映射脚本
     private String translatorStr;//导入数据转换和映射脚本


    // Constructors

    /** default constructor */
    public AbstractEtlTranslator() {
    }

    
    /** full constructor */
    public AbstractEtlTranslator(String tablename, String tableid, EtlScript script, String tablecolumn, String tablesysname, Clob translator) {
        this.tablename = tablename;
        this.tableid = tableid;
        this.script = script;
        this.tablecolumn = tablecolumn;
        this.tablesysname = tablesysname;
        this.translator = translator;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getTablename() {
        return this.tablename;
    }
    
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTableid() {
        return this.tableid;
    }
    
    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    

    public EtlScript getScript() {
		return script;
	}


	public void setScript(EtlScript script) {
		this.script = script;
	}


	public String getTablecolumn() {
        return this.tablecolumn;
    }
    
    public void setTablecolumn(String tablecolumn) {
        this.tablecolumn = tablecolumn;
    }

    public String getTablesysname() {
        return this.tablesysname;
    }
    
    public void setTablesysname(String tablesysname) {
        this.tablesysname = tablesysname;
    }

    public Clob getTranslator() {
        return this.translator;
    }
    
    public void setTranslator(Clob translator) {
        this.translator = translator;
    }


	/**
	 * translatorStr
	 *
	 * @return  the translatorStr
	 * @since   CodingExample Ver 1.0
	 */
	
	public String getTranslatorStr() {
		return translatorStr;
	}


	/**
	 * translatorStr
	 *
	 * @param   translatorStr    the translatorStr to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setTranslatorStr(String translatorStr) {
		this.translatorStr = translatorStr;
	}
   








}