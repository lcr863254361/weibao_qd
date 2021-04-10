package com.orient.background.doctemplate.handler.impl;

import com.aspose.words.Bookmark;
import com.aspose.words.DocumentBuilder;
import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.handler.IDocHandler;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import com.orient.utils.CommonTools;
import com.orient.web.util.UserContextUtil;
import org.springframework.stereotype.Component;

/**
 * output current info
 *
 * @author enjoyjava
 * @create 2016-11-30 3:02 PM
 */
@Component
public class CurrentHandler implements IDocHandler<String> {

    @Override
    public void doHandler(DocumentBuilder builder, CwmDocReportItemsEntity docReportItemsEntity, DocHandlerData<String> data) {
        try {
            String bookMarkId = "PO_" + docReportItemsEntity.getId();
            Bookmark curBookMark = builder.getDocument().getRange().getBookmarks()
                    .get(bookMarkId);
            String[] array = docReportItemsEntity.getBookmarkName().split("\\.");
            String currentType = array[0];
            if ("当前时间".equals(currentType)) {
                curBookMark.setText(CommonTools.getDateStrByFormat("yyyy-MM-dd"));
            } else if ("当前日期".equals(currentType)) {
                curBookMark.setText(CommonTools.getDateStrByFormat("yyyy-MM-dd HH:mm:ss"));
            } else if ("当前用户".equals(currentType)) {
                curBookMark.setText(UserContextUtil.getUserAllName());
            } else if ("当前部门".equals(currentType)) {
                curBookMark.setText(UserContextUtil.getUserDepName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
