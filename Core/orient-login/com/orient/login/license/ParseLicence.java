package com.orient.login.license;

/**  
 * ���ļ�����DSA����ǩ��  
 * @author Ivan  
 * @DataTime 2006-12-12 0:58  
 *  
 */  
  
import java.io.FileInputStream;   
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.io.ObjectInputStream;   
import java.io.ObjectOutputStream;   
import java.security.InvalidKeyException;   
import java.security.KeyPair;   
import java.security.KeyPairGenerator;   
import java.security.NoSuchAlgorithmException;   
import java.security.PrivateKey;   
import java.security.PublicKey;   
import java.security.SecureRandom;   
import java.security.Signature;   
import java.security.SignatureException;    
  
public class ParseLicence{   
       
    private PublicKey pubKey;   
    private PrivateKey priKey;   
    public String pubfile = "";   
    public String prifile = "";   
       
    public ParseLicence(){}   
    public ParseLicence(String pubfile, String prifile){   
        this.pubfile = pubfile;   
        this.prifile = prifile;   
    }   
    
    public static void main(String[] args){
    	XmlDomReader xmlDomReader=new XmlDomReader();
        Object obj = null;   
        try{   
            FileInputStream fis = new FileInputStream("pubKey.key");   
            ObjectInputStream ois = new ObjectInputStream(fis);   
            obj = ois.readObject();   
            ois.close();   
        }catch(IOException ex){   
            System.err.println("读取公钥信息错误");   
        }catch(ClassNotFoundException ex){   
            ex.printStackTrace();   
        }

    	try {
//    		ObjectInputStream is=new ObjectInputStream(new FileInputStream("license.xml"));
//    		byte[] test = (byte[])is.readObject();
//    		byte[] test1 = (byte[])is.readObject();
//    		FileInputStream in = new FileInputStream("license.xml");   
//            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);   
//            byte[] b = new byte[4096];   
//            int n;   
//            while ((n = in.read(b)) != -1) {   
//                out.write(b, 0, n);   
//            }   
//            in.close();   
//            out.close();   
//            byte[] test = out.toByteArray(); 
//            System.out.println(new String(test));
            
            String info = xmlDomReader.readColumn("license.license");
            String security = xmlDomReader.readSignature("license.license");
            
            //xmlDomReader.getNodeContent("license.xml");
            
    		Signature checkSignet = Signature.getInstance("DSA");   
            checkSignet.initVerify((PublicKey)obj);   
            checkSignet.update(info.getBytes());  
            if(checkSignet.verify(HexString2Bytes(security))){   
                System.out.println("======== 签名正常 =========");   
            }   
            else{   
                System.err.println("======== 签名异常 =========");   
            } 
//            System.out.println("11 " + ParseLicence.validateSignInfo(""));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch(NoSuchAlgorithmException ex){   
            ex.printStackTrace();   
        }catch(InvalidKeyException ex){   
            System.err.println("验证签名错误 异常");   
            ex.printStackTrace();   
        }catch(SignatureException ex){   
            ex.printStackTrace();   
        }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 //       dsa.checkSign(test.getBytes(),signedData);   
    }
    
    /**
     * ��֤ǩ��;
     */
    public static boolean validateSignInfo(String path){
    	XmlDomReader xmlDomReader=new XmlDomReader();
        Object obj = null;   
        boolean flag = true;
        try{   
            FileInputStream fis = new FileInputStream(path + "/pubKey.key");   
            ObjectInputStream ois = new ObjectInputStream(fis);   
            obj = ois.readObject();   
            ois.close();   
        }catch(IOException ex){   
            System.err.println("读取公钥信息错误！");   
            ex.printStackTrace();
            return false;
        }catch(ClassNotFoundException ex){   
        	System.err.println("验证签名错误!");   
            ex.printStackTrace();   
            return false;
        }
    	try {            
            String info = xmlDomReader.readColumn(path + "/license.license");
            String security = xmlDomReader.readSignature(path + "/license.license");
    		Signature checkSignet = Signature.getInstance("DSA");   
            checkSignet.initVerify((PublicKey)obj);   
            checkSignet.update(info.getBytes());  
            if(!checkSignet.verify(HexString2Bytes(security))){   
            	flag = false;
            }       
		} catch (Exception e) {
			flag = false;
		    e.printStackTrace();
		}
		return flag;
    }
    
//    //�����µ�key
//    public static void main(String[] args){
//    	try{   
//            KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");   
//            SecureRandom secrand = new SecureRandom();   
//            secrand.setSeed("random generator".getBytes());   
//            keygen.initialize(512,secrand);            //��ʼ����Կ�����   
//            KeyPair keys = keygen.generateKeyPair();//�����Կ��   
//            PublicKey pubKey1 = keys.getPublic();   
//            PrivateKey priKey1 = keys.getPrivate();   
//
//            FileOutputStream fos = new FileOutputStream("E:\\pubKey.key");   
//            ObjectOutputStream oos = new ObjectOutputStream(fos);   
//            oos.writeObject(pubKey1);   
//            oos.close();   
//            fos = new FileOutputStream("E:\\priKey.key");   
//            oos = new ObjectOutputStream(fos);   
//            oos.writeObject(priKey1);   
//            oos.close();   
//        }catch(IOException ex){   
//            System.err.println("������Կ����Ϣ���?");   
//            ex.printStackTrace();   
//        } catch(NoSuchAlgorithmException ex){   
//            ex.printStackTrace();   
//        }
//    }
    
    /** *//**  
     * ����DSA�㷨��ɷǶԳ���Կ��  
     *  
     */  
    public void genKeys(){   
        try{   
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");   
            SecureRandom secrand = new SecureRandom();   
            secrand.setSeed("random generator".getBytes());   
            keygen.initialize(512,secrand);            //��ʼ����Կ�����   
            KeyPair keys = keygen.generateKeyPair();//�����Կ��   
            pubKey = keys.getPublic();   
            priKey = keys.getPrivate();   
        }catch(NoSuchAlgorithmException ex){   
            ex.printStackTrace();   
        }   
    }   
       
    public String getPrifile(){   
        return prifile;   
    }   
    public void setPrifile(String prifile){   
        this.prifile = prifile;   
    }   
    public String getPubfile(){   
        return pubfile;   
    }   
    public void setPubfile(String pubfile){   
        this.pubfile = pubfile;   
    }   
       
    /** *//**  
     * ������Կ����Ϣ������  
     * @param filepath1 ��Կ�Ĵ��·��  
     * @param filepath2 ˽Կ�Ĵ��·��  
     */  
    public void saveKeys(){   
        if((!pubfile.equals(""))&&(!prifile.equals("")))   
            this.saveKeys(pubfile,prifile);   
        else  
            System.err.println("������Կ���?�������ñ���·����");   
    }   
    public void saveKeys(String filepath1, String filepath2){   
        try{   
            FileOutputStream fos = new FileOutputStream(filepath1);   
            ObjectOutputStream oos = new ObjectOutputStream(fos);   
            oos.writeObject(pubKey);   
            oos.close();   
            fos = new FileOutputStream(filepath2);   
            oos = new ObjectOutputStream(fos);   
            oos.writeObject(priKey);   
            oos.close();   
        }catch(IOException ex){   
            System.err.println("保存密钥对信息出错！");   
            ex.printStackTrace();   
        }   
    }   
       
    /** *//**  
     * �Ӵ��̶�ȡ��Կ��Ϣ  
     * @param filepath ��Կ���·��  
     * @return ������Կ��Ϣ��Ϊ Object �࣬Ȼ��ɸ�ݾ���� PublicKey �� PrivateKey ����ǿ������ת��  
     */  
    private Object getKey(String filepath){   
        Object obj = null;   
        try{   
            FileInputStream fis = new FileInputStream(filepath);   
            ObjectInputStream ois = new ObjectInputStream(fis);   
            obj = ois.readObject();   
            ois.close();   
        }catch(IOException ex){   
            System.err.println("读取密钥信息错误！");   
        }catch(ClassNotFoundException ex){   
            ex.printStackTrace();   
        }   
        return obj;   
    }   
       
    /** *//**  
     * ��ȡ��Կ�����ǰ��ԿΪ�գ���Ӵ����ļ���ȡ������������Ϊ��ǰ��Կ������ֱ�ӷ��ص�ǰ��Կ  
     * @return ��Կ  
     */  
    public PublicKey getPubKey(){   
        if(pubKey != null)   
            return pubKey;   
        if(!pubfile.equals("")){   
            pubKey = (PublicKey)this.getKey(pubfile);   
            return this.pubKey;   
        }   
        else{   
            System.err.println("读取公钥信息错误!!");   
            return null;   
        }   
    }   
    public PublicKey getPubKey(String filepath){   
        pubKey = (PublicKey)this.getKey(filepath);   
        return this.pubKey;   
    }   
       
    /** *//**  
     * ��ȡ˽Կ�����ǰ˽ԿΪ�գ���Ӵ����ļ���ȡ������������Ϊ��ǰ˽Կ������ֱ�ӷ��ص�ǰ˽Կ  
     * @return ˽Կ  
     */  
    public PrivateKey getPriKey(){   
        if(priKey != null)   
            return priKey;   
        if(!prifile.equals("")){   
            priKey = (PrivateKey)this.getKey(pubfile);   
            return this.priKey;   
        }   
        else{   
            System.err.println("读取密钥信息错误！!");   
            return null;   
        }   
    }   
    public PrivateKey getPriKey(String filepath){   
        priKey = (PrivateKey)this.getKey(filepath);   
        return this.priKey;   
    }   
       
    /** *//**  
     * ���õ�ǰ˽Կ����Ϣ����ǩ��  
     * @return ǩ����Ϣ��byte���ͣ�  
     */  
    public byte[] signBytes(byte[] info){   
        byte[] signed = null;   
        if(priKey == null){   
            System.err.println("======== 提取密钥信息错误，无法完成签名 =========");   
            return null;   
        }   
        try{   
            Signature signet = Signature.getInstance("DSA");   
            signet.initSign(priKey);   
            signet.update(info);   
            signed = signet.sign();   
        }catch(NoSuchAlgorithmException ex){   
            ex.printStackTrace();   
        }catch(InvalidKeyException ex){   
            System.err.println("签名错误，无效的密钥");   
            ex.printStackTrace();   
        }catch(SignatureException ex){   
            ex.printStackTrace();   
        }   
        return signed;   
    }   
       
    /** *//**  
     * ��֤ǩ����Ϣ  
     * @param info ����֤����Ϣ  
     * @param signed ����֤��Ϣ��ǩ��  
     * @return ����֤���򷵻� true ,�����򷵻� false  
     */  
    public boolean checkSign(byte[] info, byte[] signed){   
        if(pubKey == null){   
            System.err.println("======== 提取密钥信息错误，无法完成签名 =========");   
            return false;   
        }   
        try{   
            Signature checkSignet = Signature.getInstance("DSA");   
            checkSignet.initVerify(pubKey);   
            checkSignet.update(info);   
            if(checkSignet.verify(signed)){   
                System.out.println("======== 签名正常=========");   
                return true;   
            }   
            else{   
                System.err.println("======== 验证签名错误，无效的密钥 =========");   
                return false;   
            }   
        }catch(NoSuchAlgorithmException ex){   
            ex.printStackTrace();   
        }catch(InvalidKeyException ex){   
            System.err.println("验证签名错误，无效的密钥");   
            ex.printStackTrace();   
        }catch(SignatureException ex){   
            ex.printStackTrace();   
        }   
        return true;   
    }   
    
    private final static byte[] hex = "0123456789ABCDEF".getBytes();
 // ���ֽ����鵽ʮ������ַ�ת��
    public static String Bytes2HexString(byte[] b) {
     byte[] buff = new byte[2 * b.length];
     for (int i = 0; i < b.length; i++) {
      buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
      buff[2 * i + 1] = hex[b[i] & 0x0f];
     }
     return new String(buff);
    }

//    // ��ʮ������ַ��ֽ�����ת��
//    public static byte[] HexString2Bytes(String hexstr) {
//     byte[] b = new byte[hexstr.length() / 2];
//     int j = 0;
//     for (int i = 0; i < b.length; i++) {
//      char c0 = hexstr.charAt(j++);
//      char c1 = hexstr.charAt(j++);
//      b[i] = (byte) ((parse(c0) << 4) | parse(c1));
//     }
//     return b;
//    } 
    
    public static byte[] HexString2Bytes(String src){ 
    	byte[] ret = new byte[src.length()/2]; 
    	byte[] tmp = src.getBytes(); 
    	for(int i=0; i<ret.length; ++i ){ 
    		ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]); 
    	}
    	return ret; 
    }

    private static byte uniteBytes(byte src0, byte src1) {
    	byte _b0 = Byte.decode("0x" + new String(new byte[] {src0})).byteValue();
    	_b0 = (byte) (_b0 << 4);
    	byte _b1 = Byte.decode("0x" + new String(new byte[] {src1})).byteValue();
    	byte ret = (byte) (_b0 ^ _b1);
    	return ret;
    }

} 