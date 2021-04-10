package com.orient.sysmodel.domain.role;

import com.orient.sysmodel.domain.tbom.Tbom;
import com.orient.sysmodel.domain.tbom.TbomDir;
import com.orient.sysmodel.operationinterface.IRoleFunctionTbom;

// default package



/**
 * AbstractRoleFunctionTbom entity provides the base persistence definition of the RoleFunctionTbom entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractRoleFunctionTbom extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable, IRoleFunctionTbom {


    // Fields    

     private RoleFunctionTbomId id;

     private Role role;
     private Function function;
     private TbomDir tbomDir;

    // Constructors

    /** default constructor */
    public AbstractRoleFunctionTbom() {
    }

    
    /** full constructor */
    public AbstractRoleFunctionTbom(RoleFunctionTbomId id) {
        this.id = id;
    }

   
    // Property accessors

    public RoleFunctionTbomId getId() {
        return this.id;
    }
    
    public void setId(RoleFunctionTbomId id) {
        this.id = id;
    }


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public Function getFunction() {
		return function;
	}


	public void setFunction(Function function) {
		this.function = function;
	}


	/**
	 * tbomDir
	 *
	 * @return  the tbomDir
	 * @since   CodingExample Ver 1.0
	 */
	
	public TbomDir getTbomDir() {
		return tbomDir;
	}


	/**
	 * tbomDir
	 *
	 * @param   tbomDir    the tbomDir to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setTbomDir(TbomDir tbomDir) {
		this.tbomDir = tbomDir;
	}


	








}