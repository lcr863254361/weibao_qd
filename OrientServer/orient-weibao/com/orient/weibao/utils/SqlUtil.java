package com.orient.weibao.utils;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ContextLoader;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SqlUtil {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static JdbcTemplate getJdbcTemplate() {
        ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        MetaDAOFactory metaDaoFactory = applicationContext.getBean(MetaDAOFactory.class);
        return metaDaoFactory.getJdbcTemplate();
    }

    public static String pagingSql(String sql, Integer page, Integer limit) {
        String retVal = sql;
        if(page!=null && limit!=null && page>0 && limit>0) {
            Integer start = (page-1) * limit + 1;
            Integer end = start + limit - 1;
            retVal = "SELECT * FROM (  " +
                    "    SELECT \"PAGING_TABLE\".*, ROWNUM RN FROM (  " +
                    sql +
                    "    ) \"PAGING_TABLE\"  WHERE ROWNUM <=" +end+
                    ")  " +
                    "WHERE RN >= " + start;
            //retVal = "select * from (" + sql + ") WHERE ROWNUM BETWEEN " + start + " AND " + end;
        }
        return retVal;
    }

    public static String countSql(String sql) {
        String retVal = "SELECT COUNT(*) FROM (  " +
                sql +
                ")";
        return retVal;
    }

    public static List<Map<String, String>> getStringList(List list) {
        List<Map<String, String>> retList = Lists.newArrayList();
        if(list==null || list.size()==0) {
            return retList;
        }
        for(Object objMap : list) {
            retList.add(getStringMap((Map) objMap));
        }
        return retList;
    }

    public static Map<String, String> getStringMap(Map map) {
        Map<String, String> retMap = Maps.newHashMap();
        if(map==null || map.size()==0) {
            return retMap;
        }
        for(Object key : map.keySet()) {
            Object val = map.get(key);
            if(val == null) {
                retMap.put(String.valueOf(key), null);
            }
            else {
                String strVal = getStringVal(val);
                retMap.put(String.valueOf(key), strVal);
            }
        }
        return retMap;
    }

    private static String getStringVal(Object val) {
        if(val == null) {
            return null;
        }
        String retVal = null;
        if(val instanceof Timestamp) {
            long timestamp = ((Timestamp) val).getTime();
            retVal = getFormatedTime(timestamp);
        }
        else {
            retVal = String.valueOf(val);
        }
        return retVal;
    }

    private static String getFormatedTime(long timestamp) {
        if(timestamp%(24*3600*1000) == 0) {
            return dateFormat.format(new Date(timestamp));
        }
        else {
            return timeFormat.format(new Date(timestamp));
        }
    }
}
