<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <base href="<%=basePath%>">
	    
	    <title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
		-->
	  </head>
    <body>
        您上传的文件名列表：
        <br/>
        <!--对Action中对象（fileUploadTools）包含的数组（uploadFileFileName）的遍历-->
        <s:iterator value="fileUploadTools.uploadFileFileName" status="st">
            <s:iterator value="fileUploadTools.uploadFileFileName[#st.index]">
                <s:property />
                <br/>
            </s:iterator>
        </s:iterator>
        <br />
    </body>
</html>