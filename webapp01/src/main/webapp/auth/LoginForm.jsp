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
<title>로그인</title>
</head>
<body>
<h1>로그인2</h1>
<form action='login' method='post'>
이메일: <input type='text' name='email' size='40' value='<%=email%>'><br>
암호: <input type='password' name='password'><br>
<input type='checkbox' name='saveEmail' <%=checked%>> 이메일 저장<br>
<button>로그인</button>
</form>
</body>
</html>








