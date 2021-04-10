package com.aptx.utils;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class CharsetUtil {
	public static final String ASCII = "US-ASCII";
	public static final String UTF_8 = "UTF-8";
	public static final String UTF_16 = "UTF-16";
	public static final String UTF_16_BE = "UTF-16BE";
	public static final String UTF_16_LE = "UTF-16LE";
	public static final String GBK = "GBK";
	public static final String GB2312 = "GB2312";
	public static final String GB18030 = "GB18030";
	public static final String BIG5 = "Big5";
	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String ISO_2022_CN = "ISO-2022-CN";
	public static final String ISO_2022_JP = "ISO-2022-JP";
	public static final String ISO_2022_KR = "ISO-2022-KR";
	public static final String EUC_JP = "EUC-JP";
	public static final String EUC_KR = "EUC-KR";
	public static final String EUC_TW = "EUC-TW";
	public static final String WINDOWS_1252 = "windows-1252";
	public static final String HZ_GB_2312 = "HZ-GB-2312";
	public static final String SHIFT_JIS = "Shift_JIS";

	public static String getCharset(byte[] bytes, Integer length) {
		if(length==null || length<=0) {
			length = bytes.length;
		}

		boolean[] found = new boolean[]{false};
		String[] encoding = new String[]{null};

		nsDetector dedetector = new nsDetector(nsPSMDetector.CHINESE) ;
		dedetector.Init(new nsICharsetDetectionObserver() {
			public void Notify(String charset) {
				found[0] = true;
				encoding[0] = charset;
			}
		});

		boolean isAscii = true;
		boolean done = false;
		if(isAscii) {
			isAscii = dedetector.isAscii(bytes, length);
		}
		if(!isAscii) {
			done = dedetector.DoIt(bytes, length, false);
		}
		dedetector.DataEnd();

		if (isAscii) {
			found[0] = true ;
			encoding[0] = ASCII;
		}
		if (!found[0]) {
			String prob[] = dedetector.getProbableCharsets() ;
			if(!"nomatch".equals(prob[0])) {
				found[0] = true;
				encoding[0] = prob[0];
			}
		}

		if(found[0]) {
			return encoding[0];
		}
		else {
			return null;
		}
	}
	
	public static String getCharset(URL url) {
		boolean[] found = new boolean[]{false};
		String[] encoding = new String[]{null};
		
		nsDetector dedetector = new nsDetector(nsPSMDetector.CHINESE) ;
		dedetector.Init(new nsICharsetDetectionObserver() {
			public void Notify(String charset) {
				found[0] = true;
				encoding[0] = charset;
			}
		});

		boolean isAscii = true;
		try {
			ByteSource byteSource = Resources.asByteSource(url);
			InputStream inputStream = byteSource.openBufferedStream();
			byte[] buffer = new byte[1024];
			int length;
			boolean done = false;
			while((length=inputStream.read(buffer,0,buffer.length)) != -1) {
				if(isAscii) {
					isAscii = dedetector.isAscii(buffer,length);
				}
				if(!isAscii && !done) {
					done = dedetector.DoIt(buffer, length, false);
					if(done) {
						break;
					}
				}
			}
		}
		catch (IOException e) {
			throw new RuntimeException("文件读取错误："+url.getPath(), e);
		}
		dedetector.DataEnd();

		if (isAscii) {
			found[0] = true ;
			encoding[0] = ASCII;
		}
		if (!found[0]) {
			String prob[] = dedetector.getProbableCharsets() ;
			if(!"nomatch".equals(prob[0])) {
				found[0] = true;
				encoding[0] = prob[0];
			}
		}

		if(found[0]) {
			return encoding[0];
		}
		else {
			return null;
		}
	}
}
