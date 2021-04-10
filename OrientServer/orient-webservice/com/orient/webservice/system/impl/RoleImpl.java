package com.orient.webservice.system.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.orient.sysmodel.domain.role.Role;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.webservice.system.IRole;

public class RoleImpl implements IRole {

	private IRoleUtil roleEngine;
	
	public IRoleUtil getRoleEngine() {
		return roleEngine;
	}

	public void setRoleEngine(IRoleUtil roleEngine) {
		this.roleEngine = roleEngine;
	}

	@Override
	public List<String> getRoleList() {
		Map<String,Role> roles = roleEngine.getRoleModel(false).getRoles();
		List<String> roleList = new ArrayList<String>();
		Iterator<Role> ite = roles.values().iterator();
		StringBuffer sb = new StringBuffer();
		while(ite.hasNext()){
			sb.setLength(0);
			Role role = ite.next();
			if(Integer.valueOf(role.getId()) < 1)
				continue;//去除系统管理员
			sb.append(role.getId() + ";::;");
			sb.append(role.getName() + ";::;");
			sb.append(role.getMemo());
			roleList.add(sb.toString());
		}
		return roleList;
	}

}
