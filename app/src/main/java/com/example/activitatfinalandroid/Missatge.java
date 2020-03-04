package com.example.activitatfinalandroid;

public class Missatge {
    private String id;
    private String msg;
    private String date;
    private String userId;
    private String userName;
    public Missatge() {
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getId() {
        return id;
    }
    public String getMsg() {
        return msg;
    }
    public String getDate() {
        return date;
    }
    public String getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
}