package com.orient.modeldata.persistent.validator;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-05-06 14:43
 */
public abstract class Validator {
    private IBusinessColumn businessColumn;
    public abstract String validate(Map<String, Object> dataMap);

    public static List<Validator> buildValidator(IBusinessColumn bc) {
        List<Validator> validators = new ArrayList<>();
        if(bc.getNullable() == false) {
            Validator validator = new NotNullValidator();
            validator.setBusinessColumn(bc);
            validators.add(validator);
        }

        EnumInter colType = bc.getColType();
        if(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Integer==colType || EnumInter.BusinessModelEnum.BusinessColumnEnum.C_BigInteger==colType
                || EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Decimal==colType) {
            Validator validator = new LongValidator();
            validator.setBusinessColumn(bc);
            validators.add(validator);
        }
        else if(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Float==colType || EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Double==colType) {
            Validator validator = new DoubleValidator();
            validator.setBusinessColumn(bc);
            validators.add(validator);
        }
        return validators;
    }

    public IBusinessColumn getBusinessColumn() {
        return businessColumn;
    }

    public void setBusinessColumn(IBusinessColumn businessColumn) {
        this.businessColumn = businessColumn;
    }
}
