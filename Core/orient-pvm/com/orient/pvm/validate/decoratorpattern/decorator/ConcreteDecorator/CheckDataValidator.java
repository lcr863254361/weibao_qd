package com.orient.pvm.validate.decoratorpattern.decorator.ConcreteDecorator;

import com.orient.businessmodel.Util.EnumInter;
import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.pvm.validate.annotation.ValidatorOrder;
import com.orient.pvm.validate.decoratorpattern.decorator.ValidatDecorator;
import com.orient.pvm.validate.templateparser.TemplateParser;
import com.orient.utils.ExcelUtil.reader.TableEntity;
import com.orient.utils.JsonUtil;
import com.orient.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/9 0009.
 * 检查项格式校验
 */
@ValidatorOrder(order = 40)
@Component
public class CheckDataValidator extends ValidatDecorator {

    @Autowired
    @Qualifier("defaultTemplateParser")
    TemplateParser templateParser;

    @Override
    public List<String> doCheck(TableEntity tableEntity, IBusinessModel model) {
        //校验上层
        List<String> lastValidatErrors = super.doCheck(tableEntity, model);
        if (lastValidatErrors.size() == 0) {
            //特殊校验
            List<Map<String, String>> checkItems = templateParser.getCheckItems(tableEntity);
            //获取检查字段
            List<String> checkColumnNames = model.getAllBcCols().stream().filter(iBusinessColumn -> {
                return iBusinessColumn.getColType().equals(EnumInter.BusinessModelEnum.BusinessColumnEnum.C_Check);
            }).map(IBusinessColumn::getDisplay_name).collect(Collectors.toList());
            //校验数据格式
            checkItems.forEach(checkItem -> {
                checkColumnNames.forEach(checkColumnName -> {
                    String checkValue = checkItem.get(checkColumnName);
                    String sheetName = checkItem.get("SHEETNAME");
                    Integer index = Integer.valueOf(checkItem.get("ID"));
                    if (StringUtil.isEmpty(checkValue)) {
                        lastValidatErrors.add("工作簿【" + sheetName + "】中第【" + index + "】行的【" + checkColumnName + "】不可为空");
                    } else {
                        //校验是否为json格式
                        if (!StringUtil.isJson(checkValue)) {
                            lastValidatErrors.add("工作簿【" + sheetName + "】中第【" + index + "】行的【" + checkColumnName + "】值不是Json格式");
                        } else if (!StringUtil.isJSONArray(checkValue)) {
                            lastValidatErrors.add("工作簿【" + sheetName + "】中第【" + index + "】行的【" + checkColumnName + "】值不是Json数组格式");
                        } else {
                            List datas = JsonUtil.json2List(checkValue);
                            if (datas.size() == 0) {
                                lastValidatErrors.add("工作簿【" + sheetName + "】中第【" + index + "】行的【" + checkColumnName + "】值不能为空的Json数组");
                            } else {
                                datas.forEach(data -> {
                                    if (!Map.class.isAssignableFrom(data.getClass())) {
                                        lastValidatErrors.add("工作簿【" + sheetName + "】中第【" + index + "】行的【" + checkColumnName + "】Json数组中只能包含key-value形式的数据");
                                    } else {
                                        Map dataMap = (Map) data;
                                        if (!dataMap.containsKey("inputValue")) {
                                            lastValidatErrors.add("工作簿【" + sheetName + "】中第【" + index + "】行的【" + checkColumnName + "】Json数组中必须包含key为inputValue的数据");
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            });
        }
        return lastValidatErrors;
    }

}
