package com.example.super_yu.myexample.json;

import java.util.List;

/**
 * Created by super_yu on 2017/6/9.
 */

public class MapResult {


    private List<WaypointsBean> waypoints;

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
         * name : map_6_A_601
         */

        private int goal_timeout;
        private PoseBean pose;
        private int close_enough;
        private String failure_mode;
        private HeaderBean header;
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
