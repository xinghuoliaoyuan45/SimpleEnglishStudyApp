package com.mxy.englishstudy.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by mxy on 2017/4/15.
 */

public class ArticleBean {
    private List<BeauArticleBean> beau_article;

    public List<BeauArticleBean> getBeau_article() {
        return beau_article;
    }

    public void setBeau_article(List<BeauArticleBean> beau_article) {
        this.beau_article = beau_article;
    }

    public static class BeauArticleBean implements Parcelable {
        @Override
        public String toString() {
            return "BeauArticleBean{" +
                    "title='" + title + '\'' +
                    ", en_content='" + en_content + '\'' +
                    ", ch_content='" + ch_content + '\'' +
                    '}';
        }

        /**
         * title : Youth 青春
         * en_content : Youth 

         Youth is not a time of life; it is a state of mind; it is not a matter of rosy cheeks, red lips and supple knees; it is a matter of the will, a quality of the imagination, a vigor of the emotions; it is the freshness of the deep springs of life. 

         Youth means a temperamental predominance of courage over timidity, of the appetite for adventure over the love of ease. This often exists in a man of 60 more than a boy of 20. Nobody grows old merely by a number of years. We grow old by deserting our ideals. 

         Years may wrinkle the skin, but to give up enthusiasm wrinkles the soul. Worry, fear, self-distrust bows the heart and turns the spirit back to dust. 

         Whether 60 or 16, there is in every human being’s heart the lure of wonders, the unfailing appetite for what’s next and the joy of the game of living. In the center of your heart and my heart, there is a wireless station; so long as it receives messages of beauty, hope, courage and power from man and from the infinite, so long as you are young. 

         When your aerials are down, and your spirit is covered with snows of cynicism and the ice of pessimism, then you’ve grown old, even at 20; but as long as your aerials are up, to catch waves of optimism, there’s hope you may die young at 80.
         * ch_content : 青春 
         青春不是年华，而是心境；青春不是桃面、丹唇、柔膝，而是深沉的意志，恢宏的想象，炙热的恋情；青春是生命的深泉在涌流。 
         青春气贯长虹，勇锐盖过怯弱，进取压倒苟安。如此锐气，二十后生而有之，六旬男子则更多见。年岁有加，并非垂老，理想丢弃，方堕暮年。 
         岁月悠悠，衰微只及肌肤；热忱抛却，颓废必致灵魂。忧烦，惶恐，丧失自信，定使心灵扭曲，意气如灰。 
         无论年届花甲，拟或二八芳龄，心中皆有生命之欢乐，奇迹之诱惑，孩童般天真久盛不衰。人人心中皆有一台天线，只要你从天上人间接受美好、希望、欢乐、勇气和力量的信号，你就青春永驻，风华常存。 、 
         一旦天线下降，锐气便被冰雪覆盖，玩世不恭、自暴自弃油然而生，即使年方二十，实已垂垂老矣；然则只要树起天线，捕捉乐观信号，你就有望在八十高龄告别尘寰时仍觉年轻。
         */

        private String title;
        private String en_content;
        private String ch_content;



        protected BeauArticleBean(Parcel in) {
            title = in.readString();
            en_content = in.readString();
            ch_content = in.readString();
        }

        public static final Creator<BeauArticleBean> CREATOR = new Creator<BeauArticleBean>() {
            @Override
            public BeauArticleBean createFromParcel(Parcel in) {
                return new BeauArticleBean(in);
            }

            @Override
            public BeauArticleBean[] newArray(int size) {
                return new BeauArticleBean[size];
            }
        };

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEn_content() {
            return en_content;
        }

        public void setEn_content(String en_content) {
            this.en_content = en_content;
        }

        public String getCh_content() {
            return ch_content;
        }

        public void setCh_content(String ch_content) {
            this.ch_content = ch_content;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(en_content);
            dest.writeString(ch_content);
        }
    }
}
