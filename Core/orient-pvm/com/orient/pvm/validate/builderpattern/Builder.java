package com.orient.pvm.validate.builderpattern;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.pvm.bean.CheckTemplateInfo;
import com.orient.pvm.validate.decoratorpattern.CheckTemplateValidator;
import com.orient.utils.ExcelUtil.reader.TableEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public interface Builder {

    //解析模板文件
    TableEntity parseTemplate(String templatePath);

    //构建模板校验器
    CheckTemplateValidator constructValidator();

    //构建模型
    IBusinessModel constructBusinessModel(String modelId);

    //开始校验
    List<String> beginValidator(CheckTemplateValidator checkTemplateValidator, TableEntity tableEntity, IBusinessModel model);

    //解析xml 转化为检查表描述
    CheckTemplateInfo doParse(TableEntity tableEntity);
}
