package com.orient.utils;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * Created by Administrator on 2016/1/6
 */
public class EncodingDetect {

    public boolean found = false;
    public String encoding = null;

    public String getEncoding(String fileName) throws Exception {
        found = false;
        encoding = null;

        nsDetector det = new nsDetector(nsPSMDetector.ALL);
        det.Init(new nsICharsetDetectionObserver() {
            public void Notify(String charset) {
                found = true;
                encoding = charset;
            }
        });

        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream imp = new BufferedInputStream(fis);
        byte[] buf = new byte[1024];
        int len;
        boolean done = false;
        boolean isAscii = true;
        while ((len = imp.read(buf, 0, buf.length)) != -1) {
            if (isAscii)
                isAscii = det.isAscii(buf, len);
            if (!isAscii && !done)
                done = det.DoIt(buf, len, false);
        }
        det.DataEnd();

        if (isAscii) {
            found = true;
            encoding = "US-ASCII";
        }

        if (!found) {
            String prob[] = det.getProbableCharsets();
            if (!"nomatch".equals(prob[0])) {
                found = true;
                encoding = prob[0];
            }
        }

        if (found) {
            return encoding;
        } else {
            return null;
        }
    }
}
