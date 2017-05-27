package com.mxy.englishstudy.bean;

/**
 * Created by mxy on 2017/4/14.
 */

public class DailyOne {
    /**
     * sid : 2568
     * tts : http://news.iciba.com/admin/tts/2017-04-14-day.mp3
     * content : Protect your innocence… Our innocence allows us to do what the practical mind prohibits.
     * note : 保护你的纯真… 天真让我们做实际心态所不允许的事。
     * love : 0
     * translation : 词霸小编：成长的悲哀或许就在于，人们再也没有机会去表现纯真和幼稚。
     * picture : http://cdn.iciba.com/news/word/20170414.jpg
     * picture2 : http://cdn.iciba.com/news/word/big_20170414b.jpg
     * caption : 词霸每日一句
     * dateline : 2017-04-14
     * s_pv : 0
     * sp_pv : 0
     * tags : [{"id":null,"name":null},{"id":null,"name":null}]
     * fenxiang_img : http://cdn.iciba.com/web/news/longweibo/imag/2017-04-14.jpg
     */

    private String tts;
    private String content;
    private String note;
    private String picture2;
    private String dateline;
    public DailyOne(){

    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    @Override
    public String toString() {
        return "DailyOne{" +
                "tts='" + tts + '\'' +
                ", content='" + content + '\'' +
                ", note='" + note + '\'' +
                ", picture2='" + picture2 + '\'' +
                ", dateline='" + dateline + '\'' +
                '}';
    }
}
