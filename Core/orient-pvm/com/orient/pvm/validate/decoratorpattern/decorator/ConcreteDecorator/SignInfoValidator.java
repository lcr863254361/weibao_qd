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
 * 签署人信息校验
 */
@ValidatorOrder(order = 30)
@Component
public class SignInfoValidator extends ValidatDecorator{

    @Autowired
    @Qualifier("defaultTemplateParser")
    TemplateParser templateParser;

    @Override
    public List<String> doCheck(TableEntity tableEntity, IBusinessModel model) {
        //校验上一个装饰类
        List<String> lastValidatErrors = super.doCheck(tableEntity, model);
        if(lastValidatErrors.size() == 0){

        }
        return lastValidatErrors;
    }

}
