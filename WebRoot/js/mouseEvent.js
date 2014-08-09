//禁用右键、文本选择功能、复制按键
$(document).bind("contextmenu",function(){return false;});
$(document).bind("selectstart",function(){return false;});
$(document).keydown(function(){return key(arguments[0])});

//按键时提示警告
function key(e){
var keynum;
if(window.event){
keynum = e.keyCode; // IE
}else if(e.which){
keynum = e.which; // Netscape/Firefox/Opera
}
if(keynum == 17){
alert("禁止复制内容！");
return false;
}
}

//禁用右键、文本选择功能、复制按键
    $(document).bind("contextmenu",function(){return false;});
    $(document).bind("selectstart",function(){return false;});
    $(document).keydown(function(){return key(arguments[0])}); 
 
 //按键时提示警告
       function key(e){
            var keynum;
            if(window.event) // IE
              {
                keynum = e.keyCode;
              }
            else if(e.which) // Netscape/Firefox/Opera
              {
                keynum = e.which;
              }
            if(keynum == 17){ alert("禁止复制内容！");return false;}
        }

 
 
//屏蔽鼠标右键、Ctrl+N、Shift+F10、F11、F5刷新、退格键     
document.oncontextmenu(){event.returnValue=false;}//屏蔽鼠标右键   
document.onkeydown(){   
    if((window.event.altKey)&&   
      ((window.event.keyCode==37)||            //屏蔽Alt+方向键←   
      (window.event.keyCode==39))){            //屏蔽Alt+方向键→
           alert("不准你使用ALT+方向键前进或后退网页！");   
           event.returnValue=false;    
      }         if((event.keyCode==8)||                    //屏蔽退格删除键    
      (event.keyCode==116)||                   //屏蔽F5刷新键   
      (event.ctrlKey && event.keyCode==82)){   //Ctrl+R   
           event.keyCode=0;   
           event.returnValue=false;   
      }   
      if(event.keyCode==122){event.keyCode=0;event.returnValue=false;}    //屏蔽F11   
      if(event.ctrlKey && event.keyCode==78)event.returnValue=false;      //屏蔽Ctrl+n   
      if(event.shiftKey && event.keyCode==121)event.returnValue=false;    //屏蔽shift+F10   
      if(window.event.srcElement.tagName=="A" && window.event.shiftKey)     
         window.event.returnValue=false;       //屏蔽shift加鼠标左键新开一网页   
  }   
