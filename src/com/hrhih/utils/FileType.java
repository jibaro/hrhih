package com.hrhih.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 判断 文件类型
 *
 */
public class FileType {

	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	private FileType() {
	}

	static {
		getAllFileType(); // 初始化文件类型信息
	}

	/**
	 * Discription:[getAllFileType,常见文件头信息]
	 */
	private static void getAllFileType() {
		FILE_TYPE_MAP.put("d0cf11e0a1b11a", "doc"); // MS Excel， 注意：word、msi 和  excel的文件头一样
		FILE_TYPE_MAP.put("255044462d312e", "pdf"); // Adobe Acrobat (pdf)
		FILE_TYPE_MAP.put("504b0304140006", "docx");// docx文件
	}

	/**
	 * 得到上传文件的文件头
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 根据制定文件的文件头判断其文件类型
	 * 
	 * @param filePaht
	 * @return
	 */
	public static String getFileType(String filePaht) {
		String res = null;
		try {
			FileInputStream is = new FileInputStream(filePaht);
			byte[] b = new byte[7];
			is.read(b, 0, b.length);
			String fileCode = bytesToHexString(b);

			System.out.println(fileCode);

			// 这种方法在字典的头代码不够位数的时候可以用但是速度相对慢一点
			Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();
			while (keyIter.hasNext()) {
				String key = keyIter.next();
				if (key.toLowerCase().startsWith(fileCode.toLowerCase())
						|| fileCode.toLowerCase().startsWith(key.toLowerCase())) {
					res = FILE_TYPE_MAP.get(key);
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static void main(String[] args) throws Exception {

		String type = getFileType("D:/test/hrhih/RUNNING.txt");
		System.out.println("Type : " + type);

//		type = getFileType("C:/test/350996.wav");
//		System.out.println("350996.wav : " + type);
//		System.out.println();

	}

}
