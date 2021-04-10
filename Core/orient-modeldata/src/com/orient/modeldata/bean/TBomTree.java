package com.orient.modeldata.bean;

import java.io.Serializable;

/**
 * TBom树描述
 *
 * @author enjoy
 * @creare 2016-05-17 9:42
 */
public class TBomTree extends BaseNode implements Serializable{

    private Long orderSign;

    public Long getOrderSign() {
        return orderSign;
    }

    public void setOrderSign(Long orderSign) {
        this.orderSign = orderSign;
    }
}

