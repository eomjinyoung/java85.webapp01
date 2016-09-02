<%@page import="example.vo.Board"%>
<%@ page language="java" 
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>      
<%
Board board = (Board)request.getAttribute("board");
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 상세조회</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>
<h1>게시물 상세조회2</h1>
<%if (board == null) { %>
  <p>해당 게시물이 존재하지 않습니다.</p>
<%} else {%>
	<form action='update.do' method='post'>
	<input type='hidden' name='no' value='<%=board.getNo()%>'>
	번호: <%=board.getNo()%><br>
	제목: <input type='text' name='title' value='<%=board.getTitle()%>'><br>
	내용: <textarea cols='60' rows='10' name='contents'><%=board.getContents()%></textarea><br>
	암호: <input type='password' name='password'><br>
	작성자: <%=board.getWriter()%><br>
	등록일: <%=board.getCreatedDate()%><br>
	조회수: <%=board.getViewCount()%><br>
	<button>변경</button>
	<p><a href='delete.do?no=<%=board.getNo()%>'>삭제</a></p>
	</form>
<%}%>
<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>








