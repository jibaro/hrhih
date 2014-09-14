<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@taglib uri="/struts-tags" prefix="s"%>
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
        <script src="/js/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="/test/index.js"></script>
    </head>

    <body>
        <!-- 显示User实体对象 -->
        <div id="result"></div>
        <s:form name="userForm" action="test_jsonlist.action" namespace="/utils_test" method="post">
            用户名：<input name="username"/><br/>
            年纪：<input name="age"/><br/>
            邮箱：<input name="email"/><br/><br/>
            <input id="btn" type="button" value=" 提 交 "/>
        </s:form>

    </body>
</html>