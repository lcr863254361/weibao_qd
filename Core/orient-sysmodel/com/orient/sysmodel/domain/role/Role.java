package com.orient.sysmodel.domain.role;

import com.orient.metamodel.operationinterface.*;
import com.orient.sysmodel.domain.arith.Arith;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.*;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;

import java.util.*;
import java.util.stream.Collectors;

// default package


/**
 * Role entity. @author MyEclipse Persistence Tools
 */
public class Role extends AbstractRole implements java.io.Serializable, IRole {

    //add zhy 2012-10-25 角色相关的顶层TBOM节点
    private List<ITbom> allTboms;

    /**
     * allTboms
     *
     * @return the allTboms
     * @since CodingExample Ver 1.0
     */

    public List<ITbom> getAllTboms() {
        return allTboms;
    }


    /**
     * allTboms
     *
     * @param allTboms the allTboms to set
     * @since CodingExample Ver 1.0
     */

    public void setAllTboms(List<ITbom> allTboms) {
        this.allTboms = allTboms;
    }
    //end

    // Constructors


    /**
     * default constructor
     */
    public Role() {
    }


    /**
     * minimal constructor
     */
    public Role(String name) {
        super(name);
    }

    /**
     * full constructor
     */
    public Role(String name, String memo, String type, String status, String flg) {
        super(name, memo, type, status, flg);
    }

    /**
     * @return
     * @Method: getAllArith
     * <p>
     * 取得角色下的算法信息
     * @see com.orient.sysmodel.operationinterface.IRole
     */

    @Override
    public List<IArith> getAllAriths() {

        List<IArith> retList = new ArrayList<IArith>();
        Set<RoleArith> roleArithSet = this.getRoleAriths();
        for (RoleArith roleArith : roleArithSet) {
            retList.add(roleArith.getArith());
        }
        return retList;

    }

    /**
     * @return
     * @Method: getAllValidAriths
     * <p>
     * 取得角色下启用的算法信息
     * @see com.orient.sysmodel.operationinterface.IRole#getAllValidAriths()
     */
    public List<IArith> getAllValidAriths() {
        List<IArith> retList = new ArrayList<IArith>();
        Set<RoleArith> roleArithSet = this.getRoleAriths();
        for (RoleArith roleArith : roleArithSet) {
            if (roleArith.getArith().getIsValid() == 1) {
                retList.add(roleArith.getArith());
            }

        }
        return retList;
    }

    /**
     * @param type
     * @return
     * @Method: getArithsByType
     * <p>
     * 取得角色下指定算法类型的算法信息(算法类型:数据库自带算法（0），自定义算法（1），自定义算法jar文件（2），自定义算法类名（3），自定义算法方法名（4）)
     * @see com.orient.sysmodel.operationinterface.IRole#getArithsByType(java.lang.String)
     */
    public List<IArith> getArithsByType(String type) {
        List<IArith> retList = new ArrayList<IArith>();
        Set<RoleArith> roleArithSet = this.getRoleAriths();
        for (RoleArith roleArith : roleArithSet) {
            if (roleArith.getArith().getIsValid() == 1 && roleArith.getArith().getType().toString().equalsIgnoreCase(type)) {
                retList.add(roleArith.getArith());
            }

        }
        return retList;
    }

    /**
     * @param category
     * @return
     * @Method: getArithsByCategory
     * <p>
     * 取得角色下指定算法类别的算法信息
     * @see com.orient.sysmodel.operationinterface.IRole#getArithsByCategory(java.lang.String)
     */
    public List<IArith> getArithsByCategory(String category) {
        List<IArith> retList = new ArrayList<IArith>();
        Set<RoleArith> roleArithSet = this.getRoleAriths();
        for (RoleArith roleArith : roleArithSet) {
            if (roleArith.getArith().getIsValid() == 1 && roleArith.getArith().getCategory().toString().equalsIgnoreCase(category)) {
                retList.add(roleArith.getArith());
            }

        }
        return retList;
    }

    /**
     * @param arithId
     * @return
     * @Method: getArithById
     * <p>
     * 取得角色下指定id的算法
     * @see com.orient.sysmodel.operationinterface.IRole#getArithById(java.lang.String)
     */
    public IArith getArithById(String arithId) {
        Arith arith = new Arith();
        Set<RoleArith> roleArithSet = this.getRoleAriths();
        for (RoleArith roleArith : roleArithSet) {
            if (roleArith.getArith().getId().equalsIgnoreCase(arithId)) {
                arith = roleArith.getArith();
            }

        }
        return arith;
    }

    /**
     * @param arithName
     * @return
     * @Method: getArithByName
     * <p>
     * 取得角色下指定名称的算法
     * @see com.orient.sysmodel.operationinterface.IRole#getArithByName(java.lang.String)
     */
    public IArith getArithByName(String arithName) {
        Arith arith = new Arith();
        Set<RoleArith> roleArithSet = this.getRoleAriths();
        for (RoleArith roleArith : roleArithSet) {
            if (roleArith.getArith().getName().equalsIgnoreCase(arithName)) {
                arith = roleArith.getArith();
            }

        }
        return arith;
    }

    /**
     * @return
     * @Method: getAllFunctionTboms
     * <p>
     * 取得角色下的功能Tbom信息
     * @see com.orient.sysmodel.operationinterface.IRole#getAllFunctionTboms()
     */

    @Override
    public List<IRoleFunctionTbom> getAllFunctionTboms() {

        List<IRoleFunctionTbom> retList = new ArrayList<IRoleFunctionTbom>();
        Set<IRoleFunctionTbom> roleFunctionTbomSet = this.getRoleFunctionTboms();
        for (IRoleFunctionTbom roleFunctionTbom : roleFunctionTbomSet) {
            if (roleFunctionTbom.getTbomDir().getId().equalsIgnoreCase(" ") == false) {
                retList.add(roleFunctionTbom);
            }

        }
        return retList;

    }

    /**
     * @param functionId
     * @param tbomDirId
     * @return
     * @Method: getFunctionTbom
     * <p>
     * 取得角色下指定的功能Tbom信息
     * @see com.orient.sysmodel.operationinterface.IRole#getFunctionTbom(java.lang.String, java.lang.String)
     */
    public IRoleFunctionTbom getFunctionTbom(String functionId, String tbomDirId) {
        IRoleFunctionTbom roleFunTbom = new RoleFunctionTbom();
        Set<IRoleFunctionTbom> roleFunctionTbomSet = this.getRoleFunctionTboms();
        for (IRoleFunctionTbom roleFunctionTbom : roleFunctionTbomSet) {
            if (roleFunctionTbom.getFunction().getFunctionid().toString().equalsIgnoreCase(functionId) && roleFunctionTbom.getTbomDir().getId().equalsIgnoreCase(tbomDirId)) {
                roleFunTbom = roleFunctionTbom;
            }
        }
        return roleFunTbom;
    }

    /**
     * @return
     * @Method: getAllFunctions
     * <p>
     * 取得角色下的所有功能点信息
     * @see com.orient.sysmodel.operationinterface.IRole#getAllFunctions()
     */

    @Override
    public List<IFunction> getAllFunctions() {

        List<IFunction> retList = new ArrayList<IFunction>();
        Map<Long, IFunction> FucntionMap = new HashMap<Long, IFunction>();
        Set<IRoleFunctionTbom> roleFunctionTBOMSet = this.getRoleFunctionTboms();
        for (IRoleFunctionTbom roleFunctionTbom : roleFunctionTBOMSet) {

            Long functionId = roleFunctionTbom.getFunction().getFunctionid();
            if (!FucntionMap.containsKey(functionId)) {
                FucntionMap.put(functionId, roleFunctionTbom.getFunction());
            }

        }
        retList.addAll(FucntionMap.values());
        return retList;
    }

    /**
     * @param functionId
     * @return
     * @Method: getFunctionById
     * <p>
     * 根据Id取得功能点
     * @see com.orient.sysmodel.operationinterface.IRole#getFunctionById(java.lang.String)
     */
    public IFunction getFunctionById(String functionId) {

        List<IFunction> roleFunctionList = this.getAllFunctions();
        for (IFunction loopFunction : roleFunctionList) {
            if (loopFunction.getFunctionid().toString().equalsIgnoreCase(functionId)) {
                return loopFunction;
            }
        }
        return null;
    }

    /**
     * @param functionName
     * @return
     * @Method: getFunctionByName
     * <p>
     * 根据名称取得功能点
     * @see com.orient.sysmodel.operationinterface.IRole#getFunctionByName(java.lang.String)
     */
    public IFunction getFunctionByName(String functionName) {
        List<IFunction> roleFunctionList = this.getAllFunctions();
        for (IFunction loopFunction : roleFunctionList) {
            if (loopFunction.getName().equalsIgnoreCase(functionName)) {
                return loopFunction;
            }
        }
        return null;
    }

    /**
     * @param code
     * @return
     * @Method: getFunctionByCode
     * <p>
     * 根据编码取得功能点
     * @see com.orient.sysmodel.operationinterface.IRole#getFunctionByCode(java.lang.String)
     */
    public IFunction getFunctionByCode(String code) {
        List<IFunction> roleFunctionList = this.getAllFunctions();
        for (IFunction loopFunction : roleFunctionList) {
            if (loopFunction.getCode().equalsIgnoreCase(code)) {
                return loopFunction;
            }
        }
        return null;
    }

    /**
     * @return
     * @Method: getAllSchemas
     * <p>
     * 取得角色下的业务库信息
     * @see com.orient.sysmodel.operationinterface.IRole#getAllSchemas()
     */

    @Override
    public List<ISchema> getAllSchemas() {

        List<ISchema> retList = new ArrayList<ISchema>();
        Set<RoleSchema> roleSchemaSet = this.getRoleSchemas();
        for (RoleSchema roleSchema : roleSchemaSet) {
            retList.add(roleSchema.getSchema());
        }
        return retList;

    }

    /**
     * @param schemaId
     * @return
     * @Method: getSchemaById
     * <p>
     * 根据Id取得功能点
     * @see com.orient.sysmodel.operationinterface.IRole#getSchemaById(java.lang.String)
     */
    public ISchema getSchemaById(String schemaId) {

        List<ISchema> roleSchemaList = this.getAllSchemas();
        for (ISchema loopschema : roleSchemaList) {
            if (loopschema.getId().toString().equalsIgnoreCase(schemaId)) {
                return loopschema;
            }
        }
        return null;
    }

    /**
     * @return
     * @Method: getAllTbomDirs
     * <p>
     * 取得角色下的Tbom信息
     * @see com.orient.sysmodel.operationinterface.IRole#getAllTbomDirs()
     */

    @Override
    public List<ITbomDir> getAllTbomDirs() {

        List<ITbomDir> retList = new ArrayList<ITbomDir>();
        Set<IRoleFunctionTbom> roleFunctionTbomSet = this.getRoleFunctionTboms();
        for (IRoleFunctionTbom roleFunctionTbom : roleFunctionTbomSet) {
            retList.add(roleFunctionTbom.getTbomDir());
        }
        return retList;

    }

    //delete zhy 2012-10-25 一下取角色相关的顶层TBOM，需查询数据库，比较耗时间

    /**
     * @return
     * @Method: getAllTboms
     * <p>
     * TODO
     * @see com.orient.sysmodel.operationinterface.IRole#getAllTboms()
     */
    /*public List<ITbom> getAllTboms(){
        List<ITbom> retList = new ArrayList<ITbom>();
		List<ITbomDir> tbomDirList = this.getAllTbomDirs();
		if(tbomDirList!=null && tbomDirList.size()>0){
			for(int i=0; i<tbomDirList.size(); i++){
				ITbom tbom= this.getTbomByTbomDir(tbomDirList.get(i));
				if(tbom!=null){
					retList.add(tbom);
				}
			}
		}
		return retList;
	}*/
    @Override
    public ITbomDir getTbomDirByName(String tbomDirName) {
        List<IRoleFunctionTbom> roleFunctionTboms = this.getRoleFunctionTboms().stream().filter(roleFunctionTbom -> tbomDirName.equalsIgnoreCase(roleFunctionTbom.getTbomDir() != null ? "null" : roleFunctionTbom.getTbomDir().getName()))
                .collect(Collectors.toList());
        return roleFunctionTboms != null && roleFunctionTboms.size() > 0 ? roleFunctionTboms.get(0).getTbomDir() : null;
    }

    @Override
    public ITbomDir getTbomDirById(String tbomDirId) {
        List<IRoleFunctionTbom> roleFunctionTboms = this.getRoleFunctionTboms().stream().filter(roleFunctionTbom -> tbomDirId.equalsIgnoreCase(roleFunctionTbom.getTbomDir() == null ? "null" : roleFunctionTbom.getTbomDir().getId()))
                .collect(Collectors.toList());
        return roleFunctionTboms != null && roleFunctionTboms.size() > 0 ? roleFunctionTboms.get(0).getTbomDir() : null;
    }


    /**
     * @return
     * @Method: getAllUsers
     * <p>
     * 取得角色下的所有用户
     * @see com.orient.sysmodel.operationinterface.IRole#getAllUsers()
     */

    @Override
    public List<IUser> getAllUsers() {

        List<IUser> retList = new ArrayList<IUser>();
        Set<RoleUser> roleUserSet = this.getRoleUsers();
        for (RoleUser roleUser : roleUserSet) {
            if (!roleUser.getUser().getUserName().contains("&")){
                retList.add(roleUser.getUser());
            }
        }
        return retList;

    }

    /**
     * @return
     * @Method: getAllValidUsers
     * <p>
     * 取得角色下的所有启用的用户
     * @see com.orient.sysmodel.operationinterface.IRole#getAllValidUsers()
     */
    public List<IUser> getAllValidUsers() {
        List<IUser> retList = new ArrayList<IUser>();
        Set<RoleUser> roleUserSet = this.getRoleUsers();
        for (RoleUser roleUser : roleUserSet) {
            if (roleUser.getUser().getState().equalsIgnoreCase("1")) {
                retList.add(roleUser.getUser());
            }

        }
        return retList;
    }

    /**
     * @return
     * @Method: getAllValidUsersNotFlag
     * <p>
     * 取得角色下的所有启用的非固化用户
     * @see com.orient.sysmodel.operationinterface.IRole#getAllValidUsersNotFlag()
     */
    public List<IUser> getAllValidUsersNotFlag() {
        List<IUser> retList = new ArrayList<IUser>();
        Set<RoleUser> roleUserSet = this.getRoleUsers();
        for (RoleUser roleUser : roleUserSet) {
            if (roleUser.getUser().getState().equalsIgnoreCase("1") && (roleUser.getUser().getFlg() == null || !roleUser.getUser().getFlg().equalsIgnoreCase("1"))) {
                retList.add(roleUser.getUser());
            }

        }
        return retList;
    }

    /**
     * @param id
     * @return
     * @Method: getUserById
     * <p>
     * 取得角色下的指定id的用户
     * @see com.orient.sysmodel.operationinterface.IRole#getUserById(java.lang.String)
     */
    public IUser getUserById(String id) {
        User user = new User();
        Set<RoleUser> roleUserSet = this.getRoleUsers();
        for (RoleUser roleUser : roleUserSet) {
            if (roleUser.getUser().getId().equalsIgnoreCase(id)) {
                user = roleUser.getUser();
            }

        }
        return user;
    }

    /**
     * @param userName
     * @return
     * @Method: getUserByUserName
     * <p>
     * 取得角色下的指定用户名的用户
     * @see com.orient.sysmodel.operationinterface.IRole#getUserByUserName(java.lang.String)
     */
    public IUser getUserByUserName(String userName) {
        User user = new User();
        Set<RoleUser> roleUserSet = this.getRoleUsers();
        for (RoleUser roleUser : roleUserSet) {
            if (roleUser.getUser().getUserName().equalsIgnoreCase(userName)) {
                user = roleUser.getUser();
            }

        }
        return user;
    }

    /**
     * @param allName
     * @return
     * @Method: getUserByAllName
     * <p>
     * 取得角色下的指定真实姓名的用户
     * @see com.orient.sysmodel.operationinterface.IRole#getUserByAllName(java.lang.String)
     */
    public IUser getUserByAllName(String allName) {
        User user = new User();
        Set<RoleUser> roleUserSet = this.getRoleUsers();
        for (RoleUser roleUser : roleUserSet) {
            if (roleUser.getUser().getAllName().equalsIgnoreCase(allName)) {
                user = roleUser.getUser();
            }

        }
        return user;
    }

    /**
     * @return
     * @Method: getAllOperations
     * <p>
     * 取得角色下的操作权限信息
     * @see com.orient.sysmodel.operationinterface.IRole#getAllOperations()
     */

    @Override
    public List<IOverAllOperations> getAllOperations() {

        List<IOverAllOperations> retList = new ArrayList<IOverAllOperations>();
        Set<OverAllOperations> overAllOperationsSet = this.getOverAllOperations();
        for (OverAllOperations overAllOperations : overAllOperationsSet) {
            retList.add(overAllOperations);
        }
        return retList;

    }

    /**
     * @return
     * @Method: getAllPartOperations
     * <p>
     * 取得角色下的表访问权限信息
     * @see com.orient.sysmodel.operationinterface.IRole#getAllPartOperations()
     */

    @Override
    public List<IPartOperations> getAllPartOperations() {

        List<IPartOperations> retList = new ArrayList<IPartOperations>();
        Set<PartOperations> partOperationsSet = this.getPartOperations();
        for (PartOperations partOperations : partOperationsSet) {
            retList.add(partOperations);
        }
        return retList;

    }

    /**
     * @param tableId
     * @return
     * @Method: getPartOperationsOfTable
     * <p>
     * 取得角色下的指定数据源的表访问权限信息
     * @see com.orient.sysmodel.operationinterface.IRole#getPartOperationsOfTable(java.lang.String)
     */
    public List<IPartOperations> getPartOperationsOfTable(String tableId) {
        List<IPartOperations> retList = new ArrayList<IPartOperations>();
        Set<PartOperations> partOperationsSet = this.getPartOperations();
        for (PartOperations partOperations : partOperationsSet) {
            if (partOperations.getTableId().equalsIgnoreCase(tableId)) {
                retList.add(partOperations);
            }

        }
        return retList;
    }

    /**
     * @param matrixId
     * @return
     * @Method: getRightsOfMatrix
     * <p>
     * 取得角色下的数据源访问权限(如果没有设置表访问权限, 则数据源的访问权限来自角色的操作权限;如果设置了表访问权限,则数据源的访问权限来自表访问权限设置)
     * @see com.orient.sysmodel.operationinterface.IRole#
     */
    private MatrixRight getRightsOfMatrix(String matrixId) {
        MatrixRight matrixRight = new MatrixRight();
        List<IOverAllOperations> overAllOperations = this.getAllOperations();
        List<IPartOperations> partOperations = this.getPartOperationsOfTable(matrixId);
        boolean isSet = false;
        if (partOperations.size() > 0) {
            for (int i = 0; i < partOperations.size(); i++) {
                List<String> emptyList = new ArrayList<>();
                IPartOperations partOperation = partOperations.get(i);
                matrixRight.setListColIds(StringUtil.isEmpty(partOperation.getColumnId()) ? emptyList : CommonTools.arrayToList(partOperation.getColumnId().split(",")));
                matrixRight.setAddColIds(StringUtil.isEmpty(partOperation.getAddColumnIds()) ? emptyList : CommonTools.arrayToList(partOperation.getAddColumnIds().split(",")));
                matrixRight.setModifyColIds(StringUtil.isEmpty(partOperation.getModifyColumnIds()) ? emptyList : CommonTools.arrayToList(partOperation.getModifyColumnIds().split(",")));
                matrixRight.setDetailColIds(StringUtil.isEmpty(partOperation.getDetailColumnIds()) ? emptyList : CommonTools.arrayToList(partOperation.getDetailColumnIds().split(",")));
                matrixRight.setExportColIds(StringUtil.isEmpty(partOperation.getExportColumnIds()) ? emptyList : CommonTools.arrayToList(partOperation.getExportColumnIds().split(",")));
                List<Long> btnTypeIds = new ArrayList<>();
                if (!StringUtil.isEmpty(partOperation.getOperationsId())) {
                    String[] array = partOperation.getOperationsId().split(",");
                    for (String btnTypeId : array) {
                        btnTypeIds.add(Long.valueOf(btnTypeId));
                    }
                }
                matrixRight.setBtnTypeIds(btnTypeIds);
                matrixRight.setFilter(partOperations.get(i).getFilter());
                matrixRight.setUserFilter(partOperations.get(i).getUserFilter());
                matrixRight.setModelRightSet(true);
                isSet = true;
                break;
            }
        }
        if (isSet == false) {
            if (overAllOperations.size() > 0) {
                this.setMatrixRight(matrixRight, overAllOperations.get(0).getOperationIds());
            }

        }

        return matrixRight;
    }

    /**
     * @param matrix
     * @return
     * @Method: getRightsOfMatrix
     * <p>
     * 取得角色下的数据源访问权限(包括字段访问权限)
     * @see com.orient.sysmodel.operationinterface.IRole#getRightsOfMatrix(com.orient.metamodel.operationinterface.IMatrix)
     */
    public IMatrixRight getRightsOfMatrix(IMatrix matrix) {
        MatrixRight matrixRight = this.getRightsOfMatrix(matrix.getId());

        List<IColumn> columnList = new ArrayList<IColumn>();
        if (matrix.getMatrixType() == 0) {
            //表
            columnList = ((ITable) matrix).getColumns();
        } else if (matrix.getMatrixType() == 1) {
            //视图
            columnList = ((IView) matrix).getReturnColumnList();
        }
        if (columnList.size() > 0) {
            Map<String, IColumnRight> columnRightMap = new HashMap<String, IColumnRight>();
            for (int i = 0; i < columnList.size(); i++) {
                IColumnRight columnRight = this.getRightsOfColumn(matrix.getId(), columnList.get(i).getId());
                if (null != columnRight) {
                    columnRightMap.put(columnList.get(i).getId(), columnRight);
                }
            }
            matrixRight.setColumnRights(columnRightMap);
        }
        return matrixRight;
    }

    /**
     * @param matrixId
     * @param columnId
     * @return
     * @Method: getRightsOfColumn
     * <p>
     * 取得角色下的数据源中指定字段的访问权限(如果没有设置字段访问权限, 则字段的访问权限来自表访问操作权限;如果设置了字段访问权限,则字段的访问权限来自字段访问权限设置)
     * @see com.orient.sysmodel.operationinterface.IRole#getRightsOfColumn(java.lang.String, java.lang.String)
     */
    public IColumnRight getRightsOfColumn(String matrixId, String columnId) {
        ColumnRight columnRight = new ColumnRight();
        return (IColumnRight) columnRight;
    }

    /**
     * @param matrixRight
     * @param operationsId
     * @return void
     * @throws
     * @Method: setMatrixRight
     * <p>
     * 根据操作id设置操作权限
     */
    private void setMatrixRight(AbstractRight matrixRight, String operationsId) {
        List<Long> btnTypeIds = new ArrayList<>();
        if (operationsId == null)
            return;
        else {
            String[] array = operationsId.split(",");
            for (String btnTypeId : array) {
                btnTypeIds.add(Long.valueOf(btnTypeId));
            }
            matrixRight.setBtnTypeIds(btnTypeIds);
        }
    }

    /**
     * @param tableId
     * @param columnId
     * @return
     * @Method: getPartOperationsOfTableColumn
     * <p>
     * 取得角色下的指定数据源中指定字段的表访问权限信息
     * @see com.orient.sysmodel.operationinterface.IRole#getPartOperationsOfTableColumn(java.lang.String, java.lang.String)
     */

    @Override
    public IPartOperations getPartOperationsOfTableColumn(String tableId,
                                                          String columnId) {

        PartOperations operations = null;
        Set<PartOperations> partOperationsSet = this.getPartOperations();
        for (PartOperations partOperations : partOperationsSet) {
            if (partOperations.getTableId().equalsIgnoreCase(tableId) && partOperations.getColumnId().contains(columnId)) {
                operations = partOperations;
            }
        }
        return operations;

    }


    /**
     * roleFunctions
     *
     * @param
     * @since CodingExample Ver 1.0
     */


    @SuppressWarnings("unchecked")
    public RoleUser findRoleUser(RoleUserId roleUserId) {
        for (Iterator<RoleUser> it = this.getRoleUsers().iterator(); it.hasNext(); ) {
            RoleUser roleUser = it.next();
            if (roleUser.getId().toString().equals(roleUserId.toString()))
                return roleUser;
        }

        return null;
    }

    public RoleUser deleteRoleUser(RoleUserId roleUserId) {
        RoleUser roleUser = findRoleUser(roleUserId);
        if (roleUser != null)
            this.getRoleUsers().remove(roleUser);

        return roleUser;

    }

    @SuppressWarnings("unchecked")
    public IRoleFunctionTbom findRoleFunTbom(RoleFunctionTbomId roleFunTbomId) {

        for (Iterator<IRoleFunctionTbom> it = this.getRoleFunctionTboms().iterator(); it.hasNext(); ) {
            IRoleFunctionTbom roleFunTbom = it.next();
            if (roleFunTbom.getTbomDir().getId().toString().equals(roleFunTbomId.toString()))
                return roleFunTbom;
        }

        return null;
    }

    public IRoleFunctionTbom deleteRoleFunTbom(RoleFunctionTbomId roleFunTbomId) {

        IRoleFunctionTbom roleFunTbom = findRoleFunTbom(roleFunTbomId);
        if (roleFunTbom != null)
            this.getRoleFunctionTboms().remove(roleFunTbom);

        return roleFunTbom;
    }


}
