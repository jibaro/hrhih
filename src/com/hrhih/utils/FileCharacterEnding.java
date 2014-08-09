package com.hrhih.utils;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

/**
 * 获取文件编码的方式
 * 
 * @author ADMIN
 * 
 */
public class FileCharacterEnding {

	public synchronized static String getFileCharacterEnding(String filePath) {

		File file = new File(filePath);

		return getFileCharacterEnding(file);
	}

	public synchronized static String getFileCharacterEnding(File file) {
		
		String fileCharacterEnding = "UTF-8";
		
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(JChardetFacade.getInstance());
		Charset charset = null;
		try {
			
			charset = detector.detectCodepage(file.toURL());
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		if (charset != null) {
			fileCharacterEnding = charset.name();
		}
		
		// System.out.println(fileCharacterEnding);
		
		return fileCharacterEnding;
		
	}

	/**
	 * 根据文件得到该文件中文本内容的编码
	 * 
	 * @param filePath 要分析的文件的绝对路径 
	 */
	public synchronized static String getCharset(String filePath) {
		try {
			File file = new File(filePath);
			FileInputStream fis=new FileInputStream(file);
			return getCharset(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据文件得到该文件中文本内容的编码
	 * 
	 * @param file 要分析的文件
	 */
	public synchronized static String getCharset(File file) {
		try {
			FileInputStream fis=new FileInputStream(file);
			return getCharset(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据文件得到该文件中文本内容的编码
	 * 
	 * @param bis 要分析的文件的输入流
	 */
	public synchronized static String getCharset(FileInputStream fis) {
		
		BufferedInputStream bis=new BufferedInputStream(fis);
		
		String charset = "GBK"; // 默认编码
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1)
				return charset;
			if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8";
				checked = true;
			}
			bis.reset();
			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 0xF0)
						break;
					// 单独出现BF以下的，也算是GBK
					if (0x80 <= read && read <= 0xBF)
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
							// (0x80 - 0xBF),也可能在GB编码内
							continue;
						else
							break;
						// 也有可能出错，但是几率较小
					} else if (0xE0 <= read && read <= 0xEF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
//				System.out.println(loc + " " + Integer.toHexString(read));
			}
//			bis.close();//由于FileManager需要再调用一次。所以不关闭。yesq 20140628
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charset;
	}
	
	public static void main(String[] args) {

		String filePath = "D:/test/hrhih/text.txt";
		
		String type = FileCharacterEnding.getCharset(filePath);

		System.out.println(type);

	}

}
