package com.hrhih.bigpipe.test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.concurrent.CountDownLatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hrhih.bigpipe.dealer.IPageLetDealer;
import com.hrhih.bigpipe.tag.MultiThreadTag;
import com.opensymphony.xwork2.util.ValueStack;

public class MutliThreadExe implements Runnable{
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String dealClass;
	private String name;
	private ValueStack stack;
	private Writer writer;
	
	public MutliThreadExe(){}
	
	public MutliThreadExe(HttpServletRequest request,HttpServletResponse response,String dealClass,
						String name,ValueStack stack,Writer writer){
		this.request=request;
		this.response=response;
		this.dealClass=dealClass;
		this.name=name;
		this.stack=stack;
		this.writer=writer;
	}
	
	@Override
	public void run() {
		CountDownLatch countDown = (CountDownLatch)request.getAttribute(MultiThreadTag.COUNT_DOWN);
		try
		{
			if (null != dealClass && !"".equals(dealClass))
			{
				IPageLetDealer pld = (IPageLetDealer)Class.forName(dealClass).newInstance();
				PageAndModel<String, Object> deal = pld.deal(stack, request, response);//生成页面模型（包含页面的数据）。
				StringWriter sw = new StringWriter();
				//这里用FreeMarker处理页面显示，也可以用servlet/jsp。
				FreeMarkerInstance.instance(request).getConfiguration()
						.getTemplate(deal.getPage(),"utf-8")
						.process(deal.getModel(), sw);
				writer.write("<script type='text/javascript'>ii('"+name+"','"+sw.getBuffer().toString()+"');</script>");
			}
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
				countDown.countDown();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
