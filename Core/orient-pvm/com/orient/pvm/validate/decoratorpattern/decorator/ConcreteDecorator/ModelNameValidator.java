package com.orient.pvm.validate.decoratorpattern.decorator.ConcreteDecorator;

import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.pvm.validate.annotation.ValidatorOrder;
import com.orient.pvm.validate.decoratorpattern.decorator.ValidatDecorator;
import com.orient.pvm.validate.templateparser.TemplateParser;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9 0009.
 * 模型名称校验
 */
@ValidatorOrder(order = 10)
@Component
public class ModelNameValidator extends ValidatDecorator {

    @Autowired
    @Qualifier("defaultTemplateParser")
    TemplateParser templateParser;

    @Override
    public List<String> doCheck(TableEntity tableEntity, IBusinessModel model) {
        //校验上层
        List<String> lastValidatErrors = super.doCheck(tableEntity, model);
        if (lastValidatErrors.size() == 0) {
            String modelName = templateParser.getModelName(tableEntity);
            if (!model.getDisplay_name().equals(modelName)) {
                lastValidatErrors.add("请确保第二行第一列的模型名称与所选模型保持一致");
            }
        }
        return lastValidatErrors;
    }


}
