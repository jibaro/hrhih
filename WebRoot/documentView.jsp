<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("basePath==="+basePath);
String swfFilePath=basePath+"SWFresumers/word2007.swf";

System.out.println("swfFilePath==="+swfFilePath);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/flexpaper.js"></script>
		<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
<style type="text/css" media="screen"> 
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; }
			#flashContent { display:none; }
        </style> 

<title>文档在线预览系统</title>
</head>
<body> 
        <div style="position:absolute;left:10px;top:10px;">
<div id="documentViewer" class="flexpaper_viewer" style="width:770px;height:500px"></div>

<script type="text/javascript">

    $('#documentViewer').FlexPaperViewer(
            { config : {

                SWFFile : '<%=swfFilePath%>',

                Scale : 0.6,
                ZoomTransition : 'easeOut',
                ZoomTime : 0.5,
                ZoomInterval : 0.2,
                FitPageOnLoad : true,
                FitWidthOnLoad : false,
                FullScreenAsMaxWindow : false,
                ProgressiveLoading : false,
                MinZoomSize : 0.2,
                MaxZoomSize : 5,
                SearchMatchAll : false,
                InitViewMode : 'Portrait',
                RenderingOrder : 'flash',
                StartAtPage : '',

                ViewModeToolsVisible : true,
                ZoomToolsVisible : true,
                NavToolsVisible : true,
                CursorToolsVisible : true,
                SearchToolsVisible : true,
                WMode : 'window',
                localeChain: 'zh_CN'
            }}
    );
</script>
<div style="width:760px;margin-top:10px;padding-left:10px; padding-top:10px; padding-bottom:10px; font-family:Verdana;font-size:10pt;background-color:#EFEFEF; border:1px solid #999;-webkit-box-shadow: rgba(0, 0, 0, 0.246094) 0px 2px 4px 0px;font-family:'District Thin', helvetica, arial;font-weight:lighter;"><font style="font-size:15px;font-weight:bold">Notice</font><br/>You are viewing a document in FlexPaper using Adobe Flash. Consider purchasing a commercial license with support for <a href="http://flexpaper.devaldi.com/download.jsp?ref=FlexPaper">Adaptive UI </a> to maximize your browser coverage and reach devices such as the Apple iPad. <br/><br/>With <a href="http://flexpaper.devaldi.com/download.jsp?ref=FlexPaper">AdaptiveUI enabled</a>, the viewer adjust automatically to the visitors capabilities and supports rendering documents as flash, html4 and html5. The viewer gracefully degrades between all formats.<br/><br/>For more information on browser coverage please <a href="http://flexpaper.devaldi.com/docs_html_flash_html5.jsp?ref=FlexPaper">see our documentation</a>.</div>
</div>
</body>
</html>