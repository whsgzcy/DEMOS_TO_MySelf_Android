package com.example.super_yu.myexample.daemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * Created by super_yu on 2017/7/31.
 */

public class BootStartReceiver extends BroadcastReceiver {

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    /**
     * name : BeJson
     * url : http://www.bejson.com
     * page : 88
     * isNonProfit : true
     * address : {"street":"科技园路.","city":"江苏苏州","country":"中国"}
     * links : [{"name":"Google","url":"http://www.google.com"},{"name":"Baidu","url":"http://www.baidu.com"},{"name":"SoSo","url":"http://www.SoSo.com"}]
     */

    private String name;
    private String url;
    private int page;
    private boolean isNonProfit;
    private AddressBean address;
    private List<LinksBean> links;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            OnePx2Activity.startOnePx2Activity();
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            OnePx2Activity.killOnePx2Activity();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isIsNonProfit() {
        return isNonProfit;
    }

    public void setIsNonProfit(boolean isNonProfit) {
        this.isNonProfit = isNonProfit;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public List<LinksBean> getLinks() {
        return links;
    }

    public void setLinks(List<LinksBean> links) {
        this.links = links;
    }


    public static class AddressBean {
        /**
         * street : 科技园路.
         * city : 江苏苏州
         * country : 中国
         */

        private String street;
        private String city;
        private String country;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    public static class LinksBean {
        /**
         * name : Google
         * url : http://www.google.com
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
