package com.orient.sysman.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class RestoreCommandUtil{
     
	private String restoreBatPath;
	
	public RestoreCommandUtil(String restoreBatPath){
		this.restoreBatPath = restoreBatPath;
	}
	
	public void execRestore(){
		Map<String, String> evens = System.getenv();
		Iterator<String> iter = evens.keySet().iterator();
		List<String> evenList = new LinkedList<String>();
		while(iter.hasNext())
		{
			String key = iter.next();
			evenList.add(key+"="+evens.get(key));
		}
		Process process;
		try {
			process = Runtime.getRuntime().exec(restoreBatPath, evenList.toArray(new String[0]));
			new Thread(new ThreadUtil(process.getInputStream())).start();
			new Thread(new ThreadUtil(process.getErrorStream())).start();
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private class ThreadUtil implements Runnable
	{
		private InputStream is;
		
		public ThreadUtil(InputStream is) {
			this.is = is;
		}
		@Override
		public void run() {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(is, "gbk"));
				String lineStr = null;
				
				while((lineStr = br.readLine())!=null)
				{
					System.out.println(lineStr);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(br!=null)
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}

	/** 
     * 测试方法 
     */ 
    public static void main(String args[]){
    	
    } 
}
