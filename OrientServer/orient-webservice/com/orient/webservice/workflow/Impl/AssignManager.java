package com.orient.webservice.workflow.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class AssignManager extends WorkFLowBean{
	
	/**
	 *@Function Name:  getAssignee
	 *@Description: @param type
	 *@Description: @return 得到分配任务的 用户 角色 部门等信息
	 *@Date Created:  2013-1-5 上午09:43:27
	 *@Author:  Pan Duan Duan
	 *@Last Modified:     ,  Date Modified: 
	 */
	public String getAssignee(int type)
	{
		//如果类型不正确 则返回
		if(type>2||type<0){
			return "";
		}
		//拼接字符串
		StringBuffer sb = new StringBuffer();
		if(type!=2){
			//查询SQL语句
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT dep.ID, dep.pid, dep.NAME, su.USER_NAME, su.ALL_NAME FROM (");
			sql.append("SELECT user_name, dep_id, all_name, ID FROM cwm_sys_user WHERE state = '1' AND TO_NUMBER (ID) > 0");
			sql.append(") su FULL JOIN cwm_sys_department dep ON su.dep_id =dep.ID ORDER BY dep.NAME ASC");
			//得到查询结果
			List<Map<String, Object>> depList = metadaofactory.getJdbcTemplate().queryForList(sql.toString());
			//如果查询结果不为空
			if(!depList.isEmpty()){
				//中间变量
				Map<String,List<String>> map = new HashMap<String,List<String>>();
				//遍历查询结果
				for(Map<String, Object> dep:depList){
					//得到相关值
					String id=dep.get("ID")!=null?(String)dep.get("ID"):null;
					String pid=dep.get("PID")!=null?(String)dep.get("PID"):null;
					String name=dep.get("NAME")!=null?(String)dep.get("NAME"):null;
					String user_name=dep.get("USER_NAME")!=null?(String)dep.get("USER_NAME"):null;
					String all_name=dep.get("ALL_NAME")!=null?(String)dep.get("ALL_NAME"):null;
					String key=null;
					if(id!=null){
						key=id+";,;;"+pid+";,;;"+name;
					}else{
						key ="未分组";
					}
					//放入中间变量
					if(!map.containsKey(key)){
						map.put(key, new ArrayList<String>());
					}
					if(user_name!=null && all_name !=null){
						map.get(key).add(user_name+";;;;"+all_name);
					}
				}
				//遍历中间变量
				for(Iterator<String> it = map.keySet().iterator();it.hasNext();){
					//得到KEY
					String key =it.next();
					//拼接
					sb.append(key).append("####");
					//
					if(!map.get(key).isEmpty()){
						int i = 0;
						for(String user:map.get(key)){
							if(i<map.get(key).size()-1){
								sb.append(user).append("!@#!");
							}else{
								sb.append(user);
							}
							i++;
						}
					}else{
						sb.append("nouser");
					} 
					sb.append("@@@@");  
				}
			}
		}if(type==0){
			sb.append(",,,,,");
		}
		if(type!=1){
			//得到所有的角色
			List<Map<String, Object>> roleList= metadaofactory.getJdbcTemplate().queryForList("SELECT ID, NAME FROM CWM_SYS_ROLE WHERE ID>0 ORDER BY NAME ASC");
			//遍历角色结果集
			if(!roleList.isEmpty()){
				for(Map<String, Object> ob:roleList){
					String id = String.valueOf(ob.get("ID"));
					String name=(String) ob.get("NAME");
					sb.append(id).append(";;;;").append(name).append("!@#!");
				}
			}
		}
		return sb.toString();
	}
}
