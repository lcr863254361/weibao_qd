package com.orient.edm.init;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.ibatis.common.jdbc.ScriptRunner;
import com.ibatis.common.resources.Resources;
import com.orient.edm.util.DynamicLoadBean;

import com.orient.utils.PathTools;


public class DBInitBusiness {
	
	private DynamicLoadBean dynamicLoadBean;
	private WebApplicationContext wac;
	private String url;
	private String syspassword;
	private String username;
	private String password;
	private String tablespaceName;
	private String tablespaceDataFile;
	private String tablespaceSize;
	private String backPath;

	private String message = "数据库初始化成功";
	
	
	/**
	 * 初始化数据库
	 * @return
	 * @throws Exception
	 */
	public String doDBInit() throws Exception
	{
		//wac = ContextLoader.getCurrentWebApplicationContext();
		//根据jdbc.propertyzho中的配置信息创建表空间、用户、分配权限
		if(createUserAndSpace())
		{
			//初始化系统表及数据
			if(initSysTable())
			{
				//创建表空间
				if(initProcedure() && initData() && saveJDBCProperty())
				{
					//动态加载bean
					dynamicLoadBean.setApplicationContext(wac);
					dynamicLoadBean.loadBean("classpath:springdao/*.xml"); 
					
					//使用propertyConfigurer的Bean需要重置property
					BasicDataSource dataSource = (BasicDataSource)wac.getBean("dataSource");
					dataSource.setUsername(username);
					dataSource.setPassword(password);
					dataSource.setUrl(url);
					dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");

					InitLoadStart start = (InitLoadStart) wac.getBean("initContextLoad");
					start.loadModules(wac);
					
//					SchemaIO schemaIO = (SchemaIO)wac.getBean("schemaio");
//					Properties hibernateProperties = schemaIO.getHibernateProperties();
//					hibernateProperties.setProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
//					hibernateProperties.setProperty("hibernate.connection.url", url);
//					hibernateProperties.setProperty("hibernate.connection.username", username);
//					hibernateProperties.setProperty("hibernate.connection.password", password);
//					hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle9Dialect");
//					hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "true");
//
					//启动元数据模型缓存
//					MetaUtilImpl metaEngine = (MetaUtilImpl)wac.getBean("MetaEngine");
//					metaEngine.getMeta(true);
					
					//启动用户角色缓存
//					IRoleUtil roleEngine = (IRoleUtil)wac.getBean("RoleEngine");
//					roleEngine.getRoleModel(true);				
		
					//启动服务
					OrientContextLoaderListener.createIMSLLicense((ConfigurableWebApplicationContext) wac);
				}				
			}
		}
		
		return message;
		
	}

	/**
	 * 创建表空间、用户、授权
	 * @return
	 * @throws Exception
	 */
	public boolean createUserAndSpace() throws Exception
	{
	    String driver = "oracle.jdbc.driver.OracleDriver";
	    
    	Class.forName(driver).newInstance();
    	
    	Properties prop = new Properties();
		prop.put("user", "sys");
		prop.put("password", syspassword);
		prop.put("internal_logon", "sysdba");
    	
    	Connection conn = null;
    	Statement smt = null;
	    try {
	        conn = DriverManager.getConnection(url, prop);
	        smt = conn.createStatement();
	    } catch (Exception e) {
	    	message = "数据库第一次连接失败，请检查输入项是否正确";
	    	return false;
		}
    
	    
        //modify zhy 2012-1-14 自动创建数据库时, 表空间存在, 则继续
        try {
	    	StringBuffer ts1 = new StringBuffer();
	    	ts1.append("select   tablespace_name from   dba_tablespaces   where   tablespace_name= '").append(tablespaceName.toUpperCase())
	    		.append("'");
	    	
	    	ResultSet rs = smt.executeQuery(ts1.toString());
	    	if(!rs.next()){
	    		try {
	    	    	StringBuffer ts = new StringBuffer();
	    	    	ts.append("create tablespace ").append(tablespaceName)
	    	    		.append(" datafile '").append(tablespaceDataFile)
	    	    		.append("' size ").append(tablespaceSize)
	    	    		.append(" autoextend on next 50 maxsize unlimited");
	    	    	smt.execute(ts.toString());
	    	    } catch (Exception e) {
	    	    	smt.close();
	    	    	conn.rollback();
	    	    	conn.close();
	    	    	message = "表空间建立失败：请检查表空间是否已存在或者当前用户身份是否为数据库管理员";
	    	    	return false;
	    		}
	    	}
	    }catch (Exception e) {
	    	smt.close();
	    	conn.rollback();
	    	conn.close();
	    	message = "表空间建立失败：请检查表空间是否已存在或者当前用户身份是否为数据库管理员";
	    	return false;
		}
        //end

	    //modify zhy 2012-1-14 自动创建数据库时, 表空间存在, 则继续
	    String PARTITION_tablespaceName = "PARTITION_TABLESPACE";
        String PARTITION_tablespaceDatafile = "PARTITION_TABLESPACE.dbf";
        String PARTITION_tablespaceSize = "10M";
	    try {
	    	StringBuffer ts1 = new StringBuffer();
	    	ts1.append("select   tablespace_name from   dba_tablespaces   where   tablespace_name= '").append(PARTITION_tablespaceName.toUpperCase())
	    		.append("'");

	    	ResultSet rs = smt.executeQuery(ts1.toString());
	    	if(!rs.next()){
	    		try {
	    	    	StringBuffer ts = new StringBuffer();
	    	    	ts.append("create tablespace ").append(PARTITION_tablespaceName)
	    	    		.append(" datafile '").append(PARTITION_tablespaceDatafile)
	    	    		.append("' size ").append(PARTITION_tablespaceSize)
	    	    		.append(" autoextend on next 50 maxsize unlimited");
	    	    	smt.execute(ts.toString());
	    	    } catch (Exception e) {
	    	    	smt.close();
	    	    	conn.rollback();
	    	    	conn.close();
	    	    	message = "动态分区表空间建立失败：请检查表空间是否已存在或者当前用户身份是否为数据库管理员";
	    	    	return false;
	    		}
	    	}
	    } catch (Exception e) {
	    	smt.close();
	    	conn.rollback();
	    	conn.close();
	    	message = "动态分区表空间建立失败：请检查表空间是否已存在或者当前用户身份是否为数据库管理员";
	    	return false;
		}
	    //end

	    //根据jdbc.property中的配置信息 创建用户
	    try {
	    	StringBuffer user = new StringBuffer();
	    	user.append("create user ").append(username)
	    		.append(" identified by ").append(password)
	    		.append(" default tablespace ").append(tablespaceName)
	    		.append(" temporary tablespace temp");
	    	smt.execute(user.toString());
	    } catch (Exception e) {
	    	smt.close();
	    	conn.rollback();
	    	conn.close();
	    	message = "系统新用户建立失败：请检查用户"+username+"是否已存在";
	    	return false;
		}
	   
	    //为新创建的用户分配权限
	    try {
	    	StringBuffer user = new StringBuffer();
	    	user.append("grant connect,resource,dba to ").append(username);
	    	smt.execute(user.toString());
	    } catch (Exception e) {
	    	smt.close();
	    	conn.rollback();
	    	conn.close();
	    	message = "系统用户授权失败";
	    	return false;
		}
	    smt.close();
    	conn.close();
    	
    	return true;
	}

	/**
	 * 初始化系统表及数据
	 * @return
	 * @throws Exception
	 */
	public boolean initSysTable() throws Exception
	{
		Connection conn = null;			
		ScriptRunner runner = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			runner = new ScriptRunner(conn, false, false);
			runner.setErrorLogWriter(null);
			runner.setLogWriter(null);
		} catch (Exception e) {
			message = "数据库第二次连接失败：不能用系统新用户建立连接";
			return false;
		}

		//初始化systable
		try {
			InputStreamReader reader = new InputStreamReader(Resources.getResourceAsStream("/DBInitSql/afterTidy/table/Orient_COMMON.sql"),"UTF-8");
			runner.runScript(reader);
		} catch (Exception e) {
			conn.rollback();
			conn.close();
			message = "系统表建立失败：请检查Orient_COMMON.sql文件正确性";
			return false;
		}
		
		try {
			InputStreamReader reader = new InputStreamReader(Resources.getResourceAsStream("/DBInitSql/afterTidy/table/Oirent_SYSTEM.sql"),"UTF-8");
			runner.runScript(reader);
		} catch (Exception e) {
			conn.rollback();
			conn.close();
			message = "系统表建立失败：请检查Oirent_SYSTEM.sql文件正确性";
			return false;
		}
		
		
		try {
			InputStreamReader reader = new InputStreamReader(Resources.getResourceAsStream("/DBInitSql/afterTidy/table/Orient_MetaData.sql"),"UTF-8");
			runner.runScript(reader);
		
		} catch (Exception e) {
			conn.rollback();
			conn.close();
			message = "系统表建立失败：请检查Orient_MetaData.sql文件正确性";
			return false;
		}
		
		
		try {
			InputStreamReader reader = new InputStreamReader(Resources.getResourceAsStream("/DBInitSql/afterTidy/table/Orient_ETL.sql"),"UTF-8");
			runner.runScript(reader);
			
		} catch (Exception e) {
			conn.rollback();
			conn.close();
			message = "系统表建立失败：请检查Orient_ETL.sql文件正确性";
			return false;
		}
		
		try {
			InputStreamReader reader = new InputStreamReader(Resources.getResourceAsStream("/DBInitSql/afterTidy/table/Orient_TBOM.sql"),"UTF-8");
			runner.runScript(reader);
	
		} catch (Exception e) {
			conn.rollback();
			conn.close();
			message = "系统表建立失败：请检查Orient_TBOM.sql文件正确性";
			return false;
		}
		
		
		try {
			InputStreamReader reader = new InputStreamReader(Resources.getResourceAsStream("/DBInitSql/afterTidy/table/Orient_WORKFLOW.sql"),"UTF-8");
			runner.runScript(reader);
		
		} catch (Exception e) {
			conn.rollback();
			conn.close();
			message = "系统表建立失败：请检查Orient_WORKFLOW.sql文件正确性";
			return false;
		}

		//初始化序列
		try {
			InputStreamReader reader = new InputStreamReader(Resources.getResourceAsStream("/DBInitSql/afterTidy/sequence/Orient_SEQUENCE.sql"),"UTF-8");
			runner.runScript(reader);
			
		} catch (Exception e) {
			conn.rollback();
			conn.close();
			message = "系统表建立失败：请检查Orient_SEQUENCE.sql文件正确性";
			return false;
		}
		
		//初始化数据
		try {
			InputStreamReader reader = new InputStreamReader(Resources.getResourceAsStream("/DBInitSql/afterTidy/data/Orient_InitData.sql"),"UTF-8");
			runner.runScript(reader);
		} catch (Exception e) {
			conn.rollback();
			conn.close();
			message = "数据导入失败：请检查Orient_InitData.sql文件正确性";
			return false;
		}
		
		conn.close();
		
		return true;
		
	}

	public  boolean initProcedure() throws Exception
	{
		Connection conn = null;
		Statement smt = null;

		ScriptRunner runner = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			smt = conn.createStatement();
			runner = new ScriptRunner(conn, false, false);
			runner.setErrorLogWriter(null);
			runner.setLogWriter(null);
			if(!runScript("/DBInitSql/afterTidy/procedure/Orient_Produce_Sys.sql",smt,conn))
			{
				message = "存储过程导入失败：请检查Orient_Produce_Sys.sql文件正确性";
				return false;
			}

			if(!runScript("/DBInitSql/afterTidy/procedure/Orient_Produce_workflow.sql",smt,conn))
			{
				message = "存储过程导入失败：请检查Orient_Produce_workflow.sql文件正确性";
				return false;
			}
		} catch (Exception e) {
			message = "数据库第二次连接失败：不能用系统新用户建立连接";
			return false;
		}


		return true;
	}

	/**
	 * 初始化数据
	 * @return
	 * @throws Exception
	 */
	public boolean initData() throws Exception
	{
		Connection conn = null;
		ScriptRunner runner = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
			runner = new ScriptRunner(conn, false, false);
			runner.setErrorLogWriter(null);
			runner.setLogWriter(null);
		} catch (Exception e) {
			message = "数据库第二次连接失败：不能用系统新用户建立连接";
			return false;
		}

		//初始化数据
		try {
			InputStreamReader reader = new InputStreamReader(Resources.getResourceAsStream("/DBInitSql/afterTidy/data/Orient_InitData.sql"),"UTF-8");
			runner.runScript(reader);
		} catch (Exception e) {
			conn.rollback();
			conn.close();
			message = "数据导入失败：请检查Orient_InitData.sql文件正确性";
			return false;
		}

		conn.close();

		return true;

	}
	
	private boolean  saveJDBCProperty() throws Exception
	{
		FileOutputStream out = null;
		try {
			//回写 jdbc.property url属性
			Properties properties = new Properties();
			Resource rJdbc = wac.getResource("/WEB-INF/classes/jdbc.properties");
			FileInputStream in = new FileInputStream(rJdbc.getFile());
			properties.load(in);
			//properties.setProperty("jdbc.url",url);
			properties.setProperty("sysTables.type","1");
			out = new FileOutputStream(rJdbc.getFile());
			properties.store(out,"");
			out.close();
		} catch (Exception e) {
			out.close();
			message = "jdbc.properties文件写回失败";
			return false;
		}
		return true;
	}

	
	private boolean runScript(String Path,Statement smt,Connection conn) throws Exception
	{
		try {
			BufferedReader reader = new BufferedReader(Resources.getResourceAsReader(Path));
			String tempStr = null;
			String executeSql = "";
			while((tempStr = reader.readLine()) != null){
				if(tempStr.startsWith("--***") && !executeSql.equals("")){
					smt.execute(executeSql);
					executeSql = "";
				}
				executeSql += tempStr + "\n";
			}
			smt.execute(executeSql);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			conn.close();
	    	message = "运行 【"+Path+"】 错误！";
	    	return false;
		}
		return true;
	}

	/**
	 *
	 * 恢复脚本回写
	 *
	 * 新建用户信息回写恢复脚本，用于系统备份恢复
	 * @return
	 * @see
	 * @exception
	 * @since
	 */
	public boolean writeRestoreSql()
	{
		BufferedReader br = null;
		BufferedWriter bw = null;
		List<String> restoreSqls = new ArrayList<String>();

		try {
			String filePath = PathTools.getRootPath() + File.separator + "back" + File.separator + "restore.sql";
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));

			String lineStr = null;
			while((lineStr = br.readLine())!=null)
			{
				lineStr.replaceAll("<username>", username);
				lineStr = lineStr.replaceAll("<username>", username);
				lineStr = lineStr.replaceAll("<password>", password);
				lineStr = lineStr.replaceAll("<tablespace>", tablespaceName);

				String[] urlSplit = url.split(":");
				lineStr = lineStr.replaceAll("<net>", urlSplit[url.length()-1]);

				restoreSqls.add(lineStr);
			}

			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath))));
			for(String sql : restoreSqls)
			{
				bw.write(sql);
				bw.newLine();
			}

		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			message = "restore.sql文件回写失败";
			return false;

		}  finally {

			if(br!=null) {

				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if(bw!=null) {

				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return true;


	}

	
	public WebApplicationContext getWac() {
		return wac;
	}

	public void setWac(WebApplicationContext wac) {
		this.wac = wac;
	}

	public DynamicLoadBean getDynamicLoadBean() {
		return dynamicLoadBean;
	}

	public void setDynamicLoadBean(DynamicLoadBean dynamicLoadBean) {
		this.dynamicLoadBean = dynamicLoadBean;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSyspassword() {
		return syspassword;
	}

	public void setSyspassword(String syspassword) {
		this.syspassword = syspassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTablespaceName() {
		return tablespaceName;
	}

	public void setTablespaceName(String tablespaceName) {
		this.tablespaceName = tablespaceName;
	}

	public String getTablespaceDataFile() {
		return tablespaceDataFile;
	}

	public void setTablespaceDataFile(String tablespaceDataFile) {
		this.tablespaceDataFile = tablespaceDataFile;
	}

	public String getTablespaceSize() {
		return tablespaceSize;
	}

	public void setTablespaceSize(String tablespaceSize) {
		this.tablespaceSize = tablespaceSize;
	}


	/**
	 * @return the backPath
	 */
	public String getBackPath() {
		return backPath;
	}

	/**
	 * @param backPath the backPath to set
	 */
	public void setBackPath(String backPath) {
		this.backPath = backPath;
	}

	
}
