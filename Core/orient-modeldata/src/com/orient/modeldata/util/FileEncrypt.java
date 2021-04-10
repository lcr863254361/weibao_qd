package com.orient.modeldata.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-05-09 9:54
 */
public class FileEncrypt {
    public static final String XOR = "XOR";
    public static final String DES = "DES";
    public static final String AES = "AES";
    public static final String NONE = "NONE";

    private static final int xorSeed = 0xAA;
    private static final String desSeed = "F89FF2D874ECA206";
    private static final String aesSeed = "EC2F074C2F074626246ADC2F074074D8F8FEAD8F8AD63BE6E5ADD3EA27319FD8FEC2F0746AB5DFAD67E03F3BE6E5ADD368D8FEC2F0731DFE0A8FD8FEF92A8FD867EA2FF368DFEC2F0FE6E5ADD368DB7462AED67E20B5DF3B5EA2FD0B5DFA12F92A822A8F20C268DFE092A8FD8FEBE6E5A8FEC2FECDD3C2F020FD27462A8FE031";

    public static File encrypt(File src, File enc, String type) {
        if(src==null || !src.exists()) {
            return null;
        }

        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            byte[] byteIn = new byte[(int) src.length()];
            fis.read(byteIn, 0, byteIn.length);
            fis.close();

            if(enc.exists()) {
                enc.createNewFile();
            }
            fos = new FileOutputStream(enc);
            byte[] byteOut = null;
            if(XOR.equals(type)) {
                byteOut = xorEncrypt(byteIn, xorSeed);
            }
            else if(DES.equals(type)) {
                byteOut = desEncrypt(byteIn, getHexKey(desSeed));
            }
            else if(AES.equals(type)) {
                byteOut = aesEncrypt(byteIn, getHexKey(aesSeed));
            }
            if(byteOut == null) {
                return null;
            }
            fos.write(byteOut, 0, byteOut.length);
            fos.flush();
            fos.close();
        }
        catch (Exception e) {
            try {
                if(fis != null) {
                    fis.close();
                }
                if(fos != null) {
                    fos.close();
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        return enc;
    }

    public static File decrypt(File src, File dec, String type) {
        if(src==null || !src.exists()) {
            return null;
        }

        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            byte[] byteIn = new byte[(int) src.length()];
            fis.read(byteIn, 0, byteIn.length);
            fis.close();

            if(dec.exists()) {
                dec.createNewFile();
            }
            fos = new FileOutputStream(dec);
            byte[] byteOut = null;
            if(XOR.equals(type)) {
                byteOut = xorDecrypt(byteIn, xorSeed);
            }
            else if(DES.equals(type)) {
                byteOut = desDecrypt(byteIn, getHexKey(desSeed));
            }
            else if(AES.equals(type)) {
                byteOut = aesDecrypt(byteIn, getHexKey(aesSeed));
            }
            if(byteOut == null) {
                return null;
            }
            fos.write(byteOut, 0, byteOut.length);
            fos.flush();
            fos.close();
        }
        catch (Exception e) {
            try {
                if(fis != null) {
                    fis.close();
                }
                if(fos != null) {
                    fos.close();
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        return dec;
    }

    public static byte[] xorEncrypt(byte[] src, int key) {
        if(src == null) {
            return null;
        }
        byte[] retByte = new byte[src.length];
        for(int i=0; i<src.length; i++) {
            retByte[i] = (byte)((int)src[i]^key);
        }
        return retByte;
    }

    public static byte[] xorDecrypt(byte[] src, int key) {
        if(src == null) {
            return null;
        }
        byte[] retByte = new byte[src.length];
        for(int i=0; i<src.length; i++) {
            retByte[i] = (byte)((int)src[i]^key);
        }
        return retByte;
    }

    public static byte[] desEncrypt(byte[] src, byte[] key) {
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            return Base64.encodeBase64(cipher.doFinal(src));
        }
        catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] desDecrypt(byte[] src, byte[] key) {
        try {
            src = Base64.decodeBase64(src);
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            return cipher.doFinal(src);
        }
        catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] aesEncrypt(byte[] src, byte[] key) {
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128, new SecureRandom(key));
            SecretKey secretKey = keyGen.generateKey();
            SecretKeySpec aesKey = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            return cipher.doFinal(src);
        }
        catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] aesDecrypt(byte[] src, byte[] key) {
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128, new SecureRandom(key));
            SecretKey secretKey = keyGen.generateKey();
            SecretKeySpec aesKey = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            return cipher.doFinal(src);
        }
        catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getHexKey(String str) {
        byte[] bytes = new byte[str.length()/2];
        for (int i=0; i<str.length()/2; i++) {
            Integer intVal = Integer.valueOf(str.substring(2*i, 2*i+2), 16);
            bytes[i] = intVal.byteValue();
        }
        return bytes;
    }
}
