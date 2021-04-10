package com.orient.dsrestful.service;

import com.orient.metamodel.metadomain.*;
import com.orient.metamodel.metadomain.Enum;
import com.orient.metamodel.metaengine.business.MetaDAOFactory;
import com.orient.metamodel.metaengine.impl.MetaUtilImpl;
import com.orient.sysmodel.service.tbom.TbomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 删除的代码都分散在各个删除监听中了，这个类暂时不需要了
 * Created by GNY on 2018/3/26
 */
@Component
public class DeleteSchemaService {

    @Autowired
    MetaUtilImpl metaEngine;

    @Autowired
    MetaDAOFactory metaDaoFactory;

    @Autowired
    TbomService tbomService;

    /**
     * 删除一个Schema下的所有业务表
     *
     * @param schema
     */
    public void deleteBusinessTables(Schema schema) throws Exception {
        schema.getTables().forEach(table -> {
            try {
                //删除其他几张表的数据
                deleteTableAttribute(table);
                metaDaoFactory.getTableDAO().delete(table); //删除table表的数据
            } catch (Exception e) {
                e.printStackTrace();
            }
            metaDaoFactory.getJdbcTemplate().execute("drop table " + table.getTableName() + " purge "); //删除物理表
            metaDaoFactory.getJdbcTemplate().execute("drop sequence seq_" + table.getTableName());      //删除sequence
        });
    }

    private void deleteTableAttribute(Table table) throws Exception {
        //删除该table的子表
        for (Table childTable : table.getChildTables()) {
            metaDaoFactory.getTableDAO().delete(childTable);
        }

        //删除该table在CWM_TABLE_COLUMN表中的相关数据 todo 这里删除CWM_TABLE_COLUMN表，需要测试
        List columnList = metaDaoFactory.getTableColumnDAO().findByTableId(table);
        if (!columnList.isEmpty()) {
            for (Iterator kIt = columnList.iterator(); kIt.hasNext(); ) {
                TableColumn kColumn = (TableColumn) kIt.next();
                metaDaoFactory.getTableColumnDAO().delete(kColumn);
            }
        }
        //删除该table在TABLE_COLUMN表中的相关数据
        for (Column column : table.getCwmTabColumnses()) {
            metaDaoFactory.getColumnDAO().delete(column);
        }

        //删除该table的字段在CWM_RELATION_COLUMN中的相关数据
        for (Column column : table.getCwmTabColumnses()) {
            if (column.getCategory().equals(Column.CATEGORY_RELATION)) {    //关系属性
                for (RelationColumns relationColumn : table.getCwmRelationColumnses()) {
                    if (relationColumn.getCwmTabColumnsByColumnId().equals(column)) {
                        metaDaoFactory.getRelationColumnsDAO().delete(relationColumn);
                    }
                }
            }
        }

        //删除该table的算法
        for (Column column : table.getCwmTabColumnses()) {
            if (!column.getArithAttribute().isEmpty()) {
                for (ArithAttribute arithAttribute : column.getArithAttribute()) {
                    metaDaoFactory.getArithAttributeDAO().delete(arithAttribute);
                }
            }
        }
    }


    /**
     * 删除该schema下的所有约束
     *
     * @param schema
     * @throws Exception
     */
    public void deleteRestrictions(Schema schema) throws Exception {
        for (Restriction restriction : schema.getRestrictions()) {
            deleteRestrictions(restriction);
            metaDaoFactory.getRestrictionDAO().delete(restriction);
        }
    }

    private void deleteRestrictions(Restriction restriction) {
        try {
            switch (restriction.getType().intValue()) {
                case Restriction.TYPE_ENUM:    //枚举约束
                    for (Enum e : restriction.getCwmEnums()) {
                        e.setRestrictionID(restriction.getId());
                        metaDaoFactory.getEnumDAO().delete(e);
                    }
                    break;
                case Restriction.TYPE_TABLEENUM: // 数据表枚举约束
                    TableEnum te = restriction.getTableEnum();
                    te.setRestrictionId(restriction.getId());
                    //saveTableEnumDetail(te);
                    break;
                case Restriction.TYPE_DYNAMICRANGEENUM://动态范围约束
                    TableEnum tableEnum = restriction.getTableEnum();
                    tableEnum.setRestrictionId(restriction.getId());
                    metaDaoFactory.getTableEnumDAO().delete(tableEnum);
                    for (RelationTableEnum rte : tableEnum.getRelationTableEnums()) {
                        metaDaoFactory.getRelationTableEnumDAO().delete(rte);
                    }
                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteViews(Schema schema) throws Exception {
        for (View view : schema.getViews()) {
            deleteViewAttribute(view);
            metaDaoFactory.getViewDAO().delete(view);   //删除数据
            //todo 有问题，如何删除物理视图，这行代码有问题
            metaDaoFactory.getJdbcTemplate().execute("drop view " + view.getViewName());
        }
    }

    private void deleteViewAttribute(View view) {
        try {
            if (!view.getColumns().isEmpty()) {
                for (Column column : view.getColumns()) {
                    column.setView(view);
                    String cName = column.getName();
                    //这里只需要保存统计视图的属性
                    if (column.getCategory().equals(Column.CATEGORY_ARITH)) {
                        column.setName(cName);
                        if (column.getPurpose() == null || column.getPurpose().length() == 0) {
                            column.setPurpose("新增,修改,详细,列表");
                        }
                        column.setColumnName(column.getName() + "_" + view.getId());
                        metaDaoFactory.getColumnDAO().delete(column);//删除视图的统计属性到CWM_TAB_COLUMNS
                    }
                    if (!column.getArithAttribute().isEmpty()) {
                        for (ArithAttribute aa : column.getArithAttribute()) {
                            assert (!aa.getColumn().getId().equals(""));
                            aa.setAcolumn(column);
                            metaDaoFactory.getArithAttributeDAO().delete(aa);
                        }
                    }
                }
            }
            if (!view.getCanshuxiang().isEmpty()) {
                for (ArithViewAttribute ava : view.getCanshuxiang()) {
                    ava.setView(view);
                    metaDaoFactory.getArithViewAttributeDAO().delete(ava);
                }
            }

            Set<ViewRefColumn> relationTableViewSet = view.getViewRelationTables();
            if (!relationTableViewSet.isEmpty()) {
                for (ViewRefColumn viewRelTable : relationTableViewSet) {
                    metaDaoFactory.getViewRefColumnDAO().delete(viewRelTable);
                }
            }

            //将元数据库中和数据库中view相关的返回属性、排序属性删除，
            Set<ViewResultColumn> resultColumnSet = view.getReturnColumns();
            if (!resultColumnSet.isEmpty()) {
                for (ViewResultColumn vrc : resultColumnSet) {
                    metaDaoFactory.getViewResultColumnDAO().delete(vrc);
                }
            }

            Set<ViewSortColumn> sortColumnSet = view.getPaixuColumns();
            if (!sortColumnSet.isEmpty()) {
                for (ViewSortColumn vsc : sortColumnSet) {
                    metaDaoFactory.getViewSortColumnDAO().delete(vsc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteTbom(String schemaId) {
        tbomService.deleteTbom(schemaId);
    }

}
