package com.orient.weibao.utils;

import com.google.zxing.common.*;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
 import java.io.IOException;
import java.awt.image.BufferedImage;
 
 public final class MatrixToImageWriter {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    public MatrixToImageWriter() {}

 
     public static BufferedImage toBufferedImage(BitMatrix matrix) {
         int width = matrix.getWidth();
         int height = matrix.getHeight();
         BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
               image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
         return image;
    }
 

     public static BufferedImage writeToFile(BitMatrix matrix, String format, File file)
             throws IOException {
        BufferedImage image = toBufferedImage(matrix);
         if (!ImageIO.write(image, format, file)) {
             throw new IOException("Could not write an image of format " + format + " to " + file);
         }
         return image;
     }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
             throws IOException {
         BufferedImage image = toBufferedImage(matrix);
         if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
         }
    }

     public static void pressText(String pressText,String targetImg, int fontStyle, Color color, int fontSize, int width, int height) throws Exception{
         pressText=new String(pressText.getBytes(),"utf-8");
         //计算文字开始位置
         //x开始的位置：（图片宽度-字体大小*字的个数）/2
         int startX = (width-(fontSize*pressText.length()))/2;
        //y开始的位置：图片高度-（图片高度-图片宽度）/2
         int startY = height-(height-width)/2;

         try {
             File file = new File(targetImg);
             Image src = ImageIO.read(file);
             int imageW = src.getWidth(null);
             int imageH = src.getHeight(null);
             BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
             Graphics g = image.createGraphics();
             g.drawImage(src, 0, 0, imageW, imageH, null);
             g.setColor(color);
             g.setFont(new Font(null, fontStyle, fontSize));
             g.drawString(pressText, startX, startY);
             g.dispose();

             FileOutputStream out = new FileOutputStream(targetImg);
             ImageIO.write(image, "JPEG", out);
             JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
             encoder.encode(image);
             out.close();
             System.out.println("image press success");
         } catch (Exception e) {
             System.out.println(e);
         }
     }

 }