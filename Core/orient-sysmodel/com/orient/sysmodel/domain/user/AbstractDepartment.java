package com.orient.sysmodel.domain.user;

import com.orient.sysmodel.domain.BaseBean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



/**
 * AbstractDepartment entity provides the base persistence definition of the Department entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractDepartment extends BaseBean implements Serializable, Comparable<AbstractDepartment> {


    // Fields    
     /**
      * 部门编号
      */
     private String id;
     
     /**
      * 上级部门
      */
     private String pid;
     private Department parentDept;
     
     /** 子部门 */
 	 private Set childDepts=new HashSet(0);
 	 
     /**
      * 部门名称
      */
     private String name;
     
     /**
      * 部门职能
      */
     private String function;
     
     /**
      * 备注
      */
     private String notes;
     
     /**
      * 新增标志
      */
     private String addFlg;
     
     /**
      * 删除标志
      */
     private String delFlg;
     
     /**
      * 编辑标志
      */
     private String editFlg;

     /**
      * 排序
      */
     private Long order;

     /**
      * 部门用户
      */
     private Set users=new HashSet(0);
     
     /**
      * 用户登录信息
      */
     private Set userLoginHistorys=new HashSet(0);

    // Constructors

    /** default constructor */
    public AbstractDepartment() {
    }

	/** minimal constructor */
    public AbstractDepartment(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    /** full constructor */
    public AbstractDepartment(String id, String name, String function, String notes, String addFlg, String delFlg, String editFlg, Long order) {
        this.id = id;
        this.name = name;
        this.function = function;
        this.notes = notes;
        this.addFlg = addFlg;
        this.delFlg = delFlg;
        this.editFlg = editFlg;
        this.order = order;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    

    public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Department getParentDept() {
		return parentDept;
	}

	public void setParentDept(Department parentDept) {
		this.parentDept = parentDept;
	}

	public Set getChildDepts() {
		return childDepts;
	}

	public void setChildDepts(Set childDepts) {
		this.childDepts = childDepts;
	}

	public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return this.function;
    }
    
    public void setFunction(String function) {
        this.function = function;
    }

    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAddFlg() {
        return this.addFlg;
    }
    
    public void setAddFlg(String addFlg) {
        this.addFlg = addFlg;
    }

    public String getDelFlg() {
        return this.delFlg;
    }
    
    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getEditFlg() {
        return this.editFlg;
    }
    
    public void setEditFlg(String editFlg) {
        this.editFlg = editFlg;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    /**
	 * users
	 *
	 * @return  the users
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getUsers() {
		return users;
	}

	/**
	 * users
	 *
	 * @param   users    the users to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setUsers(Set users) {
		this.users = users;
	}

	/**
	 * userLoginHistorys
	 *
	 * @return  the userLoginHistorys
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getUserLoginHistorys() {
		return userLoginHistorys;
	}

	/**
	 * userLoginHistorys
	 *
	 * @param   userLoginHistorys    the userLoginHistorys to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setUserLoginHistorys(Set userLoginHistorys) {
		this.userLoginHistorys = userLoginHistorys;
	}

    @Override
    public int compareTo(AbstractDepartment dept) {
        Long order1 = 0l;
        if(this.getOrder() != null) {
            order1 = this.getOrder();
        }
        Long order2 = 0l;
        if(dept!=null && dept.getOrder()!=null) {
            order2 = dept.getOrder();
        }
        return (int) (order1 - order2);
    }
}