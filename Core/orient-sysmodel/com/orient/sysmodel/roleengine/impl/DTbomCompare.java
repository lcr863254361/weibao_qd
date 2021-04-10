package com.orient.sysmodel.roleengine.impl;

import java.util.Comparator;

import com.orient.sysmodel.domain.tbom.DynamicTbom;


public class DTbomCompare implements  Comparator<DynamicTbom>{

	public int compare(DynamicTbom o1, DynamicTbom o2) {
		String id1 = o1.getId();
		String id2 = o2.getId();	

		if(Integer.valueOf(id1)<Integer.valueOf(id2)){
			return -1;
		}
		return 1;
		
	}
}
