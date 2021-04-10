package com.orient.metamodel.metadomain;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IMatrix;
import com.orient.metamodel.operationinterface.ITable;

import javax.persistence.Transient;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author mengbin@cssrc.com.cn
 * @date Mar 22, 2012
 */
public class Table extends AbstractTable implements ITable {

    @Transient
    @Override
    public List<IColumn> getEditColumns() {
        return getColumns().stream().filter(iColumn -> iColumn.getPurpose().contains(Column.PURPOSE_MODIFY)).collect(Collectors.toList());
    }

    @Transient
    @Override
    public List<IColumn> getAddColumns() {
        return getColumns().stream().filter(iColumn -> iColumn.getPurpose().contains(Column.PURPOSE_ADD)).collect(Collectors.toList());
    }

    @Transient
    @Override
    public List<IColumn> getDetailColumns() {
        return getColumns().stream().filter(iColumn -> iColumn.getPurpose().contains(Column.PURPOSE_DETAIL)).collect(Collectors.toList());
    }

    @Transient
    @Override
    public List<IColumn> getListColumns() {
        return getColumns().stream().filter(iColumn -> iColumn.getPurpose().contains(Column.PURPOSE_LIST)).collect(Collectors.toList());
    }

    @Transient
    @Override
    public List<IColumn> getSortColumns() {
        List<IColumn> retList = new ArrayList<>();
        Map<Integer, Column> map = (Map<Integer, Column>) this.getSkColumns();
        for (Map.Entry<Integer, Column> entry : map.entrySet()) {
            Column loopColumn = entry.getValue();
            if (null == loopColumn) {
                continue;
            }
            if (loopColumn.getIsValid() == 0 ||
                    loopColumn.getIsValid() == 3) {
                continue;
            }
            retList.add(loopColumn);
        }
        return retList;
    }

    public Table() {
    }

    /**
     * minimal constructor
     *
     * @param cwmSchema   --所属Schema
     * @param name        --数据类名称
     * @param displayName --数据类显示名
     * @param isValid     --是否有效
     */
    public Table(Schema cwmSchema, String name, String displayName, Long isValid) {
        super(cwmSchema, name, displayName, isValid);
    }

    /**
     * full constructor
     *
     * @param id                    --ID
     * @param schema                --所属schema
     * @param name                  --数据类名称
     * @param displayName           --数据类的显示名
     * @param tableName             --表名
     * @param parentTable           --父表
     * @param paiXu                 --排序方向（排序方向，指定数据类记录的排序方向，ASC表示升序，DESC表示降序）
     * @param isConnectTable        --
     * @param isShow                --是否显示
     * @param detailText            --详细文字
     * @param description           --描述
     * @param bigImage
     * @param norImage
     * @param smaImage
     * @param category              --种类
     * @param isValid               --是否有效
     * @param order                 --顺序
     * @param cite                  --用于形成XML
     * @param existData             --是否存在数据
     * @param childTables           --子表
     * @param cwmViewRelationtables
     * @param cwmConsExpressions
     * @param cwmTabColumnses
     * @param cwmViewses
     * @param cwmTableEnums
     * @param cwmRelationTableEnums
     * @param cwmRelationColumnses
     * @param pkColumns
     * @param ukColumns
     * @param skColumns
     * @throws
     */
    public Table(String id, Schema schema, String name, String displayName, String tableName, Table parentTable, String paiXu,
                 String isConnectTable, String isShow, String detailText, String description, String bigImage, String norImage,
                 String smaImage, String category, Long isValid, Long order, String cite, String existData, Set childTables, Set cwmViewRelationtables, Set cwmConsExpressions, Set cwmTabColumnses, Set cwmViewses, Set cwmTableEnums, Set cwmRelationTableEnums, Set cwmRelationColumnses, Map pkColumns, Map ukColumns, Map skColumns) {
        super(id, schema, name, displayName, tableName, parentTable, paiXu, isConnectTable, isShow, detailText, description, bigImage, norImage,
                smaImage, category, isValid, order, cite, existData, childTables, cwmViewRelationtables, cwmConsExpressions, cwmTabColumnses, cwmViewses, cwmTableEnums, cwmRelationTableEnums, cwmRelationColumnses, pkColumns, ukColumns, skColumns);
    }

    public Table(boolean bExample) {
        if (bExample) {
            super.setNULL();
        } else {

        }
    }

    /**
     * 获取Table的唯一标识
     *
     * @return String
     */
    @Override
    public String getIdentity() {
        if (super.getIdentity().isEmpty()) {
            super.setIdentity("table=" + super.getName());
        }
        return super.getIdentity();
    }

    /**
     * 接口实现 ITable 的特殊函数
     */
    @Transient
    @Override
    public List<IColumn> getColumns() {

        List<IColumn> allColumns = new ArrayList<>();
        if (this.getParentTable() != null) {
            List<IColumn> parentColumns = this.getParentTable().getColumns();
            allColumns.addAll(parentColumns);
        }
        for (Iterator iter = this.getCwmTabColumnses().iterator(); iter.hasNext(); ) {
            IColumn column = (IColumn) iter.next();
            if (column.getIsValid() == 0 || column.getIsValid() == 3
                    || column.getCategory() == 3) {
                continue;
            }
            if (column.getRelationColumnIF() != null && column.getRelationColumnIF().getIsFK() == 0) {
                continue;
            }
            allColumns.add(column);
        }
        Set<TableColumn> tableColumns = this.getTableColumnSet();
        //增加排序
       /* allColumns.sort((c1, c2) -> {
            Long c1Order = tableColumns.stream().filter(tableColumn -> tableColumn.getColumn().getId().equals(c1.getId())).findFirst().get().getOrder();
            Long c2Order = tableColumns.stream().filter(tableColumn -> tableColumn.getColumn().getId().equals(c2.getId())).findFirst().get().getOrder();
            return c1Order.compareTo(c2Order);
        });*/
        return allColumns;
    }

    @Transient
    @Override
    public List<IColumn> getCommonColumns() {
        List<IColumn> commonColumns = new ArrayList<>();
        if (this.getParentTable() != null) {
            List<IColumn> parentColumns = this.getParentTable().getCommonColumns();
            commonColumns.addAll(parentColumns);
        }
        for (Iterator iter = this.getCwmTabColumnses().iterator(); iter.hasNext(); ) {
            IColumn column = (IColumn) iter.next();
            if (column.getCategory() != Column.CATEGORY_COMMON) {
                continue;
            }
            if (column.getIsValid() == 0 ||
                    column.getIsValid() == 3) {
                continue;
            }
            commonColumns.add(column);
        }
        return commonColumns;
    }

    @Transient
    @Override
    public List<IColumn> getRelationColumns() {
        List<IColumn> relationColumns = new ArrayList<>();
        if (this.getParentTable() != null) {
            List<IColumn> parentColumns = this.getParentTable().getRelationColumns();
            relationColumns.addAll(parentColumns);
        }

        for (Iterator iter = this.getCwmTabColumnses().iterator(); iter.hasNext(); ) {
            IColumn column = (IColumn) iter.next();
            if (column.getCategory() != Column.CATEGORY_RELATION) {
                continue;
            }
            if (column.getIsValid() == 0 ||
                    column.getIsValid() == 3) {
                continue;
            }
            relationColumns.add(column);
        }
        return relationColumns;
    }

    @Transient
    @Override
    public boolean isSecrecyEnable() {
        String strUseSecrecy = getUseSecrecy();
        if (strUseSecrecy.equalsIgnoreCase("") || strUseSecrecy.equalsIgnoreCase("false")) {
            return false;
        } else {
            return true;
        }
    }

    @Transient
    @Override
    public String sortType() {
        return this.getPaiXu();
    }

    @Transient
    @Override
    public List<ITable> getAllTables() {
        List<ITable> retTables = new ArrayList<>();
        for (Table childTable : getChildTables()) {
            List<ITable> subList = childTable.getAllTables();
            retTables.addAll(subList);
        }
        retTables.add(this);
        return retTables;
    }

    @Transient
    @Override
    public int getMatrixType() {
        return IMatrix.TYPE_TABLE;
    }

    @Override
    public IColumn getColumnById(String Id) {
        Boolean exist = this.getCwmTabColumnses().stream().filter(cm -> cm.getId().equalsIgnoreCase(Id)).count() > 0;
        Column oldColumn = (exist ? this.getCwmTabColumnses().stream().filter(cm -> cm.getId().equalsIgnoreCase(Id)).findFirst().get() : null);
        return oldColumn;
    }

    @Override
    public IColumn getColumnByName(String name) {
        List<IColumn> columnList = this.getColumns();
        for (IColumn column : columnList) {
            if (column.getName().equalsIgnoreCase(name)) {
                return column;
            }
        }
        return null;
    }

    @Transient
    @Override
    public ITable getMainTable() {
        return this;
    }

    @Transient
    @Override
    public List<IColumn> getSortedColumns() {
        List<IColumn> retVal = new ArrayList<>();
        Set<TableColumn> tableColumns = getTableColumnSet();
        List<TableColumn> sortedTableColumns = tableColumns.stream().sorted((t1, t2) -> t1.getOrder().intValue() - t2.getOrder().intValue()).collect(Collectors.toList());
        sortedTableColumns.forEach(tableColumn -> retVal.add(tableColumn.getColumn()));
        return retVal;
    }

}
