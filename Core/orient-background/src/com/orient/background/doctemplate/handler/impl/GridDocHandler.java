package com.orient.background.doctemplate.handler.impl;

import com.aspose.words.*;
import com.orient.background.doctemplate.bean.DocGridColumn;
import com.orient.background.doctemplate.bean.DocGridData;
import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.handler.IDocHandler;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import com.orient.utils.CommonTools;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * output grid style value
 *
 * @author enjoyjava
 * @create 2016-11-30 3:02 PM
 */
@Component
public class GridDocHandler implements IDocHandler<DocGridData> {

    @Override
    public void doHandler(DocumentBuilder builder, CwmDocReportItemsEntity docReportItemsEntity, DocHandlerData<DocGridData> data) {
        try {
            String bookMarkId = "PO_" + docReportItemsEntity.getId();
            Bookmark curBookMark = builder.getDocument().getRange().getBookmarks()
                    .get(bookMarkId);
            curBookMark.setText("");
            builder.moveToBookmark(bookMarkId);
            DocGridData docGridData = data.getAfterDataChange();
            builder.getRowFormat().setAlignment(RowAlignment.CENTER);//整个表格居中
            builder.startTable();
            if (docGridData.getDirection().equals(DocGridData.GridDirectionEnum.VERTICAL)) {
                insertVerticalGrid(builder, docGridData);
            } else {

            }
            builder.endTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertVerticalGrid(DocumentBuilder builder, DocGridData docGridData) {
        try {
            if (docGridData.getNeedHead()) {
                //insert head
                for (DocGridColumn docGridColumn : docGridData.getColumns()) {
                    builder.insertCell();
                    builder.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);//单元格边框样式
                    builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);//垂直居中对齐
                    builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);//水平居中对齐
                    builder.write(docGridColumn.getColumnDisplayName());
                }
                builder.endRow();
            }
            //insert data
            java.util.List<Map<String, DocHandlerData>> dataList = docGridData.getDataList();
            for (Map<String, DocHandlerData> dataMap : dataList) {
                for (DocGridColumn docGridColumn : docGridData.getColumns()) {
                    builder.insertCell();
                    builder.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);//单元格边框样式
                    builder.getCellFormat().setVerticalAlignment(CellVerticalAlignment.CENTER);//垂直居中对齐
                    builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);//水平居中对齐
                    if (null != dataMap.get(docGridColumn.getColumnName()) && dataMap.get(docGridColumn.getColumnName()).getAfterDataChange() instanceof String) {
                        String value = CommonTools.Obj2String(dataMap.get(docGridColumn.getColumnName()).getAfterDataChange());
                        builder.write(value);
                    } else {
                        builder.write("表格中暂未支持其他类型");
                    }
                }
                builder.endRow();
            }
        } catch (Exception ex) {
            throw new OrientBaseAjaxException("", ex.toString());
        }
    }
}
