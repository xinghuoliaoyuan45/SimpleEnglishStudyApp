package com.mxy.englishstudy.bean;

import com.avos.avoscloud.AVObject;

/**
 * Created by mxy on 2017/4/21.
 */

public class Account extends AVObject {
    private String username;
    private String password;
    private String objectId;
    private String headimage;

    public String getheadimage() {
        return headimage;
    }

    public void setheadimage(String headimage) {
        this.headimage = headimage;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", objectId='" + objectId + '\'' +
                ", headimage='" + headimage + '\'' +
                '}';
    }


}
