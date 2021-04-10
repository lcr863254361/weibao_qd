package com.orient.background.doctemplate.handler.impl;

import com.aspose.words.*;
import com.orient.background.doctemplate.bean.DocHandlerData;
import com.orient.background.doctemplate.handler.IDocHandler;
import com.orient.sysmodel.domain.doc.CwmDocReportItemsEntity;
import com.orient.utils.StringUtil;
import com.orient.utils.exception.OrientBaseAjaxException;
import org.springframework.stereotype.Component;

/**
 * 循环标签
 *
 * @author Administrator
 * @create 2016-12-08 16:40
 */
@Component
public class RepeatGridDocHandler implements IDocHandler<String> {

    @Override
    public void doHandler(DocumentBuilder builder, CwmDocReportItemsEntity docReportItemsEntity, DocHandlerData<String> data) {
        try {
            String bookMarkId = "PO_" + docReportItemsEntity.getId();
            Bookmark curBookMark = builder.getDocument().getRange().getBookmarks()
                    .get(bookMarkId);
            curBookMark.setText("");
            builder.moveToBookmark(bookMarkId);
            CompositeNode currentCellComposite = builder.getCurrentNode().getParentNode().getParentNode();
            CompositeNode currentRowComposite = currentCellComposite.getParentNode();
            CompositeNode compositeNode = currentRowComposite.getParentNode();
            if (compositeNode.getNodeType() == NodeType.TABLE) {
                Table table = (Table) compositeNode;
                Row currentRow = (Row) currentRowComposite;
                Cell currentCell = (Cell) currentCellComposite;
                int colIndex = currentRow.indexOf(currentCell);
                String toInsertData = data.getAfterDataChange();
                if (!StringUtil.isEmpty(toInsertData)) {
                    String[] array = toInsertData.split(",");
                    for (int i = 0; i < array.length; i++) {
                        if (i == 0) {
                            builder.moveTo(currentCell.getFirstChild());
                            builder.write(array[i]);
                        } else {
                            int rowCount = table.getRows().getCount();
                            if (i > rowCount - 2) {
                                Row row = new Row(table.getDocument());
                                table.appendChild(row);
                                for (int index = 0; index < table.getFirstRow().getCells().getCount(); index++) {
                                    Cell cell = new Cell(table.getDocument());
                                    Paragraph paragraph = new Paragraph(table.getDocument());
                                    cell.appendChild(paragraph);
                                    row.appendChild(cell);
                                }
                            }
                            Row row = table.getRows().get(i + 1);
                            Cell cell = row.getCells().get(colIndex);
                            builder.moveTo(cell.getFirstChild());
                            builder.write(array[i]);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new OrientBaseAjaxException("", ex.toString());
        }
    }
}
