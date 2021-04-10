package com.orient.sysmodel.domain.user;
// default package



/**
 * RcpLoginId entity. @author MyEclipse Persistence Tools
 */
public class RcpLoginId extends AbstractRcpLoginId implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public RcpLoginId() {
    }

    
    /** full constructor */
    public RcpLoginId(String username, String ip, String schemaName, String version, String tbomName, String designXml, String tbomXml, String schemaId) {
        super(username, ip, schemaName, version, tbomName, designXml, tbomXml, schemaId);        
    }
   
}
