package com.orient.background.doctemplate.handler.impl;

import com.aspose.words.Bookmark;
import com.aspose.words.DocumentBuilder;
import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.bean.HyperLink;
import com.orient.background.doctemplate.handler.IDocHandler;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import org.springframework.stereotype.Component;

/**
 * output HyperLink style value
 *
 * @author enjoyjava
 * @create 2016-11-30 3:02 PM
 */
@Component
public class HyperLinkHandler implements IDocHandler<HyperLink> {

    @Override
    public void doHandler(DocumentBuilder builder, CwmDocReportItemsEntity docReportItemsEntity, DocHandlerData<HyperLink> data) {
        try {
            String bookMarkId = "PO_" + docReportItemsEntity.getId();
            Bookmark curBookMark = builder.getDocument().getRange().getBookmarks()
                    .get(bookMarkId);
            curBookMark.setText("暂未支持");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
