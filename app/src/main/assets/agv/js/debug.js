'use strict';
// Protocol:
// https://github.com/hitrobotgroup/release
var DEBUG = DEBUG || {
	init: (url) => {
		DEBUG.websocket = new WebSocket(url);
		DEBUG.websocket.onopen = function(event)
		{
			console.log(`[INFO]websocket(debug) connected to ${url}`);
		}
		DEBUG.websocket.onmessage = function(event)
		{
			console.log(event);
		}
	},

	send: (data) => {
		if (data)
		{
			DEBUG.websocket.send(data);
			console.log(`[INFO]sending ${data}`);	
		}
	}
}

$(() => {
	$('#debugging_button').on('click', function(){
		var codeStr = $('#debugging_text').val();
		var codeJson;
		try
		{
			codeJson = JSON.parse(codeStr);
		}
		catch(e)
		{
			
		}
		DEBUG.send(codeJson);
		$('#debugging_text').val('请输入需要调试的代码');
	});
});