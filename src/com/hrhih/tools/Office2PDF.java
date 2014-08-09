package com.hrhih.tools;
//http://titanseason.iteye.com/blog/1471606
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.hrhih.utils.PropertyUtil;

/**
 * 将Office文档转换为PDF文档
 * 
 * @author yeshuqiang
 * 
 */
public class Office2PDF {

	/**
	 * 环境变量下面的OpenOffice_HOME的绝对路径
	 */
	private static final String OpenOffice_HOME = PropertyUtil.getValue("/tools.properties", "OpenOffice_HOME");
	
//	private static final String TOOLS_PATH = Thread.currentThread()
//			.getContextClassLoader().getResource("").getPath()
//			.replace("%20", " ")
//			+ "url.properties";

	/**
	 * 将Office文档转换为PDF. 运行该函数需要用到OpenOffice, OpenOffice下载地址为
	 * http://www.openoffice.org/
	 * 
	 * <pre>
	 * 方法示例:
	 * String sourcePath = "F:\\office\\source.doc";
	 * String destFile = "F:\\pdf\\dest.pdf";
	 * Converter.office2PDF(sourcePath, destFile);
	 * </pre>
	 * 
	 * @param sourceFile
	 *            源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc,
	 *            .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc
	 * @param destFile
	 *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf
	 * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或url.properties配置错误; 如果返回 0,
	 *         则表示操作成功; 返回1, 则表示转换失败，返回2，表示文件类型是PDF,不需要转换。
	 */
	public static int office2PDF(File inputFile, File outputFile) {
		
		if(inputFile.getName().toLowerCase().endsWith(".pdf")){
			outputFile=inputFile;
			return 2;//文件类型是PDF,不需要转换。
		}
		
		System.out.println("office2PDF=======start");
		
		try {
			if (!inputFile.exists()) {
				return -1;// 找不到源文件, 则返回-1
			}

			// 如果目标路径不存在, 则新建该路径
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}

			/*
			 * 从url.properties文件中读取OpenOffice的安装根目录, OpenOffice_HOME对应的键值.
			 * 我的OpenOffice是安装在D:\Program Files\OpenOffice.org 3下面的, 如果大家的
			 * OpenOffice不是安装的这个目录下面，需要修改url.properties文件中的 OpenOffice_HOME的键值.
			 * 但是需要注意的是：要用"\\"代替"\",用"\:"代替":" . 如果大家嫌麻烦,
			 * 可以直接给OpenOffice_HOME变量赋值为自己OpenOffice的安装目录
			 */
//			Properties prop = new Properties();
//			FileInputStream fis = null;
//			fis = new FileInputStream(TOOLS_PATH);// 属性文件输入流
//			prop.load(fis);// 将属性文件流装载到Properties对象中
//			fis.close();// 关闭流

//			String OpenOffice_HOME = prop.getProperty("OpenOffice_HOME");
//			if (OpenOffice_HOME == null)
//				return -1;
//			// 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'
//			if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
//				OpenOffice_HOME += "\\";
//			}
			// 启动OpenOffice的服务
			String command = OpenOffice_HOME
					+ "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
//			System.out.println(command);
			Process pro = Runtime.getRuntime().exec(command);
			// connect to an OpenOffice.org instance running on port 8100
			OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
			connection.connect();

			// convert
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputFile, outputFile);

			// close the connection
			connection.disconnect();
			// 关闭OpenOffice服务的进程
			pro.destroy();

			return 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 1;
	}
	
	public static int office2PDF(String sourceFile, String destFile) {
		File inputFile = new File(sourceFile);
		File outputFile = new File(destFile);
		return office2PDF(inputFile,outputFile);
	}
	
	
}
