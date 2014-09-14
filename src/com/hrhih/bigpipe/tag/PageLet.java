package com.hrhih.bigpipe.tag;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "pageLet", tldTagClass = "com.hrhih.bigpipe.tag.PageLetTag", description = "PageLet Tag")
public class PageLet extends Component {

	private String name;
	private String dealClass;
	private String threadClass;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDealClass(String dealClass) {
		this.dealClass = dealClass;
	}

	public String getDealClass() {
		return dealClass;
	}

	public String getThreadClass() {
		return threadClass;
	}

	public void setThreadClass(String threadClass) {
		this.threadClass = threadClass;
	}

	public PageLet(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack);
		this.request = request;
		this.response = response;
	}

	public boolean start(final Writer writer) {
		boolean result = super.start(writer);
		try {
			writer.write("<div id='"+name+"'>");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean end(final Writer writer, String body) {
		boolean end = super.end(writer, body);
		try {
			writer.write("</div>");
			writer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			//以下经过yesq更改，了解原来代码，请看BigPipeImpl.zip
			Class[] parameter = new Class[]{HttpServletRequest.class,HttpServletResponse.class,String.class
											,String.class,ValueStack.class,Writer.class};
			Constructor cons = Class.forName(threadClass).getConstructor(parameter);
			Object[] args= new Object[] { request,response,dealClass,name,getStack(),writer };
			Runnable exe = (Runnable) cons.newInstance(args);
			Thread t=new Thread(exe);
			t.start();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return end;
	}
}
