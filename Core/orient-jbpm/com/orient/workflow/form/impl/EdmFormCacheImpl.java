package com.orient.workflow.form.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.orient.workflow.form.EdmFormCache;
import com.orient.workflow.form.model.XmlFormReader;

/**
 * @ClassName EdmFormCacheImpl
 * 表单缓存实现类
 * @author zhulc@cssrc.com.cn
 * @date 2012-6-11
 */

public class EdmFormCacheImpl implements EdmFormCache{
	Map<String, XmlFormReader> forms = new HashMap<String, XmlFormReader>();

	@Override
	public void set(String pdId, XmlFormReader reader) {
		forms.put(pdId, reader);
		
	}

	@Override
	public XmlFormReader get(String pdId) {
		XmlFormReader reader  = forms.get(pdId);
		return reader;
	}

	@Override
	public Set<String> getCachedProcessDefIds() {
		if (forms != null) {
		      return new HashSet<String>(forms.keySet());
		    }
		    return Collections.emptySet();
	}

	@Override
	public void remove(String pdId) {
		if(null!=forms){
			forms.remove(pdId);
		}
		
	}

	@Override
	public void clear() {
		forms.clear();
		
	}
	
	
}
