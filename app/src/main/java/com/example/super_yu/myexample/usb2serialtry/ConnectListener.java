package com.example.super_yu.myexample.usb2serialtry;

public abstract class ConnectListener {
	public enum CommanderStatus
	{
		SUCCESS, 
		FAIL, 
		STATE, 
	}
	public abstract void OnConnectStatusCallBack( boolean bSucceed );
	public abstract void OnDisConnectCallBack();
}
