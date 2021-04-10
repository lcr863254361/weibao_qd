package com.orient.metamodel.metadomain;

/**
 * 算法信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 9, 2012
 */
public abstract class AbstractArith extends BaseMetaBean {

    /**
     * 主键Id
     */
    private String id;

    /**
     * 算法名称
     */
    private String name;

    /**
     * 算法类型，分数据库自带算法（0）和自定义算法（1）
     */
    private Long type;

    /**
     * 类别，算法所属类别
     */
    private String category;

    /**
     * 描述
     */
    private String description;

    /**
     * 文件名 自定义算法的文件名(可以多个)，以‘，’分割
     */
    private String fileName;

    /**
     * 自定义算法的方法名
     */
    private String methodName;

    /**
     * 算法的参数个数
     */
    private Long paraNumber;

    /**
     * 算法的参数类型，以‘，’分割
     */
    private String paraType;

    /**
     * 引用的lib包名
     */
    private String refLib;

    /**
     * 返回类型
     */
    private String dataType;

    /**
     * 是否启用
     */
    private Long isValid;

    /**
     * 算法所对应的函数是单行函数还是聚集函数，0表示单行函数，1表示聚集函数
     */
    private Long arithType;

    /**
     * 无限制参数的参数个数最小值，即最少有多少个参数！
     */
    private Long leastNumber;

    /**
     * 自定义算法所在类名
     */
    private String className;

    /**
     * 自定义算法所在类的包名
     */
    private String classPackage;

    /**
     * 自定义算法的文件数
     */
    private Long fileNumber;

    /**
     * 数据库内置算法公式
     */
    private String arithMethod;

    /**
     * 用于自定义算法的树形组织
     */
    private String pid;

    /**
     * 用于解析的主jar包标识
     */
    private Long mainJar;


    public AbstractArith() {
    }

    public AbstractArith(String name, Long type, String category, String description, String fileName, String methodName, Long paraNumber, String paraType, String refLib, String dataType, Long isValid, Long arithType, Long leastNumber, String className, String classPackage, Long fileNumber, String arithMethod, String pid, Long mainJar) {
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
        this.pid = pid;
        this.mainJar = mainJar;
    }

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

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Long getMainJar() {
        return this.mainJar;
    }

    public void setMainJar(Long mainJar) {
        this.mainJar = mainJar;
    }

}