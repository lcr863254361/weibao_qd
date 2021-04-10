package com.orient.metamodel.operationinterface;

import com.orient.metamodel.pedigree.TableEdge;

import java.util.List;

/**
 * Schema谱系的遍历算法
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 28, 2012
 */
public interface ISchemaGraphTravel {

    /**
     * 遍历任意两个模型之间的有效关系路径
     *
     * @param startTable 起点模型
     * @param endTable   终点模型
     * @return List<List<TableEdge>>
     * 路径List集合
     * 两两模型边的数组List集合
     */
    List<List<TableEdge>> travel(ITable startTable, ITable endTable);

}