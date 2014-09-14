<%@page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%

String str=request.getParameter("test4");
System.out.println(str);



String teststr="[[10,\"Baby Luigi\",null,\"Baby Luigi\"],[59,\"Lex Luthor\",null,\"Lex Luthor\"],[61,\"Lulu\",null,\"Lulu\"]]";
//[[10,"Baby Luigi",null,"Baby Luigi"],[59,"Lex Luthor",null,"Lex Luthor"],[61,"Lulu",null,"Lulu"]]
%>

<%= teststr %>