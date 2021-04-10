package com.orient.background.doctemplate.handler.impl;

import com.aspose.words.Bookmark;
import com.aspose.words.DocumentBuilder;
import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.handler.IDocHandler;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import org.springframework.stereotype.Component;

/**
 * output text style value
 *
 * @author enjoyjava
 * @create 2016-11-30 3:02 PM
 */
@Component
public class TextDocHandler implements IDocHandler<String> {

    @Override
    public void doHandler(DocumentBuilder builder, CwmDocReportItemsEntity docReportItemsEntity, DocHandlerData<String> data) {
        try {
            String bookMarkId = "PO_" + docReportItemsEntity.getId();
            Bookmark curBookMark = builder.getDocument().getRange().getBookmarks()
                    .get(bookMarkId);
            curBookMark.setText(data.getAfterDataChange());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
