package com.orient.sqlengine.internal.sys.cmd;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.orient.sqlengine.cmd.api.EDMCommand;
import com.orient.sqlengine.cmd.api.EDMCommandService;

public class NumberUnitCmd implements EDMCommand<Object> {
	private static final Logger log = Logger.getLogger(NumberUnitCmd.class);
	//serialVersionUID is
	private static final long serialVersionUID = 1L;
	
	protected EDMCommandService commandService;
	
	private StringBuffer sql = new StringBuffer();
	
	public List<Map<String,Object>> listUnit(){
		sql.setLength(0);
		sql.append("SELECT * FROM CWM_SYS_NUMBERUNIT ORDER BY NAME,POSITION");
		List<Map<String,Object>> units = (List<Map<String,Object>>)commandService.execute(this);
		return units;
	}
	
	public Map<String,Object> queryById(String id){
		sql.setLength(0);
		Map<String,Object> ret = null;
		sql.append("SELECT * FROM CWM_SYS_NUMBERUNIT WHERE ID='"+id+"'");
		List<Map<String,Object>> units = (List<Map<String,Object>>)commandService.execute(this);
		if(!units.isEmpty())
			ret = units.get(0);
		return ret;
	}
	
	public List<Map<String,Object>> queryByName(String name){
		sql.setLength(0);
		sql.append("SELECT * FROM CWM_SYS_NUMBERUNIT WHERE NAME='"+name+"'");
		List<Map<String,Object>> theUnits = (List<Map<String,Object>>)commandService.execute(this);
		return theUnits;
	}

	@Override
	public Object execute(JdbcTemplate jdbc) throws Exception {
		try{
			return jdbc.queryForList(sql.toString());
		}catch(Exception e){
			Throwable t = e;
			while (t != null) {
				if (t instanceof SQLException) {
					SQLException sqlException = (SQLException) t;
					SQLException nextException = sqlException
							.getNextException();
					if (nextException != null) {
						log.error("cause of " + nextException + ": ", e);
					}
				}
				t = t.getCause();
			}
			throw e; 
		}  
		
	}
	
	public void setCommandService(EDMCommandService commandService) {
		this.commandService = commandService;
	} 

}
