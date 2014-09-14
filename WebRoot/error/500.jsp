<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>程序异常</title>

<style type="text/css">
*{margin:0;padding:0;}
.errTitle{min-width:300px;width:90%;height:50px;line-height:50px;border:1px solid #999999;border-radius:5px;background:#0169B2;text-align:center;word-spacing:10px;font-family:'黑体';font-size:24px;margin:10px 70px;padding: 4px 4px 4px 6px;position:relative;}
.showErrWrap{font-size:10px;position:absolute;right:10px;bottom:-5px;}
.showErrWrap a:link,.showErrWrap a:visited{text-decoration:none;color:black;}
.showErrWrap a:hover{text-decoration:underline;color:yellow;}
.showErrWrap span{margin:0 4px;color:black;}
.errStack{min-width:300px;width:90%;font-family:"Courier New", Courier, monospace;border:0 none;margin:10px 70px;overflow:auto;padding:4px;}
</style>
<script src="/js/jquery-1.10.2.js"></script>

</head>
<body>
<div class="errTitle">程 序 出 现 异 常！
<span class="showErrWrap"><a href="javascript:void(0);" id="showErrBtn">查看详情</a><span>|</span><a href="javascript:void(0);" onclick="javascript:history.go(-1);">返回上一页</a></span></div>
<div class="errStack">
<pre>
<s:property value="exceptionStack"/><!-- 异常信息 -->
</pre>
</div>
</body>
</html>