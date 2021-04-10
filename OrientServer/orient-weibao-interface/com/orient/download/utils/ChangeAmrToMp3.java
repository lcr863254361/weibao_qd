package com.orient.download.utils;


import com.orient.utils.CommonTools;
import org.drools.process.command.Command;

import java.io.File;

/**
 * ${DESCRIPTION}
 *
 * @author User
 * @create 2019-04-02 10:36
 */
public class ChangeAmrToMp3 {

    private Command command;

    public static void changeAmrToMp3(String sourcePath, String targetPath) {
        String folderName = "videoPreview\\ffmpeg-win\\bin";

//        String webroot = "D:\\IdeaProjects\\WEIBAO_SERVER\\ffmpeg-win64\\bin";
        String webroot= getVoiceShiftPath() + File.separator+folderName;
        Runtime run = null;
        try {
            run = Runtime.getRuntime();
            long start = System.currentTimeMillis();
            System.out.println(new File(webroot).getAbsolutePath());
            //执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址，-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
            Process p = run.exec(new File(webroot).getAbsolutePath() + "/ffmpeg -i " + sourcePath + " -acodec libmp3lame " + targetPath);
            //释放进程
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();
            //执行ffmpeg.exe[增强声音]
            Process p1 = run.exec(new File(webroot).getAbsolutePath() + "/ffmpeg -i " + targetPath + " -vol 800 " + targetPath);
            //释放进程
            p1.getOutputStream().close();
            p1.getInputStream().close();
            p1.getErrorStream().close();
            p1.waitFor();
            long end = System.currentTimeMillis();
            System.out.println(sourcePath + " convert success,costs:" + (end - start) + "ms");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //run调用lame解码器最后释放内存
            run.freeMemory();
        }

    }

    private static String getVoiceShiftPath() {
        return CommonTools.getRootPath() + File.separator + "preview";
    }

}
