package com.orient.login.license;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author author:WangShan
 * @version Date&Time:2016年4月23日 下午2:19:27 Class description:
 */
public class ParseLicense {
    public static byte[] readLicense(String fileName) {
        Integer size = 0;
        FileInputStream fis;
        try {
            if (fileName != null) {
                fis = new FileInputStream(fileName + "license.lic");
            } else {
                fis = new FileInputStream("license.lic");
            }
            byte[] bys = new byte[10240];
            Integer len = 0;
            while ((len = fis.read(bys)) != -1) {
                size = size + len;
            }
            fis.close();
            byte[] encodedData = new byte[size];
            for (int i = 0; i < size; i++) {
                encodedData[i] = bys[i];
            }
            return encodedData;
        } catch (Exception e) {
            System.out.println("读取数据操作不成功");
        }
        return null;
    }

    public static Map<String, Object> propTomap(Properties prop) {
        Iterator<java.util.Map.Entry<Object, Object>> it = prop.entrySet()
                .iterator();
        Map<String, Object> map = new HashMap<String, Object>();
        while (it.hasNext()) {
            java.util.Map.Entry<Object, Object> entry = it.next();
            if (!"iPAddress".equals(entry.getKey().toString())) {
                map.put(entry.getKey().toString(), entry.getValue());
            } else {
                if (!entry.getValue().toString().equals("null")) {
                    List<String> a = new ArrayList<String>();
                    String egt = entry.getValue().toString();
                    String[] ipAddress = egt.substring(1, egt.length() - 1)
                            .split(",");
                    for (String i : ipAddress) {
                        a.add(i);
                    }
                    map.put("iPAddress", a);
                } else {
                    map.put("iPAddress", (String) null);
                }
            }
        }
        return map;
    }
}
