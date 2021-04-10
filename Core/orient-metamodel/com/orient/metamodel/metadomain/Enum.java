package com.orient.metamodel.metadomain;

import com.orient.metamodel.operationinterface.IEnum;

/**
 * 枚举值信息表
 *
 * @author mengbin@cssrc.com.cn
 * @date Feb 8, 2012
 */
public class Enum extends AbstractEnum implements IEnum {

    @Override
    public Enum clone() throws CloneNotSupportedException {
        return (Enum) super.clone();
    }

    public Enum() {
    }

    /**
     * minimal constructor.
     *
     * @param cwmRestriction
     * @param value
     * @param displayValue
     */
    public Enum(Restriction cwmRestriction, String value, String displayValue) {
        super(cwmRestriction, value, displayValue);
    }

    /**
     * full constructor.
     *
     * @param cwmRestriction
     * @param value
     * @param displayValue
     * @param imageURL
     * @param description
     */
    public Enum(Restriction cwmRestriction, String value, String displayValue, String imageURL, String description) {
        super(cwmRestriction, value, displayValue, imageURL, description);
    }

    @Override
    public boolean isOpen() {
        long isOpen = this.getIsopen();
        if (isOpen == 1) {
            return true;
        } else {
            return false;
        }
    }

}
