package com.orient.businessmodel.Util;

import com.orient.businessmodel.Util.EnumInter.SqlOperation;
import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.BusinessColumnEnum;
import com.orient.businessmodel.bean.IBusinessColumn;

/**
 * @ClassName BusinessModelHelper
 * 业务模型的帮助类
 * @author zhulc@cssrc.com.cn
 * @date Apr 28, 2012
 */

public class BusinessModelHelper {
	
    /** 
     * 返回业务字段的默认操作符
     *
     * @Method: getDefaultOperation 
     * @param bc 业务字段
     * @return  SqlOperation 操作符
     */
    public static SqlOperation getDefaultOperation(IBusinessColumn bc) {
		if (bc.getColType() == BusinessColumnEnum.C_Date
				|| bc.getColType() == BusinessColumnEnum.C_DateTime) {

		}
		SqlOperation operation = null;
		BusinessColumnEnum colType = (BusinessColumnEnum) bc.getColType();
		switch (colType) {
		case C_Date:
			operation = SqlOperation.BetweenAnd;
			break;
		case C_DateTime:
			operation = SqlOperation.BetweenAnd;
			break;
		case C_Text:
			operation = SqlOperation.Like;
			break;
		case C_Simple:
			operation = SqlOperation.Like;
			break;
		default:
			operation = SqlOperation.Equal;
		}
		return operation;
	}
    
    /** 
     * 判断字符串是否是Null或空串
     *
     * @Method: isNullString 
     * @param str
     * @return boolean
     */
    public static boolean isNullString(String str) {
		if (str == null || str.trim().equals("")
				|| str.trim().equalsIgnoreCase("NULL"))
			return true;
		else
			return false;
	}
}
