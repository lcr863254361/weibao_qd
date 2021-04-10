package com.orient.utils.ExcelUtil.editor.font;

import com.orient.utils.ExcelUtil.editor.IFontEditor;
import com.orient.utils.ExcelUtil.style.font.Font;

/**
 * 实现一些常用的字体<br/>
 * 该类用于设置斜体
 * 
 * @author zhulongchao
 * 
 */
public class ItalicFontEditor implements IFontEditor {

	public void updateFont(Font font) {
		font.italic(true);
	}

}
