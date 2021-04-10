package com.orient.edm.init;

import java.io.File;

/**
 * 文件服务运行参数
 */
public class FileServerConfig {

    //文件存放目录
    private String ftpHome;

    //临时文件存放目录
    private String temp;

    //端口号
    private int port;

    //是否开启日志
    private boolean logStatus;

    //日志存储目录
    private String logDir;

    //最大会话数
    private int maxSessions;

    //超时时间
    private int soTimeout;

    //超时检查时间间隔
    private int overTimeIntervalCheckTime;

    //备份文件存储路径
    private String backPath;

    //是否支持视频在线播放
    private Boolean enableVideoPreview;

    //hls目录 (HTTP Live Streaming)
    private String hlsHome;

    //mp4目录
    private String mp4Home;

    //nginx服务端口
    private String nginxPort;

    private String pvmTemplateHome;

    private String encrypt;

    private String decrypt;

    public Boolean getEnableVideoPreview() {
        return enableVideoPreview;
    }

    public void setEnableVideoPreview(Boolean enableVideoPreview) {
        this.enableVideoPreview = enableVideoPreview;
    }

    public String getHlsHome() {
        return hlsHome;
    }

    public void setHlsHome(String hlsHome) {
        this.hlsHome = hlsHome;
    }

    public String getNginxPort() {
        return nginxPort;
    }

    public void setNginxPort(String nginxPort) {
        this.nginxPort = nginxPort;
    }

    /**
     * @return 文件存储目录
     */
    public String getFtpHome() {
        //判断文件夹是否存在
        checkExist(ftpHome);
        return ftpHome;
    }

    /**
     * @param ftpHome the ftpHome to set
     */
    public void setFtpHome(String ftpHome) {
        this.ftpHome = ftpHome;
    }

    /**
     * @return 临时文件存储目录
     */
    public String getTemp() {
        checkExist(temp);
        return temp;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(String temp) {
        this.temp = temp;
    }

    /**
     * @return 端口号
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return 是否开启日志
     */
    public boolean isLogStatus() {
        return logStatus;
    }

    /**
     * @param logStatus the logStatus to set
     */
    public void setLogStatus(boolean logStatus) {
        this.logStatus = logStatus;
    }

    /**
     * @return 日志目录
     */
    public String getLogDir() {
        checkExist(logDir);
        return logDir;
    }

    /**
     * @param logDir the logDir to set
     */
    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    /**
     * @return 最大会话数
     */
    public int getMaxSessions() {
        return maxSessions;
    }

    /**
     * @param maxSessions the maxSessions to set
     */
    public void setMaxSessions(int maxSessions) {
        this.maxSessions = maxSessions;
    }

    /**
     * @return 超时时间
     */
    public int getSoTimeout() {
        return soTimeout;
    }

    /**
     * @param soTimeout the soTimeout to set
     */
    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    /**
     * @return 超时检查时间间隔
     */
    public int getOverTimeIntervalCheckTime() {
        return overTimeIntervalCheckTime;
    }

    /**
     * @param overTimeIntervalCheckTime the overTimeIntervalCheckTime to set
     */
    public void setOverTimeIntervalCheckTime(int overTimeIntervalCheckTime) {
        this.overTimeIntervalCheckTime = overTimeIntervalCheckTime;
    }

    public String getPvmTemplateHome() {
        checkExist(pvmTemplateHome);
        return pvmTemplateHome;
    }

    public void setPvmTemplateHome(String pvmTemplateHome) {
        this.pvmTemplateHome = pvmTemplateHome;
    }

    /**
     * @return 备份文件存储路径
     */
    public String getBackPath() {
        checkExist(backPath);
        return backPath;
    }

    /**
     * @param backPath the backPath to set
     */
    public void setBackPath(String backPath) {
        this.backPath = backPath;
    }

    void checkExist(String path) {
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
    }

    public String getMp4Home() {
        return mp4Home;
    }

    public void setMp4Home(String mp4Home) {
        this.mp4Home = mp4Home;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getDecrypt() {
        return decrypt;
    }

    public void setDecrypt(String decrypt) {
        this.decrypt = decrypt;
    }
}
