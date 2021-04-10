/**
 * ITableRight.java
 * com.orient.sysmodel.operationinterface
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-4-13 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.operationinterface;

import java.util.List;

/**
 * ClassName:ITableRight
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @Date 2012-4-13		上午08:28:09
 * @see
 * @since Ver 1.1
 */
public interface IMatrixRight extends IRight {

    public List<String> getListColIds();

    public List<String> getAddColIds();

    public List<String> getModifyColIds();

    public List<String> getDetailColIds();

    public List<String> getExportColIds();

    public List<Long> getBtnTypeIds();

    public String getFilter();

    public String getUserFilter();

    public Boolean getModelRightSet();

}

