package com.orient.metamodel.metadomain;

/**
 * 算法信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 9, 2012
 */
public class Arith extends AbstractArith  {

    public Arith() {
    }

    public Arith(String name, Long type, String category, String description, String fileName, String methodName, Long paraNumber, String paraType, String refLib, String dataType, Long isValid, Long arithType, Long leastNumber, String className, String classPackage, Long fileNumber, String arithMethod, String pid, Long mainJar) {
        super(name, type, category, description, fileName, methodName, paraNumber, paraType, refLib, dataType, isValid, arithType, leastNumber, className, classPackage, fileNumber, arithMethod, pid, mainJar);
    }

}
