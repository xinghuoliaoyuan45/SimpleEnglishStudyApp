package com.mxy.englishstudy.bean.wordsclass;

import com.avos.avoscloud.AVObject;

/**
 * Created by mxy on 2017/4/26.
 */

public class FoodDrinkBean extends AVObject {


    private String en;
    private String ch;

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }
}
