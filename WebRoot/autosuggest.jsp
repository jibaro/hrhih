<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'autosuggest.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="css/themes/base/jquery.ui.all.css">
	<script src="js/jquery-1.10.2.js"></script>
	<script src="js/ui/jquery.ui.core.js"></script>
	<script src="js/ui/jquery.ui.widget.js"></script>
	<script src="js/ui/jquery.ui.position.js"></script>
	<script src="js/ui/jquery.ui.menu.js"></script>
	<script src="js/ui/jquery.ui.autocomplete.js"></script>
	<link rel="stylesheet" href="css/themes/base/demos.css">
	<style>
	.ui-autocomplete-loading {
		background: white url('css/themes/base/images/ui-anim_basic_16x16.gif') right center no-repeat;
	}
	</style>
    <script type="text/javascript">
        $(function(){
            $("#suggestText").autocomplete({
                minLength:2,
                autoFocus:true,
                source:function(request,response){
                    $.ajax({
                        type:"POST",
                        url:"<%=basePath%>utils/index_suggest.action",
                        dataType:"json",
                        data:{ suggest :  $("#suggestText").val() },
                        success:function(json){
                            response($.map(json.suggests,function(item){
                                return {
                                    label:item,
                                    value:item
                                };
                            }));
                        }
                    });
                }
            });
        });
    </script>
  </head>
  
  <body>
   <div class="ui-widget">
		<label for="birds">关键字: </label>
		<input id="suggestText">
	</div>
	
	<div class="demo-description">
	<p>The Autocomplete widgets provides suggestions while you type into the field. Here the suggestions are bird names, displayed when at least two characters are entered into the field.</p>
	<p>The datasource is a server-side script which returns JSON data, specified via a simple URL for the source-option. In addition, the minLength-option is set to 2 to avoid queries that would return too many results and the select-event is used to display some feedback.</p>
	</div>
  </body>
</html>
