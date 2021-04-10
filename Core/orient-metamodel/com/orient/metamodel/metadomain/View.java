package com.orient.metamodel.metadomain;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IMatrix;
import com.orient.metamodel.operationinterface.ITable;
import com.orient.metamodel.operationinterface.IView;

import javax.persistence.Transient;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 视图信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 7, 2012
 */
public class View extends AbstractView implements IView {

    @Transient
    @Override
    public List<IColumn> getEditColumns() {
        return getReturnColumnList().stream().filter(iColumn -> iColumn.getPurpose().contains(Column.PURPOSE_MODIFY)).collect(Collectors.toList());
    }

    @Transient
    @Override
    public List<IColumn> getAddColumns() {
        return getReturnColumnList().stream().filter(iColumn -> iColumn.getPurpose().contains(Column.PURPOSE_ADD) ).collect(Collectors.toList());
    }

    @Transient
    @Override
    public List<IColumn> getDetailColumns() {
        return getReturnColumnList().stream().filter(iColumn -> iColumn.getPurpose().contains(Column.PURPOSE_DETAIL) ).collect(Collectors.toList());
    }

    @Transient
    @Override
    public List<IColumn> getListColumns() {
        return getReturnColumnList().stream().filter(iColumn -> iColumn.getPurpose().contains(Column.PURPOSE_LIST) ).collect(Collectors.toList());
    }

    @Transient
    @Override
    public List<IColumn> getSortColumns() {
        List<ViewSortColumn> sortColumnList = new ArrayList<>();
        Set<ViewSortColumn> viewSortColumns = this.getPaixuColumns();
        for (ViewSortColumn sortColumn : viewSortColumns) {
            Column loopColumn = sortColumn.getCwmTabColumns();
            if (loopColumn.getIsValid().equals(new Long(0)) ||
                    loopColumn.getIsValid().equals(new Long(3))) {
                continue;
            }
            sortColumnList.add(sortColumn);
        }
        //按order排序
        Collections.sort(sortColumnList, new ComparatorSortViewCol());
        List<IColumn> returnColumns = new ArrayList<>();
        for (ViewSortColumn resultColumn : sortColumnList) {
            returnColumns.add(resultColumn.getCwmTabColumns());
        }
        return returnColumns;
    }

    public View() {
    }

    /**
     * minimal constructor.
     *
     * @param cwmTables   --视图所关联的第一个数据类（主数据类）
     * @param name        --视图名称
     * @param displayName --视图显示名
     * @param type        --视图类型 1.查询视图，2.统计视图
     * @param isValid     --是否有效 0.无效 1.有效
     */
    public View(Table cwmTables, String name, String displayName, Long type, Long isValid) {
        super(cwmTables, name, displayName, type, isValid);
    }

    /**
     * full constructor.
     *
     * @param cwmSchema             --视图所属的schema
     * @param cwmTables             --主数据类
     * @param name                  --视图名称
     * @param displayName           --视图的显示名
     * @param description           --视图描述
     * @param expression            --视图的查询表达式
     * @param type                  --视图类型 1.查询视图
     * @param isValid               --是否有效 0.无效 1.有效
     * @param viewSql               --生成视图的SQL语句
     * @param cwmViewRelationtables --视图相关联的数据类
     * @param cwmViewPaixuColumns   --视图的排序字段
     * @param cwmViewReturnColumns  --视图的显示字段
     */
    public View(Schema cwmSchema, Table cwmTables, String name, String displayName, String description, String expression, Long type, Long isValid, String viewSql, Set cwmViewRelationtables, Set cwmViewPaixuColumns, Set cwmViewReturnColumns) {
        super(cwmSchema, cwmTables, name, displayName, description, expression, type, isValid, viewSql, cwmViewRelationtables, cwmViewPaixuColumns, cwmViewReturnColumns);
    }

    /**
     * 获取view的唯一标识
     *
     * @return String
     */
    @Override
    public String getIdentity() {
        if (super.getIdentity().isEmpty()) {
            super.setIdentity("view=" + super.getName());
        }
        return super.getIdentity();
    }

    /**
     * 接口实现 IView 的特殊函数
     */
    @Transient
    @Override
    public String getSecrecy() {
        Table mainTable = this.getTable();
        return mainTable.getSecrecy();
    }

    @Transient
    @Override
    public boolean isSecrecyEnable() {
        Table mainTable = this.getTable();
        return mainTable.isSecrecyEnable();
    }

    public IColumn getColumnById(String Id) {
        //统计视图字段遍历
        Set<Column> columnList = this.getColumns();
        for (IColumn column : columnList) {
            if (column.getId().equalsIgnoreCase(Id)) {
                return column;
            }
        }
        //查询 视图字段遍历
        Set<ViewResultColumn> viewReslutColumns = this.getReturnColumns();
        for (ViewResultColumn column : viewReslutColumns) {
            if (column.getCwmTabColumns().getId().equalsIgnoreCase(Id)) {
                return column.getCwmTabColumns();
            }
        }
        return null;
    }

    @Override
    public String sortType() {
        if (this.getPaixuFx().equals(View.SORT_UP)) {
            return Table.SORT_UP;
        } else {
            return Table.SORT_DOWN;
        }
    }

    class ComparatorViewCol implements Comparator<ViewResultColumn> {
        @Override
        public int compare(ViewResultColumn o1, ViewResultColumn o2) {
            if (o1.getOrder() > o2.getOrder()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    class ComparatorSortViewCol implements Comparator<ViewSortColumn> {
        @Override
        public int compare(ViewSortColumn o1, ViewSortColumn o2) {
            if (o1.getOrder() > o2.getOrder()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    @Transient
    @Override
    public List<IColumn> getReturnColumnList() {
        List<ViewResultColumn> retColumns = new ArrayList<>();
        Set<ViewResultColumn> viewResultColumns = this.getReturnColumns();
        for (ViewResultColumn resultColumn : viewResultColumns) {
            Column loopColumn = resultColumn.getCwmTabColumns();
            if (loopColumn.getIsValid().equals(new Long(0)) ||
                    loopColumn.getIsValid().equals(new Long(3))) {
                continue;
            }
            retColumns.add(resultColumn);
        }
        //视图返回结果属性按order排序
        java.util.Collections.sort(retColumns, new ComparatorViewCol());
        List<IColumn> returnColumns = new ArrayList<>();
        for (ViewResultColumn resultColumn : retColumns) {
            returnColumns.add(resultColumn.getCwmTabColumns());
        }
        return returnColumns;
    }

    @Transient
    @Override
    public int getMatrixType() {
        return IMatrix.TYPE_VIEW;
    }

    @Transient
    @Override
    public ITable getMainTable() {
        return this.getTable();
    }

    @Transient
    @Override
    public List<IColumn> getSortedColumns() {
        return getSortedColumns();
    }

}
