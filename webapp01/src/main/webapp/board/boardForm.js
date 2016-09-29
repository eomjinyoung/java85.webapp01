document.querySelector("#addBtn").addEventListener("click", function(event) {
	var board = {
	  title: document.querySelector("#title").value,
	  contents: document.querySelector("#contents").value,
	  password: document.querySelector("#password").value
	}
	ajaxAddBoard(board)
});

document.querySelector("#updateBtn").addEventListener("click", function(event) {
  var board = {
    title: document.querySelector("#title").value,
    contents: document.querySelector("#contents").value,
    password: document.querySelector("#password").value,
    no: document.querySelector("#no").value
  }
  ajaxUpdateBoard(board)
});

document.querySelector("#deleteBtn").addEventListener("click", function(event) {
  ajaxDeleteBoard(document.querySelector("#no").value)
});

function ajaxAddBoard(board) {
	  var xhr = new XMLHttpRequest();
	  xhr.onreadystatechange = function() {
	    if (xhr.readyState != 4)
	      return;
	    
	    if (xhr.status != 200) {
	      alert("서버에 잘못 요청했습니다.")
	      return;
	    }
	    
	    var result = JSON.parse(xhr.responseText)
	    /* result 예)
	       {
	    	   state : "success" or "fail",
	    	   data : 결과 데이터 
	       }
	     */
	    if (result.state != "success") {
	    	 console.log(result.data)
	    	 alert("등록 실패입니다.")
	    	 return
	    }
	    
	    window.location.href = "boardApp.html"
	  }
	  xhr.open("POST", "add.json", true)
	  
	  // POST 요청은 헤더를 추가해야 한다.
	  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
	  
	  // POST 요청은 서버에 데이터를 보낼 때 send()의 파라미터로 보낸다.
	  var params = "title=" + encodeURIComponent(board.title) + 
	               "&contents=" + encodeURIComponent(board.contents) + 
	               "&password=" + encodeURIComponent(board.password)
	  
	  // 웹브라우저인 경우 자동으로 데이터에 대해 URL인코딩을 수행한다.
	  // 그런데 자바스크립트 AJAX에서는 개발자가 직접 URL인코딩을 수행해야 한다.
	  xhr.send(params) 
}

function ajaxLoadBoard(no) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
      if (xhr.readyState != 4)
        return;
      
      if (xhr.status != 200) {
        alert("서버에 잘못 요청했습니다.")
        return;
      }
      
      var result = JSON.parse(xhr.responseText)
      /* result 예)
         {
           state : "success" or "fail",
           data : 결과 데이터 
         }
       */
      if (result.state != "success") {
         console.log(result.data)
         alert("조회 실패입니다.")
         return
      }
      
      // 서버에서 받은 데이터로 폼을 채운다.
      document.querySelector("#no").value = result.data.no;
      document.querySelector("#title").value = result.data.title;
      document.querySelector("#contents").value = result.data.contents;
      document.querySelector("#createdDate").textContent = result.data.createdDate2;
      document.querySelector("#viewCount").textContent = result.data.viewCount;
    }
    
    //GET 요청은 반드시 파라미터 값을 URL에 포함시켜 보내야 한다.
    xhr.open("GET", "detail.json?no=" + no, true)
    xhr.send() 
}

function ajaxUpdateBoard(board) {
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
         console.log(result.data)
         alert("변경 실패입니다.")
         return
      }
      
      window.location.href = "boardApp.html"
    }
    xhr.open("POST", "update.json", true)
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
    var params = "title=" + encodeURIComponent(board.title) + 
                 "&contents=" + encodeURIComponent(board.contents) + 
                 "&password=" + encodeURIComponent(board.password) +
                 "&no=" + board.no
    xhr.send(params) 
}

function ajaxDeleteBoard(no) {
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
         console.log(result.data)
         alert("삭제 실패입니다.")
         return
      }

      location.href = "boardApp.html"
    }
    
    xhr.open("GET", "delete.json?no=" + no, true)
    xhr.send() 
}
