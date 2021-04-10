package com.orient.sqlengine.internal;

import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.BusinessColumnEnum;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.metamodel.operationinterface.IRelationColumn;
import net.java.dev.eval.Expression;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.*;

public class SqlEngineHelper {
	
	/** 
	 * 把业务字段根据类型转成字符串输出的sql
	 *
	 * @param col
	 * @return  String
	 */
	public static String columnConvertSelectSql(IBusinessColumn col) {
		StringBuilder colSql = new StringBuilder();
		BusinessColumnEnum colEnum = (BusinessColumnEnum)col.getColType();
	    switch(colEnum){
	       case C_Relation: 
	    	   if(4==col.getCol().getRelationColumnIF().getRelationType()){
	    		   colSql.append("CWM_RELATION_CONVERT(");
				   colSql.append("'").append(col.getParentModel().getS_table_name()).append("', ");
				   colSql.append(col.getParentModel().getS_table_name()).append(".id, ");
				   colSql.append("'").append(col.getCol().getRelationColumnIF().getRefTable().getTableName()).append("'");
				   colSql.append(") ").append(col.getS_column_name());
	    	    }else{ 
	    	    	/*除多对多外的关系属性*/
	    	    	IRelationColumn relColumn = col.getCol().getRelationColumnIF();
	    	    	colSql.append(relColumn.getRefTable().getTableName() + "_ID");
	    	    }
				break;
	       case C_Date: 
				colSql.append("TO_CHAR(").append(col.getCol().getColumnName()).append(",'YYYY-MM-DD') ");
				colSql.append(col.getCol().getColumnName());
				break;
	       case C_DateTime: 
				colSql.append("TO_CHAR(").append(col.getCol().getColumnName()).append(",'YYYY-MM-DD HH24:MI:SS') ");
				colSql.append(col.getCol().getColumnName());
				break;
	       case C_Boolean:
			   colSql.append("TO_CHAR(").append(col.getCol().getColumnName()).append(") ");
			   colSql.append(col.getCol().getColumnName());
			   break;
	       case C_BigInteger: 
	    	   colSql.append("TO_CHAR(").append(col.getCol().getColumnName()).append(") ");
			    colSql.append(col.getCol().getColumnName());
			    break;
	       case C_Decimal: 
	    	   colSql.append("TO_CHAR(").append(col.getCol().getColumnName()).append(") ");
			    colSql.append(col.getCol().getColumnName());
			    break;
	       case C_Double: 
	    	   colSql.append("TO_CHAR(").append(col.getCol().getColumnName()).append(") ");
			    colSql.append(col.getCol().getColumnName());
			    break;
	       case C_Float: 
	    	   colSql.append("TO_CHAR(").append(col.getCol().getColumnName()).append(") ");
			    colSql.append(col.getCol().getColumnName());
			    break;
	       case C_Integer: 
	    	   colSql.append("TO_CHAR(").append(col.getCol().getColumnName()).append(") ");
			    colSql.append(col.getCol().getColumnName());
			    break;
		   default: 
			    colSql.append(col.getCol().getColumnName());	    	    
	    }
	    return colSql.toString();
		
  }
	/** 
	 * 根据业务字段类型，返回数据插入或更新的表达式
	 *
	 * @param col
	 * @return String
	 */
	public static String columnConvertInsUpSql(IBusinessColumn col){
		StringBuilder colSql = new StringBuilder();
		BusinessColumnEnum colEnum = (BusinessColumnEnum)col.getColType();
	    switch(colEnum){
	       case C_Relation: 
	    	   if(4!=col.getCol().getRelationColumnIF().getRelationType()){
	    		   colSql.append("?");
	    	   }
	    	   break;
	       case C_Date: 
				colSql.append("TO_DATE(?,'YYYY-MM-DD') "); 
				break;
	       case C_DateTime: 
				colSql.append("TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') "); 
				break;
		   default:
			    colSql.append("?");	    	    
	    }
	    return colSql.toString();
	}
	/** 
	 * 获取自增值
	 * @param seqName 自增脚本的函数名
	 * @param seqContent 自增脚本的内容
	 * @param seqVal  自增脚本的输入参数
	 * @return 
	 */
	public static String getAutoIncValue(String seqName,
			String seqContent, int seqVal) {
		String sequence_result = "";
		if (isNullString(seqName)) {
			sequence_result = String.valueOf(seqVal);
		} else {
			sequence_result = AutoIncScriptTool.invoke(seqName, seqContent,
					seqVal);
		}
		return sequence_result;
	}

	/** 
	 * 判断字符串是否是空串
	 *
	 * @param str
	 * @return   boolean
	 */
	public static boolean isNullString(String str) {
		if (str == null || str.trim().equals("")
				|| str.trim().equalsIgnoreCase("NULL"))
			return true;
		else
			return false;
	}

	/** 
	 * 把对象转成字符串，Null转成""
	 *
	 * @param obj
	 * @return  String
	 */
	public static String Obj2String(Object obj) {
		return obj == null ? "" : obj.toString();
	}
	
	/** 
	 * 把String类型的List转成字符串数组
	 *
	 * @param list
	 * @return  String[]
	 */
	public static String[] list2stringArray(List<String> list){
		String[] result = new String[list.size()];
		for(int i = 0; i < list.size(); i++){
			result[i] = list.get(i);
		}
		return result;
	}
	
	/** 
	 * 把String类型的List转成倒序字符串数组
	 *
	 * @param list
	 * @return  String[]
	 */
	public static String[] list2StringArrayDesc(List<String> list){
		String[] result = new String[list.size()];
		for(int i = list.size()-1; i>=0; i--){
			result[list.size()-1-i] = list.get(i);
		}
		return result;
	}
	
	/** 
	 * 调用JS脚本
	 * @param expression
	 *     格式:"(new RegExp('\\\\bdd\\\\b').test(['asd','asda'].join(' ')))";
	 * @param value：JS所用到的参数
	 * @return 
	 */
	public static Object scriptExpression(String expression, Map<String, ?> value) {
		Object result = null;
		// 创建脚本引擎管理器
		ScriptEngineManager sem = new ScriptEngineManager();
		// 创建一个处理JavaScript的脚本引擎
		ScriptEngine engine = sem.getEngineByExtension("js");
		try {
			// 遍历传过来Map值 并把它传到脚本引擎中
			Set<String> keySet = value.keySet();
			Iterator<String> keys = keySet.iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				engine.put(key, value.get(key));
			}
			// 执行javaScript表达式
			System.out.println("expression>>>>>"+expression);
			result = engine.eval(expression);
		} catch (ScriptException ex) {
			ex.printStackTrace();
		} 
		return result;
	}
	
	/**
	 * 带单位数值类型字段的计算
	 *@Function Name:  unitCalculateIn
	 *@Description: @param value
	 *@Description: @param formulaIn
	 *@Description: @return 
	 *@Date Created:  2015-11-30 上午09:57:53
	 *@Author:  changxk
	 *@Last Modified:     ,  Date Modified:
	 */
	public static String unitCalculate(String value, String unit ,String formula){
		if("".equals(formula)){
			return value;
		}
		else if (isNullString(value)){
			return "";
		}
		else{
			Map<String,BigDecimal> map = new HashMap<String, BigDecimal>();
			map.put(unit,BigDecimal.valueOf(Double.valueOf(value)));
			BigDecimal result = Expression.eval(formula, map);
			return result.toString();
		}
	}
	
}
