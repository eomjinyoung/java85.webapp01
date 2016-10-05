<%@page import="example.vo.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" 
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>                  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 목록조회</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>
<h1>게시물 목록조회2</h1>
<p><a href='form.html'>새 글</a></p>
<c:forEach items="${list}" var="board">
  ${board.no}, 
  <a href='detail.do?no=${board.no}'>${board.title}</a>, 
  ${(empty board.writer) ? "---" : board.writer}, 
  ${board.createdDate}, 
  ${board.viewCount}<br>  
</c:forEach>
<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>








