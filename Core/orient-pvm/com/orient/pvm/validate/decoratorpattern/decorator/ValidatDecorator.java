package com.orient.pvm.validate.decoratorpattern.decorator;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.pvm.validate.decoratorpattern.CheckTemplateValidator;
import com.orient.utils.ExcelUtil.reader.TableEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/9 0009.
 * 装饰模式校验基类
 */
public class ValidatDecorator implements CheckTemplateValidator {

    private CheckTemplateValidator lastValidator;

    @Override
    public List<String> doCheck(TableEntity tableEntity, IBusinessModel model) {
        if (null != lastValidator) {
            return lastValidator.doCheck(tableEntity, model);
        }
        return new ArrayList<>();
    }

    public void setLastValidator(CheckTemplateValidator lastValidator) {
        this.lastValidator = lastValidator;
    }
}
