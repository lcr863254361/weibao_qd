/**
 * TableRight.java
 * com.orient.sysmodel.domain.role
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2012-4-12 		zhang yan
 *
 * Copyright (c) 2012, TNT All Rights Reserved.
*/ 

package com.orient.sysmodel.domain.role;

import java.util.Map;

import com.orient.sysmodel.operationinterface.IColumnRight;
import com.orient.sysmodel.operationinterface.IMatrixRight;

/**
 * ClassName:TableRight
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zhang yan
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-4-12		上午11:34:15
 *
 * @see 	 
 */
public class MatrixRight extends AbstractRight implements IMatrixRight{

	//字段权限
	Map<String, IColumnRight> columnRights;
	
	public MatrixRight(){
		
	}

	/**
	 * columnRights
	 *
	 * @return  the columnRights
	 * @since   CodingExample Ver 1.0
	 */
	
	public Map<String, IColumnRight> getColumnRights() {
		return columnRights;
	}

	/**
	 * columnRights
	 *
	 * @param   columnRights    the columnRights to set
	 * @since   CodingExample Ver 1.0
	 */
	
	public void setColumnRights(Map<String, IColumnRight> columnRights) {
		this.columnRights = columnRights;
	}
	
	
}

