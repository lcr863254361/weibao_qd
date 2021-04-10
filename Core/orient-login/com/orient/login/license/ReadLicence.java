package com.orient.login.license;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.orient.login.service.KeyGenerater;
import com.orient.login.service.RSAUtils;

/**
 * @author author:WangShan
 * @version Date&Time:2016年4月23日 上午11:28:13 Class description:
 */
public class ReadLicence {
	public static Map<String, Object> read(String fileName) throws Exception {
		Map<String, Object> map = null;
		KeyGenerater kg = new KeyGenerater();
		kg.generater();
		String priKey = kg.getPriKey();
		String pubKey = kg.getPubKey();

		byte[] readEncodedData = ParseLicense.readLicense(fileName);
		if (!("".equals(readEncodedData) || readEncodedData == null)) {
			String sign = RSAUtils.sign(readEncodedData, priKey);
			if (RSAUtils.verify(readEncodedData, pubKey, sign)) {
				try {
					byte[] decodedData = RSAUtils.decryptByPublicKey(
							readEncodedData, pubKey);
					Properties prop = new Properties();
					prop.load(new ByteArrayInputStream(decodedData));
					map = ParseLicense.propTomap(prop);
					Set<String> set = map.keySet();
					for (Object s : set) {
						Object o = map.get(s);
						System.out.println(s + "=" + o);
					}
					System.out.println("成功了");
					return map;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("签名不正确！");
			}
		} else {
			System.out.println("未找到license文件或路径不正确。");
		}

		return map;
	}
}
