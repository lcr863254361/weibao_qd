package com.orient.login.service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import it.sauronsoftware.base64.Base64;

/**
 * @author author:WangShan
 * @version Date&Time:2016年4月8日 上午9:20:00 Class description:
 */
public class KeyGenerater {

	public String getPriKey() {
		return priKey;
	}

	public String getPubKey() {
		return pubKey;
	}

	private String priKey;
	private String pubKey;

	public void generater() {
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			SecureRandom secrand = new SecureRandom();
			secrand.setSeed("www.orient.com".getBytes()); // 初始化随机产生器
			keygen.initialize(1024, secrand);
			KeyPair keys = keygen.genKeyPair();
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();
			pubKey = new String(Base64.encode(pubkey.getEncoded()));
			priKey = new String(Base64.encode(prikey.getEncoded()));
			System.out.println("pubKey = " + new String(pubKey));
			System.out.println("priKey = " + new String(priKey));
			System.out.println("生成密钥对成功");
		} catch (java.lang.Exception e) {
			System.out.println("生成密钥对失败");
			e.printStackTrace();
		}
	}

}
