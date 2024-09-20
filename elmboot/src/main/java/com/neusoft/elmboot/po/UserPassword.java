package com.neusoft.elmboot.po;

public class UserPassword {

    private String userId;
    private String oldPassword;
    private String newPassword;

    public String getUserId() {
        return userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
