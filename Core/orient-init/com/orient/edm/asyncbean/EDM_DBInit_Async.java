package com.orient.edm.asyncbean;


/**
 * @author qjl
 * @ClassName EDM_DBinit_Async
 * <p>
 * 初始化数据库的所需信息
 * @date Jul 11, 2012
 */
public class EDM_DBInit_Async extends EDM_Object_Async {
    private String ip;//数据库地址
    private String port;//数据库端口号
    private String dbName;//数据库名称
    private String username;//数据库连接用户名
    private String password;//数据库连接密码

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
