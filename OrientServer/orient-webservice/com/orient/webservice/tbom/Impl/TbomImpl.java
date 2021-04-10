package com.orient.webservice.tbom.Impl;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.sysmodel.domain.form.ModelGridViewEntity;
import com.orient.sysmodel.domain.role.Role;
import com.orient.utils.StringUtil;
import com.orient.webservice.tbom.ITbom;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TbomImpl extends BasicTbom  implements ITbom{

	private SetTbom setTbom;
	private GetTbom getTbom;
	private ImportTbomFromFile importTbom;
	private GetSource getSource;
	
	@Override
	public String setTbom(String str, String schemaid, String username,
			String tbomName) {		 
		return setTbom.execute(str, schemaid, username, tbomName);
	}

	@Override
	public String getTbom(String schemaIdTbomID) {
		// TODO Auto-generated method stub
		return getTbom.execute(schemaIdTbomID);
	}

	@Override
	public String AutoCreateTbom(String str) {
		// TODO Auto-generated method stub
		return importTbom.execute(str);
	}
	
	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return getSource.execute();
	}

	public SetTbom getSetTbom() {
		return setTbom;
	}

	public void setSetTbom(SetTbom setTbom) {
		this.setTbom = setTbom;
	}

	public GetTbom getGetTbom() {
		return getTbom;
	}

	public void setGetTbom(GetTbom getTbom) {
		this.getTbom = getTbom;
	}

	public ImportTbomFromFile getImportTbom() {
		return importTbom;
	}

	public void setImportTbom(ImportTbomFromFile importTbom) {
		this.importTbom = importTbom;
	}

	public GetSource getGetSource() {
		return getSource;
	}

	public void setGetSource(GetSource getSource) {
		this.getSource = getSource;
	}

	@Override
	public String getColumn(String tableid) {
		try {
			if (tableid.indexOf(";") > 0) {
			    // 传送过来的字符串包含上级的动态子节点及动态子节点所属数据类，
			    // 在这里将对其动态子节点的字段是否存在等进行判断
			    // 若不存在，则该动态子节点所有下级子节点失效，返回其上一级节点的第一个子节点数据源的字段集合。
			    String[] str = tableid.split(",");
			    for (int i = 0; i < str.length; i++) {
					String[] bb = str[i].split(";");
					String cid = bb[0];// 字段ID
					// 字段列表以::分割，如果为一个字段就没有.
					StringBuilder sb_ids = new StringBuilder();
					String[] ids = cid.split("::");
					for (int j = 0; j < ids.length; j++) {
					    sb_ids.append(ids[j]).append("', '");
					}
					String t_str = sb_ids.toString();
					t_str = t_str.substring(0, t_str.length() - 3);
					String tid = bb[1];// 字段所数据类ID
					String ttid = bb[2];// 下级动态子节点的字段所在数据类ID
					List<IColumn> list = metadaofactory.getColumnDAO().findColumnsByIsvalidAndId(new Long(1),t_str);			
					if (list.isEmpty()) {
					    if (i == 0) {
					    	return i + getColumnDetail(tid, false);
					    } else {
					    	return i + getColumnDetail(str[i - 1].split(";")[2], false);
					    }
					}
					if (i == str.length - 1) {
					    return (i + 1) + getColumnDetail(ttid, false);
					}
			    }
			    return null;
			} else if (tableid.indexOf("view") == 0) {// 读取数据视图的返回属性中所有普通属性字段
			    String viewid = tableid.substring(4);
			    String columnStr = getViewColumn(viewid, false);// false表示不用添加实际字段名
			    if (columnStr != null && columnStr.length() > 4) {
				return columnStr.substring(0, columnStr.length() - 4);
			    } else {
				return null;
			    }
			} else if (tableid.indexOf("vw") == 0) {// 读取数据视图的返回属性中所有普通属性字段
			    String viewid = tableid.substring(2);
			    String columnStr = getViewColumn(viewid, true);// true表示要多加一个实际字段名
			    if (columnStr != null && columnStr.length() > 4) {
				return columnStr.substring(0, columnStr.length() - 4);
			    } else {
				return null;
			    }
			} else if (tableid.indexOf("cn") == 0) {
			    String tid = tableid.substring(2);
			    return "0" + getColumnDetail(tid, true);
			} else {
			    return "0" + getColumnDetail(tableid, false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "-1";
		}
	}
	
	/**
     * 读取字段属性详细.
     * 
     * @param tableid
     *            数据类或视图的ID
     * @param includeName
     *            是否包含数据字段在实际表中的字段名
     * 
     * @return 字段属性信息
     */
    private String getColumnDetail(String tableid, Boolean includeName) {	
		List list = metadaofactory.getColumnDAO().getSqlResultByTableId(tableid);
		StringBuffer columnStr = new StringBuffer();
		if (list.isEmpty()) {
		    return null;
		}		
		Object[] elementData = list.toArray();			
		for(int index=0;index<elementData.length;index++){
			Object[] val = (Object[])elementData[index];  
			columnStr.append(val[0]).append("..;,").append(val[1])
			.append("..;,").append(val[2]).append("..;,").append(val[3]);
			if (includeName) {
				columnStr.append("..;,").append(val[4]).append(",.;;");
			} else {
				columnStr.append(",.;;");
			}
		}
		if (columnStr != null && columnStr.length() > 4) {
		    return columnStr.substring(0, columnStr.length() - 4);
		} else {
		    return null;
		}
    }

	
    private String getViewColumn(String viewid, Boolean includeName) {
    	List list = metadaofactory.getColumnDAO().getSqlResultByViewId(viewid);
    	//wubing 修改 tbom 视图中动态子节点 出现 关联属性字段
    		//+ "' AND RETURN_COLUMN_ID=CTC.ID AND CTC.CATEGORY=1 ORDER BY CVRC.ORDER_SIGN ASC");
    	StringBuffer columnStr = new StringBuffer();
    	if (list.isEmpty()) {
    	    return null;
    	}
    	Object[] elementData = list.toArray();			
		for(int index=0;index<elementData.length;index++){
			Object[] val = (Object[])elementData[index];  
			columnStr.append(val[0]).append("..;,")
	    	.append(val[2]).append("..;,").append(val[1]).append("..;,")
	    	.append(val[3]);
	    	if (includeName) {
	    		columnStr.append("..;,").append(val[4]).append(",.;;");
	    	} else {
	    		columnStr.append(",.;;");
	    	}
		}
    	
    	/*for (int i = 0; i < list.size(); i++) {
    	    Map columnMap = (Map) list.get(i);
    	    columnStr.append(columnMap.get("RETURN_COLUMN_ID")).append("..;,")
    	    .append(columnMap.get("TYPE")).append("..;,").append(columnMap.get("DISPLAY_NAME")).append("..;,")
    	    .append(columnMap.get("CATEGORY"));
    	    // .append(",.;;");
    	    if (includeName) {
    		columnStr.append("..;,").append(columnMap.get("S_COLUMN_NAME")).append(",.;;");
    	    } else {
    		columnStr.append(",.;;");
    	    }
    	}*/
    	return columnStr.toString();
   }

	@Override
	public String getRole() {
		
		Map<String, Role> allRole = roleEngine.getRoleModel(false).getRoles();
		String result = null;
		for(Role role : allRole.values()) {
			
			String roleId = role.getId();
			if(Integer.valueOf(roleId) < 1)
			{
				continue;
			}
			String roleName = role.getName();
			result += roleId + ",..;" + roleName + "..;;";
		}
		
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Map<String, String>> getModelTemplatesByModelId(String modelId) {
		List<Map<String, String>> retVal = new ArrayList<Map<String, String>>() {{
			add(0, new HashMap<String, String>() {{
				put("success", "1");
				put("msg", "获取成功");
			}});
		}};
		if (!StringUtil.isEmpty(modelId)) {
			try {
				List<ModelGridViewEntity> modelGridViewEntitys = modelGridViewBusiness.getModelTemplateByModelId(modelId);
				modelGridViewEntitys.forEach(modelGridViewEntity -> {
					Map<String, String> dataMap = new HashMap<String, String>() {{
						put("id", modelGridViewEntity.getId().toString());
						put("name", modelGridViewEntity.getName());
					}};
					retVal.add(dataMap);
				});
			} catch (Exception e) {
				e.printStackTrace();
				retVal.get(0).put("success", "0");
				retVal.get(0).put("msg", "获取失败,异常信息为：" + e.toString());
			}
		} else {
			retVal.get(0).put("success", "0");
			retVal.get(0).put("msg", "获取失败,模型ID不可为空");
		}
		return retVal;
	}
}
