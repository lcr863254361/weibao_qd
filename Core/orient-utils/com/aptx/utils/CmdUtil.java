package com.aptx.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public abstract class CmdUtil {
    private static final Logger logger = LoggerFactory.getLogger(CmdUtil.class);

    public static String getSystemDefaultCharset() {
        if(isWindows()) {
            return CharsetUtil.GBK;
        }
        else if(isMac()) {
            return CharsetUtil.GB2312;
        }
        else {
            return CharsetUtil.UTF_8;
        }
    }

    public static boolean isWindows() {
        String str = System.getProperty("os.name").toLowerCase();
        return (str.indexOf("windows") != -1) || (str.indexOf("nt") != -1);
    }

    public static boolean isMac() {
        String str = System.getProperty("os.name").toLowerCase();
        return str.indexOf("mac") != -1;
    }

    public static boolean isLinux() {
        String str = System.getProperty("os.name").toLowerCase();
        return str.indexOf("linux") != -1;
    }

    public static int executeCommand(String[] cmd, String[] env, File dir) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = null;
            if(isLinux()) {
                process = runtime.exec(cmd, env, dir);
            }
            else if(isWindows()) {
                String[] newCmd = new String[cmd.length+2];
                newCmd[0] = "cmd";
                newCmd[1] = "/c";
                System.arraycopy(cmd, 0, newCmd, 2, cmd.length);
                process = runtime.exec(newCmd, env, dir);
            }
            else {
                String[] newCmd = new String[cmd.length+1];
                newCmd[0] = "/usr/bin/open";
                System.arraycopy(cmd, 0, newCmd, 1, cmd.length);
                process = runtime.exec(newCmd, env, dir);
            }

            Scanner scanner = new Scanner(process.getInputStream(), getSystemDefaultCharset());
            while(scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            process.waitFor();
            return process.exitValue();
        }
        catch (Exception e) {
            logger.error("Command execute error: " +cmd, e);
            return -1;
        }
    }

    public static final void openBrowser(String url) {
        try {
            if(isWindows()) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            }
            else if(isMac()) {
                Class clazz = Class.forName("com.apple.mrj.MRJFileUtils");
                Method openURL = clazz.getDeclaredMethod("openURL", new Class[]{String.class});
                openURL.invoke(null, new Object[]{url});
            }
            else {
                String[] browsers = new String[] {"firefox", "opera", "konqueror", "mozilla", "netscape"};
                String browser = null;
                for(int i=0; (browser==null) && (i<browsers.length); i++) {
                    Process process = Runtime.getRuntime().exec(new String[] {"which", browsers[i]});
                    if(process.waitFor() == 0) {
                        browser = browsers[i];
                    }
                }
                if (browser == null) {
                    throw new RuntimeException("Could not find web browser.");
                }
                Runtime.getRuntime().exec(new String[] { browser, url});
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error attempting to launch web browser", e);
        }
    }
}
