package com.mxy.englishstudy.bean;

import java.util.ArrayList;

/**
 * Created by mxy on 2017/4/4.
 */

/**
 * 从必应网页查询
 * 解析返回的html(html-parse)
 * by jsonp
 */

public class BingModel {
    public BingModel() {

    }

    private String word;//单词
    private String pronunciation;//音标 美 英 拼接好的字符串
    private String voice;//单词读音
    private String meaning; //含义 拼接好的字符串
    private ArrayList<Sentence> sams;//例句及相关功能

    public static class Sentence {
        private String number;
        private String eng;
        private String chn;
        private String mp3Url;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getEng() {
            return eng;
        }

        public void setEng(String eng) {
            this.eng = eng;
        }

        public String getChn() {
            return chn;
        }

        public void setChn(String chn) {
            this.chn = chn;
        }

        public String getMp3Url() {
            return mp3Url;
        }

        public void setMp3Url(String mp3Url) {
            this.mp3Url = mp3Url;
        }

        @Override
        public String toString() {
            return "Sentence{" +
                    "number='" + number + '\'' +
                    ", eng='" + eng + '\'' +
                    ", chn='" + chn + '\'' +
                    ", mp3Url='" + mp3Url + '\'' +
                    '}';
        }
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public ArrayList<Sentence> getSams() {
        return sams;
    }

    public void setSams(ArrayList<Sentence> sams) {
        this.sams = sams;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return "BingModel{" +
                "word='" + word + '\'' +
                ", pronunciation='" + pronunciation + '\'' +
                ", voice='" + voice + '\'' +
                ", meaning='" + meaning + '\'' +
                ", sams=" + sams +
                '}';
    }
}

