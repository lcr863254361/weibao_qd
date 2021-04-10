package com.orient.sysmodel.domain.tbom;

import java.util.Comparator;

import com.orient.sysmodel.operationinterface.IDynamicTbom;

public class IDyanmicTbomComp implements Comparator<IDynamicTbom> {

	@Override
	public int compare(IDynamicTbom o1, IDynamicTbom o2) {
		Integer order1 = Integer.valueOf(o1.getOrder());
		Integer order2 = Integer.valueOf(o2.getOrder());
		if(order1>order2){
			return 1;
		}else{
			return -1;
		} 
	}

}
