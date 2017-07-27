package com.example.super_yu.myexample.websocket;


/**
 * Created by super_yu on 2017/6/20.
 */

public abstract class AGVClient {

    public AGVClient() {}

    public static AGVClient create(String uriString) {
        // if we ever implement other ROSClient types, we'll key off the URI protocol (e.g., ws://)
        // we'd also have to abstract out Topic and Service since they depend on the ROSBridge operations
        return new AGVRobotClient(uriString);
    }

    public abstract boolean aGV2ServerConnect();
    public abstract boolean aGV2ServerDisconnect(AGVClient.AGV2ServerConnectionStatusListener listener);
    public abstract void disconnect();
    public abstract void a2ServerMessage(String json);

    public interface AGV2ServerConnectionStatusListener {

        public void onAGV2ServerConnect();

        public void onAGV2ServerDisconnect(boolean normal, String reason, int code);

        public void onAGV2ServerMessage(String msg);

        public void onAGV2ServerError(Exception ex);
    }
}
