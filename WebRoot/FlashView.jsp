<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("basePath==="+basePath);
String swfFilePath=basePath+"swfdocument/word2007.swf";

System.out.println("swfFilePath==="+swfFilePath);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>">
    <title>FlexPaper</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
    <style type="text/css" media="screen">
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; }   
			#flashContent { display:none; }
    </style> 
    
    <link rel="stylesheet" type="text/css" href="css/flexpaper_flat.css" />
    <script type="text/javascript" src="js/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="js/jquery.extensions.min.js"></script>
	<script type="text/javascript" src="js/flexpaper.js"></script>
	<script type="text/javascript" src="js/flexpaper_handlers.js"></script>
</head>
<body>
<div id="documentViewer" class="flexpaper_viewer" style="position:absolute;left:10px;top:10px;width:80%;height:80%"></div>
	        
	        <script type="text/javascript">   

			var td = (function(){try {return 'ontouchstart' in document.documentElement;} catch (e) {return false;} })();
	        		if(td){$('#documentViewer').css({left : '0px', top: '0px', width:'100%',height:'95%'});}
		         
	    		jQuery.get((!window.isTouchScreen)?'UI_flexpaper_desktop_flat.jsp':'UI_flexpaper_mobile.jsp',
        	    		function(toolbarData) {
                			$('#documentViewer').FlexPaperViewer(
                        			{ config : {

						 SWFFile : '<%=swfFilePath%>',

						 Scale : 0.6, 
						 ZoomTransition : 'easeOut',
						 ZoomTime : 0, 
						 ZoomInterval : 0.2,
						 FitPageOnLoad : false,
						 FitWidthOnLoad : true, 
						 FullScreenAsMaxWindow : false,
						 ProgressiveLoading : true,
						 MinZoomSize : 0.2,
						 MaxZoomSize : 3,
						 SearchMatchAll : false,
                         Toolbar : toolbarData,
						 InitViewMode : 'Portrait',
						 RenderingOrder : 'flash,html5',
						 
						 ViewModeToolsVisible : true,
						 ZoomToolsVisible : true,
						 NavToolsVisible : true,
						 CursorToolsVisible : true,
						 SearchToolsVisible : true,
  						 localeChain: 'zh_CN'
					}}
               			 );
            		});
	    		
	    		 $(document).ready(function(){
    			    $(document).bind("contextmenu",function(e){
    			        return false;
    			    });
    			});
	        </script>
</body>
</html>