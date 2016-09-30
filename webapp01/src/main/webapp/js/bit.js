var bit = {}

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





