package com.orient.sysmodel.domain.arith;

import java.util.HashSet;
import java.util.Set;
// default package



/**
 * AbstractArithId entity provides the base persistence definition of the ArithId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractArith extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String name;//算法名称
     private Long type;//算法类型，分数据库自带算法（0），自定义算法（1），自定义算法jar文件（2），自定义算法类名（3），自定义算法方法名（4）
     private String category;//算法所属类别
     private String description;//算法描述
     private String fileName;//自定义算法的文件名(可以多个)，以‘，’分割
     private String methodName;//自定义算法的方法名
     private Long paraNumber;//算法的参数个数
     private String paraType;//算法的参数类型，以‘，’分割
     private String refLib;//引用的lib包名
     private String dataType;//算法返回类型
     private Long isValid;//是否启用，是否有效，1表示有效和启用，0表示无效和删除
     private Long arithType;//算法所对应的函数是单行函数还是聚集函数，0表示单行函数，1表示聚集函数
     private Long leastNumber;//无限制参数的参数个数最小值，即最少有多少个参数！
     private String className;//自定义算法所在类名
     private String classPackage;//自定义算法所在类的包名
     private Long fileNumber;//自定义算法的文件数
     private String arithMethod;//数据库内置算法公式
     //private String pid;
     private Arith parentarith;//用于自定义算法的树形组织
     private Long mainJar;//用于解析的主jar包标识
     private String fileLocation;//文件所在路径

     private Set childrenArith=new HashSet(0);//子算法
     
     /**
      * 角色算法信息
      */
     private Set roleAriths = new HashSet(0);
     
    // Constructors

    /** default constructor */
    public AbstractArith() {
    }

    
    /** full constructor */
    public AbstractArith(String id, String name, Long type, String category, String description, String fileName, String methodName, Long paraNumber, String paraType, String refLib, String dataType, Long isValid, Long arithType, Long leastNumber, String className, String classPackage, Long fileNumber, String arithMethod, Arith parentarith, Long mainJar, String fileLocation) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.category = category;
        this.description = description;
        this.fileName = fileName;
        this.methodName = methodName;
        this.paraNumber = paraNumber;
        this.paraType = paraType;
        this.refLib = refLib;
        this.dataType = dataType;
        this.isValid = isValid;
        this.arithType = arithType;
        this.leastNumber = leastNumber;
        this.className = className;
        this.classPackage = classPackage;
        this.fileNumber = fileNumber;
        this.arithMethod = arithMethod;
        this.parentarith = parentarith;
        this.mainJar = mainJar;
        this.fileLocation = fileLocation;
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

    public Long getType() {
        return this.type;
    }
    
    public void setType(Long type) {
        this.type = type;
    }

    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMethodName() {
        return this.methodName;
    }
    
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getParaNumber() {
        return this.paraNumber;
    }
    
    public void setParaNumber(Long paraNumber) {
        this.paraNumber = paraNumber;
    }

    public String getParaType() {
        return this.paraType;
    }
    
    public void setParaType(String paraType) {
        this.paraType = paraType;
    }

    public String getRefLib() {
        return this.refLib;
    }
    
    public void setRefLib(String refLib) {
        this.refLib = refLib;
    }

    public String getDataType() {
        return this.dataType;
    }
    
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Long getIsValid() {
        return this.isValid;
    }
    
    public void setIsValid(Long isValid) {
        this.isValid = isValid;
    }

    public Long getArithType() {
        return this.arithType;
    }
    
    public void setArithType(Long arithType) {
        this.arithType = arithType;
    }

    public Long getLeastNumber() {
        return this.leastNumber;
    }
    
    public void setLeastNumber(Long leastNumber) {
        this.leastNumber = leastNumber;
    }

    public String getClassName() {
        return this.className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassPackage() {
        return this.classPackage;
    }
    
    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }

    public Long getFileNumber() {
        return this.fileNumber;
    }
    
    public void setFileNumber(Long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getArithMethod() {
        return this.arithMethod;
    }
    
    public void setArithMethod(String arithMethod) {
        this.arithMethod = arithMethod;
    }

    /*public String getPid() {
        return this.pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }*/
    

    public Long getMainJar() {
        return this.mainJar;
    }
    
    public Arith getParentarith() {
		return parentarith;
	}


	public void setParentarith(Arith parentarith) {
		this.parentarith = parentarith;
	}


	public void setMainJar(Long mainJar) {
        this.mainJar = mainJar;
    }

    public String getFileLocation() {
        return this.fileLocation;
    }
    
    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }


	/**
	 * childrenArith
	 *
	 * @return  the childrenArith
	 * @since   CodingExample Ver 1.0
	 */
	
	public Set getChildrenArith() {
		return childrenArith;
	}


	/**
	 * childrenArith
	 *
	 * @param   childrenArith    the childrenArith to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setChildrenArith(Set childrenArith) {
		this.childrenArith = childrenArith;
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