package com.mxy.englishstudy.ui.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.okhttp.internal.framed.FrameReader;
import com.mxy.englishstudy.MainActivity;
import com.mxy.englishstudy.R;
import com.mxy.englishstudy.adapter.SelectHeadAdapter;
import com.mxy.englishstudy.base.BaseActivity;
import com.mxy.englishstudy.databinding.ActivitySelectHeadBinding;
import com.mxy.englishstudy.utils.DebugUtil;
import com.mxy.englishstudy.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by mxy on 2017/4/21.
 */

public class SelectHeadActivity extends BaseActivity<ActivitySelectHeadBinding> {
    String cql = "update Account set headimage = ? where objectId = ?";

    String user_id;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_head);
        sp = getApplicationContext().getSharedPreferences("FILE_USER", MODE_PRIVATE);
        user_id = sp.getString("nowUserId", "-1");
        showContentView();
        initView();
        initEvent();

    }

    private void initEvent() {

        bindingView.btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headimage=SelectHeadAdapter.ll.getFirst();
                if (user_id != "-1" && !headimage.equals("a0")) {
                    AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
                        @Override
                        public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                            if (e == null) {
                                ToastUtil.showToast("换头像成功");
                            }
                        }
                    }, headimage, user_id);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                DebugUtil.error("image_head", headimage);
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                finish();

            }
        });
    }

    private void initView() {
        List<String> list_name = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            list_name.add("a" + i);
        }
        GridLayoutManager mgr = new GridLayoutManager(getApplicationContext(), 4);
        bindingView.rvHead.setLayoutManager(mgr);
        bindingView.rvHead.setAdapter(new SelectHeadAdapter(this, list_name));
        mBaseBinding.toolBar.setVisibility(View.VISIBLE);
        mBaseBinding.toolBar.setTitle("头像");

    }


}
