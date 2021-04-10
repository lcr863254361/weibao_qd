package com.orient.modeldata.fileHandle.impl;

import com.orient.modeldata.fileHandle.IModelFileHandle;
import com.orient.modeldata.fileHandle.bean.FileHandleTarget;
import org.springframework.stereotype.Component;

/**
 * 文件处理类
 *
 * @author enjoy
 * @creare 2016-05-11 15:59
 */
@Component
public class ModelFileHandle implements IModelFileHandle {

    @Override
    public void doHandleFile(FileHandleTarget fileHandleTarget) {
        //任意类型文件处理 如加密等
    }
}
