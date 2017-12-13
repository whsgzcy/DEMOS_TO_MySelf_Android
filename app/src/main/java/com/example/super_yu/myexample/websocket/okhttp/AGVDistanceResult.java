package com.example.super_yu.myexample.websocket.okhttp;

/**
 * Created by super_yu on 2017/12/13.
 */

public class AGVDistanceResult {

    /**
     * op : publish
     * id : 001
     * distance : 16
     * goal : map_xx_xx_xx
     */

    private String op;
    private String id;
    private String distance;
    private String goal;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }
}
