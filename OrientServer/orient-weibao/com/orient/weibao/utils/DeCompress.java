/**
 * Copyright 2017 Iflytek, Inc. All rights reserved.
 */
package com.orient.weibao.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeCompress {

	private static Logger logger = LoggerFactory.getLogger(DeCompress.class);

	/**
	 * 解压缩入口
	 * <p>
	 * <code>DecompressUt il</code>
	 * </p>
	 *
	 *
	 * @param sourcesFile
	 *            目标压缩文件
	 * @param saveDir
	 *            要解压到的位置
	 */
	public static void DecompressUtil(String sourcesFile, String saveDir,String encoding) {
		// 判断sourcesFile是否是以 "\" 或 "/" 结尾的
		// 取得最后一位的字符
		char lastChar = saveDir.charAt(saveDir.length() - 1);
		if (lastChar != '\\' && lastChar != '/') {
			// 添加一个"/"或"\"字符
			saveDir += File.separator;
		}
		// 获取要解压文件的类型，是zip格式还是rar格式。不需要到substring(,sourcesFile.length());这样，默认到length
		String type = sourcesFile.substring(sourcesFile.lastIndexOf(".") + 1);
		if ("zip".equals(type)) {
			logger.info("本次解压的是zip格式的文件！");
			unZip(sourcesFile, saveDir,encoding);
		} else if ("rar".equals(type)) {
			logger.info("本次解压的是rar格式的文件！");
			unRar(sourcesFile, saveDir,encoding);
		} else {
			logger.info("只支持解压zip和rar格式的文件！");
		}
	}

	/**
	 * 解压zip格式的压缩文件
	 * <p>
	 * <code>unZip</code>
	 * </p>
	 *
	 *
	 * @param sourcesFile
	 *            目标压缩文件
	 * @param saveDir
	 *            要解压到的位置
	 */
	public static void unZip(String sourcesFile, String saveDir,String encoding) {
//		String encoding = WeibaoPropertyUtil.getPropertyValueConfigured("zip.encoding","config.properties","UTF-8");
		logger.info("正在解压........");
		Project project = new Project();
		Expand expand = new Expand();
		expand.setProject(project);
		expand.setSrc(new File(sourcesFile)); // 待解压文件的路径
		expand.setOverwrite(false); // 是否重写
		expand.setDest(new File(saveDir)); // 解压后存放文件的路径
		expand.setEncoding(encoding); // 设置编码格式
		expand.execute(); // 启动解压
		logger.info("其中的文件有：：：");
		showFile(saveDir); // 显示文件doc中所有的文件
	}

	public static void showFile(String path) {
		File file = new File(path);
		for (int i = 0; i < file.listFiles().length; i++) {
			if (file.listFiles()[i].isFile()) {
				logger.info(file.listFiles()[i].toString());
			} else {
				showFile(file.listFiles()[i].toString());
			}
		}
	}

	/**
	 * 解压rar格式的压缩文件
	 * <p>
	 * <code>unRar</code>
	 * </p>
	 *
	 *
	 * @param sourcesFile
	 *            目标压缩文件
	 * @param saveDir
	 *            要解压到的位置
	 */
	public static int unRar(String sourcesFile, String saveDir,String encoding) {
		logger.info("正在解压........");
		int status=0;
		Archive archive = null;
		FileOutputStream fileOutputStream = null;
		try {
			archive = new Archive(new File(sourcesFile));
			FileHeader fileHander = archive.nextFileHeader();
			while (fileHander != null) {
				// 不是文件夹
				if (!fileHander.isDirectory()) {
					// 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
					String compressFileName = "";
					if (fileHander.isUnicode()) {
						// Unicode文件名使用getFileNameW
						compressFileName = fileHander.getFileNameW().trim();
					} else {
						compressFileName = fileHander.getFileNameString().trim();
					}
					String destFileName = "";
					String destDirName = "";
					// 非windows系统
					if (File.separator.equals("/")) {
						destFileName = saveDir + compressFileName.replaceAll("\\\\", "/");
						destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));
					} else {
						// windows系统
						destFileName = saveDir + compressFileName.replaceAll("/", "\\\\");
						destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\"));
					}
					// 2创建文件夹
					File file = new File(destDirName);
					if (!file.exists() || !file.isDirectory()) {
						try {
							file.mkdirs();
						} catch (Exception e) {
							logger.info("创建文件的保存路径的异常::");
							e.printStackTrace();
						}
					}
					// 3解压缩文件
					fileOutputStream = new FileOutputStream(new File(destFileName));
					archive.extractFile(fileHander, fileOutputStream);
					fileOutputStream.close();
					fileOutputStream = null;
				}
				fileHander = archive.nextFileHeader();
			}
			archive.close();
			archive = null;
			logger.info("其中的文件有：：：");
			status=1;
			showFile(saveDir);
		} catch (RarException e) {
			status=0;
			e.printStackTrace();
			status=0;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
					fileOutputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (archive != null) {
				try {
					archive.close();
					archive = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return  status;
	}

	/**
	 * 删除文件
	 * <p>
	 * <code>deleteFolder</code>
	 * </p>
	 *
	 *
	 * @param path
	 * @return
	 */
	public static void deleteFolder(String path) {
		// 删除完里面所有内容
		deleteDirectory(path);
		File myFilePath = new File(path.toString());
		// 删除空文件夹
		myFilePath.delete();
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * <p>
	 * <code>deleteDirectory</code>
	 * </p>
	 *
	 *
	 * @param path
	 * @return
	 */
	public static boolean deleteDirectory(String path) {
		boolean flag = false;
		File file = new File(path);
		// 判断文件是否存在或者是否是目录
		if (!file.exists() || !file.isDirectory()) {
			return flag;
		}
		// 获取所有文件的路径
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			// 判断是否是以"\"或"/"结尾的..
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				// 先删除文件夹里面的文件
				deleteDirectory(path + "/" + tempList[i]);
				// 再删除空文件夹
				deleteFolder(path + "/" + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 程序启动入口
	 * <p>
	 * <code>main</code>
	 * </p>
	 *
	 *
	 * @param args
	 */
	public static void main(String[] args) {
/*		*//**
		 * 基础路径
		 *//*
		String dir = "D:\\rar\\";
		String[] urls = new String[1];
		urls[0] = "D:\\rar\\Desktop.zip";
		logger.info("解压缩开始！");
		//deleteFolder(dir);// 删除解压之前的文件中的内容
		for (int i = 0; i < urls.length; i++) {
			logger.info("这是：：：" + (urls[i].split("\\.")[0]) + "：：：文件！");
			DecompressUtil(urls[i], "D:\\rar\\save");
		}
		logger.info("解压缩结束！");*/
		//DeCompress.deleteDirectory("D:\\data\\orient\\temp\\");
		//DeCompress.deleteFolder("D:\\data\\orient\\temp");

	}
}