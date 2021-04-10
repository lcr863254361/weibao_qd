package com.orient.sysmodel.domain.sys;
// default package



/**
 * PartitionRemarkId entity. @author MyEclipse Persistence Tools
 */
public class PartitionRemark extends AbstractPartitionRemark implements java.io.Serializable {

    // Constructors

    /** default constructor */
    public PartitionRemark() {
    }

    
    /** full constructor */
    public PartitionRemark(String id, String tableName, String gkId, String impTime, String jobId, String remark, String cols) {
        super(id, tableName, gkId, impTime, jobId, remark, cols);        
    }
   
}
