package com.orient.modeldata.fileHandle.bean;

import com.orient.sysmodel.domain.file.CwmFile;

import java.io.Serializable;

/**
 * 文件处理对象
 *
 * @author enjoy
 * @creare 2016-05-11 16:21
 */
public class FileHandleTarget implements Serializable {

    public FileHandleTarget() {

    }

    public FileHandleTarget(String modelId, String dataId, CwmFile fileDesc) {
        this.modelId = modelId;
        this.dataId = dataId;
        this.fileDesc = fileDesc;
    }

    private String modelId;

    private String dataId;

    private CwmFile fileDesc;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public CwmFile getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(CwmFile fileDesc) {
        this.fileDesc = fileDesc;
    }
}
