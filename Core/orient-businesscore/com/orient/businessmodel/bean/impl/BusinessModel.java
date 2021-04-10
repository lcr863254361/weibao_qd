package com.orient.businessmodel.bean.impl;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum;
import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.BusinessColumnEnum;
import com.orient.businessmodel.Util.EnumInter.BusinessModelEnum.BusinessTableEnum;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.bean.ITreeNodeFilterModelBean;
import com.orient.businessmodel.service.impl.BusinessModelEdge;
import com.orient.businessmodel.service.impl.CustomerFilter;
import com.orient.metamodel.operationinterface.*;
import com.orient.sqlengine.api.SqlEngineException;
import com.orient.sysmodel.operationinterface.IMatrixRight;
import com.orient.utils.CommonTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 业务表模型
 *
 * @author zhulc@cssrc.com.cn
 * @date Apr 9, 2012
 */
public class BusinessModel implements IBusinessModel {

    /**
     * @Fields matrix : 元数据模型
     */
    private IMatrix matrix;

    /**
     * @Fields matrixRight : 权限对模型的过滤bean
     */
    private IMatrixRight matrixRight = null;

    /**
     * @Fields treeNodeFilterModelBean : TBOM树对模型的过滤bean
     */
    private ITreeNodeFilterModelBean treeNodeFilterModelBean;

    /**
     * @Fields custom_filter : 保留的过滤条件字符串(Sql格式)
     */
    private String reserve_filter;

    private List<CustomerFilter> custFilterList = new ArrayList<>();

    /**
     * @Fields userSecrecy : 用户密级
     */
    private String userSecrecy;

    /**
     * @Fields allBcCols : 业务模型全字段
     */
    List<IBusinessColumn> allBcCols = new ArrayList<>();

    /**
     * @Fields mutiUkCols : 业务模型联合主键
     */
    List<IBusinessColumn> mutiUkCols = new ArrayList<>();

    /**
     * @Fields refShowColumns : 业务模型主键显示值
     */
    List<IBusinessColumn> refShowColumns = new ArrayList<>();

    /**
     * @Fields sortCols : 业务模型排序字段
     */
    List<IBusinessColumn> sortCols = new ArrayList<>();

    /**
     * @Fields refShowColumns : 列表显示字段，查询字段+结果显示字段
     */
    List<IBusinessColumn> gridShowColumns = new ArrayList<>();

    /**
     * @Fields editCols : 业务模型编辑字段
     */
    List<IBusinessColumn> editCols = new ArrayList<>();

    /**
     * @Fields editCols : 业务模型编辑字段
     */
    List<IBusinessColumn> addCols = new ArrayList<>();

    /**
     * @Fields editCols : 业务模型编辑字段
     */
    List<IBusinessColumn> detailCols = new ArrayList<>();

    List<IBusinessColumn> searchCols = new ArrayList<>();

    /**
     * @Fields pedigreeList : 该模型的谱系列表
     */
    List<List<BusinessModelEdge>> pedigreeList;

    /**
     * @Fields 主数据类业务模型
     */
    IBusinessModel mainModel = this;

    public BusinessModel(IMatrix matrix) {
        this.matrix = matrix;
    }

    public ISchema getSchema() {
        return matrix.getSchema();
    }

    public String getId() {
        return matrix.getId();
    }

    public String getTable_pid() {
        String pid = "";
        if (null != matrix.getMainTable().getParentTable()) {
            pid = matrix.getMainTable().getParentTable().getId();
        }
        return pid;
    }

    public String getS_table_name() {
        return matrix.getMainTable().getTableName().toUpperCase();
    }

    public String getDisplay_name() {
        return matrix.getDisplayName();
    }

    public String getSeq_name() {
        return "SEQ_" + matrix.getMainTable().getTableName().toUpperCase();
    }

    public IMatrix getMatrix() {
        return matrix;
    }

    public EnumInter getTableType() {
        return BusinessTableEnum.getBusinessTableType(matrix.getMainTable().getType());
    }

    public boolean getShareable() {
        if (matrix.getMainTable().getShareable().equalsIgnoreCase("False")) {
            return false;
        }
        return true;
    }

    public String getPaixu_fx() {
        return matrix.getMainTable().getPaiXu();
    }

    public int getPageColNum() {
        long colNum = matrix.getMainTable().getColSum();
        if (colNum == 0) {
            colNum = 2;
        }
        return Integer.valueOf(String.valueOf(colNum));
    }

    public boolean getSecrecyable() {
        //防止useSecrecy为null
        if (matrix.getMainTable().getUseSecrecy() == null || matrix.getMainTable().getUseSecrecy().equalsIgnoreCase("False")) {
            return false;
        } else {
            return true;
        }
    }

    public ITreeNodeFilterModelBean getTreeNodeFilterModelBean() {
        return treeNodeFilterModelBean;
    }

    public void setTreeNodeFilterModelBean(ITreeNodeFilterModelBean treeNodeFilterModelBean) {
        this.treeNodeFilterModelBean = treeNodeFilterModelBean;
    }

    @SuppressWarnings("unchecked")
    public void initColumns() {
        allBcCols.clear();
        gridShowColumns.clear();
        addCols.clear();
        editCols.clear();
        detailCols.clear();
        List<IColumn> allCols = null;
        if (matrix.getMatrixType() == IMatrix.TYPE_VIEW) {
            IView view = (IView) matrix;
            allCols = view.getReturnColumnList();
        }
        if (matrix.getMatrixType() == IMatrix.TYPE_TABLE) {
            ITable table = (ITable) matrix;
            allCols = table.getColumns();
        }
        if (allCols == null) {
            return;
        }
        List<IColumn> metaEditCols = matrix.getEditColumns();
        List<IColumn> metaSearchCols = matrix.getDetailColumns();
        List<IColumn> metaAddCols = matrix.getAddColumns();
        List<IColumn> metaDetailCols = matrix.getDetailColumns();
        List<IColumn> metaListCols = matrix.getListColumns();

        List<IColumn> metaShowCols = matrix.getDetailColumns();
        List<IColumn> metaSortCols = matrix.getSortColumns();
        IBusinessColumn orderColsArray[] = new IBusinessColumn[metaShowCols.size()];
        Map metaUkMap = matrix.getMainTable().getUkColumns();
        Map metaPkShowMap = matrix.getMainTable().getPkColumns();
        for (IColumn col : allCols) {
            BusinessColumn bc = new BusinessColumn(col);
            bc.setParentModel(this);
            int orderIndex = metaShowCols.indexOf(col);
            if (orderIndex > -1) {
                orderColsArray[orderIndex] = bc;
            }
            if (metaSearchCols.contains(col)) {
                searchCols.add(bc);
            }
            if (metaEditCols.contains(col)) {
                editCols.add(bc);
            }
            if (metaAddCols.contains(col)) {
                addCols.add(bc);
            }
            if (metaDetailCols.contains(col)) {
                detailCols.add(bc);
            }
            if (metaListCols.contains(col)) {
                gridShowColumns.add(bc);
            }
            if (metaSortCols.contains(col)) {
                sortCols.add(bc);
            }
            if (metaUkMap.containsValue(col)) {
                mutiUkCols.add(bc);
            }
            if (metaPkShowMap.containsValue(col)) {
                refShowColumns.add(bc);
            }
            allBcCols.add(bc);
        }
    }

    public BusinessModelEnum getModelType() {
        if (matrix.getMatrixType() == IMatrix.TYPE_VIEW) {
            return BusinessModelEnum.View;
        } else {
            return BusinessModelEnum.Table;
        }
    }

    public String getDefaultSelect() {
        StringBuilder defaultSelect = new StringBuilder();
        ITable table = matrix.getMainTable();
        if (null != table) {
            defaultSelect.append(table.getTableName() + ".ID");
            if (this.getTableType() == BusinessModelEnum.BusinessTableEnum.ShareTable) {
                defaultSelect.append(",")
                        .append(table.getTableName() + ".SYS_SCHEMA");
            }
        }
        return defaultSelect.toString();
    }

    public List<IBusinessColumn> getAllBcCols() {
        return allBcCols;
    }

    //得到组合唯一校验的字段集合
    public List<IBusinessColumn> getMultyUkColumns() {
        return mutiUkCols;
    }

    //得到主键显示值字段集合
    public List<IBusinessColumn> getRefShowColumns() {
        return this.refShowColumns;
    }

    public IBusinessColumn getBusinessColumnByID(String colID) {
        for (IBusinessColumn bc : allBcCols) {
            if (bc.getCol().getId().equals(colID)) {
                return bc;
            }
        }
        return null;
    }

    public IBusinessColumn getBusinessColumnBySName(String sColName) {
        for (IBusinessColumn bc : allBcCols) {
            if (bc.getS_column_name().equalsIgnoreCase(sColName)) {
                return bc;
            }
        }
        return null;
    }

    public boolean isSelfRelation() {
        for (IBusinessColumn bc : allBcCols) {
            if (bc.getColType() == BusinessColumnEnum.C_Relation) {
                if (bc.getCol().getRelationColumnIF().getRefTable().getId() == this.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getSelfRelColName() {
        if (isSelfRelation()) {
            return this.getS_table_name() + "_ID";
        }
        throw new SqlEngineException("[error]is not self table");
    }

    public void appendCustomerFilter(CustomerFilter filter) {
        custFilterList.add(filter);
    }

    public List<IBusinessColumn> getSortCols() {
        return sortCols;
    }

    public IMatrixRight getMatrixRight() {
        return matrixRight;
    }

    public void setMatrixRight(IMatrixRight matrixRight) {
        this.matrixRight = matrixRight;
    }

    public String getUserSecrecy() {
        return userSecrecy;
    }

    public void setUserSecrecy(String userSecrecy) {
        this.userSecrecy = userSecrecy;
    }

    public void setPedigree(List<List<BusinessModelEdge>> pedigreeList) {
        this.pedigreeList = pedigreeList;
    }

    public List<List<BusinessModelEdge>> getPedigreeList() {
        return pedigreeList;
    }

    public List<CustomerFilter> getCustFilterList() {
        return custFilterList;
    }

    public String getReserve_filter() {
        return CommonTools.null2String(reserve_filter);
    }

    public void setReserve_filter(String reserve_filter) {
        this.reserve_filter = reserve_filter;
    }

    public void clearCustomFilter() {
        custFilterList.clear();
    }

    public void clearAllFilter() {
        this.reserve_filter = "";
        clearCustomFilter();
    }

    public IBusinessModel getMainModel() {
        return mainModel;
    }

    public void setMainModel(IBusinessModel mainModel) {
        this.mainModel = mainModel;
    }

    public List<IBusinessColumn> getGridShowColumns() {
        //排序
        return gridShowColumns;
    }

//    private Comparator<IBusinessColumn> comparator = (c1, c2) -> {
//        List<IColumn> sortedColumns = getSortedColumns();
//        return sortedColumns.indexOf(c1.getCol()) - sortedColumns.indexOf(c2.getCol());
//    };

    @Override
    public List<IColumn> getSortedColumns() {
        return this.matrix.getSortedColumns();
    }

    @Override
    public List<IBusinessColumn> getEditCols() {
        return editCols;
    }

    public List<IBusinessColumn> getMutiUkCols() {
        return mutiUkCols;
    }

    public List<IBusinessColumn> getAddCols() {
        return addCols;
    }

    public List<IBusinessColumn> getDetailCols() {
        return detailCols;
    }

    @Override
    public List<IBusinessColumn> getSearchCols() {
        return searchCols;
    }

    @Override
    public String getName() {
        return this.mainModel.getMatrix().getName();
    }

}
