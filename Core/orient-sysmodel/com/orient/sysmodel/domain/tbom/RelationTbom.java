/*
 * Title: RelationTbom.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:56:59 AM
 * Version: 4.0
 */
package com.orient.sysmodel.domain.tbom;



/**
 * RelationTbom entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class RelationTbom extends AbstractRelationTbom implements java.io.Serializable {

    // Constructors

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/**
	 * default constructor.
	 */
    public RelationTbom() {
    }

    
    /**
	 * full constructor.
	 * 
	 * @param cwmTbomByNodeId
	 *            the cwm tbom by node id
	 * @param cwmTbomByRelationId
	 *            the cwm tbom by relation id
	 */
    public RelationTbom(Tbom cwmTbomByNodeId, Tbom cwmTbomByRelationId) {
        super(cwmTbomByNodeId, cwmTbomByRelationId);        
    }
   
}
