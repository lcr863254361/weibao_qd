/**
 * @Project: OrientEDM
 * @Title: IBusinessModel.java
 * @Package com.orient.businessmodel.bean
 * TODO
 * @author zhulc@cssrc.com.cn
 * @date Apr 6, 2012 3:33:00 PM
 * @Copyright: 2012 www.cssrc.com.cn. All rights reserved.
 * @version V1.0
 */


package com.orient.businessmodel.bean;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum;
import com.orient.businessmodel.service.impl.BusinessModelEdge;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.metamodel.operationinterface.IColumn;
import com.orient.metamodel.operationinterface.IMatrix;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.sysmodel.operationinterface.IMatrixRight;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhulc@cssrc.com.cn
 * @ClassName IBusinessModel
 * 业务模型
 * @date Apr 6, 2012
 */
public interface IBusinessModel extends Serializable {

    /**
     * 返回模型所属的Schema
     *
     * @return ISchema
     */
    ISchema getSchema();

    /**
     * 返回模型的ID(Table或者View)
     *
     * @return String
     */
    String getId();

    /**
     * 返回Table的父表ID
     * 如果是视图则返回视图主数据类的父表ID
     * 没有父表,则返回""
     *
     * @return String
     */
    String getTable_pid();

    /**
     * 返回表的实际物理名称(即数据库中创建的名称)
     * 如果是视图,则返回视图主数据类的表名
     *
     * @return String
     */
    String getS_table_name();

    /**
     * 返回表的显示名称
     * 如果是视图,则返回视图主数据类的显示名称
     *
     * @return String
     */
    String getDisplay_name();

    /**
     * 返回表的SEQUENCE名称
     * 如果是视图,则返回视图主数据类的SEQUENCE名称
     *
     * @return String
     */
    String getSeq_name();

    /**
     * 返回业务模型对应的元数据模型
     *
     * @return IMatrix
     */
    IMatrix getMatrix();

    /**
     * 返回表的类型
     * 如果是视图,则返回视图主数据类的类型
     *
     * @return EnumInter.BusinessTableEnum
     */
    EnumInter getTableType();

    /**
     * 返回模型是否数据共享，True共享
     * 主要针对共享模型
     * 一般业务模型皆为false
     *
     * @return boolean
     */
    boolean getShareable();

    /**
     * 返回模型的排序方向
     *
     * @return String
     */
    String getPaixu_fx();

    /**
     * 返回数据表在界面中分几列显示
     *
     * @return int
     */
    int getPageColNum();

    /**
     * 返回数据表的默认查询字段
     * 如果是视图，则返回视图的主数据查询字段
     *
     * @return String
     */
    String getDefaultSelect();

    /**
     * 返回数据表是否启用密级
     * 视图则返回主数据类是否启用密级
     *
     * @return boolean
     */
    boolean getSecrecyable();

    /**
     * 返回数据模型所有的业务字段
     * 过滤掉了不显示字段和伪删除字段
     *
     * @return List<IBusinessColumn>
     */
    List<IBusinessColumn> getAllBcCols();

    List<IBusinessColumn> getMutiUkCols();

    List<IBusinessColumn> getAddCols();

    List<IBusinessColumn> getDetailCols();

    /**
     * 返回数据表(视图的主数据类)所有的查询用途字段和（结果显示用途和查询查询）的字段
     * 过滤掉了不显示和伪删除字段
     *
     * @return List<IBusinessColumn>
     */
    List<IBusinessColumn> getSearchCols();

    /**
     * 返回数据表(视图的主数据类)的唯一行约束的字段列表
     *
     * @return List<IBusinessColumn>
     */
    List<IBusinessColumn> getMultyUkColumns();

    /**
     * 返回数据表(视图的主数据类)的主键显示值字段列表
     * 按字段的显示先后顺序存储在列表中
     *
     * @return List<IBusinessColumn>
     */
    List<IBusinessColumn> getRefShowColumns();

    /**
     * 返回数据表(视图的主数据类)的排序字段列表
     * 按字段排序的先后顺序存储在列表中
     *
     * @return List<IBusinessColumn>
     */
    List<IBusinessColumn> getSortCols();

    /**
     * 返回业务模型的类型
     *
     * @return BusinessModelEnum
     */
    BusinessModelEnum getModelType();

    /**
     * 返回保留的过滤条件字符串(sql格式)
     *
     * @return String
     */
    String getReserve_filter();

    /**
     * 设置模型的过滤条件字符串
     *
     * @param reserve_filter
     */
    void setReserve_filter(String reserve_filter);

    /**
     * 返回模型的业务权限
     *
     * @return IMatrixRight
     */
    IMatrixRight getMatrixRight();

    /**
     * 设置模型的业务权限
     *
     * @param matrixRight
     */
    void setMatrixRight(IMatrixRight matrixRight);

    /**
     * 返回与该模型绑定的用户密级
     *
     * @return String
     */
    String getUserSecrecy();

    /**
     * 把用户密级与该用户进行绑定
     *
     * @param userSecrecy
     */
    void setUserSecrecy(String userSecrecy);

    /**
     * 返回业务字段
     *
     * @param colID 字段的ID
     * @return IBusinessColumn
     */
    IBusinessColumn getBusinessColumnByID(String colID);

    /**
     * 返回业务字段
     *
     * @param sColName 字段的数据库名称
     * @return IBusinessColumn
     */
    IBusinessColumn getBusinessColumnBySName(String sColName);

    /**
     * 设置业务模型的谱系列表属性
     * 谱系列表的生成由作用表决定
     *
     * @param pedigreeList
     */
    void setPedigree(List<List<BusinessModelEdge>> pedigreeList);

    /**
     * 返回业务模型所对应的谱系列表
     *
     * @return List<List<BusinessModelEdge>>
     */
    List<List<BusinessModelEdge>> getPedigreeList();

    /**
     * 返回自定义过滤条件的对象列表
     *
     * @return List<CustomerFilter>
     */
    List<CustomerFilter> getCustFilterList();

    /**
     * 为模型添加自定义过滤条件对象
     *
     * @param filter
     */
    void appendCustomerFilter(CustomerFilter filter);

    boolean isSelfRelation();

    String getSelfRelColName();

    void clearCustomFilter();

    void clearAllFilter();

    ITreeNodeFilterModelBean getTreeNodeFilterModelBean();

    void setTreeNodeFilterModelBean(ITreeNodeFilterModelBean treeNodeFilterModelBean);

    /**
     * 返回主数据类的业务模型
     *
     * @return
     */
    IBusinessModel getMainModel();

    List<IBusinessColumn> getGridShowColumns();

    List<IBusinessColumn> getEditCols();

    List<IColumn> getSortedColumns();

    String getName();

}
