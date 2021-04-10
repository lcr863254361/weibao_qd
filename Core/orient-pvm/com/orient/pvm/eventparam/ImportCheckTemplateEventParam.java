package com.orient.pvm.eventparam;

import com.orient.sysmodel.domain.pvm.CheckModelDataTemplate;
import com.orient.web.base.OrientEventBus.OrientEventParams;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by mengbin on 16/7/30.
 * Purpose:
 * Detail:
 */
public class ImportCheckTemplateEventParam extends OrientEventParams {

    //输入 模板描述
    private CheckModelDataTemplate checkModelDataTemplate;

    //模板文件
    private MultipartFile templateFile;

    //输出
    private String checkResult;

    //默认构造函数
    public ImportCheckTemplateEventParam(CheckModelDataTemplate checkModelDataTemplate, MultipartFile templateFile) {
        this.checkModelDataTemplate = checkModelDataTemplate;
        this.templateFile = templateFile;
    }

    public ImportCheckTemplateEventParam() {

    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public CheckModelDataTemplate getCheckModelDataTemplate() {
        return checkModelDataTemplate;
    }

    public void setCheckModelDataTemplate(CheckModelDataTemplate checkModelDataTemplate) {
        this.checkModelDataTemplate = checkModelDataTemplate;
    }

    public MultipartFile getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(MultipartFile templateFile) {
        this.templateFile = templateFile;
    }
}
