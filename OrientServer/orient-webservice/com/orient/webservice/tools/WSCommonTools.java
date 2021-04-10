/**
 * 
 */
package com.orient.webservice.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.orient.utils.CommonTools;

/**
 * simple introduction.
 *
 * <p>detailed commentWSCommonTools</p>
 * @author [创建人]  mengbin <br/> 
 * 		   [创建时间] 2016-1-14 上午10:22:13 <br/> 
 * 		   [修改人] mengbin <br/>
 * 		   [修改时间] 2016-1-14 上午10:22:13
 * @see
 */
public class WSCommonTools {

	
	/**
	 * 将list<Map> 转换成string
	 * 其中分隔符为 key1:=:value1:==:key2:=:value2:===:key1:=:value1:==:key2:=:value2
	 * 
	 * @param list
	 * @return string
	 */
	public static String listMap2String(List<Map<String, Object>> list) {
		StringBuilder str_to_return = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Set<String> keys = map.keySet();
			// int j = 0;
			for (String key : keys) {
				str_to_return.append(key).append(":=:");
				str_to_return.append(map.get(key));
				// if (j++ < keys.size() - 1)
				str_to_return.append(":==:");
			}
			str_to_return.append(":===:");
		}
		return str_to_return.toString();
	}
	
	
	public static final Map<String, String> str2Map(String data) {
		return str2Map(data, ":==:", ":=:");
	}
	
	public static final Map<String, String> str2Map(String data, String sep2, String sep3) {
		Map<String, String> map = new HashMap<String, String>();
		if (CommonTools.isNullString(data)) {
			return map;
		}
		for (String str : data.split(sep2)) {
			String[] temp = str.split(sep3);
			map.put(temp[0], temp[1]);
		}
		return map;
	}
	
}
