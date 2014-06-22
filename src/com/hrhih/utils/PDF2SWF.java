package com.hrhih.utils;
//http://nopainnogain.iteye.com/blog/825176
//http://titanseason.iteye.com/blog/1472733
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

public class PDF2SWF {

	private static Logger log = MyLog.get();

	/**
	 * 环境变量下面的OpenOffice_HOME的绝对路径
	 */
	private static final String SWFTOOLS_HOME = PropertyUtil.getValue("/tools.properties", "SWFTOOLS_HOME");
	
	
	//注意destPath最后必须带文件分隔符
		//这个用来转换的swfTool工具，一但待转换的文件有不可识别的字符在里面时，它就会堵塞在那里。这个情况要处理掉
		public static String converse(String sourcePath, String destPath) throws Exception {
			
			// 源文件不存在则返回
			File source = new File(sourcePath);
			
			if (!source.exists()) {
				log.info("pdf转换swf失败，源文件不存在!");
				throw new Exception();
			}

			//因为下面进行系统调用，这样就会把系统执行的操作新开启一个线程（在此linux也叫进程），
			//所以它和主扫描程序是独立运行，所以下次还会扫描这个转换中的文件，所以这里要将它设置为不可读，
			
//			source.setReadable(false);
			
			List<String>  command = new	ArrayList<String>();
			command.add(SWFTOOLS_HOME+"pdf2swf.exe");//从配置文件里读取
			command.add("-z");
//			command.add("-B");
//			command.add("rfxview.swf");
//			command.add("-s");
//			command.add("flashversion=13");
			
			//加入-s poly2bitmap的目的是为了防止出现大文件或图形过多的文件转换时的出错，没有生成swf文件的异常。
			//这个参数会影响效率。http://nopainnogain.iteye.com/blog/824450
			command.add("-s");
			command.add("poly2bitmap");
			
			//windows平台下
//			command.add("languagedir=C:/xpdf/chinese-simplified/");
			command.add(sourcePath);
			command.add("-o");
			command.add(destPath);
			command.add("-T");
			command.add("9");
			command.add("-f");
			
			
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command(command);
			Process process = processBuilder.start();
			
//			dealWith(process);//改用下面的方式来处理：
			InputStreamWathThread inputWathThread = new InputStreamWathThread(process);
			inputWathThread.start();
			ErrorInputStreamWathThread errorInputWathThread = new ErrorInputStreamWathThread(process);
			errorInputWathThread.start();
			
			try {
				process.waitFor();//等待子进程的结束，子进程就是系统调用文件转换这个新进程
				inputWathThread.setOver(true);//转换完，停止流的处理
				errorInputWathThread.setOver(true);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			log.debug("转换完成");

			return destPath;
		}
}
