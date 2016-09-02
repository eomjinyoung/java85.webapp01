<%@page import="example.vo.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" 
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>      
<%
String email = (String)request.getAttribute("email");
String checked = (String)request.getAttribute("checked");
%>  
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
<%
List<Board> list = (List<Board>)request.getAttribute("list");
for (Board board : list) {%>
  <%=board.getNo()%>, 
  <a href='detail.do?no=<%=board.getNo()%>'><%=board.getTitle()%></a>, 
  <%=board.getWriter()%>, 
  <%=board.getCreatedDate()%>, 
  <%=board.getViewCount()%><br>  
<%}%>
<jsp:include page="/footer.jsp"></jsp:include>
</body>
</html>








