package com.orient.metamodel.metadomain;

import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IRelationColumn;

/**
 * 关系属性信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public class RelationColumns extends AbstractRelationColumns implements  IRelationColumn {

    @Override
    public RelationColumns clone() throws CloneNotSupportedException {
        return (RelationColumns) super.clone();
    }

    public RelationColumns() {
    }

    /**
     * full constructor.
     *
     * @param  cwmTables
     * @param  cwmTabColumnsByRefColumnId
     * @param  relationtype
     * @param  ownership
     * @param  isFK
     * @throws
     */
    public RelationColumns(Table cwmTables, Column cwmTabColumnsByRefColumnId, Long relationtype, Long ownership, Long isFK) {
        super(cwmTables, cwmTabColumnsByRefColumnId, relationtype, ownership, isFK);
    }

    /**
     * 接口实现 IRelationColumn 的特殊函数
     */

    @Override
    public IColumn getBelongColumn() {
        return this.getCwmTabColumnsByColumnId();
    }

    @Override
    public IColumn getRefColumn() {
		/*ITable refTable = this.getRefTable();
		String dispalyName = this.getRefColumnName();
		List<IColumn>  allColumns = refTable.getColumns();
		for(IColumn loopColumn: allColumns)
		{
			loopColumn.getDisplayName().equalsIgnoreCase(dispalyName);
			return loopColumn;
		}*/
        return getCwmTabColumnsByColumnId();
    }

    @Override
    public int getRelationType() {
        int type = this.getRelationtype().intValue();
        switch (type) {
            case 1:
                return IRelationColumn.RELATIONTYPE_ONE2ONE;
            case 2:
                return IRelationColumn.RELATIONTYPE_ONE2MANY;
            case 3:
                return IRelationColumn.RELATIONTYPE_MANY2ONE;
            case 4:
                return IRelationColumn.RELATIONTYPE_MANY2MANY;
        }
        return 0;
    }

    @Override
    public int getOwner() {
        int type = this.getOwnership().intValue();
        switch (type) {
            case 1:
                return IRelationColumn.OWNER_RELAX;
            case 2:
                return IRelationColumn.OWNER_SELF;
            case 3:
                return IRelationColumn.OWNER_TIGHTEN;
            case 4:
                return IRelationColumn.OWNER_NONE;
        }
        return 0;
    }

}
