<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>单文件上传，显示进度条实例</title>
	<style type="text/css">
	<!--
	body, td, th {
	    font-size: 9pt;
	}
	-->
	</style>
	<!--参考：http://api.jqueryui.com/progressbar/-->
	<link rel="stylesheet" href="css/themes/base/jquery.ui.all.css">
	
	<script src="js/jquery-1.10.2.js"></script>
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.progressbar.js"></script>
	<script type="text/javascript">
	
		$(document).ready(function(){
		    $('#uploadFile1').change(function(){
		    	file_change();  
		    });
		    
		    $('#uploadFile1').keydown(function(){
		    	f();  
		    });
		})
	
	    // 下面这三个函数是生成与刷新进度条、进度详细信息的
	    // 初始化进度条
	    $(function() {
	        $("#progressbar").progressbar({
	            value: 0
	        });
	    });
	    
	    // 调用查询进度信息接口
	    function refreshProcessBar(){
	        $.get("state_resume.action", function(data){
	            refreshProcessBarCallBack(data);
	        }, 'xml');
	    }
	    
	    // 查询进度信息接口回调函数
	    function refreshProcessBarCallBack(returnXMLParam){
	        var returnXML = returnXMLParam;
	        var percent = $(returnXML).find('percent').text();
	        var showText = "进度是：" + percent + "%";
	        showText = showText + "\n当前上传文件大小为：" + $(returnXML).find ('uploadByte').text();
	        showText = showText + "\n上传文件总大小为：" + $(returnXML).find ('fileSizeByte').text();
	        showText = showText + "\n当前上传文件为第：" + $(returnXML).find ('fileIndex').text() + "个";
	        showText = showText + "\n开始上传时间：" + $(returnXML).find ('startTime').text();
	        
	        // 刷新进度详细信息
	        $('#progressDetail').empty();
	        $('#progressDetail').text(showText);
	        
	        // 刷新进度条
	       $("#progressbar").progressbar("option", "value", parseInt(percent));
	        
	        //$('#progressbar').progressbar("setValue",percent);
	        setTimeout("refreshProcessBar()", 200);
	    }
	    
	    // 下面这三个函数是控制添加、删除、修改附件的（允许增加、删除附件，只允许指定后缀的文件被选择等）
	    var a = 0;
	    function file_change(){
	        //当文本域中的值改变时触发此方法
	        var postfix = ($("#uploadFile1").val()+'').substring(($("#uploadFile1").val()+'').lastIndexOf(".") + 1).toLocaleLowerCase();
	        
	        //判断扩展是否合法
	        if (!(postfix == "doc" || postfix == "docx" || postfix == "pdf" || postfix == "txt" || postfix == "exe")){
	            //如果不合法就删除相应的File表单及br标签
	            alert("您上传的文件类型不被支持，本系统只支持doc,docx,pdf,txt,exe文件！");
	            $("#uploadFile1").val("");
	            return false;
	        }
	    }
	    
	    function f(){
	        //方法名为f的主要作用是不允许在File表单域中手动输入文件名，必须单击“浏览”按钮
	        return false;
	    }
	    
	    // 提交表单，提交时触发刷新进度条函数
	    function submitForm(){
	        setTimeout("refreshProcessBar()", 200);
	        return true;
	    }
	</script>
	<link rel="stylesheet" href="css/themes/base/demos.css">
</head>
<body>
    <br/>
    <s:form  id="resumeForm" action="upload_resume" method="post" enctype="multipart/form-data" onsubmit="return submitForm()">
        <table width="818" border="1">
            <tr>
                <td width="176">
                    <div align="center">
                        用户账号
                    </div>
                </td>
                <td width="626">
                    <input type="text" name="fileUploadTools.username" />
                </td>
            </tr>
            <tr>
                <td>
                    <div align="center">
                        简历选择
                    </div>
                </td>
                <td id="fileForm">
                    <input size="55" name="fileUploadTools.uploadFile" id="uploadFile1" type="file" />
                </td>
            </tr>
        </table>
        <input type="submit" value="开始上传..." />
    </s:form>
    <br/>
    <div id="progressbar" style="width:500"></div>
    <br/>
    <div id="progressDetail" class="demo-description">
    <p>进度详细信息显示于此......</p>
    </div>
</body>
</html>