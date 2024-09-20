package com.neusoft.elmboot.controller;
import com.neusoft.elmboot.po.User;

public class Lock_info {

    public static User user_lock(User user) {
        user.setPassword("");
        return user;
    }

}
