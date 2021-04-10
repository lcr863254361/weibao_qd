package com.orient.webservice.base.Impl;

import com.orient.web.base.dsbean.CommonResponse;
import com.orient.webservice.base.HelloWorld;
import com.orient.webservice.base.IDBOP;
import com.orient.webservice.base.LoginChecking;
import com.orient.dsrestful.domain.schemaBaseInfo.SchemaResponse;
import com.orient.dsrestful.domain.lock.LockResponse;

/**
 * @author wubing
 *
 */
public class BaseImpl extends BaseBean implements HelloWorld,LoginChecking,IDBOP {
	
	private HelloWorldImpl helloWorldBean;
	private IDBOPImpl dbopBean;
	private LoginCheckingImpl loginBean;
	
	public HelloWorldImpl getHelloWorldBean() {
		return helloWorldBean;
	}
	public void setHelloWorldBean(HelloWorldImpl helloWorldBean) {
		this.helloWorldBean = helloWorldBean;
	}
	public IDBOPImpl getDbopBean() {
		return dbopBean;
	}
	public void setDbopBean(IDBOPImpl dbopBean) {
		this.dbopBean = dbopBean;
	}
	public LoginCheckingImpl getLoginBean() {
		return loginBean;
	}
	public void setLoginBean(LoginCheckingImpl loginBean) {
		this.loginBean = loginBean;
	}
	@Override
	public String sayHelloWorld(String name) {
		return helloWorldBean.sayHelloWorld(name);
	}
	@Override
	public String insert(String username, String schemaName, String version,
			String tableName, String arg) {
		return dbopBean.insert(username, schemaName, version, tableName, arg);
	}
	@Override
	public String update(String username, String schemaName, String version,
			String tableName, String arg, String dataId) {
		return dbopBean.update(username, schemaName, version, tableName, arg, dataId);
	}
	@Override
	public String delete(String username, String schemaName, String version,
			String tableName, String dataId) {
		return dbopBean.delete(username, schemaName, version, tableName, dataId);
	}
	@Override
	public String deleteCascade(String username, String schemaName,
			String version, String tableName, String dataId) {
		return dbopBean.deleteCascade(username, schemaName, version, tableName, dataId);
	}
	/*@Override
	public String check(String name, String password, String type) {
		return loginBean.check(name, password, type);		
	}*/


	@Override
	public CommonResponse loginCheck(String username, String ip, String password, String clientType) {
		return loginBean.loginCheck(username,ip,password,clientType);
	}

	@Override
	public SchemaResponse loginCheckAndGetSchema(String username, String ip, String password, String clientType,Integer isGetAll) {
		return loginBean.loginCheckAndGetSchema(username, ip, password, clientType, isGetAll);
	}

	@Override
	public LockResponse loginCheckAndLockSchema(String username, String ip, String password, String clientType,String schemaName,String version) {
		return loginBean.loginCheckAndLockSchema(username, ip, password, clientType,schemaName,version);
	}

}
