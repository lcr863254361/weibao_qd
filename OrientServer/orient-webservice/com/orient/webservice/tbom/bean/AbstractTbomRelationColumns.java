/*
 * Title: AbstractTbomRelationColumns.java
 * Company: DHC
 * Author: 
 * Date: Nov 5, 2009 9:57:00 AM
 * Version: 4.0
 */
package com.orient.webservice.tbom.bean;

import java.io.Serializable;

/**
 * The Class AbstractTbomRelationColumns.
 *
 * @author
 * @version 4.0
 * @since Nov 5, 2009
 */
public abstract class AbstractTbomRelationColumns implements Serializable {

    /**
     * The id.
     */
    private String id;

    /**
     * The table.
     */
    private TbomTable table;

    /**
     * The relationtype.
     */
    private Long relationtype;

    /**
     * The is fk.
     */
    private Long isFK;

    /**
     * The ref table.
     */
    private TbomTable refTable;

    /**
     * Instantiates a new abstract tbom relation columns.
     */
    public AbstractTbomRelationColumns() {

    }

    /**
     * Instantiates a new abstract tbom relation columns.
     *
     * @param id           the id
     * @param table        the table
     * @param relationtype the relationtype
     * @param ownership    the ownership
     * @param isFK         the is fk
     * @param refTable     the ref table
     */
    public AbstractTbomRelationColumns(String id, TbomTable table,
                                       Long relationtype, Long ownership, Long isFK, TbomTable refTable) {
        super();
        this.id = id;
        this.table = table;
        this.relationtype = relationtype;
        this.isFK = isFK;
        this.refTable = refTable;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the table.
     *
     * @return the table
     */
    public TbomTable getTable() {
        return table;
    }

    /**
     * Sets the table.
     *
     * @param table the new table
     */
    public void setTable(TbomTable table) {
        this.table = table;
    }

    /**
     * Gets the relationtype.
     *
     * @return the relationtype
     */
    public Long getRelationtype() {
        return relationtype;
    }

    /**
     * Sets the relationtype.
     *
     * @param relationtype the new relationtype
     */
    public void setRelationtype(Long relationtype) {
        this.relationtype = relationtype;
    }

    /**
     * Gets the checks if is fk.
     *
     * @return the checks if is fk
     */
    public Long getIsFK() {
        return isFK;
    }

    /**
     * Sets the checks if is fk.
     *
     * @param isFK the new checks if is fk
     */
    public void setIsFK(Long isFK) {
        this.isFK = isFK;
    }

    /**
     * Gets the ref table.
     *
     * @return the ref table
     */
    public TbomTable getRefTable() {
        return refTable;
    }

    /**
     * Sets the ref table.
     *
     * @param refTable the new ref table
     */
    public void setRefTable(TbomTable refTable) {
        this.refTable = refTable;
    }

}
