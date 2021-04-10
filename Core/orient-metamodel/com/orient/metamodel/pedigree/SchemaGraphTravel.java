package com.orient.metamodel.pedigree;

import com.orient.metamodel.operationinterface.ISchemaGraphTravel;
import com.orient.metamodel.operationinterface.ITable;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Schema图的遍历
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 27, 2012
 */
public class SchemaGraphTravel implements ISchemaGraphTravel {

    private static final Logger log = Logger.getLogger(SchemaGraphTravel.class);
    private SchemaGraph graph;
    Stack<Integer> theStack;
    List<List<TableEdge>> tableEdgeListList = null;

    public SchemaGraphTravel(SchemaGraph graph) {
        this.graph = graph;
    }

    /**
     *
     * @param startTable
     * @param endTable
     * @return
     */
    public List<List<TableEdge>> travel(ITable startTable, ITable endTable) {

        int start = graph.getVertexIndex(startTable);
        int end = graph.getVertexIndex(endTable);
        if (start != end && !isConnectable(start, end)) {
            log.error(startTable.getDisplayName() + "与"
                    + endTable.getTableName() + "没有业务关系");
            return tableEdgeListList;
        }
        tableEdgeListList = new ArrayList<>();
        if (start == end) {//自身到自身
            List<TableEdge> tableEdgeList = new ArrayList<>();
            TableEdge edge = new TableEdge(startTable, endTable, 2);
            edge.setManyToMany(false);
            tableEdgeList.add(edge);
            tableEdgeListList.add(tableEdgeList);
            return tableEdgeListList;
        }

        //初始化节点的访问状态
        ArrayList<Integer> tempList;
        for (int j = 0; j < graph.getVertexArray().length; j++) {
            tempList = new ArrayList<>();
            for (int i = 0; i < graph.getVertexArray().length; i++) {
                tempList.add(0);
            }
            graph.getVertexArray()[j].setAllVisitedList(tempList);
        }
        theStack = new Stack<>();
        graph.getVertexArray()[start].setWasVisited(true);
        theStack.push(start);
        while (!theStack.isEmpty()) {
            int v = getAdjUnvisitedVertex(theStack.peek());
            if (v == -1) {
                tempList = new ArrayList<>();
                for (int j = 0; j < graph.getVertexArray().length; j++) {
                    tempList.add(0);
                }
                graph.getVertexArray()[theStack.peek()]
                        .setAllVisitedList(tempList);// 把栈顶节点访问过的节点链表清空
                theStack.pop();
            } else {// if it exists
                theStack.push(v); // push it
            }
            if (!theStack.isEmpty() && end == theStack.peek()) {
                graph.getVertexArray()[end].setWasVisited(false); // mark it
                fillTheStack(theStack);
                theStack.pop();
            }
        }
        return tableEdgeListList;
    }


    /**
     * 与节点v相邻，并且这个节点没有被访问到，并且这个节点不在栈中
     *
     * @param v
     * @return int
     * @Method: getAdjUnvisitedVertex
     */
    private int getAdjUnvisitedVertex(int v) {
        ArrayList<Integer> arrayList = graph.getVertexArray()[v]
                .getAllVisitedList();
        for (int j = 0; j < graph.getVertexArray().length; j++) {
            if (graph.getAdjMat()[v][j].getConnected() == 1 && arrayList.get(j) == 0
                    && !theStack.contains(j)) {
                graph.getVertexArray()[v].setVisited(j);
                return j;
            }
        }
        return -1;
    }

    /**
     * 判断两个节点是否能连通
     *
     * @param start
     * @param end
     * @return boolean
     * @Method: isConnectable
     */
    private boolean isConnectable(int start, int end) {
        ArrayList<Integer> queue = new ArrayList<>();
        ArrayList<Integer> visited = new ArrayList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            for (int j = 0; j < graph.getVertexArray().length; j++) {
                if (graph.getAdjMat()[start][j].getConnected() == 1 && !visited.contains(j)) {
                    queue.add(j);
                }
            }
            if (queue.contains(end)) {
                return true;
            } else {
                visited.add(queue.get(0));
                queue.remove(0);
                if (!queue.isEmpty()) {
                    start = queue.get(0);
                }
            }
        }
        return false;
    }

    /**
     * 保存遍历路径
     *
     * @param theStack2 点的下标栈
     * @Method: fillTheStack
     */
    private void fillTheStack(Stack<Integer> theStack2) {
        List<TableEdge> tableEdgeList = new ArrayList<>();
        for (int i = 0; i < theStack2.size() - 1; i++) {
            ITable start = graph.getVertexTable(theStack2.get(i));
            ITable end = graph.getVertexTable(theStack2.get(i + 1));
            int edgeType = graph.getAdjMat()[theStack2.get(i)][theStack2.get(i + 1)].getEdgeType();
            boolean manyToMany = graph.getAdjMat()[theStack2.get(i)][theStack2.get(i + 1)].isManyToMany();
            TableEdge edge = new TableEdge(start, end, edgeType);
            edge.setManyToMany(manyToMany);
            tableEdgeList.add(edge);
        }
        tableEdgeListList.add(tableEdgeList);
    }

}
