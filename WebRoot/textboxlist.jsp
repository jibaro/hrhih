<%@page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'textboxlist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!-- required stylesheet for TextboxList -->
	<link rel="stylesheet" href="css/textboxlist/TextboxList.css" type="text/css" media="screen" charset="utf-8" />
	<!-- required stylesheet for TextboxList.Autocomplete -->
	<link rel="stylesheet" href="css/textboxlist/TextboxList.Autocomplete.css" type="text/css" media="screen" charset="utf-8" />
	<script src="js/jquery-1.10.2.js" type="text/javascript" charset="utf-8"></script>	
	
	<!-- required for TextboxList -->
	<script src="js/textboxlist/SuggestInput.js" type="text/javascript" charset="utf-8"></script>
	
	<!-- required for TextboxList -->
	<script src="js/textboxlist/GrowingInput.js" type="text/javascript" charset="utf-8"></script>
			
	<script src="js/textboxlist/TextboxList.js" type="text/javascript" charset="utf-8"></script>		
	<script src="js/textboxlist/TextboxList.Autocomplete.js" type="text/javascript" charset="utf-8"></script>
	<!-- required for TextboxList.Autocomplete if method set to 'binary' -->
	<script src="js/textboxlist/TextboxList.Autocomplete.Binary.js" type="text/javascript" charset="utf-8"></script>	

<!-- sample initialization -->
		<script type="text/javascript" charset="utf-8">		
			$(function(){
				// Autocomplete with poll the server as you type
				var t5 = new $.TextboxList('#form_tags_input_4', {unique: true,max:5, plugins: {autocomplete: {
					minLength: 1,
					maxResults: 10,
					queryRemote: true,
					isMatch:false,
					highlight: false,
					onlyFromValues: true,
					suggestid: 'form_tags_input_4',
					remote: {url: 'utils/suggest4region.action',
							param: 'suggest'
							}
				}}});
				t5.add('hrhih');
				t5.removeAllBit();
			});
		</script>

		<!-- sample style -->
		<style type="text/css" media="screen">
			.form_tags { margin-bottom: 10px; }

			/* Setting widget width example */
			.textboxlist { width: 400px; }

			/* Preloader for autocomplete */
			.textboxlist-loading { background: url('css/textboxlist/spinner.gif') no-repeat 380px center; }

			/* Autocomplete results styling */
			.form_friends .textboxlist-autocomplete-result { overflow: hidden; zoom: 1; }
			.form_friends .textboxlist-autocomplete-result img { float: left; padding-right: 10px; }

			.note { color: #666; font-size: 90%; }
			#footer { margin: 50px; text-align: center; }
		</style>
	</head>
	<body>
		<form action="autocomplete2.jsp" method="post" accept-charset="utf-8">
		<h2>TextboxList.Autocomplete (values seeded on demand)</h2>
		<p>Select friends to email</p>
		<div class="form_friends">
			<input type="text" name="test4" value="" id="form_tags_input_4" />
		</div>
		
		<h1>Test submit</h1>
		<p><input type="submit" name="submitform" value="Submit" id="submitform" /></p>
	</form>
	</body>
</html>
