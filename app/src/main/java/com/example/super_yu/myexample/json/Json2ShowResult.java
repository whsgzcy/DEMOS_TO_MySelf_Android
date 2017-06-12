package com.example.super_yu.myexample.json;

import java.util.List;

/**
 * Created by super_yu on 2017/6/9.
 */

public class Json2ShowResult {


    /**
     * floor : 4
     * room : {"A":[{"room_bumber":"403","map_name":"map_4_A_403"},{"room_bumber":"406","map_name":"map_4_A_406"},{"room_bumber":"407","map_name":"map_4_A_407"}],"B":[{"room_bumber":"401","map_name":"map_4_B_401"},{"room_bumber":"402","map_name":"map_4_B_402"},{"room_bumber":"404","map_name":"map_4_B_404"}],"Z":[{"room_bumber":"405","map_name":"map_4_Z_405"},{"room_bumber":"409","map_name":"map_4_Z_409"},{"room_bumber":"401","map_name":"map_4_Z_401"}]}
     */

    private int floor;
    private RoomBean room;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public RoomBean getRoom() {
        return room;
    }

    public void setRoom(RoomBean room) {
        this.room = room;
    }

    public static class RoomBean {
        private List<ABean> A;
        private List<BBean> B;
        private List<ZBean> Z;

        public List<ABean> getA() {
            return A;
        }

        public void setA(List<ABean> A) {
            this.A = A;
        }

        public List<BBean> getB() {
            return B;
        }

        public void setB(List<BBean> B) {
            this.B = B;
        }

        public List<ZBean> getZ() {
            return Z;
        }

        public void setZ(List<ZBean> Z) {
            this.Z = Z;
        }

        public static class ABean {
            /**
             * room_bumber : 403
             * map_name : map_4_A_403
             */

            private String room_bumber;
            private String map_name;

            public String getRoom_bumber() {
                return room_bumber;
            }

            public void setRoom_bumber(String room_bumber) {
                this.room_bumber = room_bumber;
            }

            public String getMap_name() {
                return map_name;
            }

            public void setMap_name(String map_name) {
                this.map_name = map_name;
            }
        }

        public static class BBean {
            /**
             * room_bumber : 401
             * map_name : map_4_B_401
             */

            private String room_bumber;
            private String map_name;

            public String getRoom_bumber() {
                return room_bumber;
            }

            public void setRoom_bumber(String room_bumber) {
                this.room_bumber = room_bumber;
            }

            public String getMap_name() {
                return map_name;
            }

            public void setMap_name(String map_name) {
                this.map_name = map_name;
            }
        }

        public static class ZBean {
            /**
             * room_bumber : 405
             * map_name : map_4_Z_405
             */

            private String room_bumber;
            private String map_name;

            public String getRoom_bumber() {
                return room_bumber;
            }

            public void setRoom_bumber(String room_bumber) {
                this.room_bumber = room_bumber;
            }

            public String getMap_name() {
                return map_name;
            }

            public void setMap_name(String map_name) {
                this.map_name = map_name;
            }
        }
    }
}
