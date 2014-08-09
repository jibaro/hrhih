<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>jobhunter 'register' starting page</title>
    
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
		// 提交表单，提交注册判断参数函数
	    function registerForm(){
	    	//注册参数判断
	    	
	    	return true;
	    }
	</script>
  </head>
  
  <body>
    <s:form  id="jhRegisterForm" action="jobhunterregister.action" method="post" onsubmit="return registerForm()">
		  username:<input type="text" name="jobhunter.username"><br>
		  mobile:<input type="text" name="jobhunter.mobile"><br>
		  email:<input type="text" name="jobhunter.email"><br>
		  passoword:<input type="password" name="jobhunter.passwd"><br>
		  <!-- 如果为QQ或微博登陆的话，不需要跳转到这个页面，只需要设置relatecounter和regtype， -->
		  <input type="submit" value="submit">
	</s:form>
	 <a href="<%=basePath%>user/jhlogin.jsp">登陆</a>
  </body>
</html>
