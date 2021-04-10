package com.orient.webservice.schema.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.sysmodel.domain.sys.SeqGenerator;
import com.orient.utils.CommonTools;


public class IScriptImpl  extends SchemaBean{

	public Map<String, Object> query(Long id) {
		Map<String, Object> script_map = new HashMap<>();
		try {
			SeqGenerator seqGenerator = seqGeneratorService.findById(id);
			String name = seqGenerator.getName();
			List<IColumn> list = metadaofactory.getColumnDAO().findColumnsByIsvalidAndSequenceName(new Long(1),name);
			script_map.put("ID", seqGenerator.getId());
			script_map.put("NAME", seqGenerator.getName());
			script_map.put("CONTENT", seqGenerator.getContent());
			script_map.put("ENABLE", seqGenerator.getEnable());
			script_map.put("RETURN_TYPE", seqGenerator.getReturnType());
			script_map.put("ISUSED", list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return script_map;
	}
	
	public List<Map<String, Object>> queryList() {	
		List<Map<String, Object>> list = new ArrayList<>();
		List<SeqGenerator> seqGeneratorList = seqGeneratorService.findAll();
		for(SeqGenerator seqGenerator:seqGeneratorList){
			Map<String, Object> script_map =  new HashMap<>();
			script_map.put("ID", seqGenerator.getId());
			script_map.put("NAME", seqGenerator.getName());
			script_map.put("CONTENT", seqGenerator.getContent());
			script_map.put("ENABLE", seqGenerator.getEnable());
			script_map.put("RETURN_TYPE", seqGenerator.getReturnType());
			list.add(script_map);
		}		
		return list;
	}

	public String insert(Map<String, Object> scriptMap) {
		String name = scriptMap.get("name".toUpperCase())!=null ? scriptMap.get("name".toUpperCase()).toString():"";
		if(!CommonTools.isNullString(name)){
			List<SeqGenerator> list = seqGeneratorService.findByName(name);
			if(list.size()>0){
				return "脚本名称重名";//脚本重名
			}
			else{
				SeqGenerator seqGenerator = new SeqGenerator();
				Long id = seqGeneratorService.findNextVal();
				String content = scriptMap.get("content".toUpperCase())!=null ? scriptMap.get("content".toUpperCase()).toString():"";
				String returntype = scriptMap.get("returntype".toUpperCase())!=null ? scriptMap.get("returntype".toUpperCase()).toString():"";
				seqGenerator.setId(id);
				seqGenerator.setName(name);
				seqGenerator.setContent(content);
				seqGenerator.setReturnType(returntype);
				seqGenerator.setChanged("TRUE");
				seqGeneratorService.insert(seqGenerator);
				return "保存成功_" + id;
			}
		}
		else{
			return "脚本名称不能为空";
		}
	}

	public String update(Map<String, Object> scriptMap, Long id) {
		/* 查询脚本是不存在 */
		SeqGenerator seqGenerator = seqGeneratorService.findById(id);
		if(seqGenerator==null){
			return this.insert(scriptMap);
		}
		/* 根据id查询更新前的name,若此脚本正在被使用，则不能更新 */
		String name = scriptMap.get("NAME")!=null ? scriptMap.get("NAME").toString():"";
		/* 脚本正被使用,不可更新 */
		List msg = seqGeneratorService.checkScriptUsed("'" + name.replace("'", "''") + "'");
		if (!msg.isEmpty()){
			return "此脚本正在使用,不可更改";
		}

		/*- 名称已经存在,不可保存 -*/
		if(!CommonTools.isNullString(name)){
			List<SeqGenerator> list = seqGeneratorService.findByName(name);
			for(SeqGenerator map:list){
				Long mapid = map.getId();
				if(mapid.equals(id)){
					return "脚本名称重名";//脚本重名
				}
			}
		}
		else{
			return "脚本名称不能为空";
		}
		/*- 更新 -*/
		String content = scriptMap.get("CONTENT")!=null ? scriptMap.get("CONTENT").toString():"";
		Boolean enable = scriptMap.get("ENABLE")!=null ? true:false;
		String returntype = scriptMap.get("RETURNTYPE")!=null ? scriptMap.get("RETURNTYPE").toString():"";	
		seqGenerator.setName(name);
		seqGenerator.setContent(content);
		seqGenerator.setReturnType(returntype);
		seqGenerator.setEnable(enable);
		seqGenerator.setChanged("TRUE");
		seqGeneratorService.update(seqGenerator);	
		return "保存成功";
	}

	public String delete(String id) {
		/* 校验数据模型是否被锁定 */
		List list = metadaofactory.getSchemaDAO().findByIsLock(new Long(1));
		if(list.size()>0){
			return "数据模型被锁定";
		}
		String[] scriptIds = id.split(",");
		for(int i=0;i<scriptIds.length;i++){
			Long scriptId = new Long(scriptIds[i]);
			SeqGenerator seqGenerator = seqGeneratorService.findById(scriptId);
			if(seqGenerator!=null){
				/* 脚本正被使用,不可删除 */
				List msg = seqGeneratorService.checkScriptUsed(seqGenerator.getName());
				if (!msg.isEmpty()){
					return "此脚本正在使用,不可更改";
				}
				seqGeneratorService.delete(seqGenerator);
			}
		}
		return "删除成功";
	}
}
