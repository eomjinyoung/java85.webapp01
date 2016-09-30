$("#loginBtn").click(function(event) {
	location.href = "../auth/authApp.html"
});

$("#logoutBtn").click(function(event) {
	location.href = "../auth/authApp.html"
});

function ajaxBoardList() {
	$.getJSON("list.json", function(result) {
		if (result.state != "success") {
	    	 alert("서버에서 데이터를 가져오는데 실패했습니다.")
	    	 return
	    }
		
		var contents = ""
	    var arr = result.data
	    for (var i in arr) {
	    	contents += "<tr>" +
	    	  "<td>" + arr[i].no + "</td>" + 
	    	  "<td><a class='titleLink' href='#' data-no='" + arr[i].no + "'>" + arr[i].title + "</a></td>" +
	    	  "<td>" + arr[i].createdDate + "</td>" +
	    	  "<td>" + arr[i].viewCount + "</td>" + 
	    	  "</tr>"
	    }
	    $("#boardTable tbody").html(contents)
	    $(".titleLink").click(function(event) {
		    window.location.href = "boardForm.html?no=" + $(this).attr("data-no")
	    })
    })
}

function ajaxLoginUser() {
	$.getJSON("../auth/loginUser.json", function(result) {
	    if (result.state != "success") { // 로그아웃 상태일 경우 로그인 상태와 관련된 태그를 감춘다.
	         $('.my-login').css("display", "none")
	         return
	    }
	      
	    $('.my-logout').css("display", "none")
	      
	    $("#userName").text(result.data.name);
    })
}




