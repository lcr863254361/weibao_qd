package com.orient.utils.ExcelUtil.editor.font;

import com.orient.utils.ExcelUtil.editor.IFontEditor;
import com.orient.utils.ExcelUtil.style.font.BoldWeight;
import com.orient.utils.ExcelUtil.style.font.Font;

/**
 * 实现一些常用的字体<br/>
 * 该类用于把字体加粗
 * @author zhulongchao
 *
 */
public class BoldFontEditor implements IFontEditor {

	public void updateFont(Font font) {
		font.boldweight(BoldWeight.BOLD);
	}

}
