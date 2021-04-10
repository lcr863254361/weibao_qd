package com.orient.login.license;

import com.orient.utils.CommonTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author author:WangShan
 * @version Date&Time:2016年4月23日 上午10:44:16 Class description:
 */
public class ValidateLicence {
	public static String delim = "\\.";

	/**
	 * 验证License信息是否与本地信息匹配;
	 *
	 * @param licenseInfo
	 * @param localInfo
	 * @return
	 */
	public static String validate(Map licenseInfo, Map localInfo) {
		String message = "success";
		String mac_license = CommonTools.null2String((String) licenseInfo
				.get("macAddress"));
		int num_license = 0;
		if (licenseInfo.get("webUserNum") != null) {
			num_license = Integer.parseInt((String) licenseInfo
					.get("webUserNum"));
		} /*
		 * else { num_license = Integer.parseInt((String)
		 * licenseInfo.get("TDMUserNum")); }
		 */

		// String product_license =
		// CommonTools.null2String((String)license.get("product"));
		String date_license = CommonTools.null2String((String) licenseInfo
				.get("endDate"));
		// String ip_local =
		// CommonTools.null2String((String)localInfo.get("sysip"));
		String startIPFilter_license = CommonTools
				.null2String((String) licenseInfo.get("startIPFilter"));
		String endIPFilter_license = CommonTools
				.null2String((String) licenseInfo.get("endIPFilter"));

		String userIP = CommonTools.null2String((String) localInfo
				.get("userIP"));

		List<String> blackIPs_license = null;
		if (licenseInfo.get("iPAddress") != "null") {
			blackIPs_license = (List<String>) licenseInfo.get("iPAddress");
		}
		/*
		 * String blackIPs = CommonTools.null2String((String) licenseInfo
		 * .get("blackIPs"));
		 */

		List<String> mac_local = (List<String>) localInfo.get("sysmacaddress");

		int num_local = Integer.parseInt((String) localInfo.get("sysnum"));
		// String produce_loacl =
		// CommonTools.null2String((String)localInfo.get("sysproduct"));
		String date_local = CommonTools.null2String((String) localInfo
				.get("systime"));
		if (!mac_local.contains(mac_license)) {
			message = "License验证无效,请更新License!!!";
		} else if (num_local >= num_license) {
			message = "服务器登录人数已满,请稍候登陆!!!";
		} else if (compareDate(date_license, date_local)) {
			message = "授权日期已过期,请更新License!!!";
		} else if (!startIPFilter_license.equals("")
				&& !endIPFilter_license.equals("")
				&& !"127.0.0.1".equals(userIP)
				&& !compareIP(startIPFilter_license, endIPFilter_license,
				userIP)) {
			message = "IP地址无效，无法访问系统!!!";
		} else if (!(blackIPs_license == null
				|| compareBlackIP(blackIPs_license, userIP))) {

			message = "IP地址被拉入黑名单，无法访问系统!!!";
		} else {
			message = "success";
		}
		return message;
	}

	private static boolean compareDate(String date_license, String date_local) {
		boolean flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dateInLicense = sdf.parse(date_license);
			Date dateInLocal = sdf.parse(date_local);
			if (dateInLocal.compareTo(dateInLicense) == 1) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean compareIP(String startIP, String endIP, String userIP) {
		String[] strIp = userIP.split(delim);
		userIP = "";
		for (int i = 0; i < strIp.length; i++) {
			userIP = userIP + getZeroString(strIp[i], 3);
		}
		int equalFlag = 0;
		String strIp1 = "";
		String strIp2 = "";
		if (startIP.equals(endIP)) {
			String[] strIpBegin = startIP.split(delim);
			for (int j = 0; j < strIpBegin.length; j++) {
				strIp1 = strIp1 + getZeroString(strIpBegin[j], 3);
			}
			if (userIP.compareTo(strIp1) == 0) {
				equalFlag = 1;
			}
		} else {
			String[] strIpBegin = startIP.split(delim);
			String[] strIpEnd = endIP.split(delim);
			for (int j = 0; j < strIpBegin.length; j++) {
				strIp1 = strIp1 + getZeroString(strIpBegin[j], 3);
				strIp2 = strIp2 + getZeroString(strIpEnd[j], 3);
			}
			if (userIP.compareTo(strIp1) >= 0 && userIP.compareTo(strIp2) <= 0) {
				equalFlag = 1;
			}
		}
		if (equalFlag == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean compareBlackIP(List<String> blackIPs_license,
										 String userIP) {
		for (String bIP : blackIPs_license) {
			if (userIP.equals(bIP)) {
				return false;
			}
		}
		return true;
	}

	public static String getZeroString(String str, int cnt) {
		String strZeroS = "";
		if (str.trim().length() >= cnt) {
			return str;
		}
		int intStrCnt = cnt - str.length();
		for (int i = 0; i < intStrCnt; i++) {
			strZeroS = strZeroS + "0";
		}
		strZeroS = strZeroS + str;
		return strZeroS;
	}
}
