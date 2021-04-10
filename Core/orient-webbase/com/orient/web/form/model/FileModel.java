package com.orient.web.form.model;

import com.orient.web.base.CommonResponseData;

import java.io.Serializable;

/**
 * 前后台交互的文件描述
 *
 * @author enjoy
 * @creare 2016-03-29 10:23
 */
public class FileModel extends CommonResponseData implements Serializable{

    String fileid;
    String filename;
    String finalname;
    String filePath;
    String filedescription;
    String filesize;
    String uploadUserName;
    String uploadDate;
    String filesecrecy;
    String fileCatalog;

    //缩略图名称
    String sFilePath;

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFinalname() {
        return finalname;
    }

    public void setFinalname(String finalname) {
        this.finalname = finalname;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFiledescription() {
        return filedescription;
    }

    public void setFiledescription(String filedescription) {
        this.filedescription = filedescription;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getUploadUserName() {
        return uploadUserName;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFilesecrecy() {
        return filesecrecy;
    }

    public void setFilesecrecy(String filesecrecy) {
        this.filesecrecy = filesecrecy;
    }

    public String getsFilePath() {
        return sFilePath;
    }

    public void setsFilePath(String sFilePath) {
        this.sFilePath = sFilePath;
    }

    public String getFileCatalog() {
        return fileCatalog;
    }

    public void setFileCatalog(String fileCatalog) {
        this.fileCatalog = fileCatalog;
    }
}
