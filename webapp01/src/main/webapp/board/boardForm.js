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
  ajaxDeleteBoard(
		  document.querySelector("#no").value,
		  document.querySelector("#password").value)
});

function ajaxAddBoard(board) {
	bit.post("add.json", board, function(result) {
		if (result.state != "success") {
	    	 alert("등록 실패입니다.")
	    	 return
	    }
	    window.location.href = "boardApp.html"
	}, "json")
}

function ajaxLoadBoard(no) {
	bit.getJSON("detail.json?no=" + no, function(result) {
		if (result.state != "success") {
			console.log(result.data)
			alert("조회 실패입니다.")
			return
		}
		
		document.querySelector("#no").value = result.data.no;
		document.querySelector("#title").value = result.data.title;
		document.querySelector("#contents").value = result.data.contents;
		document.querySelector("#createdDate").textContent = result.data.createdDate2;
		document.querySelector("#viewCount").textContent = result.data.viewCount;
	})
}

function ajaxUpdateBoard(board) {
	bit.post("update.json", board, function(result) {
		if (result.state != "success") {
			alert("변경 실패입니다.")
			return
		}
		window.location.href = "boardApp.html"
	}, "json")
}

function ajaxDeleteBoard(no, password) {
	bit.getJSON("delete.json", {
		no: no,
		password: password
	}, function(result) {
		if (result.state != "success") {
			alert("삭제 실패입니다.")
			return
		}
		location.href = "boardApp.html"
	})
}








