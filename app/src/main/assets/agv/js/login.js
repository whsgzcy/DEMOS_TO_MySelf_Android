'use strict';
$(function(){
	// login
	$('#entry_button').on('click', function(){
		var username = $('#loginUsername').val();
		var password = $('#loginPassword').val();
		console.log(username, password);
		var url = 'http://localhost:8808/api/login';
		$.ajax({
            type: 'post',
            url: url,
            data: { username: username, password: password },
            async: true,
            success: function (data) {
                if (data.code == 'auth:login_success') {
                	$('#loginInfo').text('');
                	$('#login').css('display', 'none');
                }
                else if (data.code == 'auth:user_not_found') {
                	$('#loginInfo').text('用户名不存在');
                }
                else if (data.code == '') {
                	$('#loginInfo').text('该用户已在其他设备登录');
                }
                else if (data.code == 'auth:wrong_password') {
                	$('#loginInfo').text('密码错误');
                }
            },
            dataType: "json"
        });//ajax
	});

	$('#register').on('click', function(){
		$('#registers').css('display', 'block');
	});

	$('#register_button').on('click', function(){
		var username = $('#registerName').val().trim();
		if (!isUserNameValid(username))
		{
			$('#registerInfo').text('用户名无效');
			return;
		}
		var pd1 = $('#registerPw').val();
		var pd2 = $('#registerPwRepeat').val();
		if (!isPasswdValid(pd1))
		{
			$('#registerInfo').text('密码无效');
			return;
		}
		if (pd1 !== pd2)
		{
			$('#registerInfo').text('密码不一致');
			return;
		}
		var password = pd1;
		var authcode = $('#registerAuthCode').val().trim();
		var url = 'http://localhost:8808/api/register';
		$.ajax({
            type: 'post',
            url: url,
            data: { username: username, password: password, authcode: authcode },
            async: true,
            success: function (data) {
                if (data.code.startsWith('auth:register_success')) 
                {
                	$('#registerName').val('');
                	$('#registerPw').val('');
                	$('#registerPwRepeat').val('');
                	$('#registerAuthCode').val('');
                	$('#registers').css('display', 'none');
                }
                else if (data.code == 'auth:bad_auth_code') 
                {
                	$('#registerInfo').text('授权码无效');
                }
                else if (data.code == 'auth:username_exists') 
                {
                	$('#registerInfo').text('用户名已存在');
                }
            },
            dataType: "json"
        });//ajax
	});

	$('.registers_back').on('click', function(){
		$('#registers').css('display', 'none');
	})
});

function isPasswdValid(s) 
{
    var patrn = /^(\w){6,20}$/;
    if (!patrn.exec(s))
    {
        return false;
    }
    return true;
}

function isUserNameValid(s) 
{
    var patrn = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/;
    if (!patrn.exec(s)) 
    {
    	return false;	
    }
    return true;
}