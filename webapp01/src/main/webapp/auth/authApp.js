$("#loginBtn").click(function(event) {
	var user = {
    email: $("#email").val(),
    password: $("#password").val(),
    saveEmail: $("#saveEmail").is(":checked")
  }
  ajaxLogin(user)
});

function ajaxLogin(user) {
	$.ajax({
		url: "login.json",
		method: "POST",
		dataType: "json",
		data: user,
		success: function(obj) {
			var result = obj.jsonResult
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
	$.getJSON("logout.json", function(obj) {
		var result = obj.jsonResult
		if (result.state != "success")
	        console.log("로그아웃 실패입니다.")
    })
}

function init() {
	var cookieMap = bit.cookieToObject()
	
	if ("email" in cookieMap) { // cookieMap 객체에 email 이라는 이름의 프로퍼티가 있는가?
		$("#email").val(cookieMap["email"])
		$("#saveEmail").attr("checked", true)
	}
}










