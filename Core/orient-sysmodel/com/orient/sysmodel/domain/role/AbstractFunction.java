package com.orient.sysmodel.domain.role;
// default package

import java.util.HashSet;
import java.util.Set;


/**
 * AbstractFunction entity provides the base persistence definition of the Function entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractFunction extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private Long functionid;//功能点ID
     private String code;//功能点编码
     private String name;//功能点名称
     private Long parentid;
     private Function parentFunction;//父级功能点ID
     private String url;//功能点所在的请求路径
     private String notes;//备注
     private String addFlg;//是否可以添加, 1．可以添加 0．不可以添加
     private String delFlg;//是否可以删除, 1.可以删除0.不可以删除
     private String editFlg;//是否可以编辑, 1.可以编辑0.不可以编辑
     private Long position;//同级功能点的显示位置
     private String logtype;//日志记录标识 1.记录日志0.不记录
     private String logshow;//日志显示标识 1.显示记录的日志类型 0.不显示
     private Long isShow;//给角色分配功能点页面上是否显示 1 显示, 0 不显示
     private String tbomFlg;//是否挂接tbom 1:该功能挂接了TBOM  0:功能未挂接TBOM
    private String js;
    private String icon;

     private Set childrenFunction = new HashSet(0);//子功能
     /**
      * Tbom角色功能信息
      */
     private Set roleFunctionTboms = new HashSet(0);
     
     /**
      * 角色功能信息
      */
 //    private Set roleFunctions = new HashSet(0);
     
    // Constructors

    /** default constructor */
    public AbstractFunction() {
    }

	/** minimal constructor */
    public AbstractFunction(Long functionid, String url) {
        this.functionid = functionid;
        this.url = url;
    }
    
    /** full constructor */
    public AbstractFunction(String code, String name, Long functionid, String url, String notes, String addFlg, String delFlg, String editFlg, Long position, String logtype, String logshow, Long isShow, String tbomFlg) {
        this.code = code;
        this.name = name;
        this.functionid = functionid;
        this.url = url;
        this.notes = notes;
        this.addFlg = addFlg;
        this.delFlg = delFlg;
        this.editFlg = editFlg;
        this.position = position;
        this.logtype = logtype;
        this.logshow = logshow;
        this.isShow = isShow;
        this.tbomFlg = tbomFlg;
    }

   
    // Property accessors

    

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    public Function getParentFunction() {
		return parentFunction;
	}

	public void setParentFunction(Function parentFunction) {
		this.parentFunction = parentFunction;
	}

	public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
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

    public Long getPosition() {
        return this.position;
    }
    
    public void setPosition(Long position) {
        this.position = position;
    }

    public String getLogtype() {
        return this.logtype;
    }
    
    public void setLogtype(String logtype) {
        this.logtype = logtype;
    }

    public String getLogshow() {
        return this.logshow;
    }
    
    public void setLogshow(String logshow) {
        this.logshow = logshow;
    }

    public String getJs() { return js; }

    public void setJs(String js) { this.js = js; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }

    /**
	 * functionid
	 *
	 * @return  the functionid
	 * @since   CodingExample Ver 1.0
	 */
	
	public Long getFunctionid() {
		return functionid;
	}

	/**
	 * functionid
	 *
	 * @param   functionid    the functionid to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setFunctionid(Long functionid) {
		this.functionid = functionid;
	}

	/**
	 * parentid
	 *
	 * @return  the parentid
	 * @since   CodingExample Ver 1.0
	 */
	
	public Long getParentid() {
		return parentid;
	}

	/**
	 * parentid
	 *
	 * @param   parentid    the parentid to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	/**
	 * isShow
	 *
	 * @return  the isShow
	 * @since   CodingExample Ver 1.0
	 */
	
	public Long getIsShow() {
		return isShow;
	}

	/**
	 * isShow
	 *
	 * @param   isShow    the isShow to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setIsShow(Long isShow) {
		this.isShow = isShow;
	}

	public String getTbomFlg() {
        return this.tbomFlg;
    }
    
    public void setTbomFlg(String tbomFlg) {
        this.tbomFlg = tbomFlg;
    }

	/**
	 * roleFunctionTboms
	 *
	 * @return  the roleFunctionTboms
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getRoleFunctionTboms() {
		return roleFunctionTboms;
	}

	/**
	 * roleFunctionTboms
	 *
	 * @param   roleFunctionTboms    the roleFunctionTboms to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setRoleFunctionTboms(Set roleFunctionTboms) {
		this.roleFunctionTboms = roleFunctionTboms;
	}

	/**
	 * roleFunctions
	 *
	 * @return  the roleFunctions
	 * @since   CodingExample Ver 1.0
	 */
	

	/**
	 * roleFunctions
	 *
	 * @param   roleFunctions    the roleFunctions to set
	 * @since   CodingExample Ver 1.0
	 */
	

	/**
	 * childrenFunction
	 *
	 * @return  the childrenFunction
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getChildrenFunction() {
		return childrenFunction;
	}

	/**
	 * childrenFunction
	 *
	 * @param   childrenFunction    the childrenFunction to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setChildrenFunction(Set childrenFunction) {
		this.childrenFunction = childrenFunction;
	}
   



	




}