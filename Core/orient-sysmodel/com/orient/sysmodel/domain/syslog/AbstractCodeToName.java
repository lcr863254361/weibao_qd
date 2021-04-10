package com.orient.sysmodel.domain.syslog;
// default package



/**
 * AbstractCodeToName entity provides the base persistence definition of the CodeToName entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractCodeToName extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private CodeToNameId id;


    // Constructors

    /** default constructor */
    public AbstractCodeToName() {
    }

    
    /** full constructor */
    public AbstractCodeToName(CodeToNameId id) {
        this.id = id;
    }

   
    // Property accessors

    public CodeToNameId getId() {
        return this.id;
    }
    
    public void setId(CodeToNameId id) {
        this.id = id;
    }
   








}