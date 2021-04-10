package com.orient.sqlengine.internal;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.*;
import java.util.Properties;

/**
 * 自增值生成器脚本-常用方法类.
 *
 * @author Erhu
 * @version 4.0
 * @since 2010-8-24
 */
public class AutoIncScriptTool {
    /**
     * 自增值生成器脚本调用方法
     *
     * @param className
     * @param method
     * @param arg_input 传入的参数
     * @return 经脚本处理后的输出结果
     * @throws
     */
    public static String invoke(final String className, String method, int arg_input) {
        // 检查文件夹下的同名class文件.
        String[] valid_files = null;
        try {
            String dir_path = AutoIncScriptTool.class.getResource("AutoIncScriptTool.class").toURI().getPath();
            if (dir_path != null) {
                dir_path = dir_path.substring(1, dir_path.lastIndexOf("/") + 1);
                valid_files = new File(dir_path).list(new FilenameFilter() {
                    // @Override
                    public boolean accept(File dir, String name) {
                        return name.indexOf(className) == 0 && name.endsWith(".class");
                    }
                });
            }
        } catch (URISyntaxException e2) {
            e2.printStackTrace();
        }
        // 取得当前最大的文件序号
        int max_fileSeqNumber = 1;
        for (String fileName : valid_files) {
            String fileName_excludeClassName = fileName.substring(className.length());
            String s = fileName_excludeClassName.substring(0, fileName_excludeClassName.indexOf(".class"));
            int tmp_number = 0;
            if (s != null && !s.equals(""))
                tmp_number = Integer.parseInt(s);
            if (tmp_number > max_fileSeqNumber)
                max_fileSeqNumber = tmp_number;
        }
        boolean changed = getScriptChanged(className);
        if (changed || valid_files.length == 0) {// 脚本已被修改,重新编译
            max_fileSeqNumber++;
            if (!compileScript(className, method, max_fileSeqNumber)) {
                System.err.println("编译失败!");
                return null;
            }
            if (!updateScriptStatus(className)) {
                System.err.println("更新脚本状态失败!");
                return null;
            }
            return getResult(className, arg_input, max_fileSeqNumber);
        } else {
            return getResult(className, arg_input, max_fileSeqNumber);
        }
    }


    /**
     * 编译java文件
     *
     * @param _className
     * @param method
     * @param maxNumber
     * @return boolean
     * @Method: compileScript
     */
    private static boolean compileScript(String _className, String method, int maxNumber) {
        String className = _className;
        FileChannel write_channel = null;
        String java_extend = maxNumber + ".java";
        String class_extend = maxNumber + ".class";
        try {
            /* web工程中寻找路径的方式,对于控制台程序,需要手动拷贝class文件. */
            String path = null;
            path = AutoIncScriptTool.class.getResource("AutoIncScriptTool.class").toURI().getPath();
            if (path != null) {
                path = path.substring(1, path.lastIndexOf("/") + 1);
            }
            String temp_path = path + className;
            String java_path = temp_path + java_extend;
            String class_path = temp_path + class_extend;

            StringBuilder source_builder = new StringBuilder();
            source_builder.append("package com.util;\n");
            source_builder.append("public class " + className + maxNumber + " {\n\t");
            source_builder.append(method);
            source_builder.append("\n}");

			/* 将代码写入java文件 */
            File java_file = new File(java_path);
            File class_file = new File(class_path);

            if (java_file.exists() && java_file.delete())
                java_file.createNewFile();
            java_file.deleteOnExit();// 系统退出时删除标准数据文件
            if (class_file.exists() && class_file.delete())
                class_file.createNewFile();
            class_file.deleteOnExit();

            write_channel = new FileOutputStream(java_file).getChannel();
            write_channel.write(ByteBuffer.wrap(source_builder.toString().getBytes()));

			/* 编译java文件 */
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, java_path);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (write_channel != null)
                    write_channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 调用Java的方法
     *
     * @param _className
     * @param arg_input
     * @param arg_input
     * @return
     */
    private static String getResult(String _className, int arg_input, int maxNumber) {
        /* 通过反射调用方法 */
        Class<?> c;
        try {
            c = Class.forName("com.util." + _className + maxNumber);
            Method m = c.getMethod("convert", int.class);
            return m.invoke(c.newInstance(), arg_input).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 手动获取数据库连接
     *
     * @return Connection
     * @Method: getConn
     */
    private static Connection getConn() {
        InputStream is = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Properties prop = new Properties();
            String path = AutoIncScriptTool.class.getResource("AutoIncScriptTool.class").toURI().getPath();
            if (path != null) {
                path = path.substring(1, path.lastIndexOf("classes") - 1);
            }
            File file = new File(path + "/jdbc.properties");
            is = new FileInputStream(file);
            prop.load(is);

            return DriverManager.getConnection(prop.getProperty("jdbc.url"), prop
                    .getProperty("jdbc.username"), prop.getProperty("jdbc.password"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询脚本是否被更改
     *
     * @param _className
     * @return boolean
     * @Method: getScriptChanged
     */
    private static boolean getScriptChanged(String _className) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            // 检查当前脚本是否已经被更改
            String sql = "SELECT CHANGED FROM CWM_SEQGENERATOR WHERE NAME = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, _className);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString(1) == null)
                    return true;
                else
                    return (rs.getString(1).equalsIgnoreCase("TRUE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 更新脚本属性
     *
     * @param _className
     * @return boolean
     * @Method: updateScriptStatus
     */
    private static boolean updateScriptStatus(String _className) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConn();
            String sql = "UPDATE CWM_SEQGENERATOR SET CHANGED = 'FALSE' WHERE NAME = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, _className);
            return pstmt.executeUpdate() >= 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}