package com.orient.pvm.validate.templateparser;

import com.orient.utils.ExcelUtil.reader.TableEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/9 0009.
 * 解析Excel
 */
public interface TemplateParser {

    public String getModelName(TableEntity tableEntity);

    public List<String> getSigners(TableEntity tableEntity);

    public List<Map<String, String>> getCheckItems(TableEntity tableEntity);

    public static final List<String> EXCEPT_EXCEL_METACOLUMN = new ArrayList<String>(){{
        add("ID");
        add("SHEETNAME");
    }};

    String getRemark(TableEntity tableEntity);
}
