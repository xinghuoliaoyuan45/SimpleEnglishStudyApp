package com.mxy.englishstudy.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.base.BaseFragment;
import com.mxy.englishstudy.databinding.FragmentGankBinding;
import com.mxy.englishstudy.ui.gank.child.BeautifulArticleFragment;
import com.mxy.englishstudy.ui.gank.child.CustomFragment;
import com.mxy.englishstudy.ui.gank.child.EverydayFragment;
import com.mxy.englishstudy.view.MyFragmentPagerAdapter;

import java.util.ArrayList;



public class GankFragment extends BaseFragment<FragmentGankBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showLoading();
        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻3个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        bindingView.vpGank.setAdapter(myAdapter);
        // 左右预加载页面的个数
        bindingView.vpGank.setOffscreenPageLimit(3);
        myAdapter.notifyDataSetChanged();
        bindingView.tabGank.setTabMode(TabLayout.MODE_FIXED);
        bindingView.tabGank.setupWithViewPager(bindingView.vpGank);
        showContentView();

    }

    @Override
    public int setContent() {
        return R.layout.fragment_gank;
    }

    private void initFragmentList() {
        mTitleList.add("每日推荐");
        mTitleList.add("美文阅读");
        mTitleList.add("分类记忆");

        mFragments.add(new EverydayFragment());
        mFragments.add(new BeautifulArticleFragment());
        mFragments.add(new CustomFragment());

    }

}
