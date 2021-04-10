package com.orient.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class HibernateProperties {

    private Map<String, String> map;
    private Properties properties;

    public Properties getProperties() {
        Properties p = new Properties();
        for (Iterator it = map.keySet().iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            String value = map.get(key);
            p.put(key, value);
        }
        return p;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

}
