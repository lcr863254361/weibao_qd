package com.orient.webservice.tbom.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orient.businessmodel.bean.impl.BusinessModel;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metadomain.Table;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.sqlengine.api.IBusinessModelQuery;
import com.orient.sysmodel.domain.tbom.Tbom;
import com.orient.sysmodel.domain.tbom.TbomDir;
import com.orient.webservice.tbom.bean.ColumnModel;

public  class ImportTbomFromFile extends TbomBean {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(propagation = Propagation.REQUIRED)
	public String execute(String str) {
		String[] act = str.split(";;,;,;");
		String username = act[0];// 用户名
		String schemaid = null;// TBOM树所属的业务库ID
		String tbomname = null;// TBOM树名称
		String tableid = null;// 数据源的数据类的ID
		String firstname = null;// 首位结点的名称
		String id = null;// 作为ID字段在数据库中实际字段名称
		String pid = null;// 作为PID字段在数据库中实际字段名称
		String name = null;// 作为名称字段在数据库中实际字段名称
		Boolean isCover = false;// 是否附带数据库中的TBOM树
		if (act.length == 8) {
			tbomname = act[1];
			firstname = act[2];
			schemaid = act[3];
			tableid = act[4];
			id = act[5];
			pid = act[6];
			name = act[7];
		} else {
			tbomname = act[1];
			firstname = act[2];
			schemaid = act[3];
			tableid = act[4];
			id = act[5];
			pid = act[6];
			name = act[7];
			isCover = true;
		}
		ISchema schema = metaEngine.getMeta(false).getISchemaById(
				schemaid.substring(6));
		if (null == schema) {
			return "noTable";// 数据源不存在
		}

		ITable table = schema.getTableById(tableid);
		if (null == table) {
			return "noTable";// 数据源不存在
		}
		List list = metaEngine.getMeta(false).getMetaDAOFactory().getTableDAO()
				.getSqlResultByTableName(table.getTableName());
		if (list.size() != 1) {
			return "noTable";// 数据源不存在
		}
		// 首位结点列表
		List<ColumnModel> rootList = new ArrayList<ColumnModel>();
		BusinessModel bm = new BusinessModel(table);
		IBusinessModelQuery bmquery = sqlEngine.getBmService()
				.createModelQuery(bm);
		List<Map> dataList = bmquery.list();
		if (null == dataList || dataList.size() == 0) {
			return "noData";
		}
		// 结点MAP，key为pid对象，value为具有相同pid的columnModel的list集合
		Map<String, List<ColumnModel>> nodeMap = new HashMap<String, List<ColumnModel>>();
		bm.setReserve_filter(" AND " + pid + " not in (select " + id + " from "
				+ table.getTableName() + ")");
		List<Map> dataList1 = bmquery.list();
		if (null == dataList1 || dataList1.isEmpty()) {
			return "errorData";
		}
		for (Map map : dataList1) {
			ColumnModel cm = new ColumnModel((String) map.get(id),
					(String) map.get(pid), (String) map.get(name));
			rootList.add(cm);
		}
		for (Map map : dataList) {
			ColumnModel cm = new ColumnModel((String) map.get(id),
					(String) map.get(pid), (String) map.get(name));
			if (nodeMap.containsKey((String) map.get(pid))) {
				nodeMap.get((String) map.get(pid)).add(cm);
			} else {
				nodeMap.put((String) map.get(pid), new ArrayList<ColumnModel>());
				nodeMap.get((String) map.get(pid)).add(cm);
			}
		}
		List tbomDirList = tbomService.getTbomDirDAO().findByNameAndSchemaid(
				tbomname, schemaid);
		TbomDir tbomDir = null;
		Tbom rootNode = null;
		if (isCover && !tbomDirList.isEmpty()) {
			tbomDir = (TbomDir) tbomDirList.get(0);
			if (tbomDir.getUserid() != null
					&& !tbomDir.getUserid().equals(username)
					&& tbomDir.getIsLock() == 1) {
				return "lock" + tbomDir.getUserid();
			}
			tbomDir.setIsLock(new Long(0));
			tbomDir.setModifiedTime(new Date());
			tbomDir.setUserid(username);
			tbomDir.setIsdelete(new Long(1));
			List tbomList = tbomService.getDao()
					.findByNameAndIsrootAndSchemaid(tbomname, new Long(1),
							schemaid.substring(6));
			if (!tbomList.isEmpty()) {
				rootNode = (Tbom) tbomList.get(0);
				try {
					tbomService.getDao().delete(rootNode);
				} catch (Exception e) {
					return "errorData";
				}
				tbomService.getDao().getHibernateTemplate().flush();
				rootNode.setIsValid(new Long(1));
				rootNode.setDescription("");
				rootNode.setId(null);

			} else {
				rootNode = new Tbom(null, tbomname, new Long(1), new Long(1),
						"000", (Schema) schema, null, null, "", "");
			}
		} else {
			tbomDir = new TbomDir();
			tbomDir.setName(tbomname);
			tbomDir.setSchemaid(schemaid.substring(6));
			tbomDir.setIsLock(new Long(0));
			tbomDir.setModifiedTime(new Date());
			tbomDir.setUserid(username);
			tbomDir.setLockModifiedTime(new Date());
			tbomDir.setIsdelete(new Long(1));

			rootNode = new Tbom(null, tbomname, new Long(0), new Long(1),
					"000", (Schema) schema, null, null, "", "");
		}
		try {
			tbomService.getDao().merge(rootNode);
			tbomService.getTbomDirDAO().merge(tbomDir);
		} catch (Exception e) {
			e.printStackTrace();
			return "errorData";
		}
		IColumn idcol = table.getColumnByName(id);
		IColumn pidcol = table.getColumnById(pid);
		String idcolumn = idcol.getId() + ";,;" + idcol.getType() + ";,;"
				+ idcol.getDisplayName();// id字段的信息
		String pidcolumn = pidcol.getId() + ";,;" + pidcol.getType() + ";,;"
				+ pidcol.getDisplayName();// pid字段的信息
		StringBuffer expStr = new StringBuffer();// 过滤表达式（WEB展现过滤使用）
		StringBuffer oriExpStr = new StringBuffer();// 过滤表达式（TBOM树展现使用）
		for (int i = 0; i < rootList.size(); i++) {
			ColumnModel cm = rootList.get(i);
			oriExpStr.append(idcolumn).append(";;,,").append("=")
					.append(";;,,").append(cm.getIdValue()).append(";;,,");
			if (i < rootList.size() - 1) {
				oriExpStr.append("or").append(";;,,;;;;");
			} else {
				oriExpStr.append(";;,,");
			}
			expStr.append(id).append(" = '").append(cm.getIdValue())
					.append("'");
			if (i < rootList.size() - 1) {
				expStr.append(" or ");
			}
		}
		Tbom secNode = new Tbom((Table) table, firstname, new Long(0),
				new Long(0), "000000", null, expStr.toString(),
				oriExpStr.toString(), "", "");
		secNode.setParenttbom(rootNode);
		try {
			tbomService.getDao().save(secNode);
		} catch (Exception e) {
			e.printStackTrace();
			return "errorData";
		}
		int order = 0;
		for (ColumnModel cm : rootList) {
			String xmlid = null;
			if (order <= 9) {
				xmlid = "00000000" + order;
			} else if (order <= 99) {
				xmlid = "0000000" + order;
			} else if (order <= 999) {
				xmlid = "000000" + order;
			} else {
				break;
			}
			Tbom node = new Tbom((Table) table, cm.getNameValue(), new Long(
					order), new Long(0), xmlid, null, pid + "='"
					+ cm.getIdValue() + "'", pidcolumn + ";;,,=;;,,"
					+ cm.getIdValue() + ";;,,;;,,", "", "");
			node.setParenttbom(secNode);
			try {
				tbomService.getDao().save(node);
				childNode(node, xmlid, cm.getIdValue(), nodeMap, pid, pidcolumn);
			} catch (Exception e) {
				e.printStackTrace();
				return "errorData";
			}

			order++;
		}
		return tbomDir.getId() + ";==;" + tbomDir.getName();

	}

	/** 
	 * 遍历所有子节点
	 * @Method: childNode 
	 * @param node
	 * @param xmlid
	 * @param idValue
	 * @param nodeMap
	 * @param pid
	 * @param pidcolumn
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void childNode(Tbom node, String xmlid, String idValue,
			Map<String, List<ColumnModel>> nodeMap, String pid, String pidcolumn)
			throws Exception {
		int order = 0;
		if (nodeMap.containsKey(idValue)) {
			for (ColumnModel cm : nodeMap.get(idValue)) {
				if (nodeMap.containsKey(cm.getIdValue())) {
					String childxmlid = null;
					if (order <= 9) {
						childxmlid = xmlid + "00" + order;
					} else if (order <= 99) {
						childxmlid = xmlid + "0" + order;
					} else if (order <= 999) {
						childxmlid = xmlid + order;
					} else {
						break;
					}
					Tbom childnode = new Tbom(node.getTable(),
							cm.getNameValue(), new Long(order), new Long(0),
							childxmlid, null, pid + "='" + cm.getIdValue()
									+ "'", pidcolumn + ";;,,=;;,,"
									+ cm.getIdValue() + ";;,,;;,,",
							node.getUrl(), "");
					childnode.setParenttbom(node);
					tbomService.getDao().save(childnode);
					childNode(childnode, childxmlid, cm.getIdValue(), nodeMap,
							pid, pidcolumn);
					order++;
				}
			}
		}
	}
}
