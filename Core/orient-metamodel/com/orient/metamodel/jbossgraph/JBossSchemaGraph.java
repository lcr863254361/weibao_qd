package com.orient.metamodel.jbossgraph;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IRelationColumn;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;
import org.jboss.util.graph.Edge;
import org.jboss.util.graph.Graph;
import org.jboss.util.graph.Vertex;
import org.jboss.util.graph.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-12-07 10:31
 */
public class JBossSchemaGraph {

    private Graph<ITable> graphEx;
    private Map<String, Vertex> idToVertexMap = new HashMap<>();

    private Map<Edge, Boolean> edgeType = new HashMap<>();

    public JBossSchemaGraph(ISchema schema) {
        graphEx = new Graph();
        schema.getAllTables().forEach(this::addVertex);
        schema.getAllTables().forEach(table -> {
            List<IColumn> relCols = table.getRelationColumns();
            relCols.forEach(column -> {
                IRelationColumn relation = column.getRelationColumnIF();
                if (relation.getIsFK() == 1) {
                    schema.getAllTables().forEach(subtable -> {
                        if (relation.getRefTable().getId().equals(
                                subtable.getId())) {
                            boolean manyToMany = false;
                            if (relation.getRelationType() == 4) {
                                manyToMany = true;
                            }
                            //addEdge(table, subtable, 1, manyToMany);
                            addEdge(subtable, table, 0, manyToMany);
                        }
                    });
                }
            });
        });
    }

    /**
     * 增加连线
     *
     * @param source      起点
     * @param destination 终点
     * @param direction   连线方向 1：正向；0：反向
     * @param manyToMany  是否多对多
     */
    private void addEdge(ITable source, ITable destination, int direction, boolean manyToMany) {
        graphEx.addEdge(idToVertexMap.get(source.getId()), idToVertexMap.get(destination.getId()), direction);
        edgeType.put(graphEx.getEdges().get(graphEx.getEdges().size() - 1), manyToMany);
    }

    /**
     * 增加顶点
     *
     * @param table
     */
    private void addVertex(ITable table) {
        Vertex<ITable> vertex = new Vertex<>(table.getId(), table);
        graphEx.addVertex(vertex);
        idToVertexMap.put(table.getId(), vertex);
    }

    public Map<String, Vertex> getIdToVertexMap() {
        return idToVertexMap;
    }

    public Map<Edge, Boolean> getEdgeType() {
        return edgeType;
    }

    public List<Edge> getShortestModelRelation(ITable source, ITable destinion) {
        graphEx.breadthFirstSearch(idToVertexMap.get(source.getId()), new Visitor<ITable>() {
            @Override
            public void visit(Graph<ITable> graph, Vertex<ITable> vertex) {

            }
        });
        return new ArrayList<>();
    }

}
