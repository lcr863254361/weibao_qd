package com.orient.history.core.expression.context;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class Context implements Serializable{

    private List<String> inputList;

    public List<String> getInputList() {
        return inputList;
    }

    public void setInputList(List<String> inputList) {
        this.inputList = inputList;
    }

    public Context(List<String> inputList) {
        this.inputList = inputList;
    }
}
