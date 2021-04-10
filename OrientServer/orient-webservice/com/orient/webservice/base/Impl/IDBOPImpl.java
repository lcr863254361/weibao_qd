package com.orient.webservice.base.Impl;

import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.operationinterface.IMetaModel;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.sysmodel.domain.user.User;
import com.orient.webservice.tools.WSCommonTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IDBOPImpl extends BaseBean {
	
	public String insert(String username, String schemaName, String version,String tableName,String arg) {
		String msg = "";
		try{
			/* 获取参数列表 */
			Map<String, String> argMap = WSCommonTools.str2Map(arg);
			/* 获取业务模型 */
			IMetaModel metaModel = businessModelService.getMetaEngine().getMeta(false);
			ISchema schema = metaModel.getISchema(schemaName, version);
			String schemaID = schema.getId();
			User user = userService.findByUserName(username);
			String userID = user.getId();
			ITable iTable = schema.getTableByDisplayName(tableName);			
			IBusinessModel model = businessModelService.getBusinessModelById(userID, iTable.getId(), schemaID,BusinessModelEnum.Table);
			/* 获取用户插入操作权限校验 */
			boolean right_to_insert = true;//model.getMatrixRight().isAdd();
			if (right_to_insert) {
				List<IBusinessColumn> columnList = model.getAllBcCols();	
				Map<String,String> dataMap = new HashMap<String,String>();
				for(Map.Entry<String, String> m:argMap.entrySet()){ 
					for(IBusinessColumn column:columnList){
						if(m.getKey().equals(column.getDisplay_name())){
							dataMap.put(column.getS_column_name(), m.getValue());
						}
					}
				} 
				String dataId = sqlEngine.getBmService().insertModelData(model, dataMap);
				msg = "新增成功-"+dataId;
			}
			else{
				msg = "[error]用户没有插入操作权限.";
			}
		}catch(ArrayIndexOutOfBoundsException e){
			msg = "[error]参数异常,请检查你的参数列表.";
		}catch(Exception e){
			msg = "[error]"+e.getMessage()+".";
		}
		return msg;
	}

	public String update(String username, String schemaName, String version,String tableName, String arg, String dataId) {
		String msg = "";
		try{
			/* 获取参数列表 */
			Map<String, String> argMap = WSCommonTools.str2Map(arg);
			/* 获取业务模型 */
			IMetaModel metaModel = businessModelService.getMetaEngine().getMeta(false);
			ISchema schema = metaModel.getISchema(schemaName, version);
			String schemaID = schema.getId();
			User user = userService.findByUserName(username);
			String userID = user.getId();
			ITable iTable = schema.getTableByDisplayName(tableName);			
			IBusinessModel model = businessModelService.getBusinessModelById(userID, iTable.getId(), schemaID,BusinessModelEnum.Table);
			/* 获取用户修改操作权限校验 */
			boolean right_to_modify = true;//model.getMatrixRight().isModify();
			if (right_to_modify) {
				List<IBusinessColumn> columnList = model.getAllBcCols();	
				Map<String,String> dataMap = new HashMap<String,String>();
				for(Map.Entry<String, String> m:argMap.entrySet()){ 
					for(IBusinessColumn column:columnList){
						if(m.getKey().equals(column.getDisplay_name())){
							dataMap.put(column.getS_column_name(), m.getValue());
						}
					}
				} 
				sqlEngine.getBmService().updateModelData(model, dataMap,dataId);
				msg = "修改成功";
			}
			else{
				msg = "[error]用户没有修改操作权限.";
			}
		}catch(ArrayIndexOutOfBoundsException e){
			msg = "[error]参数异常,请检查你的参数列表.";
		}catch(Exception e){
			msg = "[error]"+e.getMessage()+".";
		}
		return msg;
	}

	public String delete(String username, String schemaName, String version,
			String tableName, String dataId) {
		String msg = "";
		try{
			/* 获取业务模型 */
			IMetaModel metaModel = businessModelService.getMetaEngine().getMeta(false);
			ISchema schema = metaModel.getISchema(schemaName, version);
			String schemaID = schema.getId();
			User user = userService.findByUserName(username);
			String userID = user.getId();
			ITable iTable = schema.getTableByDisplayName(tableName);			
			IBusinessModel model = businessModelService.getBusinessModelById(userID, iTable.getId(), schemaID,BusinessModelEnum.Table);
			/* 获取用户修改删除权限校验 */
			boolean right_to_delete = true;//model.getMatrixRight().isDelete();
			if (right_to_delete) {
				sqlEngine.getBmService().delete(model, dataId);
				msg = "删除成功";
			}
			else{
				msg = "[error]用户没有删除操作权限.";
			}
		}catch(Exception e){
			msg = "[error]"+e.getMessage()+".";
		}
		return msg;
	}

	public String deleteCascade(String username, String schemaName,
			String version, String tableName, String dataId) {
		String msg = "";
		try{
			/* 获取业务模型 */
			IMetaModel metaModel = businessModelService.getMetaEngine().getMeta(false);
			ISchema schema = metaModel.getISchema(schemaName, version);
			String schemaID = schema.getId();
			User user = userService.findByUserName(username);
			String userID = user.getId();
			ITable iTable = schema.getTableByDisplayName(tableName);			
			IBusinessModel model = businessModelService.getBusinessModelById(userID, iTable.getId(), schemaID,BusinessModelEnum.Table);
			/* 获取用户修改删除权限校验 */
			boolean right_to_delete = true;//model.getMatrixRight().isDelete();
			if (right_to_delete) {
				sqlEngine.getBmService().deleteCascade(model, dataId);
				msg = "级联删除成功";
			}
			else{
				msg = "[error]用户没有删除操作权限.";
			}
		}catch(Exception e){
			msg = "[error]"+e.getMessage()+".";
		}
		return msg;
	}
}
