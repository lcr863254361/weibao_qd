package com.orient.metamodel.pedigree;

import com.orient.metamodel.operationinterface.ITable;

import java.util.ArrayList;

/**
 * Schema谱系图中模型顶点
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 27, 2012
 */
public class TableVertex {
    /**
     * @Fields wasVisited : 是否遍历过
     */
    boolean wasVisited;
    /**
     * @Fields allVisitedList : 从当前节点出发已访问过的顶点
     */
    ArrayList<Integer> allVisitedList;
    /**
     * @Fields table : 顶点关联的数据类
     */
    ITable table;

    TableVertex(ITable table) {
        this.table = table;
        wasVisited = false;
    }

    public void setAllVisitedList(ArrayList<Integer> allVisitedList) {
        this.allVisitedList = allVisitedList;
    }

    public ArrayList<Integer> getAllVisitedList() {
        return allVisitedList;
    }

    public boolean WasVisited() {
        return wasVisited;
    }

    public void setWasVisited(boolean wasVisited) {
        this.wasVisited = wasVisited;
    }

    public void setVisited(int j) {
        allVisitedList.set(j, 1);
    }

    public ITable getTable() {
        return table;
    }

}
