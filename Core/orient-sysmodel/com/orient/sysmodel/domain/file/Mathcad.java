package com.orient.sysmodel.domain.file;
// default package

import java.util.Date;


/**
 * MathcadId entity. @author MyEclipse Persistence Tools
 */
public class Mathcad extends AbstractMathcad implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public Mathcad() {
    }

    
    /** full constructor */
    public Mathcad(String id, String name, Date date, String url) {
        super(id, name, date, url);        
    }
   
}
