package com.orient.metamodel.pedigree;

import com.orient.metamodel.operationinterface.ITable;

/**
 * 连接模型的边
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 28, 2012
 */
public class TableEdge {

    /**
     * @Fields start : 起点模型
     */
    private ITable start;
    /**
     * @Fields end : 终点模型
     */
    private ITable end;
    /**
     * @Fields edgeType : 1表示 起点模型依赖终点模型, 0表示起点模型不依赖终点模型
     */
    private int edgeType;
    /**
     * @Fields manyToMany : 顶点和终点之间的数据是不是多对多关联
     */
    private boolean manyToMany;

    TableEdge(ITable start, ITable end, int edgeType) {
        this.start = start;
        this.end = end;
        this.edgeType = edgeType;
        manyToMany = false;
    }

    public TableEdge() {

    }

    public ITable getStart() {
        return start;
    }

    public void setStart(ITable start) {
        this.start = start;
    }

    public ITable getEnd() {
        return end;
    }

    public void setEnd(ITable end) {
        this.end = end;
    }

    public int getEdgeType() {
        return edgeType;
    }

    public void setEdgeType(int edgeType) {
        this.edgeType = edgeType;
    }

    public boolean isManyToMany() {
        return manyToMany;
    }

    public void setManyToMany(boolean manyToMany) {
        this.manyToMany = manyToMany;
    }

}
