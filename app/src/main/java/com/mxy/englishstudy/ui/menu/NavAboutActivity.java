package com.mxy.englishstudy.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.mxy.englishstudy.R;
import com.mxy.englishstudy.base.BaseActivity;
import com.mxy.englishstudy.databinding.ActivityNavAboutBinding;
import com.mxy.englishstudy.utils.BaseTools;
import com.mxy.englishstudy.utils.CommonUtils;
import com.mxy.englishstudy.utils.PerfectClickListener;


public class NavAboutActivity extends BaseActivity<ActivityNavAboutBinding> {

    private static String string_url_update = "https://code.aliyun.com/yunmxy/SimpleEnglishStudyApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_about);
        showContentView();
        setTitle("关于简单英语");
        bindingView.tvVersionName.setText("当前版本 V" + BaseTools.getVersionName());


        // 直接写在布局文件里会很耗内存
        Glide.with(this).load(R.mipmap.ic_launcher).into(bindingView.ivIcon);


        initListener();
    }

    private void initListener() {

        bindingView.tvFunction.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                BaseTools.openLink(v.getContext(), string_url_update);
            }
        });
        bindingView.tvNewVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseTools.openLink(v.getContext(), CommonUtils.getString(R.string.string_url_new_version));
            }
        });
    }



    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavAboutActivity.class);
        mContext.startActivity(intent);
    }
}
