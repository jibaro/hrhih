<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>jobhunter 'login' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<%=basePath%>js/jquery-1.10.2.js"></script>
	<script type="text/javascript">
		$(function () {  
		  	//点击图片更换验证码
	    	$("#Verify").click(function(){
		        $(this).attr("src","<%=basePath%>/utils/imagecode.action?timestamp="+new Date().getTime());
		    });
		 });
	</script>
  </head>
  
  <body>
      <form action="login_jobhunter.action" method="post">
		  User:<input type="text" name="loginstr"><br>
		  Passoword:<input type="password" name="passwd"><br>
		  VerifyCode:<input type="text" name="securitycode"><img src="<%=basePath%>/utils/imagecode.action" id="Verify" style="cursor:pointer;" alt="看不清，换一张"/>
		  <input type="submit" value="submit">
	  </form>
	  <a href="<%=basePath%>user/jhregister.jsp">注册</a>
  </body>
</html>
