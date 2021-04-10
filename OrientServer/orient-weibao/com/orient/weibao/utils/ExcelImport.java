package com.orient.weibao.utils;

import com.orient.businessmodel.bean.IBusinessColumn;
import com.orient.businessmodel.bean.IBusinessModel;
import com.orient.utils.CommonTools;
import com.orient.utils.DateFormatUtil;
import com.orient.utils.TimeUtil;
import com.orient.web.base.BaseBusiness;
import jxl.write.DateFormat;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * @description: 文件导入
 * @author: USER
 * @create: 2020-06-04 10:51
 **/
@Component
public class ExcelImport extends BaseBusiness {

    //获取cell时损失精度的解决办法
    private static NumberFormat numberFormat = NumberFormat.getInstance();

    static {
        numberFormat.setGroupingUsed(false);
    }

    /**
     * description: 读取excel数据
     *
     * @param multipartFile
     * @return List<List       <       Object>>
     * @version v1.0
     * @author w
     * @date 2020年3月31日 下午3:36:39
     */
    public List<List<Map<String, String>>> importFile(MultipartFile multipartFile, IBusinessModel[] models) throws Exception {

        if (multipartFile == null) {
            return null;
        }

        return readExcel(multipartFile.getInputStream(), models);

    }

    /**
     * 测试 workBoolFactory  读取excel
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    private List<List<Map<String, String>>> readExcel(java.io.InputStream inputStream, IBusinessModel[] models) throws Exception {
        List<List<Map<String, String>>> list = new ArrayList<>();
        // 读取excel
//        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        Workbook workbook = WorkbookFactory.create(inputStream);
        // 获取sheet 页数量
        int sheets = workbook.getNumberOfSheets();
        for (int num = 0; num < sheets; num++) {
            List<Map<String, String>> list1 = new ArrayList<>();
            Map<String, List<Map<String, String>>> d = new HashMap<>();
            Sheet sheet = workbook.getSheetAt(num);
            if (null == sheet) {
                continue;
            }

            //获取列对应 真实字段
//            String columns[] = getFirstRowColumnName(sheet, models[num]);
            String columns[] = getFirstRowColumnName(sheet, models);
            // sheet 页的总行数
            int rows = sheet.getLastRowNum();
            // startRow 开始读取的行数 --- 第二行开始读
            for (int startRow = 1; startRow <= rows; startRow++) {
                Row row = sheet.getRow(startRow);
                Map<String, String> rowMap = new HashMap<>();
                if (null != row) {
                    // row 行中的 单元格总个数
                    short cells = row.getLastCellNum();
                    if (cells > columns.length) {
                        cells = (short) columns.length;
                    }
                    for (int x = 0; x < cells; x++) {
                        Cell cell = row.getCell(x);
                        if (null == cell) {
                            rowMap.put(columns[x], "");
                        } else {
//                            System.out.println(columns[x]);
//                            System.out.println(CommonTools.Obj2String(getCellFormatValue(cell)));
//                            System.out.println("------------------------------------------------------------------");
                            String cellValue = CommonTools.Obj2String(getCellFormatValue(cell));
                            cellValue = "无".equals(cellValue) ? "" : cellValue;
                            rowMap.put(columns[x], cellValue);
                        }
                    }
                    list1.add(rowMap);
                }
            }
            list.add(list1);
        }
        return list;
    }

    /**
     * 获取excel的 列名的 真实字段值
     *
     * @param sheet
     * @param model
     * @return
     */
    public String[] getFirstRowColumnName(Sheet sheet, IBusinessModel[] models) {
        String columns[] = null;
        Row firstRow = sheet.getRow(0);
//        List<IBusinessColumn> columnsList = model.getAllBcCols();

        if (null != firstRow) {
            // row 行中的 单元格总个数
            short cells = firstRow.getLastCellNum();
            columns = new String[cells];
            for (int x = 0; x < cells; x++) {
                Cell cell = firstRow.getCell(x);
                for (IBusinessModel model : models) {
                    List<IBusinessColumn> columnsList = model.getAllBcCols();
                    for (IBusinessColumn column : columnsList) {
                        String cellValue = CommonTools.Obj2String(getCellFormatValue(cell));
                        cellValue = cellValue.replaceAll("\r|\n", "");
                        //将对应的字段真实值放进数组
                        if ("ID".equals(cellValue)) {
                            //以前的ID存入旧ID字段
//                        columns[x] = "OLDID_" + model.getId();
                            break;
                        } else if (cellValue.equals(column.getDisplay_name())) {
//                        System.out.println(cellValue);
//                        System.out.println(column.getS_column_name());
                            columns[x] = column.getS_column_name();
                            break;
                        }
                    }
                }
            }
        }
        return columns;
    }

    /**
     * 测试 workBookFactory  获得cell的值
     *
     * @param cell
     * @return
     */
    private String getCellFormatValue(Cell cell) {
        if (cell == null)
            return "";
        String cellvalue = "";
        cellvalue = cellvalue.trim();
        // 判断当前单元格的type
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                // /取得当前Cell的字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;

            // 如果当前Cell的type为NUMERIC或者_FORMULA
            case Cell.CELL_TYPE_NUMERIC:
            case Cell.CELL_TYPE_FORMULA:
                // 判断当前的Cell是否为Date
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 如果是在Date类型，则取得该Cell的Date值
                    Date date = cell.getDateCellValue();
                    cellvalue = DateFormatUtil.format(date);
                    //区分Date  以及 DateTime
                    if (cellvalue.substring(cellvalue.indexOf(" ") + 1).equals("00:00:00")) {
                        cellvalue = cellvalue.substring(0, cellvalue.indexOf(" "));
                    }
                    if (cellvalue.contains("1899-12-31")) {
                        long differentMs = 0;
//                        if (cellvalue.contains("：")){
//                           cellvalue. replaceAll("：", ":");
//                        }
                        Date endDate = TimeUtil.convertString(cellvalue);
                        if (endDate != null) {
                            differentMs = TimeUtil.getTime(TimeUtil.convertString("1899-12-31 00:00:00"), endDate);
                            cellvalue = "" + differentMs;
                        }
                    }
                } else {
                    // 如果是纯数字
                    // 取得当前cell的数值
//                    cellvalue = String.valueOf((long) cell.getNumericCellValue());
                    Long longVal = Math.round(cell.getNumericCellValue());
                    Double doubleVal = cell.getNumericCellValue();
                    if (Double.parseDouble(longVal + ".0") == doubleVal) {
                        cellvalue = String.valueOf(longVal);
                    } else {
                        cellvalue = String.valueOf(doubleVal);
//                        DecimalFormat decimalFormat=new DecimalFormat("#.####");
//                        cellvalue=String .valueOf(decimalFormat.format(doubleVal));
                    }
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellvalue = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                cellvalue = "";
        }
        return cellvalue;
    }
}
