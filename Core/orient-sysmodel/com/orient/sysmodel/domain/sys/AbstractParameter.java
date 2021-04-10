package com.orient.sysmodel.domain.sys;
// default package



/**
 * AbstractParameter entity provides the base persistence definition of the Parameter entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractParameter extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String name;//参数名称
     private String datatype;//参数类型
     private String value;//参数值
     private String description;//参数描述


    // Constructors

    /** default constructor */
    public AbstractParameter() {
    }

    
    /** full constructor */
    public AbstractParameter(String name, String datatype, String value, String description) {
        this.name = name;
        this.datatype = datatype;
        this.value = value;
        this.description = description;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDatatype() {
        return this.datatype;
    }
    
    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
   








}