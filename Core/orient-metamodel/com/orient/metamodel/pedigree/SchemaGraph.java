package com.orient.metamodel.pedigree;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IRelationColumn;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;

import java.util.List;

/**
 * Schema的谱系图
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 27, 2012
 */
public class SchemaGraph {

    /**
     * @Fields adjMat : 邻接矩阵
     */
    private GraphEdge adjMat[][];
    /**
     * @Fields vertexArray : 顶点数组
     */
    private TableVertex[] vertexArray;
    /**
     * @Fields nVerts : 顶点的数量
     */
    private int nVerts;
    int i = 0;
    int j = 0;

    /**
     * 初始化Schema谱系图
     *
     * @param schema
     */
    public SchemaGraph(ISchema schema) {
        int verNum = schema.getAllTables().size();
        adjMat = new GraphEdge[verNum][verNum];
        vertexArray = new TableVertex[verNum];
        nVerts = 0;
        // 初始化邻接矩阵
        for (i = 0; i < verNum; i++) {
            for (j = 0; j < verNum; j++) {
                adjMat[i][j] = new GraphEdge(0, 0);
            }
        }
        // 初始化顶点
        for (ITable table : schema.getAllTables()) {
            addVertex(table);
        }
        // 边的初始化
        for (int m = 0; m < verNum; m++) {
            ITable table = schema.getAllTables().get(m);
            List<IColumn> relCols = table.getRelationColumns();
            for (IColumn relCol : relCols) {
                IRelationColumn relation = relCol.getRelationColumnIF();
                if (relation.getIsFK() == 1) {
                    for (int n = 0; n < verNum; n++) {
                        ITable subtable = schema.getAllTables().get(n);
                        if (relation.getRefTable().getId().equals(
                                subtable.getId())) {
                            boolean manyToMany = false;
                            if (relation.getRelationType() == 4) {
                                manyToMany = true;
                            }
                            addEdge(m, n, 1, manyToMany);
                            addEdge(n, m, 0, manyToMany);
                        }
                    }
                }
            }
        }
    }

    /**
     * 添加边
     *
     * @param start      起点
     * @param end        终点
     * @param edgeType   1表示 起点依赖终点 0表示起点不依赖终点
     * @param manyToMany 是不是多对多关系
     */
    private void addEdge(int start, int end, int edgeType, boolean manyToMany) {// 有向图，添加边
        adjMat[start][end].setConnected(1);
        adjMat[start][end].setEdgeType(edgeType);
        adjMat[start][end].setManyToMany(manyToMany);
    }

    /**
     * 创建模型顶点
     *
     * @param table
     */
    private void addVertex(ITable table) {
        vertexArray[nVerts++] = new TableVertex(table);// 添加点
    }

    /**
     * 返回模型在图中所对应的位置
     *
     * @param table
     * @return int
     */
    public int getVertexIndex(ITable table) {
        int index = -1;
        for (int m = 0; m < vertexArray.length; m++) {
            if (vertexArray[m].getTable().getId().equals(table.getId())) {
                index = m;
                break;
            }
        }
        return index;
    }

    /**
     * 返回模型顶点数组
     *
     * @return TableVertex[]
     */
    public TableVertex[] getVertexArray() {
        return vertexArray;
    }

    /**
     * 返回图的连接矩阵
     *
     * @return GraphEdge[][]
     */
    public GraphEdge[][] getAdjMat() {
        return adjMat;
    }

    /**
     * 返回顶点号所对应的模型
     *
     * @param i
     * @return ITable
     */
    public ITable getVertexTable(int i) {
        return vertexArray[i].getTable();
    }

}
