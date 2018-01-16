'use strict';

var DATA = new DataServer();

function DataServer(){
	// TODO
};

DataServer.prototype.register = function(key, value, options){
	if (this.hasOwnProperty(key))
	{
		return;
	}
	var fnGetDefault = function(){
		return value;
	};
	var fnSetDefault = function(val){
		if (val === value)
		{
			return;
		}
		if (val.hasOwnProperty('property'))
		{
			value = val.value;
		}
		else
		{
			value = val;	
		}
	};
	var fnGet = options.fnGet || fnGetDefault;
	var fnSetOptions = options.fnSet || function(){};
	var fnSet = function(val){
		fnSetDefault(val);
		fnSetOptions(val);
	};
	Object.defineProperty(this, key, {
		get: fnGet,
		set: fnSet
	});
};

DataServer.prototype.syncToLocalStorage = function(){
	//TODO
};

/**********************/
function test()
{
	DATA.register('map', null, {
		fnSet: () => {
			console.log('test')
		}
	})
}
