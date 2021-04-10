package com.orient.sysmodel.domain.etl;
// default package



/**
 * EtlScript entity. @author MyEclipse Persistence Tools
 */
public class EtlScript extends AbstractEtlScript implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public EtlScript() {
    }

    
    /** full constructor */
    public EtlScript(String scriptname, String filename, String filetype, String dataindex, String linesplit, String username, String errorsolve, String jobtype, String filepath, String filelength, String filelastmod, String jobtime, String srccolumn, String importType) {
        super(scriptname, filename, filetype, dataindex, linesplit, username, errorsolve, jobtype, filepath, filelength, filelastmod, jobtime, srccolumn, importType);        
    }
   
}
