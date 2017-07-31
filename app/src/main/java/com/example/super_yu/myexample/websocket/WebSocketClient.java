package com.example.super_yu.myexample.websocket;


/**
 * Created by super_yu on 2017/6/20.
 */

public abstract class WebSocketClient {

    public WebSocketClient() {}

    public static WebSocketClient create(String uriString) {
        // if we ever implement other ROSClient types, we'll key off the URI protocol (e.g., ws://)
        // we'd also have to abstract out Topic and Service since they depend on the ROSBridge operations
        return new WebSocketClientCall(uriString);
    }

    public abstract boolean aGV2ServerConnect();
    public abstract boolean aGV2ServerDisconnect(WebSocketClient.AGV2ServerConnectionStatusListener listener);
    public abstract void disconnect();
    public abstract void a2ServerMessage(String json);

    public interface AGV2ServerConnectionStatusListener {

        void onAGV2ServerConnect();

        void onAGV2ServerDisconnect(boolean normal, String reason, int code);

        void onAGV2ServerMessage(String msg);

        void onAGV2ServerError(Exception ex);
    }
}
