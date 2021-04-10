package com.orient.metamodel.dijkstra;

import com.orient.metamodel.operationinterface.ITable;
import com.orient.metamodel.pedigree.TableEdge;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-15 10:04
 */
public interface IDijikstraSchemaTravel {

    List<TableEdge> getMinStep(ITable start, ITable end);
}
