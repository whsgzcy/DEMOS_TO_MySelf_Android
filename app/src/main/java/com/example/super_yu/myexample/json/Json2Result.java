package com.example.super_yu.myexample.json;

import java.util.List;

/**
 * Created by super_yu on 2017/6/9.
 */

public class Json2Result {


    /**
     * floor : 6
     * map : {"mapCode":"20170606111111","waypoints":[{"goal_timeout":0,"pose":{"position":{"z":0,"y":-0.8102628499631921,"x":2.8602422782736436},"orientation":{"w":0.17221235548465064,"z":0.9850598248019138,"y":0,"x":0}},"close_enough":0,"failure_mode":"NONE","header":{"seq":1,"stamp":{"secs":1494482683,"nsecs":957325935},"frame_id":"map"},"classify":"A","name":"map_4_A_403"},{"goal_timeout":0,"pose":{"position":{"z":0,"y":-0.39162504996972797,"x":4.119879918417171},"orientation":{"w":0.9925723751909039,"z":0.12154919734400417,"y":0,"x":0}},"close_enough":0,"failure_mode":"NONE","header":{"seq":2,"stamp":{"secs":1494482737,"nsecs":82062005},"frame_id":"map"},"classify":"B","name":"map_b"},{"goal_timeout":0,"pose":{"position":{"z":0,"y":0.7415742250325088,"x":3.801334005357302},"orientation":{"w":0.6006457560296239,"z":0.7995148502928555,"y":0,"x":0}},"close_enough":0,"failure_mode":"NONE","header":{"seq":3,"stamp":{"secs":1494482773,"nsecs":242420911},"frame_id":"map"},"classify":"C","name":"map_c"},{"goal_timeout":0,"pose":{"position":{"z":0,"y":0.059577807928737736,"x":-0.23101512046212858},"orientation":{"w":0.9994535234062796,"z":-0.030639017710174105,"y":0,"x":0}},"close_enough":0,"failure_mode":"NONE","header":{"seq":4,"stamp":{"secs":1494482836,"nsecs":594048023},"frame_id":"map"},"classify":"A","name":"map_d"},{"goal_timeout":0,"pose":{"position":{"z":0,"y":0.39459103526489114,"x":2.373866946765057},"orientation":{"w":0.9978755407086826,"z":0.06512074261007626,"y":0,"x":0}},"close_enough":0,"failure_mode":"NONE","header":{"seq":5,"stamp":{"secs":1494482884,"nsecs":752923011},"frame_id":"map"},"classify":"A","name":"map_e"}]}
     */

    private int floor;
    private MapBean map;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public static class MapBean {
        /**
         * mapCode : 20170606111111
         * waypoints : [{"goal_timeout":0,"pose":{"position":{"z":0,"y":-0.8102628499631921,"x":2.8602422782736436},"orientation":{"w":0.17221235548465064,"z":0.9850598248019138,"y":0,"x":0}},"close_enough":0,"failure_mode":"NONE","header":{"seq":1,"stamp":{"secs":1494482683,"nsecs":957325935},"frame_id":"map"},"classify":"A","name":"map_4_A_403"},{"goal_timeout":0,"pose":{"position":{"z":0,"y":-0.39162504996972797,"x":4.119879918417171},"orientation":{"w":0.9925723751909039,"z":0.12154919734400417,"y":0,"x":0}},"close_enough":0,"failure_mode":"NONE","header":{"seq":2,"stamp":{"secs":1494482737,"nsecs":82062005},"frame_id":"map"},"classify":"B","name":"map_b"},{"goal_timeout":0,"pose":{"position":{"z":0,"y":0.7415742250325088,"x":3.801334005357302},"orientation":{"w":0.6006457560296239,"z":0.7995148502928555,"y":0,"x":0}},"close_enough":0,"failure_mode":"NONE","header":{"seq":3,"stamp":{"secs":1494482773,"nsecs":242420911},"frame_id":"map"},"classify":"C","name":"map_c"},{"goal_timeout":0,"pose":{"position":{"z":0,"y":0.059577807928737736,"x":-0.23101512046212858},"orientation":{"w":0.9994535234062796,"z":-0.030639017710174105,"y":0,"x":0}},"close_enough":0,"failure_mode":"NONE","header":{"seq":4,"stamp":{"secs":1494482836,"nsecs":594048023},"frame_id":"map"},"classify":"A","name":"map_d"},{"goal_timeout":0,"pose":{"position":{"z":0,"y":0.39459103526489114,"x":2.373866946765057},"orientation":{"w":0.9978755407086826,"z":0.06512074261007626,"y":0,"x":0}},"close_enough":0,"failure_mode":"NONE","header":{"seq":5,"stamp":{"secs":1494482884,"nsecs":752923011},"frame_id":"map"},"classify":"A","name":"map_e"}]
         */

        private String mapCode;
        private List<WaypointsBean> waypoints;

        public String getMapCode() {
            return mapCode;
        }

        public void setMapCode(String mapCode) {
            this.mapCode = mapCode;
        }

        public List<WaypointsBean> getWaypoints() {
            return waypoints;
        }

        public void setWaypoints(List<WaypointsBean> waypoints) {
            this.waypoints = waypoints;
        }

        public static class WaypointsBean {
            /**
             * goal_timeout : 0
             * pose : {"position":{"z":0,"y":-0.8102628499631921,"x":2.8602422782736436},"orientation":{"w":0.17221235548465064,"z":0.9850598248019138,"y":0,"x":0}}
             * close_enough : 0
             * failure_mode : NONE
             * header : {"seq":1,"stamp":{"secs":1494482683,"nsecs":957325935},"frame_id":"map"}
             * classify : A
             * name : map_4_A_403
             */

            private int goal_timeout;
            private PoseBean pose;
            private int close_enough;
            private String failure_mode;
            private HeaderBean header;
            private String classify;
            private String name;

            public int getGoal_timeout() {
                return goal_timeout;
            }

            public void setGoal_timeout(int goal_timeout) {
                this.goal_timeout = goal_timeout;
            }

            public PoseBean getPose() {
                return pose;
            }

            public void setPose(PoseBean pose) {
                this.pose = pose;
            }

            public int getClose_enough() {
                return close_enough;
            }

            public void setClose_enough(int close_enough) {
                this.close_enough = close_enough;
            }

            public String getFailure_mode() {
                return failure_mode;
            }

            public void setFailure_mode(String failure_mode) {
                this.failure_mode = failure_mode;
            }

            public HeaderBean getHeader() {
                return header;
            }

            public void setHeader(HeaderBean header) {
                this.header = header;
            }

            public String getClassify() {
                return classify;
            }

            public void setClassify(String classify) {
                this.classify = classify;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public static class PoseBean {
                /**
                 * position : {"z":0,"y":-0.8102628499631921,"x":2.8602422782736436}
                 * orientation : {"w":0.17221235548465064,"z":0.9850598248019138,"y":0,"x":0}
                 */

                private PositionBean position;
                private OrientationBean orientation;

                public PositionBean getPosition() {
                    return position;
                }

                public void setPosition(PositionBean position) {
                    this.position = position;
                }

                public OrientationBean getOrientation() {
                    return orientation;
                }

                public void setOrientation(OrientationBean orientation) {
                    this.orientation = orientation;
                }

                public static class PositionBean {
                    /**
                     * z : 0
                     * y : -0.8102628499631921
                     * x : 2.8602422782736436
                     */

                    private int z;
                    private double y;
                    private double x;

                    public int getZ() {
                        return z;
                    }

                    public void setZ(int z) {
                        this.z = z;
                    }

                    public double getY() {
                        return y;
                    }

                    public void setY(double y) {
                        this.y = y;
                    }

                    public double getX() {
                        return x;
                    }

                    public void setX(double x) {
                        this.x = x;
                    }
                }

                public static class OrientationBean {
                    /**
                     * w : 0.17221235548465064
                     * z : 0.9850598248019138
                     * y : 0
                     * x : 0
                     */

                    private double w;
                    private double z;
                    private int y;
                    private int x;

                    public double getW() {
                        return w;
                    }

                    public void setW(double w) {
                        this.w = w;
                    }

                    public double getZ() {
                        return z;
                    }

                    public void setZ(double z) {
                        this.z = z;
                    }

                    public int getY() {
                        return y;
                    }

                    public void setY(int y) {
                        this.y = y;
                    }

                    public int getX() {
                        return x;
                    }

                    public void setX(int x) {
                        this.x = x;
                    }
                }
            }

            public static class HeaderBean {
                /**
                 * seq : 1
                 * stamp : {"secs":1494482683,"nsecs":957325935}
                 * frame_id : map
                 */

                private int seq;
                private StampBean stamp;
                private String frame_id;

                public int getSeq() {
                    return seq;
                }

                public void setSeq(int seq) {
                    this.seq = seq;
                }

                public StampBean getStamp() {
                    return stamp;
                }

                public void setStamp(StampBean stamp) {
                    this.stamp = stamp;
                }

                public String getFrame_id() {
                    return frame_id;
                }

                public void setFrame_id(String frame_id) {
                    this.frame_id = frame_id;
                }

                public static class StampBean {
                    /**
                     * secs : 1494482683
                     * nsecs : 957325935
                     */

                    private int secs;
                    private int nsecs;

                    public int getSecs() {
                        return secs;
                    }

                    public void setSecs(int secs) {
                        this.secs = secs;
                    }

                    public int getNsecs() {
                        return nsecs;
                    }

                    public void setNsecs(int nsecs) {
                        this.nsecs = nsecs;
                    }
                }
            }
        }
    }
}
