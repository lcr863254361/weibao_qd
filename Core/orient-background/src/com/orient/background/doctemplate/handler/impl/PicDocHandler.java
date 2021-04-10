package com.orient.background.doctemplate.handler.impl;

import com.aspose.words.Bookmark;
import com.aspose.words.DocumentBuilder;
import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.handler.IDocHandler;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import com.orient.utils.FileOperator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * output picture style value
 *
 * @author enjoyjava
 * @create 2016-11-30 3:02 PM
 */
@Component
public class PicDocHandler implements IDocHandler<List<File>> {

    @Override
    public void doHandler(DocumentBuilder builder, CwmDocReportItemsEntity docReportItemsEntity, DocHandlerData<List<File>> data) {
        try {
            String bookMarkId = "PO_" + docReportItemsEntity.getId();
            Bookmark curBookMark = builder.getDocument().getRange().getBookmarks()
                    .get(bookMarkId);
            curBookMark.setText("");
            builder.moveToBookmark(bookMarkId);
            data.getAfterDataChange().forEach(file -> {
                String filePath = file.getAbsolutePath();
                //check file is image
                if (FileOperator.isImage(filePath)) {
                    try {
                        builder.insertImage(filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
