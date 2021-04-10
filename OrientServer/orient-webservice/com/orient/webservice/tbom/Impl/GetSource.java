package com.orient.webservice.tbom.Impl;

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

import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.View;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.webservice.tbom.bean.TbomRelationColumns;
import com.orient.webservice.tbom.bean.TbomSchema;
import com.orient.webservice.tbom.bean.TbomSource;
import com.orient.webservice.tbom.bean.TbomTable;
import com.orient.webservice.tbom.bean.TbomView;

@SuppressWarnings("rawtypes")
public class GetSource extends TbomBean {
	final Map<String, TbomTable> tableMap = new HashMap<String, TbomTable>();

	public String execute() {
		String value = null;
		TbomSource source = new TbomSource();
		Map<String, Schema> map = metaEngine.getMeta(false).getSchemas();
		final List<TbomSchema> schemaList = new ArrayList<TbomSchema>();
		for (Map.Entry<String, Schema> entry : map.entrySet()) {
			Schema metaSchema = entry.getValue();
			if (metaSchema.getIsdelete() != 1
					|| metaSchema.getId().equals("****")) {
				continue;
			}
			TbomSchema schema = new TbomSchema();
			schema.setId("schema" + metaSchema.getId());
			schema.setName(metaSchema.getName());
			schema.setVersion(metaSchema.getVersion());
			final Set<TbomTable> tableSet = new HashSet<TbomTable>();
			table(schema, metaSchema, tableSet);

			final Set<TbomTable> dynamictableSet = new HashSet<TbomTable>();
			dynamicTable(schema, metaSchema, dynamictableSet);// 动态数据类的添加
			schema.setDynamicTables(dynamictableSet);

			for (Iterator it = tableMap.keySet().iterator(); it.hasNext();) {
				TbomTable table = tableMap.get((String) it.next());
				final List<TbomRelationColumns> rColumnList = new ArrayList<TbomRelationColumns>();
				RelationColumn(table, metaSchema, rColumnList);
				table.setCwmRelationColumnses(rColumnList);
			}

			schema.setTables(tableSet);
			final Set<TbomView> viewList = new HashSet<TbomView>();
			view(schema, metaSchema, viewList);
			schema.setViews(viewList);
			schemaList.add(schema);
			tableMap.clear();
		}
		source.setSource(schemaList);
		Writer a = null;
		try {
			Mapping tbommap = new Mapping();
			String url = GetSource.class.getResource("GetSource.class").toString().replace("GetSource.class", "map_tbom.xml");
			tbommap.loadMapping(url);
			a = new StringWriter();
			Marshaller marshaller = new Marshaller(a);
			marshaller.setEncoding("GBK");
			marshaller.setMapping(tbommap);
			marshaller.marshal(source);
			value = a.toString();
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
		return value;
	}

	private void table(final TbomSchema schema, final Schema metaSchema,
			final Set<TbomTable> tableSet) {
		List<ITable> tableList = metaSchema.getAllTables();
		for (ITable mtable : tableList) {
			if (mtable.getParentTable() != null) {
				continue;
			}
			TbomTable table = new TbomTable();
			table.setId(mtable.getId());
			table.setName(mtable.getName());
			table.setDisplayName(mtable.getDisplayName());
			table.setOrder(mtable.getOrder());
			table.setParentTable(null);
			table.setSchema(schema);
			table.setSchemaid(schema.getId());
			table.setType(0);
			final Set<TbomTable> childtableList = new HashSet<TbomTable>();
			childTable(schema, metaSchema, table, childtableList);
			table.setChildTables(childtableList);
			tableSet.add(table);
			tableMap.put(table.getId(), table);
		}
	}

	private void childTable(final TbomSchema schema, final Schema metaSchema,
			final TbomTable table, final Set<TbomTable> childtableList) {
		List<ITable> tableList = metaSchema.getAllTables();
		for (ITable mtable : tableList) { 
			if(null==mtable.getParentTable()){
				continue;
			}
			if (!mtable.getParentTable().getId().equals(table.getId())) {
				continue;
			}
			final TbomTable childtable = new TbomTable();
			childtable.setId(mtable.getId());
			childtable.setName(mtable.getName());
			childtable.setDisplayName(mtable.getDisplayName());
			childtable.setOrder(mtable.getOrder());
			childtable.setParentTable(table);
			childtable.setSchema(schema);
			childtable.setSchemaid(schema.getId());
			childtable.setType(0);
			final Set<TbomTable> ctList = new HashSet<TbomTable>();
			childTable(schema, metaSchema, childtable, ctList);
			childtable.setChildTables(ctList);
			childtableList.add(childtable);
			tableMap.put(childtable.getId(), childtable);
		}
	}

	private void dynamicTable(final TbomSchema schema, final Schema metaSchema,
			final Set<TbomTable> tableSet) {
		List<ITable> tableList = metaSchema.getAllTables();
		for (ITable mtable : tableList) {
			if (!mtable.getType().equals("3")) {
				continue;
			}
			if (null == tableMap.get(mtable.getMainTable().getId())) {
				continue;
			}

			TbomTable table = new TbomTable();
			table.setId(mtable.getId());
			table.setName(mtable.getName());
			table.setDisplayName(mtable.getDisplayName());
			table.setOrder(new Long(0));
			table.setParentTable(tableMap.get(mtable.getMainTable().getId()));// 动态数据表对应的数据类
			table.setSchema(schema);
			table.setSchemaid(schema.getId());
			table.setType(1);
			tableSet.add(table);
		}
		//schema.setDynamicTables(tableSet);
	}

	private void RelationColumn(final TbomTable table, final Schema metaSchema,
			final List<TbomRelationColumns> columnList) {
		ITable mtable = metaSchema.getTableById(table.getId());
		List<IColumn> rcs = mtable.getRelationColumns();
		//List<TbomRelationColumns> rcol = new ArrayList();
		for (IColumn col : rcs) {
			TbomRelationColumns column = new TbomRelationColumns();
			column.setId(col.getRelationColumnIF().getId());
			long type = col.getRelationColumnIF().getRelationType();
			column.setRelationtype(type);
			column.setIsFK(col.getRelationColumnIF().getIsFK());
			column.setRefTable(tableMap.get(col.getRelationColumnIF()
					.getRefTable().getId()));
			column.setTable(table);
			columnList.add(column);
		}
		//table.setCwmRelationColumnses(rcol);
	}

	@SuppressWarnings("unchecked")
	private void view(final TbomSchema schema, final Schema metaSchema,
			final Set<TbomView> viewList) {
		Set<View> views = metaSchema.getViews();
		for (View mview : views) {
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
}
