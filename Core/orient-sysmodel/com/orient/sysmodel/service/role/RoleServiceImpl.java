/**
 * RoleServiceImpl.java
 * com.sysmodel.service.role
 * <p>
 * Function： TODO
 * <p>
 * ver     date      		author
 * ──────────────────────────────────
 * 2012-3-16 		zhang yan
 * <p>
 * Copyright (c) 2012, TNT All Rights Reserved.
 */

package com.orient.sysmodel.service.role;

import java.util.*;

import com.orient.metamodel.metadomain.MetaModel;
import com.orient.metamodel.metadomain.Schema;
import com.orient.metamodel.metaengine.MetaUtil;
import com.orient.metamodel.operationinterface.ISchema;
import com.orient.sysmodel.dao.form.IModelBtnTypeDao;
import com.orient.sysmodel.domain.arith.Arith;
import com.orient.sysmodel.domain.form.ModelBtnTypeEntity;
import com.orient.sysmodel.domain.role.Function;
import com.orient.sysmodel.domain.role.Operation;
import com.orient.sysmodel.domain.role.OperationDAO;
import com.orient.sysmodel.domain.role.OverAllOperations;
import com.orient.sysmodel.domain.role.OverAllOperationsDAO;
import com.orient.sysmodel.domain.role.PartOperations;
import com.orient.sysmodel.domain.role.PartOperationsDAO;
import com.orient.sysmodel.domain.role.Role;
import com.orient.sysmodel.domain.role.RoleArith;
import com.orient.sysmodel.domain.role.RoleArithDAO;
import com.orient.sysmodel.domain.role.RoleArithId;
import com.orient.sysmodel.domain.role.RoleDAO;
import com.orient.sysmodel.domain.role.RoleFunctionTbom;
import com.orient.sysmodel.domain.role.RoleFunctionTbomDAO;
import com.orient.sysmodel.domain.role.RoleFunctionTbomId;
import com.orient.sysmodel.domain.role.RoleSchema;
import com.orient.sysmodel.domain.role.RoleSchemaDAO;
import com.orient.sysmodel.domain.role.RoleSchemaId;
import com.orient.sysmodel.domain.role.RoleUser;
import com.orient.sysmodel.domain.role.RoleUserDAO;
import com.orient.sysmodel.domain.role.RoleUserId;
import com.orient.sysmodel.domain.tbom.TbomDir;
import com.orient.sysmodel.domain.user.User;
import com.orient.sysmodel.operationinterface.IOperation;
import com.orient.sysmodel.operationinterface.IOverAllOperations;
import com.orient.sysmodel.operationinterface.IRoleModel;
import com.orient.sysmodel.roleengine.IRoleUtil;
import com.orient.utils.CommonTools;
import com.orient.utils.StringUtil;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import static javax.swing.UIManager.get;

/**
 * ClassName:RoleServiceImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author zhang yan
 * @Date 2012-3-16		下午02:56:18
 * @see
 * @since Ver 1.1
 */
public class RoleServiceImpl extends com.orient.sysmodel.domain.BaseBean implements RoleService {

    private RoleDAO dao;
    private RoleArithDAO roleArithDao;
    private RoleSchemaDAO roleSchemaDao;
    private RoleFunctionTbomDAO roleFunctionTbomDao;
    private RoleUserDAO roleUserDao;
    private OverAllOperationsDAO overAllOperationsDao;
    private PartOperationsDAO partOperationsDao;
    private OperationDAO operationDao;
    private MetaUtil metaEngine;
    private IRoleUtil roleEngine;
    private IModelBtnTypeDao modelBtnTypeDao;

    public IModelBtnTypeDao getModelBtnTypeDao() {
        return modelBtnTypeDao;
    }

    public void setModelBtnTypeDao(IModelBtnTypeDao modelBtnTypeDao) {
        this.modelBtnTypeDao = modelBtnTypeDao;
    }

    /**
     * dao
     *
     * @return the dao
     * @since CodingExample Ver 1.0
     */

    public RoleDAO getDao() {
        return dao;
    }

    /**
     * dao
     *
     * @param dao the dao to set
     * @since CodingExample Ver 1.0
     */

    public void setDao(RoleDAO dao) {
        this.dao = dao;
    }

    /**
     * roleArithDao
     *
     * @return the roleArithDao
     * @since CodingExample Ver 1.0
     */

    public RoleArithDAO getRoleArithDao() {
        return roleArithDao;
    }

    /**
     * roleArithDao
     *
     * @param roleArithDao the roleArithDao to set
     * @since CodingExample Ver 1.0
     */

    public void setRoleArithDao(RoleArithDAO roleArithDao) {
        this.roleArithDao = roleArithDao;
    }

    /**
     * roleSchemaDao
     *
     * @return the roleSchemaDao
     * @since CodingExample Ver 1.0
     */

    public RoleSchemaDAO getRoleSchemaDao() {
        return roleSchemaDao;
    }

    /**
     * roleSchemaDao
     *
     * @param roleSchemaDao the roleSchemaDao to set
     * @since CodingExample Ver 1.0
     */

    public void setRoleSchemaDao(RoleSchemaDAO roleSchemaDao) {
        this.roleSchemaDao = roleSchemaDao;
    }

    /**
     * roleFunctionTbomDao
     *
     * @return the roleFunctionTbomDao
     * @since CodingExample Ver 1.0
     */

    public RoleFunctionTbomDAO getRoleFunctionTbomDao() {
        return roleFunctionTbomDao;
    }

    /**
     * roleFunctionTbomDao
     *
     * @param roleFunctionTbomDao the roleFunctionTbomDao to set
     * @since CodingExample Ver 1.0
     */

    public void setRoleFunctionTbomDao(RoleFunctionTbomDAO roleFunctionTbomDao) {
        this.roleFunctionTbomDao = roleFunctionTbomDao;
    }

    /**
     * roleUserDao
     *
     * @return the roleUserDao
     * @since CodingExample Ver 1.0
     */

    public RoleUserDAO getRoleUserDao() {
        return roleUserDao;
    }

    /**
     * roleUserDao
     *
     * @param roleUserDao the roleUserDao to set
     * @since CodingExample Ver 1.0
     */

    public void setRoleUserDao(RoleUserDAO roleUserDao) {
        this.roleUserDao = roleUserDao;
    }

    /**
     * overAllOperationsDao
     *
     * @return the overAllOperationsDao
     * @since CodingExample Ver 1.0
     */

    public OverAllOperationsDAO getOverAllOperationsDao() {
        return overAllOperationsDao;
    }

    /**
     * overAllOperationsDao
     *
     * @param overAllOperationsDao the overAllOperationsDao to set
     * @since CodingExample Ver 1.0
     */

    public void setOverAllOperationsDao(OverAllOperationsDAO overAllOperationsDao) {
        this.overAllOperationsDao = overAllOperationsDao;
    }

    /**
     * partOperationsDao
     *
     * @return the partOperationsDao
     * @since CodingExample Ver 1.0
     */

    public PartOperationsDAO getPartOperationsDao() {
        return partOperationsDao;
    }

    /**
     * partOperationsDao
     *
     * @param partOperationsDao the partOperationsDao to set
     * @since CodingExample Ver 1.0
     */

    public void setPartOperationsDao(PartOperationsDAO partOperationsDao) {
        this.partOperationsDao = partOperationsDao;
    }

    /**
     * operationDao
     *
     * @return the operationDao
     * @since CodingExample Ver 1.0
     */

    public OperationDAO getOperationDao() {
        return operationDao;
    }

    /**
     * operationDao
     *
     * @param operationDao the operationDao to set
     * @since CodingExample Ver 1.0
     */

    public void setOperationDao(OperationDAO operationDao) {
        this.operationDao = operationDao;
    }

    /**
     * @return the metaEngine
     */
    public MetaUtil getMetaEngine() {
        return metaEngine;
    }

    /**
     * @param metaEngine the metaEngine to set
     */
    public void setMetaEngine(MetaUtil metaEngine) {
        this.metaEngine = metaEngine;
    }

    /**
     * @return the roleEngine
     */
    public IRoleUtil getRoleEngine() {
        return roleEngine;
    }

    /**
     * @param roleEngine the roleEngine to set
     */
    public void setRoleEngine(IRoleUtil roleEngine) {
        this.roleEngine = roleEngine;
    }

    /**
     * 查找所有的角色
     *
     * @Enclosing_Method : findAll
     * @Version : v1.00
     */
    public List<Role> findAll() {
        return dao.findAll();
    }

    /**
     * 查找所有的角色用户显示
     *
     * @Enclosing_Method : findAllForShow
     * @Version : v1.00
     */
    public List<Role> findAllForShow() {
        return dao.findAllForShow();
    }

    /**
     * 查找角色信息
     *
     * @param roleId
     * @return Role
     * @throws
     * @Method: findById
     * <p>
     * TODO
     */
    public Role findById(String roleId) {
        return dao.findById(roleId);
    }

    /**
     * 按角色名称查找角色信息
     *
     * @param roleName
     * @return Role
     * @throws
     * @Method: findByRoleName
     * <p>
     * TODO
     */
    public Role findByRoleName(String roleName) {
        List<Role> roleList = dao.findByName(roleName);
        if (roleList != null && roleList.size() > 0) {
            return roleList.get(0);
        } else {
            return null;
        }
    }

    public List findByExampleLike(Role instance) {
        return dao.findByExampleLike(instance);
    }

    /**
     * 保存角色信息,并初始化角色包含的业务库
     *
     * @param role
     * @Method: createRole
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.role.RoleService#createRole(com.orient.sysmodel.domain.role.Role, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public void createRole(Role role) {
        //保存角色信息
        dao.save(role);

        // 保存分配给角色的业务库
//		if(!"".equals(schemaIds)){
//			String[] schemaId = schemaIds.split(",");
//
//			MetaModel metaModel = metaEngine.getMeta(false);
//			for(int i=0;i<schemaId.length;i++){
//				RoleSchemaId roleSchemaId = new RoleSchemaId(role.getId(), schemaId[i]);
//				RoleSchema roleSchema = new RoleSchema(roleSchemaId);
//				roleSchema.setRole(role);
//				roleSchema.setSchema((Schema)metaModel.getISchemaById(schemaId[i]));
//
//				roleSchemaDao.save(roleSchema);
//				role.getRoleSchemas().add(roleSchema);
//			}
//		}


        // 初始化角色权限
        OverAllOperations overAllOperations = new OverAllOperations(role.getId(), "0");
        overAllOperations.setRole(role);
        overAllOperationsDao.save(overAllOperations);
        role.getOverAllOperations().add(overAllOperations);

        roleEngine.getRoleModel(false).getRoles().put(role.getId(), role);

    }

    /**
     * 更新角色信息
     *
     * @param role
     * @return void
     * @throws
     * @Method: updateRole
     * <p>
     * TODO
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void updateRole(Role role) {
        //更新角色信息
        dao.attachDirty(role);

        Role roleRam = roleEngine.getRoleModel(false).getRoles().get(role.getId());
        roleRam.setName(role.getName());
        roleRam.setMemo(role.getMemo());

        // 更新分配给角色的业务库
//		if("null".equals(schemaIds) || "".equals(schemaIds)){
//			//业务库删除
//			for(Iterator it = role.getRoleSchemas().iterator(); it.hasNext();){
//				RoleSchema roleSchema = (RoleSchema)it.next();
//				roleSchemaDao.delete(roleSchema);
//				it.remove();
//			}
//		}else if(!"".equals(schemaIds)){
//			// 分配的业务库被更新
//			for(Iterator it = role.getRoleSchemas().iterator(); it.hasNext();){
//				RoleSchema roleSchema = (RoleSchema)it.next();
//				roleSchemaDao.delete(roleSchema);
//				it.remove();
//			}
//
//			MetaModel metaModel = metaEngine.getMeta(false);
//			String[] schemaId = schemaIds.split(",");
//			for(int i=0;i<schemaId.length;i++){
//				RoleSchemaId roleSchemaId = new RoleSchemaId(role.getId(), schemaId[i]);
//				RoleSchema roleSchema = new RoleSchema(roleSchemaId);
//				roleSchema.setRole(role);
//				roleSchema.setSchema((Schema)metaModel.getISchemaById(schemaId[i]));
//
//				roleSchemaDao.save(roleSchema);
//				role.getRoleSchemas().add(roleSchema);
//			}
//		}

    }

    /**
     * 删除角色,及其关联权限信息
     *
     * @param roleIds
     * @return void
     * @throws
     * @Method: deleteRole
     * <p>
     * TODO
     */
    @SuppressWarnings("rawtypes")
    public void deleteRole(String[] roleIds) {
        IRoleModel roleModel = roleEngine.getRoleModel(false);

        for (int i = 0; i < roleIds.length; i++) {
            Role role = roleModel.getRoleById(roleIds[i]);

            //Tbom角色功能信息roleFunctionTbom
            for (Iterator it = role.getRoleFunctionTboms().iterator(); it.hasNext(); ) {
                RoleFunctionTbom roleFunctionTbom = (RoleFunctionTbom) it.next();
                roleFunctionTbomDao.delete(roleFunctionTbom);
                roleFunctionTbom.getFunction().deleteRoleFunTbom(roleFunctionTbom.getId());
                roleFunctionTbom.getTbomDir().deleteRoleFunTbom(roleFunctionTbom.getId());
            }
            //删除角色业务库信息roleSchema
            for (Iterator it = role.getRoleSchemas().iterator(); it.hasNext(); ) {
                RoleSchema roleSchema = (RoleSchema) it.next();
                roleSchemaDao.delete(roleSchema);
            }
            //删除角色权限信息overAllOperations
            for (Iterator it = role.getOverAllOperations().iterator(); it.hasNext(); ) {
                OverAllOperations overAllOperations = (OverAllOperations) it.next();
                overAllOperationsDao.delete(overAllOperations);
            }
            //删除角色特殊权限信息partOperation
            for (Iterator it = role.getPartOperations().iterator(); it.hasNext(); ) {
                PartOperations partOperations = (PartOperations) it.next();
                partOperationsDao.delete(partOperations);
            }
            //删除角色用户信息roleUser
            for (Iterator it = role.getRoleUsers().iterator(); it.hasNext(); ) {
                RoleUser roleUser = (RoleUser) it.next();
                roleUserDao.delete(roleUser);
                roleUser.getUser().deleteRoleUser(roleUser.getId());
            }
            //删除角色算法信息roleArith
            for (Iterator it = role.getRoleAriths().iterator(); it.hasNext(); ) {
                RoleArith roleArith = (RoleArith) it.next();
                roleArithDao.delete(roleArith);
            }

            //删除角色信息
            dao.delete(role);

            roleModel.getRoles().remove(roleIds[i]);
        }
    }

    /**
     * 根据名称模糊查找角色信息
     *
     * @param roleName
     * @return List<Role>
     * @throws
     * @Method: queryRoleList
     * <p>
     * TODO
     */
    public List<Role> queryRoleList(String roleName) {
        StringBuffer sql = new StringBuffer();

        // SQL文拼写
        sql.append("from Role ");
        sql.append("WHERE type IS NULL ");
        if (!"".equals(roleName)) {
            sql.append(" AND name LIKE '%").append(roleName).append("%' ");
        }
        sql.append(" order by id");

        return dao.getSqlResult(sql.toString());
    }

    /**
     * 取得角色的用户信息(不包括特殊权限用户)
     *
     * @param roleIds
     * @return List<User>
     * @throws
     * @Method: getRoleUsers
     * <p>
     * TODO
     */
    public List<User> getRoleUsers(String roleIds) {
        roleIds = roleIds.replaceAll(",", "','");
        StringBuffer sql = new StringBuffer();
        sql.append(" from User ");
        sql.append(" where id in ( select id.userId from RoleUser where id.roleId in('" + roleIds + "'))  ");
        sql.append(" and state='1' ");
        sql.append(" and (flg != '1' or flg is null) ");
        sql.append("order by id asc ");

        return dao.getSqlResult(sql.toString());
    }

    /**
     * 查找没有分配给指定角色的算法
     *
     * @param roleId
     * @param arithName
     * @return
     * @Method: getArithNotAssignedRoleId
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.role.RoleService#getArithNotAssignedRoleId(java.lang.String, java.lang.String)
     */
    public List<Arith> getArithNotAssignedRoleId(String roleId, String arithName) {
        StringBuffer sql = new StringBuffer();
        String where = "";
        if (!"".equals(arithName)) {
            where = "and a.name LIKE '%" + arithName + "%' ";
        }


        // 未被分配的算法信息
        //sql.append("select a.id as ID,a.name as NAME from Arith as a ");
        sql.append("from Arith as a ");
        sql.append("where a.type = '1' ");
        sql.append(where);
        sql.append("and a.id NOT IN ( ");
        sql.append("	select c.id from Arith as c, RoleArith as d ");
        sql.append("	where d.id.roleId = '").append(roleId).append("' ");
        sql.append("		and d.id.arithId = c.id and c.type = '1') ");
        sql.append("order by a.id asc");


        return dao.getSqlResult(sql.toString());
    }

    /**
     * 查找分配给指定角色的算法
     *
     * @param roleId
     * @param arithName
     * @return
     * @Method: getArithAssignedRoleId
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.role.RoleService#getArithAssignedRoleId(java.lang.String, java.lang.String)
     */
    public List<Arith> getArithAssignedRoleId(String roleId, String arithName) {
        StringBuffer sql = new StringBuffer();
        String where = "";
        if (!"".equals(arithName)) {
            where = "and a.name LIKE '%" + arithName + "%' ";
        }

        // 已被分配的算法信息
        sql.append("from Arith as a ");
        sql.append("where a.type = '1' ");
        sql.append(where);
        sql.append("and a.id IN ( ");
        sql.append("	select c.id from Arith as c, RoleArith as d ");
        sql.append("	where d.id.roleId = '").append(roleId).append("' ");
        sql.append("		and d.id.arithId = c.id and c.type = '1') ");
        sql.append("order by a.id asc");

        return dao.getSqlResult(sql.toString());
    }

    /**
     * 保存角色算法信息
     *
     * @param roleId
     * @param arithIds
     * @return void
     * @throws
     * @Method: createRoleAriths
     * <p>
     * TODO
     */
    public void createRoleAriths(String roleId, String arithIds) {
        String[] arithId = arithIds.split(",");
        for (int i = 0; i < arithId.length; i++) {
            RoleArithId roleArithId = new RoleArithId(roleId, arithId[i]);
            RoleArith roleArith = new RoleArith(roleArithId);
            roleArithDao.save(roleArith);
        }

        //更新内存信息
        Role role = dao.findById(roleId);
        IRoleUtil engine = (IRoleUtil) getBean("RoleEngine");
        IRoleModel roleModel = engine.getRoleModel(false);
        roleModel.getRoles().remove(role.getId());
        roleModel.getRoles().put(role.getId(), role);
    }

    /**
     * 删除角色算法信息
     *
     * @param roleId
     * @param arithIds
     * @return void
     * @throws
     * @Method: deleteRoleAriths
     * <p>
     * TODO
     */
    public void deleteRoleAriths(String roleId, String arithIds) {
        String[] arithId = arithIds.split(",");
        for (int i = 0; i < arithId.length; i++) {
            RoleArithId roleArithId = new RoleArithId(roleId, arithId[i]);
            RoleArith roleArith = roleArithDao.findById(roleArithId);
            roleArithDao.delete(roleArith);
        }

        //更新内存信息
        Role role = dao.findById(roleId);
        IRoleUtil engine = (IRoleUtil) getBean("RoleEngine");
        IRoleModel roleModel = engine.getRoleModel(false);
        roleModel.getRoles().remove(role.getId());
        roleModel.getRoles().put(role.getId(), role);
    }

    public void createRoleSchemas(String roleId, String schemaIds) {
        if (!"".equals(schemaIds)) {
            Role role = roleEngine.getRoleModel(false).getRoleById(roleId);

            String[] toAssignIds = schemaIds.split(",");

            MetaModel metaModel = metaEngine.getMeta(false);
            for (int i = 0; i < toAssignIds.length; i++) {
                RoleSchemaId roleSchemaId = new RoleSchemaId(role.getId(), toAssignIds[i]);
                RoleSchema roleSchema = new RoleSchema(roleSchemaId);
                roleSchema.setRole(role);
                roleSchema.setSchema((Schema) metaModel.getISchemaById(toAssignIds[i]));

                roleSchemaDao.save(roleSchema);
                role.getRoleSchemas().add(roleSchema);
            }
        }
    }

    public void deleteRoleSchemas(String roleId, String schemaIds) {
        if (!"".equals(schemaIds)) {
            Role role = roleEngine.getRoleModel(false).getRoleById(roleId);
            Set<RoleSchema> roleSchemasCache = role.getRoleSchemas();
            List<RoleSchema> toRemoveSchemas = new ArrayList<>();
            String[] toRemoveIds = schemaIds.split(",");
            Set<RoleSchema> roleSchemas = dao.findById(roleId).getRoleSchemas();
            for (RoleSchema roleSchema : roleSchemas) {
                for (int i = 0; i < toRemoveIds.length; i++) {
                    if (toRemoveIds[i].equals(roleSchema.getSchema().getId())) {
                        roleSchemaDao.delete(roleSchema);
                        roleSchemasCache.forEach(rs -> {
                            if (rs.getSchema().getId().equals(roleSchema.getSchema().getId())) {
                                toRemoveSchemas.add(rs);
                            }
                        });
                        break;
                    }
                }
            }
            role.getRoleSchemas().removeAll(toRemoveSchemas);
        }
    }

    public List<ModelBtnTypeEntity> getBtnTypesByRoleId(String roleId, Boolean assigned) {
        List<IOverAllOperations> roleOperations = roleEngine.getRoleModel(false).getRoleById(roleId).getAllOperations();
        List<ModelBtnTypeEntity> retVal = new ArrayList<>();
        String[] btnTypeIds = roleOperations.get(0).getOperationIds().split(",");
        List<Long> btnTypeArray = new ArrayList<>();
        for (String btnTypeId : btnTypeIds) {
            btnTypeArray.add(Long.valueOf(btnTypeId));
        }
        Criterion criterion = assigned ? Restrictions.in("id", btnTypeArray) : Restrictions.not(Restrictions.in("id", btnTypeArray));
        retVal = modelBtnTypeDao.list(ModelBtnTypeEntity.class, criterion);
        return retVal;

    }

    public List<Schema> getAssignedSchemasByRoleId(String roleId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" from Schema  ");
        sql.append("where id in( select id.schemaId from RoleSchema where id.roleId='" + roleId + "' )  ");
        sql.append("order by id asc ");
        return dao.getSqlResult(sql.toString());
    }

    public List<Schema> getUnassignedSchemasByRoleId(String roleId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" from Schema  ");
        sql.append("where id not in( select id.schemaId from RoleSchema where id.roleId='" + roleId + "' )  ");
        sql.append("order by id asc ");
        return dao.getSqlResult(sql.toString());
    }

    /**
     * 查找已经分配给指定角色的用户
     *
     * @param roleId
     * @return
     * @Method: getAssignedUsersByRoleId
     * <p>
     * TODO
     */
    public List<User> getAssignedUsersByRoleId(String roleId, String name, String departMent) {
        StringBuffer sql = new StringBuffer();
        sql.append(" from User  ");
        sql.append("where id in( select id.userId from RoleUser where id.roleId='" + roleId + "' )  ");
        sql.append("      and state = '1' ");
        if (!StringUtil.isEmpty(name)) {
            sql.append(" and (allName LIKE '%" + name + "%' OR userName LIKE '%" + name + "%' )");
        }
        if (!StringUtil.isEmpty(departMent)) {
            sql.append(" and dept.id = " + departMent);
        }
        sql.append("order by id asc ");
        List<User> list = dao.getSqlResult(sql.toString());

        return list;
    }

    /**
     * 查找未分配给指定角色的用户
     *
     * @param roleId
     * @return
     * @Method: getUsersNotAssignedRoleId
     */
    public List<User> getUnassignedUsersByRoleId(String roleId, String name, String departMent) {
        StringBuffer sql = new StringBuffer();
        sql.append(" from User  ");
        sql.append("where id not in( select id.userId from RoleUser where id.roleId='" + roleId + "' )  ");
        sql.append("      and state = '1' ");
        if (!StringUtil.isEmpty(name)) {
            sql.append(" and (allName LIKE '%" + name + "%' OR userName LIKE '%" + name + "%' )");
        }
        if (!StringUtil.isEmpty(departMent)) {
            sql.append(" and dept.id = " + departMent);
        }
        sql.append("order by id asc ");
        List<User> list = dao.getSqlResult(sql.toString());
        return list;
    }

    /**
     * 保存角色分配的用户信息
     *
     * @param roleId
     * @param userIds 用户的Id集合,用","号隔开
     * @Method: createRoleUsers
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.role.RoleService#createRoleUsers(java.lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public void createRoleUsers(String roleId, String userIds) {
        IRoleModel roleModel = roleEngine.getRoleModel(false);
        Role role = roleModel.getRoleById(roleId);
        Map<String, User> allUsers = roleModel.getUsers();

        if (userIds != null && !userIds.equals("")) {
            String[] users = userIds.split(",");
            for (int i = 0; i < users.length; i++) {
                RoleUserId roleUserId = new RoleUserId(roleId, users[i]);
                RoleUser roleUser = new RoleUser(roleUserId);
                roleUser.setRole(role);
                User user = allUsers.get(users[i]);
                roleUser.setUser(user);

                roleUserDao.save(roleUser);
                role.getRoleUsers().add(roleUser);
                user.getRoleUsers().add(roleUser);
            }

        }
    }

    /**
     * 删除角色用户信息
     *
     * @param roleId
     * @param userIds
     * @Method: deleteRoleUsers
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.role.RoleService#deleteRoleUsers(java.lang.String, java.lang.String)
     */
    public void deleteRoleUsers(String roleId, String userIds) {
        Role role = roleEngine.getRoleModel(false).getRoleById(roleId);

        if (userIds != null && !userIds.equals("")) {
            String[] users = userIds.split(",");
            for (int i = 0; i < users.length; i++) {

                RoleUserId roleUserId = new RoleUserId(roleId, users[i]);
                roleUserDao.delete(new RoleUser(roleUserId));

                RoleUser roleUser = role.deleteRoleUser(roleUserId);
                roleUser.getUser().deleteRoleUser(roleUserId);
            }

        }
    }

    /**
     * 更新角色操作权限信息
     *
     * @param roleId
     * @param operationIds
     * @Method: updateOverAllOperations
     * <p>
     * TODO
     * @see com.orient.sysmodel.service.role.RoleService#updateOverAllOperations(java.lang.String, java.lang.String)
     */
    public void updateOverAllOperations(String roleId, String operationIds) {

        if (CommonTools.isNullString(operationIds))
            operationIds = "0";

        OverAllOperations overAllOperations =
                (OverAllOperations) roleEngine.getRoleModel(false).getRoleById(roleId)
                        .getOverAllOperations().iterator().next();

        overAllOperations.setOperationIds(operationIds);
        overAllOperationsDao.attachDirty(overAllOperations);

    }

    /**
     * 保存分配的业务功能点信息操作
     *
     * @param roleId
     * @param funIds 功能点和Tbom树功能的Id集合
     * @return String
     * @throws
     * @Method: updateRoleFunctions
     * <p>
     * TODO
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String updateRoleFunctions(String roleId, String[] funIds) {
        IRoleModel roleModel = roleEngine.getRoleModel(false);
        Role role = roleModel.getRoleById(roleId);
        Map<String, Function> allFunctions = roleModel.getFunctions();
        Map<String, TbomDir> allTbomDirs = roleModel.getTbomDirs();

        //Tbom角色功能信息roleFunctionTbom
        for (Iterator it = role.getRoleFunctionTboms().iterator(); it.hasNext(); ) {
            RoleFunctionTbom roleFunctionTbom = (RoleFunctionTbom) it.next();
            roleFunctionTbomDao.delete(roleFunctionTbom);
            roleFunctionTbom.getFunction().deleteRoleFunTbom(roleFunctionTbom.getId());
            roleFunctionTbom.getTbomDir().deleteRoleFunTbom(roleFunctionTbom.getId());
            it.remove();
        }

        //保存角色功能信息
        String fid = "";
        if (funIds != null && !funIds.equals("")) {
            for (int i = 0; i < funIds.length; i++) {
                String tbomId = funIds[i];
                if ("T".equals(tbomId.substring(0, 1))) {
                    String[] tbomFunctionIdDesc = tbomId.split("_");
                    String tbom_id = tbomFunctionIdDesc[1];
                    String function_id = tbomFunctionIdDesc[2];
                    RoleFunctionTbomId roleFunctionTbomId = new RoleFunctionTbomId(roleId, function_id, tbom_id);
                    RoleFunctionTbom roleFunctionTbom = new RoleFunctionTbom(roleFunctionTbomId);
                    roleFunctionTbom.setRole(role);
                    roleFunctionTbom.setFunction(allFunctions.get(function_id));
                    roleFunctionTbom.setTbomDir(allTbomDirs.get(tbom_id));
                    roleFunctionTbomDao.save(roleFunctionTbom);
                    role.getRoleFunctionTboms().add(roleFunctionTbom);
                    roleFunctionTbom.getFunction().getRoleFunctionTboms().add(roleFunctionTbom);
                    roleFunctionTbom.getTbomDir().getRoleFunctionTboms().add(roleFunctionTbom);
                    fid = fid + function_id + ",";
                } else if (!"S".equals(tbomId.substring(0, 1))) {
                    String function_id = tbomId;
                    RoleFunctionTbomId roleFunctionTbomId = new RoleFunctionTbomId(roleId, function_id, " ");
                    RoleFunctionTbom roleFunctionTbom = new RoleFunctionTbom(roleFunctionTbomId);
                    roleFunctionTbom.setRole(role);
                    roleFunctionTbom.setFunction(allFunctions.get(function_id));
                    roleFunctionTbom.setTbomDir(allTbomDirs.get(" "));
                    roleFunctionTbomDao.save(roleFunctionTbom);
                    role.getRoleFunctionTboms().add(roleFunctionTbom);
                    roleFunctionTbom.getFunction().getRoleFunctionTboms().add(roleFunctionTbom);
                    roleFunctionTbom.getTbomDir().getRoleFunctionTboms().add(roleFunctionTbom);
                    fid = fid + function_id + ",";
                }
            }
        }
        roleModel.initRoleTboms();
        if (fid.length() > 0) {
            fid = fid.substring(0, fid.length() - 1);
        }
        return fid;

    }

    public List<IOperation> getOperations(IOverAllOperations iOverAllOperation) {
        Map<String, Operation> operations = roleEngine.getRoleModel(false).getOperations();
        List<IOperation> iOperationList = new ArrayList<IOperation>();
        if (!CommonTools.isNullString(iOverAllOperation.getOperationIds())) {
            String[] operationId = iOverAllOperation.getOperationIds().split(",");

            for (String operId : operationId) {
                Operation operation = operations.get(operId);
                if (operation != null) iOperationList.add(operation);
            }
        }

        return iOperationList;


    }
}

