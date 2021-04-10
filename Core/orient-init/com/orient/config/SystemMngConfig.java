package com.orient.config;

/**
 * ${DESCRIPTION}
 *
 * @author panduanduan
 * @create 2017-04-14 9:53 AM
 */
public class SystemMngConfig extends ConfigInfo {

    public static String FILESERVICE_IP;
    public static String FILESERVICE_PORT;
    public static String FILESERVICE_CONTEXT;
    public static String SYSTEM_MAIL_SENDER;

    static {
        FILESERVICE_IP = getPropertyValueConfigured("orient.tdm.microservice.fileservice.ip", "config.properties", "");
        FILESERVICE_PORT = getPropertyValueConfigured("orient.tdm.microservice.fileservice.port", "config.properties", "");
        FILESERVICE_CONTEXT = getPropertyValueConfigured("orient.tdm.microservice.fileservice.context", "config.properties", "");
        SYSTEM_MAIL_SENDER = getPropertyValueConfigured("orient.mail.sender.user", "mail.properties", "");
    }
}
