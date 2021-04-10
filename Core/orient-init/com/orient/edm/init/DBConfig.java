package com.orient.edm.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 数据库服务器信息
 */
@Component
public class DBConfig implements Serializable {

    private String dbUserName;

    private String dbPassword;

    private String dbSid;

    private String dbIp;


    public String getDbUserName() {
        return dbUserName;
    }
    @Value("${jdbc.username}")
    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }


    public String getDbPassword() {
        return dbPassword;
    }
    @Value("${jdbc.password}")
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }


    public String getDbSid() {
        return dbSid;
    }
    @Value("${jdbc.net}")
    public void setDbSid(String dbSid) {
        this.dbSid = dbSid;
    }


    public String getDbIp() {
        return dbIp;
    }
    @Value("${jdbc.ip}")
    public void setDbIp(String dbIp) {
        this.dbIp = dbIp;
    }

}
