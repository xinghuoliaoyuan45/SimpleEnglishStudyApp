package com.mxy.englishstudy.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.mxy.englishstudy.R;
import com.mxy.englishstudy.adapter.PersonalWordItemAdapter;
import com.mxy.englishstudy.base.BaseActivity;
import com.mxy.englishstudy.bean.UserWord;
import com.mxy.englishstudy.databinding.ActivityPersonwordBinding;
import com.mxy.englishstudy.utils.DebugUtil;

import java.util.List;

/**
 * Created by mxy on 2017/4/28.
 */

public class PersonWordActivity extends BaseActivity<ActivityPersonwordBinding> {
    private SharedPreferences sp;
    private String cql = "select *  from UserWords where userid = ?";
    private String nowUserId;
    private String nowUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personword);
        //拿到当前用户的对象
        sp = getApplicationContext().getSharedPreferences("FILE_USER", MODE_PRIVATE);
        nowUserId = sp.getString("nowUserId", "tourist");
        nowUserName = sp.getString("nowUserName", "小明");
        bindingView.tvUsername.setText(nowUserName);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //为recyclerView瀑布流布局
        bindingView.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        bindingView.recyclerView.setItemAnimator(new DefaultItemAnimator());
        showContentView();
        initView();

    }

    private void initView() {
        if (nowUserId.equals("tourist")) {
            return;
        }

        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e == null) {
                    List<UserWord> userwords = (List<UserWord>) avCloudQueryResult.getResults();
                    PersonalWordItemAdapter personalWordItemAdapter = new PersonalWordItemAdapter(userwords, getApplicationContext());
                    bindingView.recyclerView.setAdapter(personalWordItemAdapter);
                    DebugUtil.error(userwords.toString());
                } else {
                    Log.e("33333", e.toString());
                }
            }
        }, UserWord.class, nowUserId);
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, PersonWordActivity.class);
        mContext.startActivity(intent);
    }


}
