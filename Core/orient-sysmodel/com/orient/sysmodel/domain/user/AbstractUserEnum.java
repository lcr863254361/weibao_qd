package com.orient.sysmodel.domain.user;
// default package



/**
 * AbstractUserEnum entity provides the base persistence definition of the UserEnum entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserEnum extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String enumId;
     private String value;
     private String displayValue;
     private String imageUrl;
     private String description;


    // Constructors

    /** default constructor */
    public AbstractUserEnum() {
    }

	/** minimal constructor */
    public AbstractUserEnum(String enumId, String value, String displayValue) {
        this.enumId = enumId;
        this.value = value;
        this.displayValue = displayValue;
    }
    
    /** full constructor */
    public AbstractUserEnum(String enumId, String value, String displayValue, String imageUrl, String description) {
        this.enumId = enumId;
        this.value = value;
        this.displayValue = displayValue;
        this.imageUrl = imageUrl;
        this.description = description;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getEnumId() {
        return this.enumId;
    }
    
    public void setEnumId(String enumId) {
        this.enumId = enumId;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayValue() {
        return this.displayValue;
    }
    
    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
   








}