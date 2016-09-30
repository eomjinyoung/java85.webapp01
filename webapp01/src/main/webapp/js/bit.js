var bit = {}

bit.ajax = function(settings) {
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
    
    if (settings.method == "POST") {
      xhr.open(settings.method, settings.url, true)
      xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
      xhr.send(bit.objectToQueryString(settings.data))
    } else {
      var url = settings.url
      if (settings.data) {
         var queryString = bit.objectToQueryString(settings.data)
         var urlpath = settings.url.split("?")
         var url = urlpath[0]
         if (urlpath.length == 2 && urlpath[1].length > 0) {
        	queryString += "&" + urlpath[1]
         }
         url += "?" + queryString
      }
      xhr.open(settings.method, url, true)
      xhr.send()
    }
}


bit.getJSON = function(url) {
	var settings = {
		url: url,
		method: "GET",
		dataType: "json"
	}
	
	if (arguments.length == 2) {
		if ((typeof arguments[1]) == "function") {
			settings.success = arguments[1]
		} else {
			settings.data = arguments[1]
		}
	}
	
	if (arguments.length == 3) {
		settings.data = arguments[1]
		settings.success = arguments[2]
	}
	
	bit.ajax(settings);
}

bit.post = function(url /*,data, success, dataType*/) {
	var settings = {
		url: url,
		method: "POST",
		dataType: "text"
	}
	
	for (var i = 1; i < arguments.length; i++) {
		type = typeof arguments[i]
		if (type == "function") {
			settings.success = arguments[i]
		} else if (type == "string") {
			settings.dataType = arguments[i]
		} else {
			settings.data = arguments[i]
		}
	}
	
	bit.ajax(settings);
}


bit.objectToQueryString = function(obj) {
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

bit.cookieToObject = function() {
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

var $ = bit





