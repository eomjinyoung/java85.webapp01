document.querySelector("#loginBtn").onclick = function(event) {
	location.href = "../auth/authApp.html"
}

document.querySelector("#logoutBtn").onclick = function(event) {
	location.href = "../auth/authApp.html"
}

function ajaxBoardList() {
  var xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function() {
    if (xhr.readyState != 4)
      return;
    
    if (xhr.status != 200) {
      alert("서버에 잘못 요청했습니다.")
      return;
    }
    
    var result = JSON.parse(xhr.responseText)
    
    if (result.state != "success") {
    	alert("서버에서 데이터를 가져오는데 실패하였습니다.")
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
    
    // tr 태그를 추가한 후에 제목에 대해 click 리스너를 추가한다.
    var aTags = document.querySelectorAll(".titleLink")
    for (var i = 0; i < aTags.length; i++) {
	    aTags[i].onclick = function(event) {
	    	//alert(this.getAttribute("data-no")) // 태그 고유의 프로퍼티가 아니기 때문에 getAttribute() 사용
	    	window.location.href = "boardForm.html?no=" + this.getAttribute("data-no")
	    }
    }
  }
  xhr.open("GET", "list.json", true)
  //xhr.open("GET", "test.jsp", true)
  //xhr.open("GET", "test.txt", true)
  xhr.send() 
}

function ajaxLoginUser() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
      if (xhr.readyState != 4)
        return;
      
      if (xhr.status != 200) {
        alert("서버에 잘못 요청했습니다.")
        return;
      }
      
      var result = JSON.parse(xhr.responseText)
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
    }
    
    xhr.open("GET", "../auth/loginUser.json", true)
    xhr.send() 
}

