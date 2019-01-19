package com.bwie.wang.bwm.activity;

/**
 * date:2019/1/18.
 *
 * @author 王丙均
 */

public class EventBusMassage {
    String phone;
    String pwd;

    public EventBusMassage(String phone, String pwd) {
        this.phone = phone;
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
