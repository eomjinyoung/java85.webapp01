document.querySelector("#loginBtn").addEventListener("click", function(event) {
	var user = {
    email: document.querySelector("#email").value,
    password: document.querySelector("#password").value,
    saveEmail: document.querySelector("#saveEmail").checked 
  }
	
  ajaxLogin(user)
});

function ajaxLogin(user) {
	bit.ajax({
		url: "login.json",
		method: "POST",
		dataType: "json",
		data: user,
		success: function(result) {
		    if (result.state != "success") {
	            alert("로그인 실패입니다.\n이메일 또는 암호를 확인하세요.")
	            return
	        }
	        window.location.href = "../board/boardApp.html"
		},
		error: function(msg) {
			alert(msg)
		}
	})
}

function ajaxLogout(user) {
	bit.getJSON("logout.json", function(result) {
		if (result.state != "success")
	        console.log("로그아웃 실패입니다.")
    })
}

function init() {
	var cookieMap = bit.cookieToObject()
	
	//if (cookieMap["email"]) { // cookieMap 객체에 email 이름으로 저장된 값이 있는가?
	if ("email" in cookieMap) { // cookieMap 객체에 email 이라는 이름의 프로퍼티가 있는가?
		document.querySelector("#email").value = cookieMap["email"]
		document.querySelector("#saveEmail").checked = true
	}
}
