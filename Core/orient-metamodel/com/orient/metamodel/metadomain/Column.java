package com.orient.metamodel.metadomain;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IMatrix;
import com.orient.metamodel.operationinterface.IRelationColumn;
import com.orient.metamodel.operationinterface.IRestriction;

import java.util.Iterator;
import java.util.Set;

/**
 * 数据类的属性
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public class Column extends AbstractColumn implements IColumn {

    public Column() {
    }

    /**
     * minimal constructor.
     *
     * @param cwmTables   the cwm tables
     * @param name        the name
     * @param displayName the display name
     * @param category    the category
     * @param type        the type
     * @param isValid     the is valid
     */
    public Column(Table cwmTables, String name, String displayName, Long category, String type, Long isValid) {
        super(cwmTables, name, displayName, category, type, isValid);
    }

    /**
     * full constructor.
     *
     * @param cwmTables                          the cwm tables
     * @param name                               the name
     * @param displayName                        the display name
     * @param category                           the category
     * @param description                        the description
     * @param columnName                         the column name
     * @param isWhoSearch                        the is who search
     * @param isForSearch                        the is for search
     * @param isIndex                            the is index
     * @param operateSign                        the operate sign
     * @param purpose                            the purpose
     * @param casesensitive                      the casesensitive
     * @param defalutValue                       the defalut value
     * @param isNullable                         the is nullable
     * @param isOnly                             the is only
     * @param isPK                               the is pk
     * @param isAutoIncrement                    the is auto increment
     * @param restrictionID                      the restriction id
     * @param type                               the type
     * @param sequenceName                       the sequence name
     * @param maxLength                          the max length
     * @param minLength                          the min length
     * @param isShow                             the is show
     * @param isWarp                             the is warp
     * @param propertyParagraph                  the property paragraph
     * @param properyCategory                    the propery category
     * @param linage                             the linage
     * @param isValid                            the is valid
     * @param isMutiUK                           the is muti uk
     * @param isUsedPaiXu                        the is used pai xu
     * @param cwmViewPaixuColumns                the cwm view paixu columns
     * @param cwmViewReturnColumns               the cwm view return columns
     * @param cwmTableEnums                      the cwm table enums
     * @param cwmRelationColumnsesForRefColumnId the cwm relation columnses for ref column id
     * @param cwmRelationColumnsesForColumnId    the cwm relation columnses for column id
     */
    public Column(Table cwmTables, String name, String displayName, Long category, String description, String columnName, String isWhoSearch, String isForSearch, String isIndex, String operateSign, String purpose, String casesensitive, String defalutValue, String isNullable, String isOnly, String isPK, String isAutoIncrement, String restrictionID, String type, String sequenceName, Long maxLength, Long minLength, String isShow, String isWarp, String propertyParagraph, String properyCategory, Long linage, Long isValid, Long isMutiUK, Long isUsedPaiXu, String selector, String unit, Set cwmViewPaixuColumns, Set cwmViewReturnColumns, Set cwmTableEnums, Set cwmRelationColumnsesForRefColumnId, Set cwmRelationColumnsesForColumnId) {
        super(cwmTables, name, displayName, category, description, columnName, isWhoSearch, isForSearch, isIndex, operateSign, purpose, casesensitive, defalutValue, isNullable, isOnly, isPK, isAutoIncrement, restrictionID, type, sequenceName, maxLength, minLength, isShow, isWarp, propertyParagraph, properyCategory, linage, isValid, isMutiUK, isUsedPaiXu, selector, unit, cwmViewPaixuColumns, cwmViewReturnColumns, cwmTableEnums, cwmRelationColumnsesForRefColumnId, cwmRelationColumnsesForColumnId);
    }

    public Column(boolean bExample) {
        if (bExample) {
            this.setNULL();
        }
    }

    /**
     * 获取column的唯一标识
     *
     * @return String
     */
    @Override
    public String getIdentity() {
        if (super.getIdentity().isEmpty()) {
            String parent = "";
            if (this.getTable() != null) {
                parent = this.getTable().getName();
            } else if (this.getView() != null) {
                parent = this.getView().getName();    //统计视图
            }
            super.setIdentity(parent + "=" + super.getName());
        }
        return super.getIdentity();
    }

    public IRelationColumn getRelationColumnIF() {
        if (this.getCategory().equals(Column.CATEGORY_RELATION)) {
            return this.getRelationColumn();
        }
        return null;
    }

    public IRestriction getRefRestriction() {
        return this.getRestriction();
    }

    public IMatrix getRefMatrix() {
        if (this.getTable() != null) {
            return this.getTable();
        } else {
            return this.getView();
        }
    }

    @Override
    public Long getMinLength() {
        return super.getMinLength();
    }

    @Override
    public Long getMaxLength() {
        return super.getMaxLength();
    }

    @Override
    public Long getNumLength() {
        return super.getNumlength();
    }

    @Override
    public Long getNumprecision() {
        return super.getNumprecision();
    }

    @Override
    public int compareTo(Object o) {

        if (o instanceof IColumn) {
            Column column = (Column) o;
            Set tableColumnSet = column.getTable().getTableColumnSet();
            Iterator ite = tableColumnSet.iterator();
            long toCompareOrder = 0l, selfCompareOrder = 0l;
            while (ite.hasNext()) {
                TableColumn tableColumn = (TableColumn) ite.next();
                if (tableColumn.getColumn().getId() == column.getId()) {
                    toCompareOrder = tableColumn.getOrder();
                }
                if (tableColumn.getColumn().getId() == this.getId()) {
                    selfCompareOrder = tableColumn.getOrder();
                }
            }
            if (selfCompareOrder >= toCompareOrder)
                return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Column) {
            Column that = (Column) obj;
            if (this.getId().equals(that.getId())) {
                return true;
            }
        }
        return super.equals(obj);
    }

    @Override
    public Column clone() throws CloneNotSupportedException {
        return (Column) super.clone();
    }

}
