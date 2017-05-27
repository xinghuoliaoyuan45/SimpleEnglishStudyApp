package com.mxy.englishstudy.http;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.adapter.SampleAdapter;
import com.mxy.englishstudy.bean.BingModel;
import com.mxy.englishstudy.databinding.ActivityTranslateBinding;
import com.mxy.englishstudy.utils.Player;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mxy on 2017/5/3.
 */

public class BingAsyncTask extends AsyncTask<String, Void, BingModel> {
    private SampleAdapter adapter;
    private Context mcontext;
    private ActivityTranslateBinding bindingView;

    public BingAsyncTask(Context mcontext, ActivityTranslateBinding view) {
        this.mcontext = mcontext;
        this.bindingView = view;

    }

    private final String REGEX_MP3 = "https://[A-z./\\d]*?mp3";
    final Pattern p = Pattern.compile(REGEX_MP3);

    @Override
    protected BingModel doInBackground(String... params) {


        BingModel bingModel = new BingModel();

        Document doc = null;
        try {
            doc = Jsoup.connect(params[0]).get();

            //查询的单词
            String wordname = doc.getElementById("headword").child(0).child(0).text();
            Log.e("单词", wordname);
            bingModel.setWord(wordname);
            //音标  美hd_prUS   英hd_pr
            String hd_pr = doc.getElementsByClass("hd_pr").get(0).text();
            Log.e("英hd_pr", hd_pr);
            String hd_prUS = doc.getElementsByClass("hd_prUS").get(0).text();
            Log.e("美hd_prUS", hd_prUS);
            bingModel.setPronunciation(hd_prUS + "\n" + hd_pr);
            //读音
            String voiceHtml = doc.getElementsByClass("bigaud").attr("onmouseover");
            //正则表达式拿出voice
            Matcher matcher_word = p.matcher(voiceHtml);
            String voice = new String();
            while (matcher_word.find()) {
                voice = matcher_word.group();
            }
            Log.e("美读音", voice);
            bingModel.setVoice(voice);
            //单词意思
            StringBuffer meaning = new StringBuffer();
            Elements meaning_ul = doc.getElementsByTag("ul").get(1).children();
            for (Element meaning_li : meaning_ul) {
                meaning.append(meaning_li.text());
                meaning.append("\n");
            }
            String meaningString = meaning.toString().replace("\n网络", "\n网络:");

            Log.e("单词意思", meaningString);
            bingModel.setMeaning(meaningString);

            /////////////////句子
            ArrayList<BingModel.Sentence> sentence_list = new ArrayList<BingModel.Sentence>();

            Element element = doc.getElementById("sentenceSeg");
            Elements se_lis = element.getElementsByClass("se_li");
            for (Element se_li : se_lis) {
                //new 一个 BingModel.Sentence
                BingModel.Sentence sentence = new BingModel.Sentence();
                String title = se_li.getElementsByClass("se_n_d").get(0).text();
                sentence.setNumber(title);
                Elements se_li1s = se_li.getElementsByClass("se_li1");
                for (Element se_li1 : se_li1s) {
                    StringBuffer en = new StringBuffer();
                    StringBuffer cn = new StringBuffer();

                    Elements se_li1_en_childs = se_li1.getElementsByClass("sen_en").get(0).children();
                    Elements se_li1_cn_childs = se_li1.getElementsByClass("sen_cn").get(0).children();
                    Elements se_li1_mm_div = se_li1.getElementsByClass("mm_div").get(0).children();
                    for (Element se_li1_en_child : se_li1_en_childs) {
                        String one_en = se_li1_en_child.text();
                        en.append(one_en);
                        en.append(" ");
                    }
                    for (Element se_li1_cn_child : se_li1_cn_childs) {
                        String one_cn = se_li1_cn_child.text();
                        cn.append(one_cn);
                    }
                    String str_en=en.toString();
                    str_en=str_en.substring(0,str_en.length()-3)+".";
                    sentence.setEng(str_en);
                    sentence.setChn(cn.toString());
                    Element a = se_li1_mm_div.get(0).child(0);
                    String onmousedown = a.attr("onmousedown");
                    //正则 匹配 例句
                    Matcher matcher_sentence = p.matcher(onmousedown);
                    String voice_sentence = new String();
                    while (matcher_sentence.find()) {
                        voice_sentence = matcher_sentence.group();
                    }
                    Log.e("例句的mp3voice", voice_sentence);
                    sentence.setMp3Url(voice_sentence);
                }
                sentence_list.add(sentence);
            }
            Log.e("sentence_list", sentence_list.toString());
            bingModel.setSams(sentence_list);

        } catch (Exception e) {
            Snackbar.make(bindingView.buttonTranslate, R.string.trans_error, Snackbar.LENGTH_SHORT).show();
        }

        return bingModel;
    }

    @Override
    protected void onPostExecute(final BingModel bingModel) {
        super.onPostExecute(bingModel);
        if (bingModel.getSams() == null) {
            Snackbar.make(bindingView.getRoot(), R.string.trans_error, Snackbar.LENGTH_SHORT).show();
            bindingView.progressBar.setVisibility(View.GONE);
            bindingView.include.cardView.setVisibility(View.VISIBLE);
        } else {
            adapter = new SampleAdapter(mcontext, bingModel);
            bindingView.recyclerView.setAdapter(adapter);

            //把单词 音标 翻译拼出来 注意 要加换行符
            String allMessage = bingModel.getWord() + "\n" + bingModel.getPronunciation() + "\n" + bingModel.getMeaning();
            bindingView.include.textViewOutput.setText(allMessage);
            bindingView.include.imageViewVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Player.getInstance().play(bingModel.getVoice());
                }
            });
            bindingView.progressBar.setVisibility(View.GONE);
            bindingView.include.cardView.setVisibility(View.VISIBLE);
        }

    }
}
