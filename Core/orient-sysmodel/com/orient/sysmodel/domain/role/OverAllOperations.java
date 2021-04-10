package com.orient.sysmodel.domain.role;

import java.util.ArrayList;
import java.util.List;

import com.orient.sysmodel.operationinterface.IOperation;
import com.orient.sysmodel.operationinterface.IOverAllOperations;



/**
 * OverAllOperationsId entity. @author MyEclipse Persistence Tools
 */
public class OverAllOperations extends AbstractOverAllOperations implements java.io.Serializable, IOverAllOperations {

    // Constructors

    /** default constructor */
    public OverAllOperations() {
    }

    
    /** full constructor */
    public OverAllOperations(String roleId, String operationIds) {
        super(roleId, operationIds);        
    }
   
    /**
     * 
    
     * @Method: getOperations 
    
     * 取得权限下的有效操作
    
     * @return 
    
     * @see com.orient.sysmodel.operationinterface.IOverAllOperations#getOperations()
     */
    public List<IOperation> getOperations(){
    	List<IOperation> operationList = new ArrayList<IOperation>();
    	if(this.getOperationIds()!=null && !this.getOperationIds().equalsIgnoreCase("")){
    		String[] operationId = this.getOperationIds().split(",");
    		
    		for(int i=0;i<operationId.length;i++){
    			operationList.add(this.getRoleDAOFactory().getOperationDAO().findById(Long.valueOf(operationId[i])));
        	}
    		
    	}
    	return operationList;
    }
}
