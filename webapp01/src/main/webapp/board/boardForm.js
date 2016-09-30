$("#addBtn").click(function(event) {
	var board = {
	  title: $("#title").val(),
	  contents: $("#contents").val(),
	  password: $("#password").val()
	}
	ajaxAddBoard(board)
});

$("#updateBtn").click(function(event) {
  var board = {
    title: $("#title").val(),
    contents: $("#contents").val(),
    password: $("#password").val(),
    no: $("#no").val()
  }
  ajaxUpdateBoard(board)
});

$("#deleteBtn").click(function(event) {
  ajaxDeleteBoard($("#no").val(), $("#password").val())
});

function ajaxAddBoard(board) {
	$.post("add.json", board, function(result) {
		if (result.state != "success") {
	    	 alert("등록 실패입니다.")
	    	 return
	    }
	    window.location.href = "boardApp.html"
	}, "json")
}

function ajaxLoadBoard(no) {
	$.getJSON("detail.json?no=" + no, function(result) {
		if (result.state != "success") {
			alert("조회 실패입니다.")
			return
		}
		
		$("#no").val(result.data.no);
		$("#title").val(result.data.title);
		$("#contents").val(result.data.contents);
		$("#createdDate").text(result.data.createdDate2);
		$("#viewCount").text(result.data.viewCount);
	})
}

function ajaxUpdateBoard(board) {
	$.post("update.json", board, function(result) {
		if (result.state != "success") {
			alert("변경 실패입니다.")
			return
		}
		window.location.href = "boardApp.html"
	}, "json")
}

function ajaxDeleteBoard(no, password) {
	$.getJSON("delete.json", {
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








