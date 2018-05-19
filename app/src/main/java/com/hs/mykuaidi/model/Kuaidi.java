package com.hs.mykuaidi.model;

/**
 * Created by 黄晟 on 2016/9/3.
 */
public class Kuaidi {
    private String Time; //日期
    private String shijian;
    private String Context;
    private String newTime;

    public Kuaidi() {

    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public Kuaidi(String context, String time, String shijian) {
        this.shijian = shijian;
        Context = context;
        Time = time;
    }

    public Kuaidi(String context, String time, String shijian, String newtime) {
        this.shijian = shijian;
        Context = context;
        Time = time;
        newTime = newtime;
    }

    public String getNewTime() {
        return newTime;
    }

    public void setNewTime(String newTime) {
        this.newTime = newTime;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
