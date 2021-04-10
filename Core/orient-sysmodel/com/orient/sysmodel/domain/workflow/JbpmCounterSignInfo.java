package com.orient.sysmodel.domain.workflow;
 



/**
 * JbpmCounterSignInfo entity. @author MyEclipse Persistence Tools
 */

public class JbpmCounterSignInfo  implements java.io.Serializable {


    // Fields    

     private JbpmCounterSignInfoId id;


    // Constructors

    /** default constructor */
    public JbpmCounterSignInfo() {
    }

    
    /** full constructor */
    public JbpmCounterSignInfo(JbpmCounterSignInfoId id) {
        this.id = id;
    }

   
    // Property accessors

    public JbpmCounterSignInfoId getId() {
        return this.id;
    }
    
    public void setId(JbpmCounterSignInfoId id) {
        this.id = id;
    }
   








}