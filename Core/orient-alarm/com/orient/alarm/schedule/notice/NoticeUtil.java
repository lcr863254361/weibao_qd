package com.orient.alarm.schedule.notice;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;

public class NoticeUtil
{
	public static String getContent(Reader reader) {
		
		String result = "";
		
		try
		{
			CharArrayWriter charArray = new CharArrayWriter();
			
			int readCnt = -1;
			char[] chars = new char[8096];
			while((readCnt=reader.read(chars))!=-1) {
				charArray.write(chars, 0, readCnt);
			}
			
			charArray.close();
			reader.close();
			
			result = charArray.toString();
			
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
}
