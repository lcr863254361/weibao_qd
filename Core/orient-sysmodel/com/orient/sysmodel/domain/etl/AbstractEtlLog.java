package com.orient.sysmodel.domain.etl;
// default package

import java.sql.Clob;
import java.util.Date;



/**
 * AbstractEtlLogId entity provides the base persistence definition of the EtlLogId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractEtlLog extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String logInfo;//日志表的关联信息
     private String userId;//导入用户的ID(用户登录名)
     private Date startTime;//数据开始导入的时间
     private String tableId;//导入数据类的ID
     private String status;//数据导入状态：包含开始导入数据、外部表创建成功、读取外部表失败、数据导入成功、数据导入失败几种状态
     private String dataAmount;//导入文件中的数据总条数
     private String fileName;//导入的数据文件名称
     private String tableDisname;//导入表名称(显示名)
     private Date endTime;//文件导入完成的时间
     private String rightData;//导入成功的数据总条数
     private String wrongData;//导入错误的数据总条数
     private Date time1;//数据文件解析完成的时间
     private Date time2;//数据文件上传完成的时间
     private Long isDelete;//是否删除: 0 没有删除; 1 已删除
     private EtlJob job;//所属JOB
     private String jobResult;//数据是否正常导入：1导入正常；0导入异常
     private String tableName;//导入目标数据表的名称(S_Name)
     private String fileSize;//导入的数据文件大小
     private Clob logddl;//异常数据处理SQL
     private String logddlStr;//异常数据处理SQL


    // Constructors

    /** default constructor */
    public AbstractEtlLog() {
    }

    
    /** full constructor */
    public AbstractEtlLog(String id, String logInfo, String userId, Date startTime, String tableId, String status, String dataAmount, String fileName, String tableDisname, Date endTime, String rightData, String wrongData, Date time1, Date time2, Long isDelete, EtlJob job, String jobResult, String tableName, String fileSize, Clob logddl) {
        this.id = id;
        this.logInfo = logInfo;
        this.userId = userId;
        this.startTime = startTime;
        this.tableId = tableId;
        this.status = status;
        this.dataAmount = dataAmount;
        this.fileName = fileName;
        this.tableDisname = tableDisname;
        this.endTime = endTime;
        this.rightData = rightData;
        this.wrongData = wrongData;
        this.time1 = time1;
        this.time2 = time2;
        this.isDelete = isDelete;
        this.job = job;
        this.jobResult = jobResult;
        this.tableName = tableName;
        this.fileSize = fileSize;
        this.logddl = logddl;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getLogInfo() {
        return this.logInfo;
    }
    
    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getTableId() {
        return this.tableId;
    }
    
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataAmount() {
        return this.dataAmount;
    }
    
    public void setDataAmount(String dataAmount) {
        this.dataAmount = dataAmount;
    }

    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTableDisname() {
        return this.tableDisname;
    }
    
    public void setTableDisname(String tableDisname) {
        this.tableDisname = tableDisname;
    }

    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRightData() {
        return this.rightData;
    }
    
    public void setRightData(String rightData) {
        this.rightData = rightData;
    }

    public String getWrongData() {
        return this.wrongData;
    }
    
    public void setWrongData(String wrongData) {
        this.wrongData = wrongData;
    }

    public Date getTime1() {
        return this.time1;
    }
    
    public void setTime1(Date time1) {
        this.time1 = time1;
    }

    public Date getTime2() {
        return this.time2;
    }
    
    public void setTime2(Date time2) {
        this.time2 = time2;
    }

    public Long getIsDelete() {
        return this.isDelete;
    }
    
    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
    }

    

    public EtlJob getJob() {
		return job;
	}


	public void setJob(EtlJob job) {
		this.job = job;
	}


	public String getJobResult() {
        return this.jobResult;
    }
    
    public void setJobResult(String jobResult) {
        this.jobResult = jobResult;
    }

    public String getTableName() {
        return this.tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFileSize() {
        return this.fileSize;
    }
    
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Clob getLogddl() {
        return this.logddl;
    }
    
    public void setLogddl(Clob logddl) {
        this.logddl = logddl;
    }


	/**
	 * logddlStr
	 *
	 * @return  the logddlStr
	 * @since   CodingExample Ver 1.0
	 */
	
	public String getLogddlStr() {
		return logddlStr;
	}


	/**
	 * logddlStr
	 *
	 * @param   logddlStr    the logddlStr to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setLogddlStr(String logddlStr) {
		this.logddlStr = logddlStr;
	}
   



   


}