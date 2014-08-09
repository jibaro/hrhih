<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id='toolbar1' class='flexpaper_toolbar flexpaper_toolbarstd'>
    <img src='images/flat/icon-arrow-left.png' class='flexpaper_bttnPrevPage flexpaper_tbbutton' onclick='$FlexPaper("documentViewer").prevPage()' title='Previous Page' alt='Previous Page'/>
    <input type='text' class='flexpaper_txtPageNumber flexpaper_tbtextinput' style='width:36px;' />
    <div class='flexpaper_lblTotalPages flexpaper_tblabel'> / </div>
    <img src='images/flat/icon-arrow-right.png' class='flexpaper_bttnPrevNext flexpaper_tbbutton' onclick='$FlexPaper("documentViewer").nextPage()' title='Next Page' alt='Next Page'/>
</div>

<script language="javascript">
    // adjust IE 7 and below for textbox input size
    var userAgent   = navigator.userAgent.toLowerCase();
    var ismsie      = /msie/.test(userAgent) && !/opera/.test(userAgent);
    var browser_v   = (userAgent.match(/.+?(?:rv|it|ra|ie)[\/: ]([\d.]+)(?!.+opera)/) || [])[1];

    if(ismsie && browser_v < 8){
        jQuery('.flexpaper_tbtextinput').css('height','12px');
    }
</script>
