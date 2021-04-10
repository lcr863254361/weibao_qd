package com.orient.sysmodel.domain.role;
// default package

import com.orient.sysmodel.operationinterface.IRoleFunctionTbom;

import java.util.HashSet;
import java.util.Set;


/**
 * AbstractRole entity provides the base persistence definition of the Role entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRole extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    
     /**
      * 角色编码
      */
     private String id;
     /**
      * 角色名称
      */
     private String name;
     /**
      * 备注信息
      */
     private String memo;
     /**
      * 角色类型 A：账户管理员 B：系统管理员 C：权限管理员 D：审计管理员 E：模型管理员 0:内置管理员
      */
     private String type;
     /**
      * 角色状态Y：启用N：禁用
      */
     private String status;
     private String flg;

     /**
      * 是否可编辑, Y 可编辑, N 不可编辑
      */
     private String	DELICO	= "";
     
     /**
      * 用户角色信息
      */
     private Set roleUsers = new HashSet(0);
     
     /**
      * Tbom角色功能信息
      */
     private Set<IRoleFunctionTbom> roleFunctionTboms = new HashSet(0);
     
     /**
      * 角色功能信息
      */
    // private Set roleFunctions = new HashSet(0);
     
     /**
      * 角色操作权限信息
      */
     private Set overAllOperations = new HashSet(0);
     
     /**
      * 角色特殊权限信息
      */
     private Set partOperations = new HashSet(0);
     
     /**
      * 角色业务库权限信息
      */
     private Set roleSchemas = new HashSet(0);
     
     /**
      * 角色算法信息
      */
     private Set roleAriths = new HashSet(0);
     
    // Constructors

	/** default constructor */
    public AbstractRole() {
    }

	/** minimal constructor */
    public AbstractRole(String name) {
        this.name = name;
    }
    
    /** full constructor */
    public AbstractRole(String name, String memo, String type, String status, String flg) {
        this.name = name;
        this.memo = memo;
        this.type = type;
        this.status = status;
        this.flg = flg;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlg() {
        return this.flg;
    }
    
    public void setFlg(String flg) {
        this.flg = flg;
    }
   

    public String getDELICO() {
		return DELICO;
	}

	public void setDELICO(String dELICO) {
		DELICO = dELICO;
	}

	/**
	 * roleUsers
	 *
	 * @return  the roleUsers
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getRoleUsers() {
		return roleUsers;
	}

	/**
	 * roleUsers
	 *
	 * @param   roleUsers    the roleUsers to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleUsers(Set roleUsers) {
		this.roleUsers = roleUsers;
	}

	/**
	 * roleFunctionTboms
	 *
	 * @return  the roleFunctionTboms
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set<IRoleFunctionTbom> getRoleFunctionTboms() {
		return roleFunctionTboms;
	}

	/**
	 * roleFunctionTboms
	 *
	 * @param   roleFunctionTboms    the roleFunctionTboms to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleFunctionTboms(Set<IRoleFunctionTbom> roleFunctionTboms) {
		this.roleFunctionTboms = roleFunctionTboms;
	}

	/**
	 * roleFunctions
	 *
	 * @return  the roleFunctions
	 * @since   CodingExample Ver 1.0
	 */
	
//	public Set getRoleFunctions() {
//		return roleFunctions;
//	}

	/**
	 * roleFunctions
	 *
	 * @param   roleFunctions    the roleFunctions to set
	 * @since   CodingExample Ver 1.0
	 */
	
//	public void setRoleFunctions(Set roleFunctions) {
//		this.roleFunctions = roleFunctions;
//	}

	/**
	 * overAllOperations
	 *
	 * @return  the overAllOperations
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getOverAllOperations() {
		return overAllOperations;
	}

	/**
	 * overAllOperations
	 *
	 * @param   overAllOperations    the overAllOperations to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setOverAllOperations(Set overAllOperations) {
		this.overAllOperations = overAllOperations;
	}

	/**
	 * roleSchemas
	 *
	 * @return  the roleSchemas
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getRoleSchemas() {
		return roleSchemas;
	}

	/**
	 * roleSchemas
	 *
	 * @param   roleSchemas    the roleSchemas to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleSchemas(Set roleSchemas) {
		this.roleSchemas = roleSchemas;
	}

	/**
	 * partOperations
	 *
	 * @return  the partOperations
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getPartOperations() {
		return partOperations;
	}

	/**
	 * partOperations
	 *
	 * @param   partOperations    the partOperations to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setPartOperations(Set partOperations) {
		this.partOperations = partOperations;
	}

	/**
	 * roleAriths
	 *
	 * @return  the roleAriths
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getRoleAriths() {
		return roleAriths;
	}

	/**
	 * roleAriths
	 *
	 * @param   roleAriths    the roleAriths to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleAriths(Set roleAriths) {
		this.roleAriths = roleAriths;
	}







}