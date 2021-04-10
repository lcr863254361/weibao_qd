package com.orient.modeldata.fileHandle.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件后处理集合
 *
 * @author enjoy
 * @creare 2016-05-12 15:27
 */
public class FileDecoratorContainer implements Serializable{

    private List<String> decoratorNames = new ArrayList<>();

    public List<String> getDecoratorNames() {
        return decoratorNames;
    }

    public void setDecoratorNames(List<String> decoratorNames) {
        this.decoratorNames = decoratorNames;
    }
}
