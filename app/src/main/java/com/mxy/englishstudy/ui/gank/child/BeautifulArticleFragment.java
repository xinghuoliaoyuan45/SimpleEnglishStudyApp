package com.mxy.englishstudy.ui.gank.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.mxy.englishstudy.R;
import com.mxy.englishstudy.adapter.ActicleTileAdapter;
import com.mxy.englishstudy.base.BaseFragment;
import com.mxy.englishstudy.bean.ArticleBean;
import com.mxy.englishstudy.databinding.FragmentBeautifularticleBinding;
import com.mxy.englishstudy.http.cache.ACache;
import com.mxy.englishstudy.utils.DebugUtil;
import com.mxy.englishstudy.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 30篇美文写死
 */
public class BeautifulArticleFragment extends BaseFragment<FragmentBeautifularticleBinding> {

    private static final String TAG = "BeautifulArticle";
    List<ArticleBean.BeauArticleBean> artList = new ArrayList<>();
    ArticleBean articleBean;
    RecyclerView recyclerView;

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private ACache aCache;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        DebugUtil.error("--BeautifulArticleFragment   ----onActivityCreated");

        initView();
        initEvent();
        isPrepared = true;
        loadData();


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new ActicleTileAdapter(artList, getContext()));
    }

    @Override
    protected void loadData() {
        if (!isPrepared ) {
            return;
        }
        DebugUtil.error("BeautifulArticleFragment loadData");

        try {
            InputStream inputStream = getContext().getAssets().open("data.json");
            String data = StringUtils.inputStream2String(inputStream);
            Gson gson = new Gson();
            articleBean = gson.fromJson(data, ArticleBean.class);
        } catch (IOException e) {
            Log.e(TAG, "读取data.json异常");
        }
        artList = articleBean.getBeau_article();


    }

    private void initView() {

        recyclerView = bindingView.xrvBeautifulTitle;
        showContentView();
    }

    private void initEvent() {

    }
    @Override
    protected void onRefresh() {
        showContentView();
        loadData();
    }


    @Override
    public int setContent() {
        return R.layout.fragment_beautifularticle;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.error("--BeautifulArticleFragment   ----onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        DebugUtil.error("BeautifulArticleFragment    ----onResume");
    }


}


