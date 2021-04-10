package com.orient.workflow.ext.identity;



/**
 * GroupMemberImpl entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class GroupMemberImpl  implements java.io.Serializable {


    // Fields    

     private GroupMemberImplId id;


    // Constructors

    /** default constructor */
    public GroupMemberImpl() {
    }

    
    /** full constructor */
    public GroupMemberImpl(GroupMemberImplId id) {
        this.id = id;
    }

   
    // Property accessors

    public GroupMemberImplId getId() {
        return this.id;
    }
    
    public void setId(GroupMemberImplId id) {
        this.id = id;
    }
   








}