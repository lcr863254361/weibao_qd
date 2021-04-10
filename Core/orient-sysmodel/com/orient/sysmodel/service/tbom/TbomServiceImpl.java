package com.orient.sysmodel.service.tbom;

import com.orient.sysmodel.domain.role.RoleFunctionTbom;
import com.orient.sysmodel.domain.role.RoleFunctionTbomDAO;
import com.orient.sysmodel.domain.tbom.*;

import java.util.Iterator;
import java.util.List;

/**
 * @author zhang yan
 * @Date 2012-3-21		上午08:53:02
 */
public class TbomServiceImpl implements TbomService {

    private TbomDAO dao;
    private TbomDirDAO tbomDirDAO;
    private RelationTbomDAO relationTbomDAO;
    private DynamicTbomDAO dynamicTbomDAO;
    private DynamicTbomRoleDAO dynamicTbomRoleDAO;
    private TbomRoleDAO tbomRoleDAO;
    private RoleFunctionTbomDAO roleFunctionTbomDAO;

    public RoleFunctionTbomDAO getRoleFunctionTbomDAO() {
        return roleFunctionTbomDAO;
    }

    public void setRoleFunctionTbomDAO(RoleFunctionTbomDAO roleFunctionTbomDAO) {
        this.roleFunctionTbomDAO = roleFunctionTbomDAO;
    }

    public RelationTbomDAO getRelationTbomDAO() {
        return relationTbomDAO;
    }

    public void setRelationTbomDAO(RelationTbomDAO relationTbomDAO) {
        this.relationTbomDAO = relationTbomDAO;
    }

    public DynamicTbomDAO getDynamicTbomDAO() {
        return dynamicTbomDAO;
    }

    public void setDynamicTbomDAO(DynamicTbomDAO dynamicTbomDAO) {
        this.dynamicTbomDAO = dynamicTbomDAO;
    }

    public void setTbomRoleDAO(TbomRoleDAO tbomRoleDAO) {
        this.tbomRoleDAO = tbomRoleDAO;
    }

    public TbomRoleDAO getTbomRoleDAO() {
        return tbomRoleDAO;
    }

    public void setDynamicTbomRoleDAO(DynamicTbomRoleDAO dynamicTbomRoleDAO) {
        this.dynamicTbomRoleDAO = dynamicTbomRoleDAO;
    }

    public DynamicTbomRoleDAO getDynamicTbomRoleDAO() {
        return dynamicTbomRoleDAO;
    }

    public TbomDirDAO getTbomDirDAO() {
        return tbomDirDAO;
    }

    public void setTbomDirDAO(TbomDirDAO tbomDirDAO) {
        this.tbomDirDAO = tbomDirDAO;
    }


    /**
     * dao
     *
     * @return the dao
     * @since CodingExample Ver 1.0
     */

    public TbomDAO getDao() {
        return dao;
    }

    /**
     * dao
     *
     * @param dao the dao to set
     * @since CodingExample Ver 1.0
     */

    public void setDao(TbomDAO dao) {
        this.dao = dao;
    }

    /**
     * 查找Tbom信息
     *
     * @param id
     * @return Tbom
     */
    public Tbom findById(String id) {
        return dao.findById(id);
    }


    @Override
    public void deleteTbom(String schemaId) {
        List<TbomDir> list = tbomDirDAO.findBySchemaid(schemaId);
        for (TbomDir tbomdir : list) {
            String name = tbomdir.getName();
            deleteTbom(name, schemaId);
        }
    }


    /* (non-Javadoc)
     * @see com.orient.sysmodel.service.tbomdir.TbomDirService#deleteTbom(java.lang.String, java.lang.String)
     */
    @Override
    public boolean deleteTbom(String name, String schemaId) {
        List<TbomDir> list = tbomDirDAO.findByNameAndSchemaid(name, schemaId);
        if (!list.isEmpty()) {
            TbomDir tbomDir = (TbomDir) list.get(0);
            //删除TBOM树时删除CWM_SYS_ROLE_TBOM中将该TBOM赋给角色的记录
            for (Iterator it = tbomDir.getRoleFunctionTboms().iterator(); it.hasNext(); ) {
                RoleFunctionTbom roleTbom = (RoleFunctionTbom) it.next();
                roleFunctionTbomDAO.delete(roleTbom);
            }
            List tl = tbomDirDAO.findByNameAndIsrootAndSchemaid(name, new Long(1), schemaId);
            Tbom tbom = null;
            if (!tl.isEmpty()) {
                tbom = (Tbom) tl.get(0);
            } else {
                return true;
            }
            String tbomId = tbom.getId();

            // 将涉及该TBOM树的所有动态子节点的权限删除
            dynamicTbomRoleDAO.deleteCascade(tbomId);

			/*将涉及该TBOM树的静态子节点权限删除*/
            tbomRoleDAO.deleteCascade(tbomId);

			/*将涉及该TBOM树的所有节点的关联关系删除*/
            relationTbomDAO.deleteCascade(tbomId);
			
			/*将涉及该TBOM树的所有动态子节点删除*/
            dynamicTbomDAO.deleteCascade(tbomId);
			
			/*将涉及该TBOM树的所有节点删除*/
            dao.deleteCascade(tbomId);

            tbomDirDAO.delete(tbomDir);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateTbom(String tbomId) {

        // 将涉及该TBOM树的所有动态子节点的权限删除
        dynamicTbomRoleDAO.deleteCascade(tbomId);

		/*将涉及该TBOM树的静态子节点权限删除*/
        tbomRoleDAO.deleteCascade(tbomId);
		
		/*将涉及该TBOM树的所有节点的关联关系删除*/
        relationTbomDAO.deleteCascade(tbomId);
		
		/*将涉及该TBOM树的所有动态子节点删除*/
        dynamicTbomDAO.deleteCascade(tbomId);
		
		
		/*将涉及该TBOM树的所有节点修改*/
        dao.updateCascade(tbomId);
    }
}

