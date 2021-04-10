package com.orient.background.doctemplate.handler;

import com.aspose.words.DocumentBuilder;
import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;

/**
 * ${DESCRIPTION}
 *
 * @author enjoyjava
 * @create 2016-11-30 3:01 PM
 */
public interface IDocHandler<T> {

    void doHandler(DocumentBuilder builder, CwmDocReportItemsEntity docReportItemsEntity, DocHandlerData<T> data);
}
