/*
 * Title: AbstractRelationTbom.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;



/**
 * AbstractRelationTBOM entity provides the base persistence definition of the
 * RelationTBOM entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractRelationTbom extends com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {


    // Fields    

     /** The id. */
    private String id;
     
     /** The cwm tbom by node id. */
     private Tbom cwmTbomByNodeId;//TBOM表的节点ID，不唯一，此表用来记录该节点关联的其他节点的信息表
     
     /** The cwm tbom by relation id. */
     private Tbom cwmTbomByRelationId;//节点ID所关联的ID


    // Constructors

    /**
	 * default constructor.
	 */
    public AbstractRelationTbom() {
    }

    
    /**
	 * full constructor.
	 * 
	 * @param cwmTbomByNodeId
	 *            the cwm tbom by node id
	 * @param cwmTbomByRelationId
	 *            the cwm tbom by relation id
	 */
    public AbstractRelationTbom(Tbom cwmTbomByNodeId, Tbom cwmTbomByRelationId) {
        this.cwmTbomByNodeId = cwmTbomByNodeId;
        this.cwmTbomByRelationId = cwmTbomByRelationId;
    }

   
    // Property accessors

    /**
	 * Gets the id.
	 * 
	 * @return the id
	 */
    public String getId() {
        return this.id;
    }
    
    /**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
    public void setId(String id) {
        this.id = id;
    }

    /**
	 * Gets the cwm tbom by node id.
	 * 
	 * @return the cwm tbom by node id
	 */
    public Tbom getCwmTbomByNodeId() {
        return this.cwmTbomByNodeId;
    }
    
    /**
	 * Sets the cwm tbom by node id.
	 * 
	 * @param cwmTbomByNodeId
	 *            the new cwm tbom by node id
	 */
    public void setCwmTbomByNodeId(Tbom cwmTbomByNodeId) {
        this.cwmTbomByNodeId = cwmTbomByNodeId;
    }

    /**
	 * Gets the cwm tbom by relation id.
	 * 
	 * @return the cwm tbom by relation id
	 */
    public Tbom getCwmTbomByRelationId() {
        return this.cwmTbomByRelationId;
    }
    
    /**
	 * Sets the cwm tbom by relation id.
	 * 
	 * @param cwmTbomByRelationId
	 *            the new cwm tbom by relation id
	 */
    public void setCwmTbomByRelationId(Tbom cwmTbomByRelationId) {
        this.cwmTbomByRelationId = cwmTbomByRelationId;
    }
   








}