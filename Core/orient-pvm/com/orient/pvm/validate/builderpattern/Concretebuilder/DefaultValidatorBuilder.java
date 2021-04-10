package com.orient.pvm.validate.builderpattern.Concretebuilder;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.businessmodel.service.IBusinessModelService;
import com.orient.edm.init.OrientContextLoaderListener;
import com.orient.pvm.bean.CheckTemplateInfo;
import com.orient.pvm.validate.annotation.ValidatorOrder;
import com.orient.pvm.validate.builderpattern.Builder;
import com.orient.pvm.validate.decoratorpattern.CheckTemplateValidator;
import com.orient.pvm.validate.decoratorpattern.decorator.ValidatDecorator;
import com.orient.pvm.validate.templateparser.TemplateParser;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.reader.ExcelReader;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.FileOperator;
import com.orient.web.util.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
@Component("defaultValidatorBuilder")
public class DefaultValidatorBuilder implements Builder {

    @Autowired
    @Qualifier("defaultTemplateParser")
    TemplateParser templateParser;

    @Autowired
    protected IBusinessModelService businessModelService;

    @Override
    public TableEntity parseTemplate(String templatePath) {
        ExcelReader excelReader = new ExcelReader();
        //是否是2007以后的excel文件
        Boolean after2007 = FileOperator.getSuffix(templatePath).equals("xlsx") ? true : false;
        TableEntity excelData = null;
        try {
            excelData = excelReader.readFile(new FileInputStream(new File(templatePath)), after2007);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excelData;
    }

    @Override
    public CheckTemplateValidator constructValidator() {
        final ValidatDecorator[] retVal = {null};
        String[] beanNames = OrientContextLoaderListener.Appwac.getBeanNamesForType(CheckTemplateValidator.class);
        CheckTemplateValidator defaultValidator = null;
        Map<Integer, ValidatDecorator> checkTemplateValidatorMap = new LinkedHashMap<>();
        for (String beanName : beanNames) {
            CheckTemplateValidator checkTemplateValidator = (CheckTemplateValidator) OrientContextLoaderListener.Appwac.getBean(beanName);
            Class validatorClass = checkTemplateValidator.getClass();
            //判断是否继承
            if (ValidatDecorator.class.isAssignableFrom(validatorClass)) {
                //排序
                ValidatorOrder validatorOrder = (ValidatorOrder) validatorClass.getAnnotation(ValidatorOrder.class);
                int order = validatorOrder.order();
                checkTemplateValidatorMap.put(order, (ValidatDecorator) checkTemplateValidator);
            } else {
                defaultValidator = checkTemplateValidator;
            }
        }
        //排序
        List<Integer> sortedKeys = checkTemplateValidatorMap.keySet().stream().sorted((i1, i2) -> {
            return i1 - i2;
        }).collect(Collectors.toList());
        //装饰
        final CheckTemplateValidator finalDefaultValidator = defaultValidator;
        sortedKeys.forEach(order -> {
            ValidatDecorator validatDecorator = checkTemplateValidatorMap.get(order);
            if (null == retVal[0]) {
                retVal[0] = validatDecorator;
                retVal[0].setLastValidator(finalDefaultValidator);
            } else {
                CheckTemplateValidator tmpCheckTemplateValidator = retVal[0];
                validatDecorator.setLastValidator(tmpCheckTemplateValidator);
                retVal[0] = validatDecorator;
            }
        });
        return retVal[0];
    }

    @Override
    public IBusinessModel constructBusinessModel(String modelId) {
        String userId = UserContextUtil.getUserId();
        IBusinessModel model = businessModelService.getBusinessModelById(userId, modelId, null, EnumInter.BusinessModelEnum.Table);
        return model;
    }

    @Override
    public List<String> beginValidator(CheckTemplateValidator checkTemplateValidator, TableEntity tableEntity, IBusinessModel model) {
        List<String> errorList = new ArrayList<>();
        if (tableEntity == null) {
            errorList.add("模版文件不存在,请检查");
        }
        if (null != checkTemplateValidator && null != tableEntity && null != model) {
            return checkTemplateValidator.doCheck(tableEntity, model);
        }
        return errorList;
    }

    @Override
    public CheckTemplateInfo doParse(TableEntity tableEntity) {
        String modelName = templateParser.getModelName(tableEntity);
        List<String> singers = templateParser.getSigners(tableEntity);
        String remark = templateParser.getRemark(tableEntity);
        List<Map<String, String>> checkItems = templateParser.getCheckItems(tableEntity);
        CheckTemplateInfo checkTemplateInfo = new CheckTemplateInfo();
        checkTemplateInfo.setModelDisplayName(modelName);
        checkTemplateInfo.setSignroles(CommonTools.list2String(singers));
        checkTemplateInfo.setDatas(checkItems);
        checkTemplateInfo.setRemark(remark);
        return checkTemplateInfo;
    }
}
