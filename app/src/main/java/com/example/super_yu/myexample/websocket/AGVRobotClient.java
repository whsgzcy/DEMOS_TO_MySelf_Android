package com.example.super_yu.myexample.websocket;

/**
 * Created by super_yu on 2017/6/20.
 */

public class AGVRobotClient extends AGVClient {

    private String uriString;
    private AGVRobot2ServerWebSocket client;

    public AGVRobotClient(String uriString) {
        this.uriString = uriString;
    }

    @Override
    public boolean aGV2ServerConnect() {
        return aGV2ServerDisconnect(null);
    }

    public void setROSMonitorListener(AGVClient.AGV2ServerConnectionStatusListener listener){
        client.setListener(listener);
    }

    @Override
    public boolean aGV2ServerDisconnect(AGV2ServerConnectionStatusListener listener) {
        boolean result = false;
        client = AGVRobot2ServerWebSocket.create(uriString);
        if (client != null) {
            client.setListener(listener);
            try {
                result = client.connectBlocking();
            } catch (InterruptedException ex) {
            }
        }
        return result;
    }

    @Override
    public void disconnect() {
        try {
            client.closeBlocking();
        } catch (InterruptedException ex) {
        }
    }

    @Override
    public void a2ServerMessage(String msg) {
        client.sendServer4Message(msg);
    }
}
