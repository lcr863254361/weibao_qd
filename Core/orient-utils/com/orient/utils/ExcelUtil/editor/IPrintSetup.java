package com.orient.utils.ExcelUtil.editor;

import org.apache.poi.hssf.usermodel.HSSFPrintSetup;

/**
 * 设置打印格式
 * @author zhulongchao
 *
 */
public interface IPrintSetup {
	
	public void setup(HSSFPrintSetup printSetup);
}
