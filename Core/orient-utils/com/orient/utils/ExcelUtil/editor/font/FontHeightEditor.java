package com.orient.utils.ExcelUtil.editor.font;

import com.orient.utils.ExcelUtil.editor.IFontEditor;
import com.orient.utils.ExcelUtil.style.font.Font;

/**
 * 实现一些常用的字体<br/>
 * 该类用于设置字体大小
 * @author zhulongchao
 *
 */
public class FontHeightEditor implements IFontEditor {

	private int height = 12;

	public void updateFont(Font font) {
		font.fontHeightInPoints(height);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	

}
