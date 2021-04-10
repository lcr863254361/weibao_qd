package com.orient.sysmodel.domain.sys;
// default package



/**
 * AbstractPartitionRemarkId entity provides the base persistence definition of the PartitionRemarkId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractPartitionRemark extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String tableName;//动态表(被导入数据表)名称
     private String gkId;//动态数据类对应数据类 的 记录的ID(CWM_TABLES)
     private String impTime;//导入时间
     private String jobId;//CWM_ETL_JOB中的ID
     private String remark;//导入备注
     private String cols;//文件中的数据列名


    // Constructors

    /** default constructor */
    public AbstractPartitionRemark() {
    }

    
    /** full constructor */
    public AbstractPartitionRemark(String id, String tableName, String gkId, String impTime, String jobId, String remark, String cols) {
        this.id = id;
        this.tableName = tableName;
        this.gkId = gkId;
        this.impTime = impTime;
        this.jobId = jobId;
        this.remark = remark;
        this.cols = cols;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return this.tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getGkId() {
        return this.gkId;
    }
    
    public void setGkId(String gkId) {
        this.gkId = gkId;
    }

    public String getImpTime() {
        return this.impTime;
    }
    
    public void setImpTime(String impTime) {
        this.impTime = impTime;
    }

    public String getJobId() {
        return this.jobId;
    }
    
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCols() {
        return this.cols;
    }
    
    public void setCols(String cols) {
        this.cols = cols;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AbstractPartitionRemark) ) return false;
		 AbstractPartitionRemark castOther = ( AbstractPartitionRemark ) other; 
         
		 return ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) )
 && ( (this.getTableName()==castOther.getTableName()) || ( this.getTableName()!=null && castOther.getTableName()!=null && this.getTableName().equals(castOther.getTableName()) ) )
 && ( (this.getGkId()==castOther.getGkId()) || ( this.getGkId()!=null && castOther.getGkId()!=null && this.getGkId().equals(castOther.getGkId()) ) )
 && ( (this.getImpTime()==castOther.getImpTime()) || ( this.getImpTime()!=null && castOther.getImpTime()!=null && this.getImpTime().equals(castOther.getImpTime()) ) )
 && ( (this.getJobId()==castOther.getJobId()) || ( this.getJobId()!=null && castOther.getJobId()!=null && this.getJobId().equals(castOther.getJobId()) ) )
 && ( (this.getRemark()==castOther.getRemark()) || ( this.getRemark()!=null && castOther.getRemark()!=null && this.getRemark().equals(castOther.getRemark()) ) )
 && ( (this.getCols()==castOther.getCols()) || ( this.getCols()!=null && castOther.getCols()!=null && this.getCols().equals(castOther.getCols()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
         result = 37 * result + ( getTableName() == null ? 0 : this.getTableName().hashCode() );
         result = 37 * result + ( getGkId() == null ? 0 : this.getGkId().hashCode() );
         result = 37 * result + ( getImpTime() == null ? 0 : this.getImpTime().hashCode() );
         result = 37 * result + ( getJobId() == null ? 0 : this.getJobId().hashCode() );
         result = 37 * result + ( getRemark() == null ? 0 : this.getRemark().hashCode() );
         result = 37 * result + ( getCols() == null ? 0 : this.getCols().hashCode() );
         return result;
   }   





}