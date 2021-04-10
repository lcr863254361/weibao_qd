package com.orient.webservice.system.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.orient.sqlengine.api.ISqlEngine;
import com.orient.utils.CommonTools;
import com.orient.webservice.system.IUnit;

public class UnitImpl implements IUnit {
	/**
	 * 提供单位数据字典信息的接口类
	 * pressure;::;压强;::;1(id)=Pa=0(isBase),2=KPa=1,3=Mpa=0
	 *@Function Name:  getUnit
	 *@Description: @return  
	 *@Date Created:  2015-11-27 下午02:14:16
	 *@Author:  changxk
	 *@Last Modified:     ,  Date Modified:
	 */
	@Override
	public List<String> getUnit() {
		List<String> ret = new ArrayList<String>();
		List<Map<String,Object>> unitList = orientSqlEngine.getSysModelService().queryNumberUnit();
		for(Map<String,Object> map : unitList){
			String name = CommonTools.Obj2String(map.get("NAME"));
			String showName = CommonTools.Obj2String(map.get("SHOW_NAME"));
			String id = CommonTools.Obj2String(map.get("ID"));
			String unit = CommonTools.Obj2String(map.get("UNIT"));
			String isBase = CommonTools.Obj2String(map.get("IS_BASE"));
			int index = isExist(ret, name);
			if(index >= 0){
				String str = ret.get(index);
				str += "," + id + "=" + unit + "=" + isBase;
				ret.remove(index);
				ret.add(index,str);
			}
			else{
				StringBuffer sb = new StringBuffer();
				sb.append(name + ";::;" + showName + ";::;");
				sb.append(id + "=" + unit + "=" + isBase);
				ret.add(sb.toString());
			}
		}
		return ret;
	}
	
	private int isExist(List<String> list, String str){
		int index = -1;
		for(int i=0;i<list.size();i++){
			String string = list.get(i);
			if(string.indexOf(str) >= 0){
				index = i;
				break;
			}				
		}
		return index;
	}
	
	public ISqlEngine getOrientSqlEngine() {
		return orientSqlEngine;
	}


	public void setOrientSqlEngine(ISqlEngine orientSqlEngine) {
		this.orientSqlEngine = orientSqlEngine;
	}


	private ISqlEngine orientSqlEngine;
}
