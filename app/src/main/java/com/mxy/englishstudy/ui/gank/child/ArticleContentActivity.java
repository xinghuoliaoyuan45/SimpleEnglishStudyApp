package com.mxy.englishstudy.ui.gank.child;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.base.BaseActivity;
import com.mxy.englishstudy.bean.ArticleBean;
import com.mxy.englishstudy.databinding.ActivityArticleBinding;


/**
 * Created by mxy on 2017/4/16.
 */

public class ArticleContentActivity extends BaseActivity<ActivityArticleBinding> {
    private static String TAG = ArticleContentActivity.class.getSimpleName();

    ArticleBean.BeauArticleBean art;
    String en_content;
    String ch_content;


    public ArticleContentActivity() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        setTitle("英语美文");
        art = getIntent().getParcelableExtra("artContent");
        ch_content=art.getCh_content();
        en_content=art.getEn_content();
        initView();

    }

    private void initView() {
        showContentView();
        bindingView.tvChContent.setText(ch_content);
        bindingView.tvEnContent.setText(en_content);
    }

}
