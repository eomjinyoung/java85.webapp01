<%@page import="example.vo.Board"%>
<%@ page language="java" 
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>             
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세조회</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>
<h1>게시물 상세조회2</h1>
<c:choose>
<c:when test="${empty board}">
  <p>해당 게시물이 존재하지 않습니다.</p>
</c:when>
<c:otherwise>
	<form action='update.do' method='post'>
	<input type='hidden' name='no' value='${board.no}'>
	번호: ${board.no}<br>
	제목: <input type='text' name='title' value='${board.title}'><br>
	내용: <textarea cols='60' rows='10' name='contents'>${board.contents}</textarea><br>
	암호: <input type='password' name='password'><br>
	작성자: ${board.writer}<br>
	등록일: ${board.createdDate}<br>
	조회수: ${board.viewCount}<br>
	<button>변경</button>
	<p><a href='delete.do?no=${board.no}'>삭제</a></p>
	</form>
</c:otherwise>
</c:choose>
<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>








