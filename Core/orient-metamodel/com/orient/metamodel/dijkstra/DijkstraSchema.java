package com.orient.metamodel.dijkstra;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IRelationColumn;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.metamodel.operationinterface.ITable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-15 9:51
 */
public class DijkstraSchema implements Serializable {

    private Map<ITable, Integer> tablePositation = new HashMap<>();

    private Map<Integer, ITable> tablePositationInverse = new HashMap<>();


    private HashMap<Integer, HashMap<Integer, Integer>> stepLength = new HashMap<>();

    public DijkstraSchema(ISchema schema) {

        int verNum = schema.getAllTables().size();
        int index = 0;
        for (ITable table : schema.getAllTables()) {
            addVertex(table, index);
            index++;
        }
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

    private void addEdge(int start, int end, int edgeType, boolean manyToMany) {
        HashMap<Integer, Integer> step = stepLength.get(start);
        step.put(end, 1);
    }

    void addVertex(ITable table, int index) {
        tablePositation.put(table, index);
        tablePositationInverse.put(index, table);
        HashMap<Integer, Integer> step = new HashMap<>();
        stepLength.put(index, step);
    }

    public Map<ITable, Integer> getTablePositation() {
        return tablePositation;
    }

    public void setTablePositation(Map<ITable, Integer> tablePositation) {
        this.tablePositation = tablePositation;
    }

    public HashMap<Integer, HashMap<Integer, Integer>> getStepLength() {
        return stepLength;
    }

    public void setStepLength(HashMap<Integer, HashMap<Integer, Integer>> stepLength) {
        this.stepLength = stepLength;
    }

    public Map<Integer, ITable> getTablePositationInverse() {
        return tablePositationInverse;
    }

    public void setTablePositationInverse(Map<Integer, ITable> tablePositationInverse) {
        this.tablePositationInverse = tablePositationInverse;
    }
}
