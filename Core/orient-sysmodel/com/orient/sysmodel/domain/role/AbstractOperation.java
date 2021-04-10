package com.orient.sysmodel.domain.role;
// default package


/**
 * AbstractOperation entity provides the base persistence definition of the Operation entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractOperation extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private Long id;
     private String name;//操作名称


    // Constructors

    /** default constructor */
    public AbstractOperation() {
    }

    
    /** full constructor */
    public AbstractOperation(String name) {
        this.name = name;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
   








}