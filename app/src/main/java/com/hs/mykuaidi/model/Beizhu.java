package com.hs.mykuaidi.model;

/**
 * Created by 黄晟 on 2016/9/23.
 */
public class Beizhu {
    private String beizhu;  //备注
    private String kuaididanhao;  // 快递单号
    private String date; // 时间
    private String type;  // 快递公司
    private String newTime;
    public Beizhu() {
    }

    /**
     *
     * @param beizhu 备注信息
     * @param kuaididan 快递单号
     */
    public Beizhu(String beizhu, String kuaididan) {
        this.beizhu = beizhu;
        this.kuaididanhao = kuaididan;
    }

    /**
     *
     * @param beizhu 备注
     * @param kuaididanhao 快递单号
     * @param date  时间
     */
    public Beizhu(String beizhu, String kuaididanhao,String date,String type) {
        this.beizhu = beizhu;
        this.kuaididanhao = kuaididanhao;
        this.date = date;
        this.type = type;
    }
    public Beizhu(String beizhu, String kuaididanhao,String date,String type,String newtime) {
        this.beizhu = beizhu;
        this.kuaididanhao = kuaididanhao;
        this.date = date;
        this.type = type;
        this.newTime = newtime;
    }

    public String getNewTime() {
        return newTime;
    }

    public void setNewTime(String newTime) {
        this.newTime = newTime;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getKuaididanhao() {
        return kuaididanhao;
    }

    public void setKuaididan(String kuaididan) {
        this.kuaididanhao = kuaididan;
    }

    public String toString() {
        return this.kuaididanhao+this.beizhu+this.date+this.type;
    }
}
