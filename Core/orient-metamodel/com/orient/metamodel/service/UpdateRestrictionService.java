package com.orient.metamodel.service;

import com.orient.metamodel.DBUtil;
import com.orient.metamodel.metadomain.*;
import com.orient.metamodel.metadomain.Enum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author GNY
 * @create 2018-04-02 21:50
 */
@Service
public class UpdateRestrictionService extends DBUtil {

    @Autowired
    SaveRestrictionService saveRestrictionService;

    public void updateRestrictions(Schema newSchema, Schema oldSchema, Map<String, Column> columnMap) throws Exception {
        for (Restriction restriction : newSchema.getRestrictions()) {
            updateRestriction(oldSchema, restriction, columnMap);
        }
    }

    /**
     * 更新数据约束
     *
     * @param oldSchema
     * @param restriction
     * @throws Exception
     */
    private void updateRestriction(Schema oldSchema, Restriction restriction, Map<String, Column> columnMap) throws Exception {

        Restriction oldRestriction = (Restriction) oldSchema.getRestrictionByName(restriction.getName());// 查看该数据约束在数据库中是否存在
        if (oldRestriction == null) {//新增的restriction
            saveRestrictionService.saveRestriction(restriction, columnMap);
        } else {
            restriction.setId(oldRestriction.getId());
            restriction.setIsValid(Restriction.VALID);
            if (restriction.getType().equals(Restriction.EnumType)) {
                //删除数据中的Enums
                deleteRestrictionEnums(oldRestriction);
                //枚举约束
                for (Enum enu : restriction.getCwmEnums()) {
                    enu.setRestrictionID(restriction.getId());
                    metaDAOFactory.getEnumDAO().save(enu);// 保存约束值
                }
            } else if (restriction.getType().equals(Restriction.TabelEnumType)) {
                TableEnum te = restriction.getTableEnum();
                te.setRestrictionId(oldRestriction.getId());
                TableEnum oldTableEnum = oldRestriction.getTableEnum();
                if (oldTableEnum != null) {
                    deleteRestrictionTableEnum(oldTableEnum);
                }
                te.setRestrictionId(oldRestriction.getId());
                saveRestrictionService.saveTableEnumDetail(te, columnMap);    //保存数据表约束的信息
            } else if (restriction.getType().equals(Restriction.DynamicRangeEnumType)) {
                TableEnum te = restriction.getTableEnum();
                te.setRestrictionId(oldRestriction.getId());
                TableEnum oldTableEnum = oldRestriction.getTableEnum();
                if (oldTableEnum != null) {
                    deleteRestrictionTableEnum(oldTableEnum);
                }
                metaDAOFactory.getTableEnumDAO().save(te);
            }
        }
        metaDAOFactory.getRestrictionDAO().merge(restriction);
    }

    /**
     * 在数据库中删除枚举变量
     *
     * @param restriction
     * @throws Exception
     */
    private void deleteRestrictionEnums(Restriction restriction) throws Exception {
        for (Enum loopEnum : restriction.getCwmEnums()) {
            metaDAOFactory.getEnumDAO().delete(loopEnum);
        }
    }

    /**
     * 删除Restriction的TableEnum
     *
     * @param tableEnum
     * @return
     */
    public void deleteRestrictionTableEnum(TableEnum tableEnum) throws Exception {
        for (RelationTableEnum rte : tableEnum.getRelationTableEnums()) {
            metaDAOFactory.getRelationTableEnumDAO().delete(rte);
        }
        metaDAOFactory.getTableEnumDAO().delete(tableEnum);
    }

}
