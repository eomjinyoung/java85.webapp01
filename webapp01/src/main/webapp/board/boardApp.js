document.querySelector("#loginBtn").onclick = function(event) {
	location.href = "../auth/authApp.html"
}

document.querySelector("#logoutBtn").onclick = function(event) {
	location.href = "../auth/authApp.html"
}

function ajaxBoardList() {
	bit.getJSON("list.json", function(result) {
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
	    document.querySelector("#boardTable tbody").innerHTML = contents;
	    var aTags = document.querySelectorAll(".titleLink")
	    for (var i = 0; i < aTags.length; i++) {
		    aTags[i].onclick = function(event) {
		    	window.location.href = "boardForm.html?no=" + this.getAttribute("data-no")
		    }
	    }
    })
}

function ajaxLoginUser() {
	bit.getJSON("../auth/loginUser.json", function(result) {
	    if (result.state != "success") { // 로그아웃 상태일 경우 로그인 상태와 관련된 태그를 감춘다.
	         var tags = document.querySelectorAll('.my-login')
	         for (var i = 0; i < tags.length; i++) 
	        	 tags[i].style.display = "none";
	         return
	    }
	      
	    var tags = document.querySelectorAll('.my-logout')
	    for (var i = 0; i < tags.length; i++) 
	        tags[i].style.display = "none";
	      
	    document.querySelector("#userName").textContent = result.data.name;
    })
}

