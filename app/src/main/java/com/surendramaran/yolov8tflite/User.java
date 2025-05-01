package com.surendramaran.yolov8tflite;

public class User {

    private Long user_id;
    private String login;
    private String password;

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }
}
