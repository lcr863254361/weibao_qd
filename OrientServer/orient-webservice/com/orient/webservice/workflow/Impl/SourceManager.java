package com.orient.webservice.workflow.Impl;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.View;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.webservice.tbom.bean.TbomRelationColumns;
import com.orient.webservice.tbom.bean.TbomSchema;
import com.orient.webservice.tbom.bean.TbomSource;
import com.orient.webservice.tbom.bean.TbomTable;
import com.orient.webservice.tbom.bean.TbomView;

public class SourceManager extends WorkFLowBean {
	
	//中间变量
	final Map<String, TbomTable> tableMap = new HashMap<String, TbomTable>();
	
	/**
	 * @return 执行
	 */
	public String getSource()
	{
		//返回结果
		String value = null;
		//
		TbomSource source = new TbomSource();
		//得到所有的schema对象集合
		Map<String, Schema> map = metaEngine.getMeta(false).getSchemas();
		//初始化模型对象集合
		final List<TbomSchema> schemaList = new ArrayList<TbomSchema>();
		//遍历schema对象集合
		for (Map.Entry<String, Schema> entry : map.entrySet()) {
			//得到schema对象
			Schema metaSchema = entry.getValue();
			//如果schema已经删除 或者 ID为根schema时 跳过
			if (metaSchema.getIsdelete() != null && metaSchema.getIsdelete() != 1) {
				continue;
			}
			//初始化schema对象
			TbomSchema schema = new TbomSchema();
			//赋入基本属性
			schema.setId("schema" + metaSchema.getId());
			schema.setName(metaSchema.getName());
			schema.setVersion(metaSchema.getVersion());
			
			//初始化所属该schema的基本模型集合
			final Set<TbomTable> tableSet = new HashSet<TbomTable>();
			//得到属于该schema的所有基本模型
			table(schema, metaSchema, tableSet);
			//初始化所属该schema的动态模型集合
			final Set<TbomTable> dynamictableSet = new HashSet<TbomTable>();
			//动态数据类的添加
			dynamicTable(schema, metaSchema, dynamictableSet);
			//
			schema.setDynamicTables(dynamictableSet);
			//初始化模型的关系属性
			relationColumnConnect(metaSchema);
			//将模型集合装配至Schema
			schema.setTables(tableSet);
			
			//初始化所属该schema的视图集合
			final Set<TbomView> viewList = new HashSet<TbomView>();
			//得到属于该schema的所有视图
			view(schema, metaSchema, viewList);
			//将视图集合装配至Schema
			schema.setViews(viewList);
			//添加至schema集合
			schemaList.add(schema);
			//清空中间变量
			tableMap.clear();
		}
		//根据XML描述 将对象转化为XML格式的字符串
		value = marshalSchemas(source, schemaList);
		return value;
	}
	


	/**
	 *@Function Name:  marshalSchemas
	 *@Description:  根据XML描述 将对象转化为XML格式的字符串
	 *@Date Created:  2013-1-4 上午10:26:42
	 *@Author:  Pan Duan Duan
	 *@Last Modified:     ,  Date Modified: 
	 */
	private String marshalSchemas(TbomSource source,List<TbomSchema> schemaList) {
		//返回结果
		String result = "";
		//转给schema集合
		source.setSource(schemaList);
		//输入
		Writer write = null;
		try {
			Mapping tbommap = new Mapping();
			String url = SourceManager.class.getResource("SourceManager.class").toString().replace("SourceManager.class", "map_tbom.xml");
			tbommap.loadMapping(url);
			write = new StringWriter();
			Marshaller marshaller = new Marshaller(write);
			marshaller.setEncoding("GBK");
			marshaller.setMapping(tbommap);
			marshaller.marshal(source);
			result = write.toString();
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
			return null;
		} catch (MarshalException ex) {
			ex.printStackTrace(System.err);
			return null;
		} catch (MappingException ex) {
			ex.printStackTrace(System.err);
			return null;
		} catch (ValidationException ex) {
			ex.printStackTrace(System.err);
			return null;
		}
		return result;
	}



	/**
	 *@param tableMap 
	 * @Function Name:  table
	 *@Description: @param schema
	 *@Description: @param metaSchema
	 *@Description: @param tableSet 
	 *@Date Created:  2013-1-4 上午09:55:44
	 *@Author:  Pan Duan Duan 根据所属schema 得到所有的基本模型集合
	 *@Last Modified:     ,  Date Modified: 
	 */
	private void table(final TbomSchema schema, final Schema metaSchema,
			final Set<TbomTable> tableSet) {
		//得到所有该schema下的所有模型
		List<ITable> tableList = metaSchema.getAllTables();
		//遍历集合 如果子表则交与父表初始化
		for (ITable mtable : tableList) {
			if (mtable.getParentTable() != null) {
				continue;
			}
			if(mtable.getIsValid() == 0l){
				continue;
			}
			//初始化webService中的模型对象
			TbomTable table = new TbomTable();
			//赋值基本属性
			table.setId(mtable.getId());
			table.setName(mtable.getName());
			table.setDisplayName(mtable.getDisplayName());
			table.setOrder(mtable.getOrder());
			table.setParentTable(null);
			table.setSchema(schema);
			table.setSchemaid(schema.getId());
			table.setType(0);
			//初始化子表集合
			final Set<TbomTable> childtableList = new HashSet<TbomTable>();
			//得到子表集合
			childTable(schema, metaSchema, table, childtableList);
			table.setChildTables(childtableList);
			//添加至返回返回结果
			tableSet.add(table);
			tableMap.put(table.getId(), table);
		}
	}
	
	/**
	 *@Function Name:  childTable
	 *@Description: @param schema
	 *@Description: @param metaSchema
	 *@Description: @param table
	 *@Description: @param childtableList 
	 *@Date Created:  2013-1-4 上午09:58:52
	 *@Author:  Pan Duan Duan 根据父表 得到属于该表的子表集合
	 *@Last Modified:     ,  Date Modified: 
	 */
	private void childTable(final TbomSchema schema, final Schema metaSchema,
			final TbomTable table, final Set<TbomTable> childtableList) {
		//根据Schema得到所有基本模型对象
		List<ITable> tableList = metaSchema.getAllTables();
		//遍历集合 如果是父表 或者 父表的ID与此次父表ID不一致  则越过
		for (ITable mtable : tableList) { 
			if(null==mtable.getParentTable()){
				continue;
			}
			if (!mtable.getParentTable().getId().equals(table.getId())) {
				continue;
			}
			//初始化子表对象
			final TbomTable childtable = new TbomTable();
			//赋基本属性
			childtable.setId(mtable.getId());
			childtable.setName(mtable.getName());
			childtable.setDisplayName(mtable.getDisplayName());
			childtable.setOrder(mtable.getOrder());
			childtable.setParentTable(table);
			childtable.setSchema(schema);
			childtable.setSchemaid(schema.getId());
			childtable.setType(0);
			final Set<TbomTable> ctList = new HashSet<TbomTable>();
			//递归调用
			childTable(schema, metaSchema, childtable, ctList);
			//添加至返回结果集
			childtable.setChildTables(ctList);
			childtableList.add(childtable);
			tableMap.put(childtable.getId(), childtable);
		}
	}
	
	/**
	 *@Function Name:  dynamicTable
	 *@Description: @param schema
	 *@Description: @param metaSchema
	 *@Description: @param tableSet 
	 *@Date Created:  2013-1-4 上午10:02:51
	 *@Author:  Pan Duan Duan 根据所属schema得到该schema下的所有动态模型
	 *@Last Modified:     ,  Date Modified: 
	 */
	private void dynamicTable(final TbomSchema schema, final Schema metaSchema,
			final Set<TbomTable> tableSet) {
		//得到所有模型集合
		List<ITable> tableList = metaSchema.getAllTables();
		//遍历集合 找到动态模型
		for (ITable mtable : tableList) {
			if (!mtable.getType().equals("3")) {
				continue;
			}
			if (null == tableMap.get(mtable.getMainTable().getId())) {
				continue;
			}
			//初始化webService的模型对象
			TbomTable table = new TbomTable();
			//赋入基本属性
			table.setId(mtable.getId());
			table.setName(mtable.getName());
			table.setDisplayName(mtable.getDisplayName());
			table.setOrder(new Long(0));
			table.setParentTable(tableMap.get(mtable.getMainTable().getId()));// 动态数据表对应的数据类
			table.setSchema(schema);
			table.setSchemaid(schema.getId());
			table.setType(1);
			//添加至返回结果
			tableSet.add(table);
		}
	}
	
	/**
	 *@Function Name:  relationColumnConnect
	 *@Description:  模型关系属性关联
	 *@Date Created:  2013-1-4 上午10:18:20
	 *@Author:  Pan Duan Duan
	 *@Last Modified:     ,  Date Modified: 
	 */
	@SuppressWarnings("unchecked")
	private void relationColumnConnect(Schema metaSchema) {
		//遍历模型集合
		for (Iterator it = tableMap.keySet().iterator(); it.hasNext();) {
			//得到模型对象
			TbomTable table = tableMap.get((String) it.next());
			//初始化关系属性集合
			final List<TbomRelationColumns> rColumnList = new ArrayList<TbomRelationColumns>();
			//根据模型 初始化其关系属性字段
			RelationColumn(table, metaSchema, rColumnList);
			//赋值
			table.setCwmRelationColumnses(rColumnList);
		}
	}
	
	/**
	 *@Function Name:  RelationColumn
	 *@Description: @param table
	 *@Description: @param metaSchema
	 *@Description: @param columnList 
	 *@Date Created:  2013-1-4 上午10:20:02
	 *@Author:  Pan Duan Duan 根据模型 初始化其关系属性字段
	 *@Last Modified:     ,  Date Modified: 
	 */
	private void RelationColumn(final TbomTable table, final Schema metaSchema,
			final List<TbomRelationColumns> columnList) {
		//根据模型对象 得到模型
		ITable mtable = metaSchema.getTableById(table.getId());
		//得到关系属性字段集合
		List<IColumn> rcs = mtable.getRelationColumns();
		//初始化WebService 的关系属性对象
		for (IColumn col : rcs) {
			TbomRelationColumns column = new TbomRelationColumns();
			column.setId(col.getRelationColumnIF().getId());
			long type = col.getRelationColumnIF().getRelationType();
			column.setRelationtype(type);
			column.setIsFK(col.getRelationColumnIF().getIsFK());
			column.setRefTable(tableMap.get(col.getRelationColumnIF()
					.getRefTable().getId()));
			column.setTable(table);
		}
	}
	
	/**
	 *@Function Name:  view
	 *@Description: @param schema
	 *@Description: @param metaSchema
	 *@Description: @param viewList 
	 *@Date Created:  2013-1-4 上午10:23:37
	 *@Author:  Pan Duan Duan 根据所属schema 得到该schema下的所有视图
	 *@Last Modified:     ,  Date Modified: 
	 */
	@SuppressWarnings("unchecked")
	private void view(TbomSchema schema, Schema metaSchema,
			Set<TbomView> viewList) {
		
		Set<View> views = metaSchema.getViews();
		for (View mview : views) {
			//初始化webService 中的视图对象
			TbomView view = new TbomView();
			view.setId(mview.getId());
			view.setName(mview.getName());
			view.setDisplayName(mview.getDisplayName());
			view.setOrder(mview.getOrder());
			view.setType(String.valueOf(mview.getType()));
			view.setSchema(schema);
			view.setTable(tableMap.get(mview.getTable().getId()));
			view.setSchemaid(schema.getId());
			viewList.add(view);
		}
	}
	
	/**
	 *@Function Name:  getTableDetail
	 *@Description: @param tableId
	 *@Description: @param type
	 *@Description: @return 根据模型ID 以及 模型类型 得到模型描述
	 *@Date Created:  2013-1-4 上午11:03:52
	 *@Author:  Pan Duan Duan
	 *@Last Modified:     ,  Date Modified: 
	 */
	public String getTableDetail(String tableId, String type){
		if(type.equals("column")){
			//返回结果
			StringBuilder columnStr=new StringBuilder();
			//根据模型ID 得到其字段的描述集合
			IBusinessModel businessModel = businessModelService.getBusinessModelById("", tableId, "", BusinessModelEnum.Table);
			List<IColumn> columns = businessModel.getMatrix().getMainTable().getColumns();
			//如果字段为空 则返回空值
			if(null == columns || columns.isEmpty())
			{
				return null;
			}else
			{
				//遍历Column集合
				for(IColumn column : columns)
				{
					//拼接返回结果
					columnStr.append(column.getId()).append("..;,").append(column.getType()).append("..;,").append(column.getDisplayName()).append("..;,").append(column.getCategory());
					columnStr.append(",.;;");
				}
				if(columnStr!=null && columnStr.length()>4){
					return columnStr.substring(0, columnStr.length()-4);
				}else{
					return null;
				}
			}
		}else if (type.equals("schema")){
			//根据schemaID 得到其下的所有模型的描述集合
			ISchema schema = metaEngine.getMeta(false).getISchemaById(tableId);
			//得到该Schema下面的所有模型
			List<ITable> tables = schema.getAllTables();
			//返回结果
			StringBuilder columnStr=new StringBuilder();
			//遍历模型
			for(ITable table : tables)
			{
				columnStr.append(table.getId()).append(";,.;,");
				//得到所有字段集合
				List<IColumn> columns = table.getColumns();
				//遍历Column集合
				for(IColumn column : columns)
				{
					//拼接返回结果
					columnStr.append(column.getId()).append("..;,").append(column.getType()).append("..;,").append(column.getDisplayName()).append("..;,").append(column.getCategory());
					columnStr.append(",.;;");
				}
				//拼接结束标识符
				columnStr.append(";;;...");
			}
			if(columnStr != null && columnStr.length()>6){
				return columnStr.substring(0, columnStr.length()-6);
			}else{
				return null;
			}
		}
		return null;
	}
}
