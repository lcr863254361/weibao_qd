package com.orient.background.doctemplate.handler.impl;

import com.aspose.words.Bookmark;
import com.aspose.words.DocumentBuilder;
import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.handler.IDocHandler;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * output file link style value
 *
 * @author enjoyjava
 * @create 2016-11-30 3:02 PM
 */
@Component
public class FileDocHandler implements IDocHandler<List<File>> {

    @Override
    public void doHandler(DocumentBuilder builder, CwmDocReportItemsEntity docReportItemsEntity, DocHandlerData<List<File>> data) {
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
