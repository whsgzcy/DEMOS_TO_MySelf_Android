'use strict';
var NET = NET || {
	lastNetworkSettingHandle: (config) => {
		var data = config;
		if (data.data)
		{
			data = data.data;
		}
		var params = data.split(',');
		if (params.length === 3)
		{
			var networkSettings = {
				ssid: params[0].split(':')[1].trim(),
				password: params[1].split(':')[1].trim(),
				ip: params[2].split(':')[1].trim()
			};
			$('#network_ssid').val(networkSettings.ssid);
			$('#network_password').val(networkSettings.password);
			$('#network_ip').val(networkSettings.ip);
		}
	}
};

$(() => {
	// switch to wifi
	$('#network_set').on('click', function(){
		var ssid = $('#network_ssid').val();
		var password = $('#network_password').val();
		var ip = $('#network_ip').val().trim();
		var rememberSetting = ($('#rememberNetSetting').attr('checked') === 'checked');
		console.log(`ssid: ${ssid}, password: ${password}, ip: ${ip},rememberSetting: ${rememberSetting}`);
		if (!password)
		{
			password = 'null';
		}
		if (ip)
		{
			if (!checkIp(ip))
			{
				ALERT.error({
					title: '参数错误',
					text: 'ip不合法'
				});
				return;
			}
		}
		else
		{
			ip = 'null';
		}
		NAV.setNetwork(PARAMS.NetworkMode.wifi, {
			ssid: ssid,
			password: password,
			ip: ip,
			rememberSetting: rememberSetting
		});
		// reconnect
		DATA.loading = '切换中';
		setTimeout(function(){
			window.location.href = `http://${ip}`;
		}, 10000);
		$('#network_settings').css('display', 'none');
	});

	// switch to ap
	$('#network_reset').on('click', function(){
		NAV.setNetwork(PARAMS.NetworkMode.ap, {});
		// reconnect
		DATA.loading = '切换中';
		setTimeout(function(){
			window.location.href = `http://10.42.0.1`;
		}, 10000);
		$('#network_settings').css('display', 'none');
	});
});

function checkIp(ipStr)
{
    ipStr = ipStr.match(/(\d+)\.(\d+)\.(\d+)\.(\d+)/g);
    if (ipStr == null) 
    {
        return false;
    } 
    else if (RegExp.$1 > 255 || RegExp.$2 > 255 || RegExp.$3 > 255 || RegExp.$4 > 255) 
    {
        return false;
    } 
    else 
    {
        return true;
    }
}