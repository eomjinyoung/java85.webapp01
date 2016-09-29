document.querySelector("#loginBtn").addEventListener("click", function(event) {
	var user = {
    email: document.querySelector("#email").value,
    password: document.querySelector("#password").value,
    saveEmail: document.querySelector("#saveEmail").checked 
  }
	
  ajaxLogin(user)
});

function ajaxLogin(user) {
	  var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(result) {
      if (xhr.readyState != 4)
        return;
      
      if (xhr.status != 200) {
        alert("서버에 잘못 요청했습니다.")
        return;
      }
      
      var result = JSON.parse(xhr.responseText)
      if (result.state != "success") {
         console.log(result.data)
         alert("로그인 실패입니다.\n이메일 또는 암호를 확인하세요.")
         return
      }
      
      window.location.href = "../board/boardApp.html"
    }
    xhr.open("POST", "login.json", true)
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
    
    var params = "email=" + user.email + "&password=" + user.password;
    if (user.saveEmail == true) {
    	params += "&saveEmail=on"
    }

    xhr.send(params) 
}

function ajaxLogout(user) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(result) {
      if (xhr.readyState != 4)
        return;
      
      if (xhr.status != 200) {
        console.log("서버 요청 오류입니다.")
        return;
      }
      
      var result = JSON.parse(xhr.responseText)
      if (result.state != "success")
        console.log("로그아웃 실패입니다.")
    }
    xhr.open("GET", "logout.json", true)
    xhr.send() 
}

function init() {
	var cookieMap = cookieToObject()
	
	//if (cookieMap["email"]) { // cookieMap 객체에 email 이름으로 저장된 값이 있는가?
  if ("email" in cookieMap) { // cookieMap 객체에 email 이라는 이름의 프로퍼티가 있는가?
		document.querySelector("#email").value = cookieMap["email"]
	  document.querySelector("#saveEmail").checked = true
	}
}

function cookieToObject() {
	var cookies = document.cookie.split(";")
	var obj = {}
	
	if (cookies.length == 0) 
		return obj;
	
  cookies.forEach(function(data) {
	  var cookie = data.trim().split("=")
	  obj[cookie[0]] = cookie[1].replace(/\"/gi, "")
  });
    
	return obj
}