package test;

import org.slf4j.Logger;

import com.hrhih.utils.MyLog;

public class LogDemo {
	
	private static Logger log = MyLog.get();
	
	public static void main(String[] args) {
		
		//第一种使用方法（效率低）
		MyLog.debug("我是一条debug消息");
		
		//第二种使用方法
		MyLog.debug(log, "我是一条debug消息 {} {}", "参数1", "参数2");
		
		MyLog.info(log, "我是一条info消息 {} {}", "参数1", "参数2");
		
		RuntimeException e = new RuntimeException("错误");
		
		//第一种使用方法（效率低）
		MyLog.error("我是一条error消息");
		
		//第二种使用方法
		MyLog.error(log, e, "<-异常对象放前面, 我是一条带参数的error消息 {} {}", "参数1", "参数2");
		
		MyLog.error(log, e, "<-异常对象放前面, 我是一条带参数的error消息！");
		
		MyLog.error(log, e);
	}
}