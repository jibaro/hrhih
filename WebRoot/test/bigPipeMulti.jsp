<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="b" uri="/WEB-INF/bigpipe.tld"%>

<%long pstart = System.currentTimeMillis();%>

<b:multiThread pageLetNum="6" bigPipeJSPath="/test/js/bigpipeMulti.js">
	<table border="1" width="100%" height="500">
		<caption>多线程例子</caption>
		<tr>
			<td><b:pageLet threadClass="com.hrhih.bigpipe.test.MutliThreadExe" dealClass="com.hrhih.bigpipe.test.Index1" name="index1">
		    	编号：1<img src="/test/images/loading.gif"/>
		    </b:pageLet></td>
			<td><b:pageLet threadClass="com.hrhih.bigpipe.test.MutliThreadExe" dealClass="com.hrhih.bigpipe.test.Index2" name="index2">
		    	编号：2<img src="/test/images/loading.gif"/>
		    </b:pageLet></td>
			<td><b:pageLet threadClass="com.hrhih.bigpipe.test.MutliThreadExe" dealClass="com.hrhih.bigpipe.test.Index3" name="index3">
		    	编号：3<img src="/test/images/loading.gif"/>
		    </b:pageLet></td>
		</tr>
		<tr>
			<td><b:pageLet threadClass="com.hrhih.bigpipe.test.MutliThreadExe" dealClass="com.hrhih.bigpipe.test.Index4" name="index4">
		    	编号：4<img src="/test/images/loading.gif"/>
		    </b:pageLet></td>
			<td><b:pageLet threadClass="com.hrhih.bigpipe.test.MutliThreadExe" dealClass="com.hrhih.bigpipe.test.Index5" name="index5">
		    	编号：5<img src="/test/images/loading.gif"/>
		    </b:pageLet></td>
			<td><b:pageLet threadClass="com.hrhih.bigpipe.test.MutliThreadExe" dealClass="com.hrhih.bigpipe.test.Index6" name="index6">
		    	编号：6<img src="/test/images/loading.gif"/>
		    </b:pageLet></td>
		</tr>
	</table>
</b:multiThread>

<%long secs = System.currentTimeMillis() - pstart;%>
整个页面加载耗费了：<%=secs%>毫秒