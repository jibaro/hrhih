<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id='toolbar1' class='flexpaper_toolbar flexpaper_toolbarios'>
    <img src='images/touch/print.png' class='flexpaper_bttnPortrait flexpaper_tbbutton_large flexpaper_print' style='margin-left:5px;' onclick='$FlexPaper("documentViewer").printPaper();'/>
    <img src='images/touch/document.png' class='flexpaper_tbbutton_large flexpaper_tbbutton_viewmode  flexpaper_singlepage' style='margin-left:15px;' onclick='$FlexPaper("documentViewer").switchMode("Portrait")'>
    <img src='images/touch/twodocuments.png' style='margin-left:-1px;' class='flexpaper_bttnTwoPage flexpaper_tbbutton_large flexpaper_tbbutton_viewmode flexpaper_twopage' onclick='$FlexPaper("documentViewer").switchMode("TwoPage")' >
    <img src='images/touch/thumbs.png' style='margin-left:-1px;' class='flexpaper_bttnTileMode flexpaper_tbbutton_large flexpaper_tbbutton_viewmode flexpaper_thumbview' onclick='$FlexPaper("documentViewer").switchMode("Tile")' >
    <img src='images/touch/zoomin.png' class='flexpaper_bttnZoomIn flexpaper_tbbutton_large' style='margin-left:15px;' onclick='$FlexPaper("documentViewer").Zoom(2)'/>
    <img src='images/touch/zoomout.png' class='flexpaper_bttnZoomOut flexpaper_tbbutton_large' style='margin-left:-1px;' onclick='$FlexPaper("documentViewer").Zoom(1)' />
    <img src='images/touch/fullscreen.png' class='flexpaper_bttnFullScreen flexpaper_tbbutton_large' style='margin-left:-1px;' onclick='jQuery("#documentViewer").showFullScreen();'/>
    <img src='images/touch/prev.png' class='flexpaper_bttnPrevPage flexpaper_tbbutton_large flexpaper_previous' style='margin-left:15px;' onclick='$FlexPaper("documentViewer").prevPage()'/>
    <input type='text' class='flexpaper_txtPageNumber flexpaper_tbtextinput_large flexpaper_currPageNum' value='1' style='width:80px;text-align:right;' />
    <div class='flexpaper_lblTotalPages flexpaper_tblabel_large flexpaper_numberOfPages'> / </div>
    <img src='images/touch/next.png'  class='flexpaper_bttnNextPage flexpaper_tbbutton_large flexpaper_next' onclick='$FlexPaper("documentViewer").nextPage()'/>
    <input type='text' class='flexpaper_txtSearchText flexpaper_tbtextinput_large' style='margin-left:15px;width:130px;' />
    <img src='images/touch/search.png' class='flexpaper_bttnTextSearch flexpaper_find flexpaper_tbbutton_large' style='' onclick='$FlexPaper("documentViewer").searchText(jQuery(this).parent().find(".flexpaper_txtSearchText").val());' />
</div>