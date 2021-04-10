package com.orient.modeldata.util;

import com.orient.utils.VideoUtil.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-05-11 15:32
 */
@Component
@EnableAsync
public class VideoTranscode {
    @Async
    public void toMp4(String srcPath, String destPath) {
        File src = new File(srcPath);
        File dest = new File(destPath);

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("aac");
        audio.setBitRate(new Integer(64000));
        audio.setChannels(new Integer(1));
        audio.setSamplingRate(new Integer(22050));
        VideoAttributes video = new VideoAttributes();
        video.setCodec("libx264");
        video.setSize(new VideoSize(1024, 768));
        video.setBitRate(new Integer(384000));
        video.setFrameRate(new Integer(5)); //1f/s帧频，1是目前测试比较清楚的，越大越模糊
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp4");
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);
        Encoder encoder = new Encoder();
        try {
            encoder.encode(src, dest, attrs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void toPng(String srcPath, String destPath) {
        File src = new File(srcPath);
        File dest = new File(destPath);

        VideoAttributes video = new VideoAttributes();
        video.setCodec("png");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("image2");
        attrs.setOffset(3f);
        attrs.setDuration(0.01f);
        attrs.setVideoAttributes(video);
        Encoder encoder = new Encoder();
        try {
            encoder.encode(src, dest, attrs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
