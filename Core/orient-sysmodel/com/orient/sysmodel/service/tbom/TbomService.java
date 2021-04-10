package com.orient.sysmodel.service.tbom;

import com.orient.sysmodel.domain.tbom.*;

/**
 * @author zhang yan
 * @Date 2012-3-21		上午08:52:36
 */
public interface TbomService {

    /**
     * 查找Tbom信息
     *
     * @param id
     * @return Tbom
     */
    Tbom findById(String id);

    /**
     * 修改Tbom信息
     *
     * @param tbomId
     * @return
     */
    void updateTbom(String tbomId);

    /**
     * 删除Tbom信息
     *
     * @param schemaId
     * @return
     */
    void deleteTbom(String schemaId);

    /**
     * 删除Tbom信息
     *
     * @param name
     * @param schemaId
     * @return boolean
     */
    boolean deleteTbom(String name, String schemaId);

    RelationTbomDAO getRelationTbomDAO();

    DynamicTbomDAO getDynamicTbomDAO();

    DynamicTbomRoleDAO getDynamicTbomRoleDAO();

    TbomRoleDAO getTbomRoleDAO();

    TbomDAO getDao();

    TbomDirDAO getTbomDirDAO();

}

