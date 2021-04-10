package com.orient.modeldata.fileHandle.decorator;

import com.orient.modeldata.fileHandle.IModelFileHandle;
import com.orient.modeldata.fileHandle.bean.FileHandleTarget;

/**
 * 文件处理装饰抽象类
 *
 * @author enjoy
 * @creare 2016-05-11 16:01
 */
public abstract class ModelFileDecorator implements IModelFileHandle {

    protected IModelFileHandle decorator;

    public void setDecorator(IModelFileHandle decorator) {
        this.decorator = decorator;
    }

    @Override
    public void doHandleFile(FileHandleTarget fileHandleTarget) {
        if (decorator != null) {
            decorator.doHandleFile(fileHandleTarget);
        }
    }
}
