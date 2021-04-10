package com.orient.sysmodel.service.template;

import com.orient.sysmodel.domain.template.CollabTemplate;
import com.orient.sysmodel.service.IBaseService;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2016-09-21 上午10:54
 */
public interface ICollabTemplateService extends IBaseService<CollabTemplate> {

    List<String> getDistinctTypes();
}
