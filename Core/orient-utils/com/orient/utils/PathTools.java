/**
 * @ClassName Tools.java
 * @author Administrator
 * @date 2012-5-15
 */
package com.orient.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Properties;


/**
 * @author Administrator
 */
public class PathTools {

    /**
     * 获取根目录
     *
     * @return 项目的根路径
     */
    public static String getRootPath() {
        String result = null;
        try {
            result = PathTools.class.getResource("/").toURI().getPath().toString();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        int index = result.indexOf("WEB-INF");
        if (index == -1) {
            index = result.indexOf("bin");
            if (-1 == index) {
                index = result.length();
            }
        }
        //判断是否是linux系统
        if (WindowOrLinux()) {
            result = result.substring(1, index);
        } else
            result = result.substring(0, index);
        if (result.endsWith("/")) result = result.substring(0, result.length() - 1);// 不包含最后的"/"
        return result;
    }

    public static String getClassPath() {
        return PathTools.getRootPath() + File.separator + "WEB-INF" + File.separator + "classes";
    }

    /**
     * 获取FTP上传文件目录地址.
     *
     * @return the ftp url
     */
    public static String getFtpUrl() {
        String filePath = getRootPath() + File.separator + "WEB-INF" + File.separator + "classes"
                + File.separator + "ftpServer.properties";
        Properties propertie = new Properties();
        try {
            FileInputStream inputFile = new FileInputStream(filePath);
            propertie.load(inputFile);
            inputFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
            ex.printStackTrace();
            return null;
        } catch (IOException ex) {
            System.out.println("装载文件--->失败!");
            ex.printStackTrace();
        }
        return SysReplace(propertie.getProperty("ftpServer.ftpHome"));
    }

    /**
     * 判断当前操作系统，如果是Windows系统，替换路径。
     *
     * @param
     * @return
     */
    public static String SysReplace(String url) {
        if (WindowOrLinux()) {
            return UrlReplace(url);
        } else {
            return url;
        }
    }

    /**
     * 判断当前系统是WINDOWS还是LINUX
     *
     * @param
     * @return true windows false linux
     */
    public static boolean WindowOrLinux() {
        if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
            return true;
        }
        return false;
    }

    /**
     * 如果是Windows系统，替换路径
     *
     * @param
     * @return
     */
    public static String UrlReplace(String url) {
        return url.replaceAll("/", "\\\\");
    }

    /**
     * 文件的拷贝
     *
     * @return boolean
     */
    public static void copyFile(String source, String desDirector) {
        FileInputStream input = null;
        FileOutputStream output = null;
        File sourceFile = new File(source);
        File directorFile = new File(desDirector);
        File directorDir = new File(directorFile.getParent());
        if (!directorDir.exists()) {
            directorDir.mkdirs();
        }
        try {
            input = new FileInputStream(sourceFile);
            output = new FileOutputStream(directorFile);
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = input.read(b)) != -1) {
                output.write(b, 0, len);
            }
            output.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
