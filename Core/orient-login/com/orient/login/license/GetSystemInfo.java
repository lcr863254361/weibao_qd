package com.orient.login.license;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * ��ȡ����ϵͳ��Ϣ;
 * @author wangw070684
 *
 */
public class GetSystemInfo {
	
	InetAddress addr = null;
	
	public GetSystemInfo() {
		try {
		    addr = InetAddress.getLocalHost();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
    
	/**
	 * ��ò���ϵͳ��Ϣ;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  Map<String, Object> getSystemInfo(){
		Map<String, Object> map = new HashMap();
		map.put("sysip", this.getSystemIp());
		map.put("systime", this.getSystemTime());
		map.put("sysmacaddress", this.getSystemMacAddress());
    	return map;
    }
	
	/**
	 * ��ò���ϵͳʱ��;
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getSystemTime(){
		Calendar  cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String day = String.valueOf(cal.get(Calendar.DATE));
		if(day.length() == 1){
			day = "0" + day;
		}
		String time = year + "-" + month + "-" + day;
		return time;
	}
	
	/**
	 * ��ò���ϵͳ���;
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getSystemName(){
		String os = System.getProperty("os.name").toLowerCase().trim();
		return os;
	}
	
	/**
	 * ��ñ��ػ������;
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getLocalMachineName(){
		String name = addr.getHostName().toString();
		return name;
	}
	
	/**
	 * ��ò���ϵͳip;
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getSystemIp(){
        String ip = addr.getHostAddress().toString();//��ñ���IP
		return ip;
	}
	
	/**
	 * ��ò���ϵͳ��mac��ַ;
	 * @return
	 */
	@SuppressWarnings({ "unused", "finally" })
	private List<String> getSystemMacAddress(){
//		String mac = "";
//		String os = this.getSystemName();
//		if (os.indexOf("windows") != -1) {
//			mac = this.getWindowsMacAddress();
//		} else if (os.indexOf("linux") != -1) {
//			mac = this.getLinuxMacAddress();
//		}
//		return mac;
		
		//jdk1.6�����ԣ�ȡ�������ַ
		List<String> address = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
			while (el.hasMoreElements()) {
				byte[] mac = el.nextElement().getHardwareAddress();
				if (mac == null || mac.length <= 0)
					continue;
				StringBuilder builder = new StringBuilder();
				for (byte b : mac) {
					builder.append(hexByte(b));
					builder.append("-");
				}
				builder.deleteCharAt(builder.length() - 1);
				address.add(builder.toString().toUpperCase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return address;
		}
	}
	
	static String hexByte(byte b) {
		String s = "000000" + Integer.toHexString(b);
		return s.substring(s.length() - 2);
	}
	
	/**
	 * ���windows����ϵͳMac��ַ;
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getWindowsMacAddress(){
	    String mac = "";   
        BufferedReader bufferedReader = null;   
        Process process = null;   
        try {   
            process = Runtime.getRuntime().exec("ipconfig /all");// windows�µ������ʾ��Ϣ�а���mac��ַ��Ϣ   
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));   
            
            String line = "";   
            int index = -1;   
            while ((line = bufferedReader.readLine()) != null) {   
                index = line.toLowerCase().indexOf("physical address");// Ѱ�ұ�ʾ�ַ�[physical address]   
                if (index >= 0) {// �ҵ���   
                    index = line.indexOf(":");// Ѱ��":"��λ��   
                    if (index>=0) {   
                        mac = mac + line.substring(index + 1).trim() + ",";//  ȡ��mac��ַ��ȥ��2�߿ո�   
                    }  
                }
            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (bufferedReader != null) {   
                    bufferedReader.close();   
                }   
            } catch (IOException e1) {   
                e1.printStackTrace();   
            }   
            bufferedReader = null;   
            process = null;   
        }   
	    return mac.substring(0, mac.length()-1);
	}
	
	/**
	 * ���linux����ϵͳmac��ַ;
	 * @return
	 */
    @SuppressWarnings("unused")
	private String getLinuxMacAddress(){
    	return null;
    }
    
    public static void main(String[] args){
    	System.out.println(new GetSystemInfo().getSystemInfo());
    }
}
