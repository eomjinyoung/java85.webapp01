$("#loginBtn").click(function(event) {
	location.href = "../auth/authApp.html"
});

$("#logoutBtn").click(function(event) {
	location.href = "../auth/authApp.html"
});

function ajaxBoardList() {
	$.getJSON(serverAddr + "/board/list.json", function(obj) {
		var result = obj.jsonResult
		if (result.state != "success") {
	    	 alert("서버에서 데이터를 가져오는데 실패했습니다.")
	    	 return
	    }
		
		var contents = ""
	    var arr = result.data
	    var template = Handlebars.compile($('#trTemplateText').html())
	    for (var i in arr) {
	    	contents += template(arr[i])
	    }
	    $("#boardTable tbody").html(contents)
	    $(".titleLink").click(function(event) {
		    window.location.href = "boardForm.html?no=" + $(this).attr("data-no")
	    })
    })
}

function ajaxLoginUser() {
	$.getJSON(serverAddr + "/auth/loginUser.json", function(obj) {
		var result = obj.jsonResult
	    if (result.state != "success") { // 로그아웃 상태일 경우 로그인 상태와 관련된 태그를 감춘다.
	         $('.my-login').css("display", "none")
	         return
	    }
	      
	    $('.my-logout').css("display", "none")
	      
	    $("#userName").text(result.data.name);
    })
}




