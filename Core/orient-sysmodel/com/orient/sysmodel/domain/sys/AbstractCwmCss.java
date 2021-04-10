package com.orient.sysmodel.domain.sys;
// default package

import java.util.Date;


/**
 * AbstractCwmCssId entity provides the base persistence definition of the CwmCssId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractCwmCss extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String filename;//文件名称
     private String fileurl;//文件位置
     private String sybs;//使用标识
     private String scr;//上传人
     private String syr;//使用人
     private Date expTime;//上传时间
     private String remarks;//备注


    // Constructors

    /** default constructor */
    public AbstractCwmCss() {
    }

	/** minimal constructor */
    public AbstractCwmCss(String id, String filename, String fileurl, String sybs, String scr) {
        this.id = id;
        this.filename = filename;
        this.fileurl = fileurl;
        this.sybs = sybs;
        this.scr = scr;
    }
    
    /** full constructor */
    public AbstractCwmCss(String id, String filename, String fileurl, String sybs, String scr, String syr, Date expTime, String remarks) {
        this.id = id;
        this.filename = filename;
        this.fileurl = fileurl;
        this.sybs = sybs;
        this.scr = scr;
        this.syr = syr;
        this.expTime = expTime;
        this.remarks = remarks;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return this.filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return this.fileurl;
    }
    
    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getSybs() {
        return this.sybs;
    }
    
    public void setSybs(String sybs) {
        this.sybs = sybs;
    }

    public String getScr() {
        return this.scr;
    }
    
    public void setScr(String scr) {
        this.scr = scr;
    }

    public String getSyr() {
        return this.syr;
    }
    
    public void setSyr(String syr) {
        this.syr = syr;
    }

    public Date getExpTime() {
        return this.expTime;
    }
    
    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    public String getRemarks() {
        return this.remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AbstractCwmCss) ) return false;
		 AbstractCwmCss castOther = ( AbstractCwmCss ) other; 
         
		 return ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) )
 && ( (this.getFilename()==castOther.getFilename()) || ( this.getFilename()!=null && castOther.getFilename()!=null && this.getFilename().equals(castOther.getFilename()) ) )
 && ( (this.getFileurl()==castOther.getFileurl()) || ( this.getFileurl()!=null && castOther.getFileurl()!=null && this.getFileurl().equals(castOther.getFileurl()) ) )
 && ( (this.getSybs()==castOther.getSybs()) || ( this.getSybs()!=null && castOther.getSybs()!=null && this.getSybs().equals(castOther.getSybs()) ) )
 && ( (this.getScr()==castOther.getScr()) || ( this.getScr()!=null && castOther.getScr()!=null && this.getScr().equals(castOther.getScr()) ) )
 && ( (this.getSyr()==castOther.getSyr()) || ( this.getSyr()!=null && castOther.getSyr()!=null && this.getSyr().equals(castOther.getSyr()) ) )
 && ( (this.getExpTime()==castOther.getExpTime()) || ( this.getExpTime()!=null && castOther.getExpTime()!=null && this.getExpTime().equals(castOther.getExpTime()) ) )
 && ( (this.getRemarks()==castOther.getRemarks()) || ( this.getRemarks()!=null && castOther.getRemarks()!=null && this.getRemarks().equals(castOther.getRemarks()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
         result = 37 * result + ( getFilename() == null ? 0 : this.getFilename().hashCode() );
         result = 37 * result + ( getFileurl() == null ? 0 : this.getFileurl().hashCode() );
         result = 37 * result + ( getSybs() == null ? 0 : this.getSybs().hashCode() );
         result = 37 * result + ( getScr() == null ? 0 : this.getScr().hashCode() );
         result = 37 * result + ( getSyr() == null ? 0 : this.getSyr().hashCode() );
         result = 37 * result + ( getExpTime() == null ? 0 : this.getExpTime().hashCode() );
         result = 37 * result + ( getRemarks() == null ? 0 : this.getRemarks().hashCode() );
         return result;
   }   





}