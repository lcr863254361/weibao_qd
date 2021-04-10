package com.orient.sysmodel.domain.etl;
// default package

import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * AbstractEtlJob entity provides the base persistence definition of the EtlJob entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractEtlJob extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String userName;//数据导入用户名
     private Date jobTime;//数据文件的导入时间
     private String status;//数据导入状态：0-未导入；1-已导入
     private Clob ddl;//创建外部表SQL脚本
     private Clob dml;//查询外部表SQL脚本
     private Clob loadsql;//导入数据SQL脚本
     
     private String ddlStr;//创建外部表SQL脚本
     private String dmlStr;//查询外部表SQL脚本
     private String loadsqlStr;//导入数据SQL脚本

     private Set etlLogs=new HashSet(0);//导入日志

    // Constructors

    /** default constructor */
    public AbstractEtlJob() {
    }

    
    /** full constructor */
    public AbstractEtlJob(String userName, Date jobTime, String status, Clob ddl, Clob dml, Clob loadsql) {
        this.userName = userName;
        this.jobTime = jobTime;
        this.status = status;
        this.ddl = ddl;
        this.dml = dml;
        this.loadsql = loadsql;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getJobTime() {
        return this.jobTime;
    }
    
    public void setJobTime(Date jobTime) {
        this.jobTime = jobTime;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public Clob getDdl() {
        return this.ddl;
    }
    
    public void setDdl(Clob ddl) {
        this.ddl = ddl;
    }

    public Clob getDml() {
        return this.dml;
    }
    
    public void setDml(Clob dml) {
        this.dml = dml;
    }

    public Clob getLoadsql() {
        return this.loadsql;
    }
    
    public void setLoadsql(Clob loadsql) {
        this.loadsql = loadsql;
    }


	/**
	 * etlLogs
	 *
	 * @return  the etlLogs
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getEtlLogs() {
		return etlLogs;
	}


	/**
	 * etlLogs
	 *
	 * @param   etlLogs    the etlLogs to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setEtlLogs(Set etlLogs) {
		this.etlLogs = etlLogs;
	}


	/**
	 * ddlStr
	 *
	 * @return  the ddlStr
	 * @since   CodingExample Ver 1.0
	 */
	
	public String getDdlStr() {
		return ddlStr;
	}


	/**
	 * ddlStr
	 *
	 * @param   ddlStr    the ddlStr to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setDdlStr(String ddlStr) {
		this.ddlStr = ddlStr;
	}


	/**
	 * dmlStr
	 *
	 * @return  the dmlStr
	 * @since   CodingExample Ver 1.0
	 */
	
	public String getDmlStr() {
		return dmlStr;
	}


	/**
	 * dmlStr
	 *
	 * @param   dmlStr    the dmlStr to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setDmlStr(String dmlStr) {
		this.dmlStr = dmlStr;
	}


	/**
	 * loadsqlStr
	 *
	 * @return  the loadsqlStr
	 * @since   CodingExample Ver 1.0
	 */
	
	public String getLoadsqlStr() {
		return loadsqlStr;
	}


	/**
	 * loadsqlStr
	 *
	 * @param   loadsqlStr    the loadsqlStr to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setLoadsqlStr(String loadsqlStr) {
		this.loadsqlStr = loadsqlStr;
	}
   








}