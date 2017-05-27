package com.mxy.englishstudy.app;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.avos.avoscloud.AVOSCloud;
import com.example.http.HttpUtils;
import com.mxy.englishstudy.utils.DebugUtil;


public class SimpleEnglishStudyApplication extends Application {

    private static SimpleEnglishStudyApplication simpleEnglishApplication;

    public static SimpleEnglishStudyApplication getInstance() {
        return simpleEnglishApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"LrX8HQNY1LokKKOHMDBokccH-gzGzoHsz","lXtrRHdgCYL6GMX6eSHYCyBk");
        simpleEnglishApplication = this;

        //初始化httpUtils
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
        initTextSize();
    }

    /**
     * 使其系统更改字体大小无效
     */
    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

}
