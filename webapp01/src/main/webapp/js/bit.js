function ajax(settings) {
	var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(result) {
      if (xhr.readyState != 4)
        return;
      
      if (xhr.status != 200) {
        if (settings.error) { 
          settings.error("서버 실행 중 오류 발생!")
        }
        return;
      }
      
      if (settings.dataType == "json") {
        var result = JSON.parse(xhr.responseText)
      } else {
    	var result = xhr.responseText;  
      }
      
      if (settings.success) {
    	  settings.success(result)
      }
    }
    xhr.open(settings.method, settings.url, true)
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
    
    if (settings.method == "POST") {
      xhr.send(objectToQueryString(settings.data))
    } else {
      xhr.send()
    }
}

function objectToQueryString(obj) {
  var params = ""
  if (obj) {
	var firstParam = true
	for (var propName in obj) {
	  if (!firstParam) {
		params += "&"
	  }
	  params += propName + "=" + obj[propName]
	  firstParam = false
	}
  }
  return params
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