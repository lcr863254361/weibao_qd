package com.orient.metamodel.metadomain;

import com.orient.metamodel.operationinterface.IEnum;
import com.orient.metamodel.operationinterface.IRestriction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 数据约束信息
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public class Restriction extends AbstractRestriction implements IRestriction {

    public Restriction() {
    }

    /**
     * @param name
     * @param displayName
     * @param type        --类型1表示枚举约束，2表示数据表枚举约束，3表示范围约束，4动态范围约束
     * @param displayType -- 枚举约束的显示方式，0为文字显示，1为图片显示，此值针对枚举约束
     * @param isValid
     */
    public Restriction(String name, String displayName, Long type, Long displayType, Long isValid) {
        super(name, displayName, type, displayType, isValid);
    }

    /**
     * full constructor.
     *
     * @param cwmSchema       --所属的Schema
     * @param name
     * @param displayName
     * @param type
     * @param idMultiSelected --是否多选，TRUE为当前记录可以选择多个枚举值，FALSE不可以多选
     * @param errorInfo       --错误信息，用户出现错误操作弹出的错误信息
     * @param description
     * @param displayType
     * @param maxLength       --范围约束的最大值
     * @param minLength       --范围约束的最小值
     * @param isValid
     * @param cwmEnums        --对应的枚举约束值集合
     * @param cwmTableEnums   --对应的数据类约束集合
     */
    public Restriction(Schema cwmSchema, String name, String displayName, Long type, String idMultiSelected, String errorInfo, String description, Long displayType, BigDecimal maxLength, BigDecimal minLength, Long isValid, Set cwmEnums, Set cwmTableEnums) {
        super(cwmSchema, name, displayName, type, idMultiSelected, errorInfo, description, displayType, maxLength, minLength, isValid, cwmEnums, cwmTableEnums);
    }

    /**
     * 获取restriction的唯一标识
     * @return String
     */
    @Override
    public String getIdentity() {
        if (super.getIdentity().isEmpty()) {
            super.setIdentity("restriction=" + super.getName());
        }
        return super.getIdentity();
    }

    @Override
    public List<IEnum> getAllEnums() {
        List<IEnum> retList = new ArrayList<>();
        Set<Enum> enums = this.getCwmEnums();
        enums.iterator().forEachRemaining(anEnum -> {
            retList.add(anEnum);
        });
        retList.sort((e1, e2) -> e1.getOrder().intValue() - e2.getOrder().intValue());
        return retList;
    }

    @Override
    public int getRestionType() {
        int type = this.getType().intValue();
        switch (type) {
            case 1:
                return IRestriction.TYPE_ENUM;
            case 2:
                return IRestriction.TYPE_TABLEENUM;
            case 3:
                return IRestriction.TYPE_RANGEENUM;
            case 4:
                return IRestriction.TYPE_DYNAMICRANGEENUM;
        }
        return 0;

    }

    @Override
    public boolean isMutiSelected() {
        String multiSelect = this.getIsMultiSelected();
        if (null != multiSelect && multiSelect.equalsIgnoreCase(this.MULTISELECT_ALLOWED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> getDisplayEnumByDBValue(List<String> dbValues) {
        List<String> retList = new ArrayList<>();
        int type = this.getType().intValue();
        if (type == Restriction.TYPE_ENUM) {
            for (String dbValue : dbValues) {
                Set<Enum> enumSet = this.getCwmEnums();
                for (IEnum loopEnum : enumSet) {
                    if (loopEnum.getValue().equalsIgnoreCase(dbValue)) {
                        retList.add(loopEnum.getDisplayValue());
                        break;
                    }
                }
            }
            return retList;
        } else {
            return retList;
        }
    }

}
