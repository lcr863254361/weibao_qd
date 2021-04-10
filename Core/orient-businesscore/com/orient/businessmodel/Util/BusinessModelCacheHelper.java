package com.orient.businessmodel.Util;

import com.orient.businessmodel.bean.impl.BusinessModel;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-07-12 15:16
 */
@Component
@Singleton
public class BusinessModelCacheHelper implements Serializable {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    @Qualifier("businessModelCache")
    public Cache businessModelCache;

    public synchronized BusinessModel getBusinessModel(String modelId, String userId) {
        String cacheKey = modelId + "_" + userId;
        Element element = businessModelCache.get(cacheKey);
        return null == element ? null : (BusinessModel) element.getObjectValue();
    }

    public synchronized void setBusinessModel(String modelId, String userId, BusinessModel businessModel) {
        String cacheKey = modelId + "_" + userId;
        businessModelCache.remove(cacheKey);
        Element element = new Element(cacheKey, businessModel);
        businessModelCache.put(element);
    }
}
