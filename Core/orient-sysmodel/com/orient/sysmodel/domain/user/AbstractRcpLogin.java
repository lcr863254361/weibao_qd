package com.orient.sysmodel.domain.user;
// default package



/**
 * AbstractRcpLogin entity provides the base persistence definition of the RcpLogin entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRcpLogin extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private RcpLoginId id;


    // Constructors

    /** default constructor */
    public AbstractRcpLogin() {
    }

    
    /** full constructor */
    public AbstractRcpLogin(RcpLoginId id) {
        this.id = id;
    }

   
    // Property accessors

    public RcpLoginId getId() {
        return this.id;
    }
    
    public void setId(RcpLoginId id) {
        this.id = id;
    }
   








}