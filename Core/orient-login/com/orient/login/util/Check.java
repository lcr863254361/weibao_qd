package com.orient.login.util;

import java.util.Map;

import com.orient.login.license.GetSystemInfo;
import com.orient.login.license.ReadLicence;
import com.orient.login.license.ValidateLicence;

/**
 * @author author:WangShan
 * @version Date&Time:2016年4月23日 上午10:44:16 Class description:
 */
public class Check {
	/**
	 *
	 * @param licensePath
	 * 			license路径
	 * @param IPAddress
	 * 			访问的ip地址
	 * @param loginUserNum
	 * 			访问SessionListener.getLoginUser().size()的人数
	 * @param errorInfo
	 * 			信息
	 * @return
	 * @throws Exception
	 */

	public static boolean checkLicense(String licensePath, String IPAddress,
									   int loginUserNum, StringBuffer errorInfo) throws Exception {
		Map<String, Object> localInfo = null;
		Map<String, Object> licenseInfo = ReadLicence.read(licensePath);
		String message;
		GetSystemInfo getSystemInfo = new GetSystemInfo();
		localInfo = getSystemInfo.getSystemInfo();
		localInfo.put("sysnum", String.valueOf(loginUserNum));
		localInfo.put("userIP", IPAddress);
		message = ValidateLicence.validate(licenseInfo, localInfo);
		if ("success".equals(message)) {
			return true;
		} else {
			errorInfo.append(message);
			return false;
		}
	}
}
