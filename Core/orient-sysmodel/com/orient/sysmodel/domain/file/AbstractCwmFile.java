package com.orient.sysmodel.domain.file;

// default package

import java.util.Date;

import com.orient.sysmodel.domain.user.User;

/**
 * AbstractCwmFile entity provides the base persistence definition of the
 * CwmFile entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractCwmFile extends
        com.orient.sysmodel.domain.BaseBean implements java.io.Serializable {

    public final static String UPLOAD_STATUS_WAIT = "-1";
    public final static String UPLOAD_STATUS_PROCESS = "-2";
    public final static String UPLOAD_STATUS_FAIL = "-3";
    public final static String UPLOAD_STATUS_SUCCESS = "-4";
    public final static String UPLOAD_STATUS_STOP = "-5";

    // Fields

    private String fileid;// 主键ID，自增
    private String schemaid;// CWM_SCHEMA, 业务库
    private String tableid;// CWM_TABLES, 表
    private String filename;// 文件显示名称,如果已经转化为大数据文件，则为分布式的文件路径
    private String filedescription;// 文件描述
    private String filetype;// 文件类型(后缀名), 如果已经转化为大数据文件，则为OrientHDFS
    // 文件位置, 相对位置, 相对fileftpserver.properties中配置的路径,如果已经转化为大数据文件，则为分布式的文件路径
    private String filelocation;
    private Long filesize;// 文件大小, 字节数
    private String parseRule;// 备用
    private String uploadUserId;//上传用户ID
    private User uploadUser;
    private Date uploadDate;// 上传日期
    private String deleteUserId;//删除用户ID
    private User deleteUser;
    private Date deleteDate;// 删除日期
    private String dataid;// 数据记录的ID
    private String finalname;// 文件存放的最终文件名
    private String edition;// 版本号
    private Long isValid;// 是否有效, 1 有效,0 无效/已删除
    private String filesecrecy;// 文件密级
    private String uploadUserMac;// 上传人的MAC地址
    private String uploadStatus;// 上传状态，0表示未完成，1表示上传完成
    private String fileFolder;// 文件夹
    private Long isFoldFile;// 是否包含文件夹信息，默认0 不包含文件夹信息，1表示该文件包含文件夹信息
    private String folderSerial;// 文件夹序列，每选择一个文件夹会生成一个唯一序列，标记文件所属同一文件夹
    private Long isWholeSearch;// 文件是否进行全文检索,0为不能，1为能，默认为0
    private Long isDataFile;// 文件是否能进行数据处理，0为不能，1为能，默认为0
    private String cwmFolderId; // 所属文件夹
    private String converState; // 文件状态  5表示存入了hbase

    private String fileCatalog = "common";//文件类型 COMMON：普通文件;ODS：ODS文件;BIGTABLE:大数据文件
    private String fileMark;//文件标签

    private String uploadUserName;// 用于查询
    private String uploadDateS;
    private String uploadDateE;


    public static String CONVERT_UNSTART = "UNSTART";
    public static String CONVERT_ING = "ING";
    public static String CONVERT_DONE = "DONE";

    public static String HBASETYPE = "OrientHDFS";
    // Constructors

    /**
     * default constructor
     */
    public AbstractCwmFile() {
    }

    /**
     * full constructor
     */
    public AbstractCwmFile(String schemaid, String tableid, String filename,
                           String filedescription, String filetype, String filelocation,
                           Long filesize, String parseRule, User uploadUser, Date uploadDate,
                           User deleteUser, Date deleteDate, String dataid, String finalname,
                           String edition, Long isValid, String filesecrecy,
                           String uploadUserMac, String uploadStatus, String fileFolder,
                           Long isFoldFile, String folderSerial, Long isWholeSearch,
                           Long isDataFile, String cwmFolderId, String converState) {
        this.schemaid = schemaid;
        this.tableid = tableid;
        this.filename = filename;
        this.filedescription = filedescription;
        this.filetype = filetype;
        this.filelocation = filelocation;
        this.filesize = filesize;
        this.parseRule = parseRule;
        this.uploadUser = uploadUser;
        this.uploadDate = uploadDate;
        this.deleteUser = deleteUser;
        this.deleteDate = deleteDate;
        this.dataid = dataid;
        this.finalname = finalname;
        this.edition = edition;
        this.isValid = isValid;
        this.filesecrecy = filesecrecy;
        this.uploadUserMac = uploadUserMac;
        this.uploadStatus = uploadStatus;
        this.fileFolder = fileFolder;
        this.isFoldFile = isFoldFile;
        this.folderSerial = folderSerial;
        this.isWholeSearch = isWholeSearch;
        this.isDataFile = isDataFile;
        this.cwmFolderId = cwmFolderId;
        this.converState = converState;
    }

    // Property accessors

    public String getFileid() {
        return this.fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getSchemaid() {
        return this.schemaid;
    }

    public void setSchemaid(String schemaid) {
        this.schemaid = schemaid;
    }

    public String getTableid() {
        return this.tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiledescription() {
        return this.filedescription;
    }

    public void setFiledescription(String filedescription) {
        this.filedescription = filedescription;
    }

    public String getFiletype() {
        return this.filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFilelocation() {
        return this.filelocation;
    }

    public void setFilelocation(String filelocation) {
        this.filelocation = filelocation;
    }

    public Long getFilesize() {
        return this.filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public String getParseRule() {
        return this.parseRule;
    }

    public void setParseRule(String parseRule) {
        this.parseRule = parseRule;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public User getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(User uploadUser) {
        this.uploadUser = uploadUser;
    }

    public User getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(User deleteUser) {
        this.deleteUser = deleteUser;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public Date getDeleteDate() {
        return this.deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getDataid() {
        return this.dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getFinalname() {
        return this.finalname;
    }

    public void setFinalname(String finalname) {
        this.finalname = finalname;
    }

    public String getEdition() {
        return this.edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Long getIsValid() {
        return this.isValid;
    }

    public void setIsValid(Long isValid) {
        this.isValid = isValid;
    }

    public String getFilesecrecy() {
        return this.filesecrecy;
    }

    public void setFilesecrecy(String filesecrecy) {
        this.filesecrecy = filesecrecy;
    }

    public String getUploadUserMac() {
        return this.uploadUserMac;
    }

    public void setUploadUserMac(String uploadUserMac) {
        this.uploadUserMac = uploadUserMac;
    }

    public String getUploadStatus() {
        return this.uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getFileFolder() {
        return this.fileFolder;
    }

    public void setFileFolder(String fileFolder) {
        this.fileFolder = fileFolder;
    }

    public Long getIsFoldFile() {
        return this.isFoldFile;
    }

    public void setIsFoldFile(Long isFoldFile) {
        this.isFoldFile = isFoldFile;
    }

    public String getFolderSerial() {
        return this.folderSerial;
    }

    public void setFolderSerial(String folderSerial) {
        this.folderSerial = folderSerial;
    }

    public Long getIsWholeSearch() {
        return this.isWholeSearch;
    }

    public void setIsWholeSearch(Long isWholeSearch) {
        this.isWholeSearch = isWholeSearch;
    }

    public Long getIsDataFile() {
        return this.isDataFile;
    }

    public void setIsDataFile(Long isDataFile) {
        this.isDataFile = isDataFile;
    }

    /**
     * uploadUserName
     *
     * @return the uploadUserName
     * @since CodingExample Ver 1.0
     */

    public String getUploadUserName() {
        return uploadUserName;
    }

    /**
     * uploadUserName
     *
     * @param uploadUserName the uploadUserName to set
     * @since CodingExample Ver 1.0
     */

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    /**
     * uploadDateS
     *
     * @return the uploadDateS
     * @since CodingExample Ver 1.0
     */

    public String getUploadDateS() {
        return uploadDateS;
    }

    /**
     * uploadDateS
     *
     * @param uploadDateS the uploadDateS to set
     * @since CodingExample Ver 1.0
     */

    public void setUploadDateS(String uploadDateS) {
        this.uploadDateS = uploadDateS;
    }

    /**
     * uploadDateE
     *
     * @return the uploadDateE
     * @since CodingExample Ver 1.0
     */

    public String getUploadDateE() {
        return uploadDateE;
    }

    /**
     * uploadDateE
     *
     * @param uploadDateE the uploadDateE to set
     * @since CodingExample Ver 1.0
     */

    public void setUploadDateE(String uploadDateE) {
        this.uploadDateE = uploadDateE;
    }

    public String getCwmFolderId() {
        return cwmFolderId;
    }

    public void setCwmFolderId(String cwmFolderId) {
        this.cwmFolderId = cwmFolderId;
    }

    public String getConverState() {
        return converState;
    }

    public void setConverState(String converState) {
        this.converState = converState;
    }

    public String getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(String uploadUserId) {
        this.uploadUserId = uploadUserId;
    }

    public String getDeleteUserId() {
        return deleteUserId;
    }

    public void setDeleteUserId(String deleteUserId) {
        this.deleteUserId = deleteUserId;
    }

    public String getFileCatalog() {
        return fileCatalog;
    }

    public void setFileCatalog(String fileCatalog) {
        this.fileCatalog = fileCatalog;
    }

    public String getFileMark() {
        return fileMark;
    }

    public void setFileMark(String fileMark) {
        this.fileMark = fileMark;
    }
}