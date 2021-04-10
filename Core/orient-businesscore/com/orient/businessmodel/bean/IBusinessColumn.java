/**
 * @Project: OrientEDM
 * @Title: IBusinessColumn.java
 * @Package com.orient.businessmodel.bean
 * TODO
 * @author zhulc@cssrc.com.cn
 * @date Apr 6, 2012 3:33:24 PM
 * @Copyright: 2012 www.cssrc.com.cn. All rights reserved.
 * @version V1.0
 */


package com.orient.businessmodel.bean;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.metamodel.metadomain.Restriction;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IRelationColumn;
import com.orient.sysmodel.operationinterface.IColumnRight;

import java.io.Serializable;

/**
 * @ClassName IBusinessColumn
 * 业务模型的字段
 * @author zhulc@cssrc.com.cn
 * @date Apr 6, 2012
 */

public interface IBusinessColumn extends Serializable {

    /**
     * 返回字段的类型
     *
     * @Method: getColType
     * @return EnumInter.BusinessColumnEnum
     */
    public EnumInter getColType();

    /**
     * 返回业务字段的元数据字段对象
     *
     * @Method: getCol
     * @return IColumn
     */
    public IColumn getCol();

    /**
     * 该字段能否编辑,默认为可编辑
     *
     * @Method: getEditable
     *          True 可以编辑
     *          False 限制编辑
     * @return boolean
     */
    public boolean getEditable();

    /**
     * 该字段能否创建,默认为可创建
     *
     * @Method: getCreateable
     * @return boolean
     */
    public boolean getCreateable();

    /**
     * 返回该业务字段的父业务模型
     *
     * @Method: getParentModel
     * @return IBusinessModel
     */
    public IBusinessModel getParentModel();

    /**
     * 返回该字段的数据表映射名称
     *
     * @Method: getS_column_name
     * @return String
     */
    public String getS_column_name();

    /**
     * 返回该字段页面显示名称
     *
     * @Method: getDisplay_name
     * @return String
     */
    public String getDisplay_name();


    /**
     * 返回该字段的权限
     *
     * @Method: getColRight
     * @return IColumnRight
     */
    public IColumnRight getColRight();

    /**
     * 设置该字段的权限
     *
     * @Method: setColRight
     * @param colRight
     */
    public void setColRight(IColumnRight colRight);

    /**
     * 返回字段是否可以为空
     *
     * @Method: getNullable
     */
    public boolean getNullable();

    /**
     *@Function Name:  getIs_Pk
     *@Description: @return 是否是主键显示值
     *@Date Created:  2013-3-19 下午02:04:19
     *@Author: Pan Duan Duan
     *@Last Modified:     ,  Date Modified:
     */
    public String getIs_Pk();

    /**
     *@Function Name:  getRestriction
     *@Description: @return 得到字段的约束
     *@Date Created:  2013-3-19 下午02:55:30
     *@Author: Pan Duan Duan
     *@Last Modified:     ,  Date Modified:
     */
    public Restriction getRestriction();


    /**
     *@Function Name:  getRelationColumnIF
     *@Description: @return  得到字段的关系属性信息
     *@Date Created:  2013-3-19 下午03:45:53
     *@Author: Pan Duan Duan
     *@Last Modified:     ,  Date Modified:
     */
    public IRelationColumn getRelationColumnIF();

    /**
     * @return 获取字段的唯一性校验额外条件
     */
    public String getExtraCondition();

    /**
     * @return 获取字段的唯一性校验额外条件
     */
    public void setExtraCondition(String extraCondition);

    public String getSelector();

    public String getUnit();

    public String getId();

    String getEditType();
}
