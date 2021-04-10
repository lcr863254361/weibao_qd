package com.orient.sysmodel.domain.sys;
// default package


/**
 * AbstractSeqGenerator entity provides the base persistence definition of the SeqGenerator entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractSeqGenerator extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     private Long id;
     private String name;//脚本名称
     private String content;//脚本内容
     private Boolean enable;//脚本是否有效: 1或空为有效
     private String returnType;
     private String changed;//是否被修改(TRUE表示已经被修改,需要重新编译,FALSE反之)


    // Constructors

    /** default constructor */
    public AbstractSeqGenerator() {
    }

    
    /** full constructor */
    public AbstractSeqGenerator(String name, String content, Boolean enable, String returnType, String changed) {
        this.name = name;
        this.content = content;
        this.enable = enable;
        this.returnType = returnType;
        this.changed = changed;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getEnable() {
        return this.enable;
    }
    
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getReturnType() {
        return this.returnType;
    }
    
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getChanged() {
        return this.changed;
    }
    
    public void setChanged(String changed) {
        this.changed = changed;
    }
   








}