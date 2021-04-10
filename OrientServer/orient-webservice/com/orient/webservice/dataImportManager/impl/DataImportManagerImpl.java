package com.orient.webservice.dataImportManager.impl;

import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.utils.PasswordUtil;
import com.orient.webservice.dataImportManager.IDataImportManager;
import com.orient.webservice.schema.Impl.SchemaImpl;
import com.orient.webservice.schema.Impl.SchemaInfoImpl;


public class DataImportManagerImpl implements IDataImportManager {

	private SchemaImpl schemaBean;
	private SchemaInfoImpl schemaInfoBean;
	protected IRoleUtil roleEngine;
	
	public SchemaImpl getSchemaBean() {
		return schemaBean;
	}

	public void setSchemaBean(SchemaImpl schemaBean) {
		this.schemaBean = schemaBean;
	}

	public SchemaInfoImpl getSchemaInfoBean() {
		return schemaInfoBean;
	}

	public void setSchemaInfoBean(SchemaInfoImpl schemaInfoBean) {
		this.schemaInfoBean = schemaInfoBean;
	}

	public IRoleUtil getRoleEngine() {
		return roleEngine;
	}

	public void setRoleEngine(IRoleUtil roleEngine) {
		this.roleEngine = roleEngine;
	}

	@Override
	public String getSchemaXml(String name, String version) {
		return schemaBean.getSchema(name, version);
	}

	@Override
	public String getSchema() {
		return schemaInfoBean.getSchema();
	}

	@Override
	public String check(String name, String password, String type) {
		String returnMsg = "";
		password = password == null ? "" : PasswordUtil.generatePassword(password);
		User user =  (User)roleEngine.getRoleModel(false).getUserByUserName(name);
		if(user == null)
		{
			returnMsg = "0";
			return returnMsg;
		}
		String userPasWord = user.getPassword();
		if(!password.equals(userPasWord))
		{
			returnMsg = "1";
			return returnMsg;
		}
		returnMsg = type+"==="+user.getAllName();
		return returnMsg;
	}

}
