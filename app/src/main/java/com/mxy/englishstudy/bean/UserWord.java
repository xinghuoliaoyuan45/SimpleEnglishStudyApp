package com.mxy.englishstudy.bean;

import com.avos.avoscloud.AVObject;

/**
 * Created by mxy on 2017/4/28.
 */

public class UserWord extends AVObject {
    String objectId;
    String word;
    String username;
    String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }


}
