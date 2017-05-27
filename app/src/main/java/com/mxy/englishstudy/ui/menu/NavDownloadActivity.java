package com.mxy.englishstudy.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.base.BaseActivity;
import com.mxy.englishstudy.databinding.ActivityNavDownloadBinding;
import com.mxy.englishstudy.utils.PerfectClickListener;
import com.mxy.englishstudy.utils.QRCodeUtil;
import com.mxy.englishstudy.utils.ShareUtils;

public class NavDownloadActivity extends BaseActivity<ActivityNavDownloadBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_download);
        showContentView();

        setTitle("扫码下载");
        String url = "https://fir.im/simpleEnglish";
        QRCodeUtil.showThreadImage(this, url, bindingView.ivErweima, R.mipmap.ic_launcher);
        bindingView.tvShare.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ShareUtils.share(v.getContext(), R.string.string_share_text);
            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavDownloadActivity.class);
        mContext.startActivity(intent);
    }
}
