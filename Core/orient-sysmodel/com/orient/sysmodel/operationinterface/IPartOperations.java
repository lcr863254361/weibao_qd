/**
 * IPartOperations.java
 * com.orient.sysmodel.roleengine.operationinterface
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-4-5 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.operationinterface;

/**
 * ClassName:IPartOperations
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @version
 * @since Ver 1.1
 * @Date 2012-4-5		上午11:01:05
 *
 * @see
 */
public interface IPartOperations {

    /**
     *

     * @Method: getId

     * id

     * @return

     * @return Long

     * @throws
     */
    public Long getId();

    /**
     *

     * @Method: getTableId

     * 数据类或视图ID

     * @return

     * @return String

     * @throws
     */
    public String getTableId();

    /**
     *

     * @Method: getColumnId

     * 字段ID或*

     * @return

     * @return String

     * @throws
     */
    public String getColumnId();

    /**
     *

     * @Method: getOperationsId

     * 操作权限, OPERATION的ID集合,用","隔开

     * @return

     * @return String

     * @throws
     */
    public String getOperationsId();

    /**
     *

     * @Method: getFilter

     * 过滤条件

     * @return

     * @return String

     * @throws
     */
    public String getFilter();

    /**
     *

     * @Method: getIsTable

     * 是否是数据类, 1:是数据类2:不是数据类

     * @return

     * @return String

     * @throws
     */
    public String getIsTable();

    public String getUserFilter();

    public String getAddColumnIds();


    public String getDetailColumnIds();


    public String getModifyColumnIds();


    public String getExportColumnIds();

}

