package com.orient.testresource.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-05-02 13:54
 */
public class TestResourceUtil {
    public static String getAbsolutePath() {
        URL url = TestResourceUtil.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            filePath = URLDecoder.decode(url.getPath(), "utf-8");

            if(filePath.endsWith(".jar") || filePath.endsWith(".class")) {
                filePath = filePath.substring(0, filePath.lastIndexOf("/")+1);
            }

            File file = new File(filePath);
            return file.getAbsolutePath()+File.separator;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getFormatedPath(String path) {
        try {
            File file = new File(path);
            return file.getAbsolutePath();
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void sqlListToFile(List<String> list, String filePath) {
        FileWriter fw = null;
        try {
            File file = new File(filePath);
            if(!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            for(String str : list) {
                fw.write(str);
                fw.write(";\n");
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            if(fw != null) {
                try {
                    fw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }
}
