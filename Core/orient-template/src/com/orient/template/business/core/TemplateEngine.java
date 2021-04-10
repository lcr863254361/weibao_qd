package com.orient.template.business.core;

import com.orient.sysmodel.domain.template.CollabTemplate;
import com.orient.sysmodel.domain.template.CollabTemplateNode;
import com.orient.template.model.CollabTemplateImportExtraInfo;
import com.orient.template.model.CollabTemplatePreviewNode;

import java.io.Serializable;

/**
 * do template related operations
 *
 * @author Seraph
 *         2016-09-13 上午10:42
 */
public interface TemplateEngine {

    void doImport(CollabTemplateNode rootNode, CollabTemplateImportExtraInfo extraInfo);

    <T extends Serializable> CollabTemplateNode doExport(T node, CollabTemplate collabTemplate);

    CollabTemplatePreviewNode convertTemplateNodeToPreviewNode(CollabTemplateNode node, String previewType);
}
