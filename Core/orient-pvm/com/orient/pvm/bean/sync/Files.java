package com.orient.pvm.bean.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */
public class Files {
    List<PVMFile> files = new ArrayList<>();

    public List<PVMFile> getFiles() {
        return files;
    }

    public void setFiles(List<PVMFile> files) {
        this.files = files;
    }
}
