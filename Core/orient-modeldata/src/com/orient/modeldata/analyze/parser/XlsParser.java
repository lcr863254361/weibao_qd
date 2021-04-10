package com.orient.modeldata.analyze.parser;

import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2016-11-24 13:59
 */
public class XlsParser implements Parser {
    private HSSFWorkbook workBook = null;
    private HSSFSheet sheet = null;
    private HSSFRow row = null;
    private List<String> headers = null;

    public XlsParser(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            this.workBook = new HSSFWorkbook(fis);
            this.sheet = this.workBook.getSheetAt(0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean readHeaders(int row) {
        if(this.sheet == null) {
            return false;
        }
        this.row = this.sheet.getRow(row);
        if(this.row == null) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getHeaders() {
        List<String> retList = new ArrayList<>();
        int first = this.row.getFirstCellNum();
        int last = this.row.getLastCellNum();
        for(int i=first; i<last; i++) {
            String val = parseCell(this.row.getCell(i));
            retList.add(val);
        }

        this.headers = retList;
        return retList;
    }

    @Override
    public boolean readRecord() {
        if(this.sheet==null || this.row==null) {
            return false;
        }

        int rowNum = this.row.getRowNum();
        this.row = this.sheet.getRow(rowNum+1);
        if(this.row == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean readCurrentRecord() {
        if(this.sheet==null || this.row==null) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getValues() {
        List<String> retList = new ArrayList<>();
        int first = this.row.getFirstCellNum();
        int last = this.row.getLastCellNum();
        for(int i=first; i<last; i++) {
            String val = parseCell(this.row.getCell(i));
            retList.add(val);
        }

        this.headers = retList;
        return retList;
    }

    private String parseCell(HSSFCell cell) {
        if(cell == null) {
            return null;
        }

        String value = null;
        int cellType = cell.getCellType();
        if(cellType == HSSFCell.CELL_TYPE_BLANK) {
            value = "";
        }
        else if(cellType == HSSFCell.CELL_TYPE_STRING) {
            value = cell.getStringCellValue();
        }
        else if(cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
            value = String.valueOf(cell.getBooleanCellValue());
        }
        else if(cellType == HSSFCell.CELL_TYPE_NUMERIC) {
            if(HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                if(date != null) {
                    value = date.getTime()+"";
                }
            }
            else {
                double doubleVal = cell.getNumericCellValue();
                long longVal = (long) doubleVal;
                if(Math.abs(doubleVal-longVal) < 0.00001) {
                    value = String.valueOf(longVal);
                }
                else {
                    value = String.valueOf(doubleVal);
                }
            }
        }
        else if(cellType == HSSFCell.CELL_TYPE_FORMULA) {
            value = String.valueOf(cell.getCellFormula());
        }
        else if(cellType == HSSFCell.CELL_TYPE_ERROR) {
            value = null;
        }

        return value;
    }
}
