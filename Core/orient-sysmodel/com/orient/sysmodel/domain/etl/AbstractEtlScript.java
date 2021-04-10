package com.orient.sysmodel.domain.etl;

import java.util.HashSet;
import java.util.Set;
// default package



/**
 * AbstractEtlScript entity provides the base persistence definition of the EtlScript entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractEtlScript extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String scriptname;//导入脚本的名称
     private String filename;//导入文件的名称
     private String filetype;//导入文件的类型(txt等)
     private String dataindex;//导入文件的数据起始行
     private String linesplit;//导入文件的行分隔符
     private String username;//导入数据文件的用户ID(用户登录名)
     private String errorsolve;//导入文件的错误处理方式
     private String jobtype;//ETL处理方式：0立即导入或延时导入
     private String filepath;//导入文件存放的路径(客户端)
     private String filelength;//导入文件的大小
     private String filelastmod;//导入文件的最后修改时间
     private String jobtime;//导入数据文件的时间
     private String srccolumn;//导入数据文件的列名
     private String importType;//导入类型 （单文件导入等）

     private Set etlTranslators=new HashSet(0);//导入规则信息

    // Constructors

    /** default constructor */
    public AbstractEtlScript() {
    }

    
    /** full constructor */
    public AbstractEtlScript(String scriptname, String filename, String filetype, String dataindex, String linesplit, String username, String errorsolve, String jobtype, String filepath, String filelength, String filelastmod, String jobtime, String srccolumn, String importType) {
        this.scriptname = scriptname;
        this.filename = filename;
        this.filetype = filetype;
        this.dataindex = dataindex;
        this.linesplit = linesplit;
        this.username = username;
        this.errorsolve = errorsolve;
        this.jobtype = jobtype;
        this.filepath = filepath;
        this.filelength = filelength;
        this.filelastmod = filelastmod;
        this.jobtime = jobtime;
        this.srccolumn = srccolumn;
        this.importType = importType;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getScriptname() {
        return this.scriptname;
    }
    
    public void setScriptname(String scriptname) {
        this.scriptname = scriptname;
    }

    public String getFilename() {
        return this.filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return this.filetype;
    }
    
    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getDataindex() {
        return this.dataindex;
    }
    
    public void setDataindex(String dataindex) {
        this.dataindex = dataindex;
    }

    public String getLinesplit() {
        return this.linesplit;
    }
    
    public void setLinesplit(String linesplit) {
        this.linesplit = linesplit;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getErrorsolve() {
        return this.errorsolve;
    }
    
    public void setErrorsolve(String errorsolve) {
        this.errorsolve = errorsolve;
    }

    public String getJobtype() {
        return this.jobtype;
    }
    
    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getFilepath() {
        return this.filepath;
    }
    
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilelength() {
        return this.filelength;
    }
    
    public void setFilelength(String filelength) {
        this.filelength = filelength;
    }

    public String getFilelastmod() {
        return this.filelastmod;
    }
    
    public void setFilelastmod(String filelastmod) {
        this.filelastmod = filelastmod;
    }

    public String getJobtime() {
        return this.jobtime;
    }
    
    public void setJobtime(String jobtime) {
        this.jobtime = jobtime;
    }

    public String getSrccolumn() {
        return this.srccolumn;
    }
    
    public void setSrccolumn(String srccolumn) {
        this.srccolumn = srccolumn;
    }

    public String getImportType() {
        return this.importType;
    }
    
    public void setImportType(String importType) {
        this.importType = importType;
    }


	/**
	 * etlTranslators
	 *
	 * @return  the etlTranslators
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getEtlTranslators() {
		return etlTranslators;
	}


	/**
	 * etlTranslators
	 *
	 * @param   etlTranslators    the etlTranslators to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setEtlTranslators(Set etlTranslators) {
		this.etlTranslators = etlTranslators;
	}
   








}