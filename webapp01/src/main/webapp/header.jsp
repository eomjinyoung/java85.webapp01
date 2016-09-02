<%@page import="example.vo.Member"%>
<%@ page language="java" 
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>      
<div style='background-color:navy;font-size:150%;color:white;position:relative;'>
  자바85기^^
<%
Member member = (Member)session.getAttribute("member");
if (member != null) {%>
  <div style='position:absolute;right:0px;top:0px;font-size:15px;'>
    <%=member.getName()%>
    <a href='<%=request.getContextPath()%>/auth/logout' style='color:yellow;'>로그아웃</a></div> 
<%} else {%>
  <div style='position:absolute;right:0px;top:0px;font-size:15px;'>
    <a href='<%=request.getContextPath()%>/auth/login' style='color:yellow;'>로그인</a></div> 
<%}%>
</div>







