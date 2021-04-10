package com.orient.sysmodel.domain.workflow;
 



/**
 * JbpmConfigUser entity. @author MyEclipse Persistence Tools
 */

public class JbpmConfigUser implements java.io.Serializable {


    // Fields    

     /** 
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 1L;
	private JbpmConfigUserId id;


    // Constructors

    /** default constructor */
    public JbpmConfigUser() {
    }

    
    /** full constructor */
    public JbpmConfigUser(JbpmConfigUserId id) {
        this.id = id;
    }

   
    // Property accessors

    public JbpmConfigUserId getId() {
        return this.id;
    }
    
    public void setId(JbpmConfigUserId id) {
        this.id = id;
    }
   








}