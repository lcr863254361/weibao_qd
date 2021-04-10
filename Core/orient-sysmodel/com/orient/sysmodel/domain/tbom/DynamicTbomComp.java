package com.orient.sysmodel.domain.tbom;

import java.io.Serializable;
import java.util.Comparator;

public class DynamicTbomComp implements Comparator<DynamicTbom>,Serializable {

	@Override
	public int compare(DynamicTbom o1, DynamicTbom o2) { 
		Integer order1 = Integer.valueOf(o1.getOrder());
		Integer order2 = Integer.valueOf(o2.getOrder());
		if(order1>order2){
			return 1;
		}else{
			return -1;
		} 
	}

}
