package com.orient.sysmodel.domain.syslog;
// default package



/**
 * AbstractCodeToNameId entity provides the base persistence definition of the CodeToNameId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractCodeToNameId extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String name;
     private String typeid;
     private String typename;
     private String remark;


    // Constructors

    /** default constructor */
    public AbstractCodeToNameId() {
    }

    
    /** full constructor */
    public AbstractCodeToNameId(String id, String name, String typeid, String typename, String remark) {
        this.id = id;
        this.name = name;
        this.typeid = typeid;
        this.typename = typename;
        this.remark = remark;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getTypeid() {
        return this.typeid;
    }
    
    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return this.typename;
    }
    
    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AbstractCodeToNameId) ) return false;
		 AbstractCodeToNameId castOther = ( AbstractCodeToNameId ) other; 
         
		 return ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getTypeid()==castOther.getTypeid()) || ( this.getTypeid()!=null && castOther.getTypeid()!=null && this.getTypeid().equals(castOther.getTypeid()) ) )
 && ( (this.getTypename()==castOther.getTypename()) || ( this.getTypename()!=null && castOther.getTypename()!=null && this.getTypename().equals(castOther.getTypename()) ) )
 && ( (this.getRemark()==castOther.getRemark()) || ( this.getRemark()!=null && castOther.getRemark()!=null && this.getRemark().equals(castOther.getRemark()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getTypeid() == null ? 0 : this.getTypeid().hashCode() );
         result = 37 * result + ( getTypename() == null ? 0 : this.getTypename().hashCode() );
         result = 37 * result + ( getRemark() == null ? 0 : this.getRemark().hashCode() );
         return result;
   }   





}