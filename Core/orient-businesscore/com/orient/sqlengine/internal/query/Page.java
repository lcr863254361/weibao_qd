package com.orient.sqlengine.internal.query;

import java.io.Serializable;

/**
 * 分页类
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 11, 2012
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @Fields start : 开始的行号
     */
    public int start;
    /**
     * @Fields end : 结束行号
     */
    public int end;

    public Page(int start, int end) {
        this.start = start;
        this.end = end;
    }

}
