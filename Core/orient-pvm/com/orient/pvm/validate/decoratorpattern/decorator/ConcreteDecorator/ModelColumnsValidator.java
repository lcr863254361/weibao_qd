package com.orient.pvm.validate.decoratorpattern.decorator.ConcreteDecorator;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.pvm.validate.annotation.ValidatorOrder;
import com.orient.pvm.validate.decoratorpattern.decorator.ValidatDecorator;
import com.orient.pvm.validate.templateparser.TemplateParser;
import com.orient.utils.CommonTools;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/9 0009.
 * 模型名称校验
 */
@ValidatorOrder(order = 20)
@Component
public class ModelColumnsValidator extends ValidatDecorator {

    @Autowired
    @Qualifier("defaultTemplateParser")
    TemplateParser templateParser;

    @Override
    public List<String> doCheck(TableEntity tableEntity, IBusinessModel model) {
        //校验上层
        List<String> lastValidatErrors = super.doCheck(tableEntity, model);
        if (lastValidatErrors.size() == 0) {
            List<Map<String, String>> checkItems = templateParser.getCheckItems(tableEntity);
            if (CommonTools.isEmptyList(checkItems)) {
                lastValidatErrors.add("请确保工作簿中至少有一个检查项目");
            } else {
                Set<String> keySets = checkItems.get(0).keySet();
                List<String> columnNames = model.getAllBcCols().stream().map(IBusinessColumn::getDisplay_name).collect(Collectors.toList());
                List<String> unMapedColumns = new ArrayList<>();
                keySets.forEach(columnName -> {
                    if (!TemplateParser.EXCEPT_EXCEL_METACOLUMN.contains(columnName) && !columnNames.contains(columnName)) {
                        unMapedColumns.add(columnName);
                    }
                });
                if (!CommonTools.isEmptyList(unMapedColumns)) {
                    lastValidatErrors.add("检查项中【" + CommonTools.list2String(unMapedColumns) + "】列头与所选模型不一致");
                }
            }
        }
        return lastValidatErrors;
    }
}
