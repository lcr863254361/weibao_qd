package com.orient.metamodel.dijkstra;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IRelationColumn;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.metamodel.pedigree.TableEdge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-15 10:02
 */
public class DijikstraSchemaTravel implements Serializable, IDijikstraSchemaTravel {

    private DijkstraSchema graph;

    public DijikstraSchemaTravel(DijkstraSchema graph) {
        this.graph = graph;
    }

    @Override
    public List<TableEdge> getMinStep(ITable start, ITable end) {
        List<TableEdge> retVal = new ArrayList<>();
        Distance distance = new DistanceDijkstraImpl();
        int startPosition = graph.getTablePositation().get(start);
        int endPosition = graph.getTablePositation().get(end);
        MinStep step = distance.getMinStep(startPosition, endPosition, graph.getStepLength());
        if (step.isReachable()) {
            step.getStep().forEach(position -> {
                ITable table = graph.getTablePositationInverse().get(position);
                TableEdge tableEdge = new TableEdge();
                tableEdge.setStart(table);
                retVal.add(tableEdge);
            });
        }
        //init relation
        initRelation(retVal);
        //remove last one
        if(retVal.size() > 0){
            retVal.remove(retVal.size() - 1);
        }
        return retVal;
    }

    private void initRelation(List<TableEdge> retVal) {
        TableEdge[] last = new TableEdge[]{null};
        retVal.forEach(tableEdge -> {
            if (last[0] != null) {
                initRelation(last[0].getStart(), tableEdge.getStart(), last[0]);
                last[0].setEnd(tableEdge.getStart());
            }
            last[0] = tableEdge;
        });
    }

    private void initRelation(ITable start, ITable end, TableEdge tableEdge) {
        Long count = start.getRelationColumns().stream().filter(rlc -> null != rlc.getRelationColumnIF() && rlc.getRelationColumnIF().getIsFK() == 1
                && rlc.getRelationColumnIF().getRefTable().getId().equals(end.getId())).count();
        ITable oneTable = count == 0 ? start : end;
        ITable manyTable = count == 1 ? start : end;
        List<IColumn> relCols = manyTable.getRelationColumns();
        boolean manyToMany = false;
        for (IColumn relCol : relCols) {
            IRelationColumn relation = relCol.getRelationColumnIF();
            if (relation.getIsFK() == 1) {
                if (relation.getRefTable().getId().equals(
                        oneTable.getId())) {
                    if (relation.getRelationType() == 4) {
                        manyToMany = true;
                    }

                }
            }
        }
        tableEdge.setManyToMany(manyToMany);
        tableEdge.setEdgeType(count.intValue());

    }
}
