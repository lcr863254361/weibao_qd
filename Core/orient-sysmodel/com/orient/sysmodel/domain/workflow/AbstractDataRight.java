package com.orient.sysmodel.domain.workflow;
// default package



/**
 * AbstractDataRight entity provides the base persistence definition of the DataRight entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractDataRight extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private DataRightId id;
     private String right;//数据记录的权限设置，第一位为修改位0可以修改，1不可以修改，第二位0可以删除1不可以删除


    // Constructors

    /** default constructor */
    public AbstractDataRight() {
    }

    
    /** full constructor */
    public AbstractDataRight(DataRightId id) {
        this.id = id;
    }

    public AbstractDataRight(DataRightId id, String right) {
        this.id = id;
        this.right = right;
    }
   
    // Property accessors

    public DataRightId getId() {
        return this.id;
    }
    
    public void setId(DataRightId id) {
        this.id = id;
    }
   
    public String getRight() {
        return this.right;
    }
    
    public void setRight(String right) {
        this.right = right;
    }







}