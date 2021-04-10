package com.orient.pvm.validate.decoratorpattern;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.utils.ExcelUtil.reader.TableEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public interface CheckTemplateValidator {

    public List<String> doCheck(TableEntity tableEntity, IBusinessModel model);
}
