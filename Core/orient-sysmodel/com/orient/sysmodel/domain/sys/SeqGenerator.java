package com.orient.sysmodel.domain.sys;
// default package


import java.io.Serializable;

/**
 * SeqGenerator entity. @author MyEclipse Persistence Tools
 */
public class SeqGenerator extends AbstractSeqGenerator implements Serializable {

    // Constructors

    /** default constructor */
    public SeqGenerator() {
    }

    
    /** full constructor */
    public SeqGenerator(String name, String content, Boolean enable, String returnType, String changed) {
        super(name, content, enable, returnType, changed);        
    }
   
}
