package com.orient.pvm.validate.builderpattern.director;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.pvm.bean.CheckTemplateInfo;
import com.orient.pvm.bean.CheckTemplateParseResult;
import com.orient.pvm.validate.builderpattern.Builder;
import com.orient.pvm.validate.decoratorpattern.CheckTemplateValidator;
import com.orient.utils.ExcelUtil.reader.TableEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class DefaultDirector implements Director {

    private String templatePath;

    private String modelId;

    public DefaultDirector(String templatePath, String modelId) {
        this.templatePath = templatePath;
        this.modelId = modelId;
    }

    @Override
    public CheckTemplateParseResult doBuild(Builder builder) {
        //解析xml
        TableEntity tableEntity = builder.parseTemplate(this.templatePath);
        //构建检查器
        CheckTemplateValidator checkTemplateValidator = builder.constructValidator();
        //构建模型
        IBusinessModel model = builder.constructBusinessModel(this.modelId);
        //检查
        List<String> errorList = builder.beginValidator(checkTemplateValidator, tableEntity, model);
        CheckTemplateInfo checkTemplateInfo = null;
        if (errorList.isEmpty()) {
            //解析XML 返回检查表格内容
            checkTemplateInfo = builder.doParse(tableEntity);
        }
        CheckTemplateParseResult result = new CheckTemplateParseResult(checkTemplateInfo, errorList);
        return result;
    }
}
