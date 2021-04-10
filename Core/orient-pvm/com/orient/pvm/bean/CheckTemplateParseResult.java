package com.orient.pvm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 上传检查表格模板后 解析结果
 * Created by Administrator on 2016/8/9 0009.
 */
public class CheckTemplateParseResult implements Serializable {

    private CheckTemplateInfo checkTemplateInfo;

    private List<String> errors;

    public CheckTemplateInfo getCheckTemplateInfo() {
        return checkTemplateInfo;
    }

    public List<String> getErrors() {
        return errors;
    }

    public CheckTemplateParseResult(CheckTemplateInfo checkTemplateInfo, List<String> errors) {
        this.checkTemplateInfo = checkTemplateInfo;
        this.errors = errors;
    }
}
