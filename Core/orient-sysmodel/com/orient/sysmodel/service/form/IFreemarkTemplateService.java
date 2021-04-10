package com.orient.sysmodel.service.form;

import com.orient.sysmodel.domain.form.FreemarkTemplateEntity;
import com.orient.sysmodel.service.IBaseService;


/**
 * Created by enjoy on 2016/3/15 0015.
 */
public interface IFreemarkTemplateService extends IBaseService<FreemarkTemplateEntity> {
    void initSystemTemplate();

    void deleteSystemTemplates();

    void addTemplate();
}
