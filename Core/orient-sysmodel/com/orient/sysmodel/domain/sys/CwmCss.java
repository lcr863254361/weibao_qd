package com.orient.sysmodel.domain.sys;
// default package

import java.util.Date;


/**
 * CwmCssId entity. @author MyEclipse Persistence Tools
 */
public class CwmCss extends AbstractCwmCss implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public CwmCss() {
    }

	/** minimal constructor */
    public CwmCss(String id, String filename, String fileurl, String sybs, String scr) {
        super(id, filename, fileurl, sybs, scr);        
    }
    
    /** full constructor */
    public CwmCss(String id, String filename, String fileurl, String sybs, String scr, String syr, Date expTime, String remarks) {
        super(id, filename, fileurl, sybs, scr, syr, expTime, remarks);        
    }
   
}
