package com.orient.sysmodel.domain.role;

import java.util.ArrayList;
import java.util.List;

import com.orient.sysmodel.operationinterface.IOperation;
import com.orient.sysmodel.operationinterface.IPartOperations;

// default package



/**
 * PartOperations entity. @author MyEclipse Persistence Tools
 */
public class PartOperations extends AbstractPartOperations implements java.io.Serializable, IPartOperations {

    // Constructors

    /** default constructor */
    public PartOperations() {
    }

	/** minimal constructor */
    public PartOperations(String isTable) {
        super(isTable);        
    }
    
    /** full constructor */
    public PartOperations(Role role, String tableId, String columnId, String operationsId, String filter, String isTable) {
        super(role, tableId, columnId, operationsId, filter, isTable);        
    }

}
