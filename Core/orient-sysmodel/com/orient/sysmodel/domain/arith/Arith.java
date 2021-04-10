package com.orient.sysmodel.domain.arith;

import com.orient.sysmodel.operationinterface.IArith;



/**
 * ArithId entity. @author MyEclipse Persistence Tools
 */
public class Arith extends AbstractArith implements java.io.Serializable, IArith {

    // Constructors

    /** default constructor */
    public Arith() {
    }

    
    /** full constructor */
    public Arith(String id, String name, Long type, String category, String description, String fileName, String methodName, Long paraNumber, String paraType, String refLib, String dataType, Long isValid, Long arithType, Long leastNumber, String className, String classPackage, Long fileNumber, String arithMethod, Arith parentarith, Long mainJar, String fileLocation) {
        super(id, name, type, category, description, fileName, methodName, paraNumber, paraType, refLib, dataType, isValid, arithType, leastNumber, className, classPackage, fileNumber, arithMethod, parentarith, mainJar, fileLocation);        
    }
   
}
