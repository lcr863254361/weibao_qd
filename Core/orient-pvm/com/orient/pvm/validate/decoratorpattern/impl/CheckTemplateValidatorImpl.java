package com.orient.pvm.validate.decoratorpattern.impl;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.pvm.validate.decoratorpattern.CheckTemplateValidator;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/9 0009.
 * 被包装基类
 */
@Component
public class CheckTemplateValidatorImpl implements CheckTemplateValidator {

    @Override
    public List<String> doCheck(TableEntity tableEntity, IBusinessModel model) {
        return new ArrayList<>();
    }
}
