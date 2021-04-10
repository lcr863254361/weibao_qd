package com.orient.sysmodel.domain.user;
// default package



/**
 * AbstractRcpLoginId entity provides the base persistence definition of the RcpLoginId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRcpLoginId extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String username;
     private String ip;
     private String schemaName;
     private String version;
     private String tbomName;
     private String designXml;
     private String tbomXml;
     private String schemaId;


    // Constructors

    /** default constructor */
    public AbstractRcpLoginId() {
    }

    
    /** full constructor */
    public AbstractRcpLoginId(String username, String ip, String schemaName, String version, String tbomName, String designXml, String tbomXml, String schemaId) {
        this.username = username;
        this.ip = ip;
        this.schemaName = schemaName;
        this.version = version;
        this.tbomName = tbomName;
        this.designXml = designXml;
        this.tbomXml = tbomXml;
        this.schemaId = schemaId;
    }

   
    // Property accessors

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return this.ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSchemaName() {
        return this.schemaName;
    }
    
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getVersion() {
        return this.version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }

    public String getTbomName() {
        return this.tbomName;
    }
    
    public void setTbomName(String tbomName) {
        this.tbomName = tbomName;
    }

    public String getDesignXml() {
        return this.designXml;
    }
    
    public void setDesignXml(String designXml) {
        this.designXml = designXml;
    }

    public String getTbomXml() {
        return this.tbomXml;
    }
    
    public void setTbomXml(String tbomXml) {
        this.tbomXml = tbomXml;
    }

    public String getSchemaId() {
        return this.schemaId;
    }
    
    public void setSchemaId(String schemaId) {
        this.schemaId = schemaId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AbstractRcpLoginId) ) return false;
		 AbstractRcpLoginId castOther = ( AbstractRcpLoginId ) other; 
         
		 return ( (this.getUsername()==castOther.getUsername()) || ( this.getUsername()!=null && castOther.getUsername()!=null && this.getUsername().equals(castOther.getUsername()) ) )
 && ( (this.getIp()==castOther.getIp()) || ( this.getIp()!=null && castOther.getIp()!=null && this.getIp().equals(castOther.getIp()) ) )
 && ( (this.getSchemaName()==castOther.getSchemaName()) || ( this.getSchemaName()!=null && castOther.getSchemaName()!=null && this.getSchemaName().equals(castOther.getSchemaName()) ) )
 && ( (this.getVersion()==castOther.getVersion()) || ( this.getVersion()!=null && castOther.getVersion()!=null && this.getVersion().equals(castOther.getVersion()) ) )
 && ( (this.getTbomName()==castOther.getTbomName()) || ( this.getTbomName()!=null && castOther.getTbomName()!=null && this.getTbomName().equals(castOther.getTbomName()) ) )
 && ( (this.getDesignXml()==castOther.getDesignXml()) || ( this.getDesignXml()!=null && castOther.getDesignXml()!=null && this.getDesignXml().equals(castOther.getDesignXml()) ) )
 && ( (this.getTbomXml()==castOther.getTbomXml()) || ( this.getTbomXml()!=null && castOther.getTbomXml()!=null && this.getTbomXml().equals(castOther.getTbomXml()) ) )
 && ( (this.getSchemaId()==castOther.getSchemaId()) || ( this.getSchemaId()!=null && castOther.getSchemaId()!=null && this.getSchemaId().equals(castOther.getSchemaId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUsername() == null ? 0 : this.getUsername().hashCode() );
         result = 37 * result + ( getIp() == null ? 0 : this.getIp().hashCode() );
         result = 37 * result + ( getSchemaName() == null ? 0 : this.getSchemaName().hashCode() );
         result = 37 * result + ( getVersion() == null ? 0 : this.getVersion().hashCode() );
         result = 37 * result + ( getTbomName() == null ? 0 : this.getTbomName().hashCode() );
         result = 37 * result + ( getDesignXml() == null ? 0 : this.getDesignXml().hashCode() );
         result = 37 * result + ( getTbomXml() == null ? 0 : this.getTbomXml().hashCode() );
         result = 37 * result + ( getSchemaId() == null ? 0 : this.getSchemaId().hashCode() );
         return result;
   }   





}