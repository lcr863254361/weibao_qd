package com.orient.metamodel.pedigree;

/**
 * 图形的顶点边
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 28, 2012
 */
public class GraphEdge {

    /**
     * @Fields edgeType : 1表示 起点依赖终点 0表示起点不依赖终点
     */
    private int edgeType;
    /**
     * @Fields connected : 1是连接，0不连接
     */
    private int connected;
    /**
     * @Fields manyToMany : 顶点和终点之间的数据是不是多对多关联
     */
    private boolean manyToMany;

    GraphEdge(int edgeType, int connected) {
        this.edgeType = edgeType;
        this.connected = connected;
        manyToMany = false;
    }

    public int getEdgeType() {
        return edgeType;
    }

    public void setEdgeType(int edgeType) {
        this.edgeType = edgeType;
    }

    public int getConnected() {
        return connected;
    }

    public void setConnected(int connected) {
        this.connected = connected;
    }

    public boolean isManyToMany() {
        return manyToMany;
    }

    public void setManyToMany(boolean manyToMany) {
        this.manyToMany = manyToMany;
    }

}
