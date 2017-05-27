package com.mxy.englishstudy.ui.loginregister;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.mxy.englishstudy.R;
import com.mxy.englishstudy.base.BaseActivity;
import com.mxy.englishstudy.databinding.ActivityRegisterBinding;
import com.mxy.englishstudy.utils.ToastUtil;

/**
 * Created by mxy on 2017/4/20.
 */

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {
    private Boolean canRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("注册");
        showContentView();
        initEvent();
    }

    private void registerData() {
        AVObject account = new AVObject("Account");
        account.put("username", bindingView.etAccount.getText().toString().trim());
        account.put("password", bindingView.etPsw.getText().toString().trim());
        account.put("headimage", "a12");
        account.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    ToastUtil.showToast("注册成功");
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                    finish();
                } else {
                    // 失败的话，请检查网络环境以及 SDK 配置是否正确
                    Snackbar.make(bindingView.getRoot(), "储存失败", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkUserNameAndregisterData() {

        String username = bindingView.etAccount.getText().toString().trim();
        String cql = " select * from Account where username = ?";
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (avCloudQueryResult.getResults().size() == 0) {
                    registerData();
                }else{
                    Snackbar.make(bindingView.getRoot(),"该用户名已经被注册，换个名字",Snackbar.LENGTH_SHORT).show();
                }
            }
        }, username);

    }

    private void initEvent() {
        bindingView.layRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (canRegister) {
                    checkUserNameAndregisterData();
                } else return;
            }
        });
        bindingView.etPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkCanRegister();
                if (bindingView.etPsw.getText().toString().equals("")) {
                    bindingView.imgPswDel.setVisibility(View.INVISIBLE);
                } else {
                    bindingView.imgPswDel.setVisibility(View.VISIBLE);
                }
            }
        });
        bindingView.etPswRe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkCanRegister();
                if (bindingView.etPsw.getText().toString().equals("")) {
                    bindingView.imgPswDelRe.setVisibility(View.INVISIBLE);
                } else {
                    bindingView.imgPswDelRe.setVisibility(View.VISIBLE);
                }
            }
        });
        bindingView.etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkCanRegister();
                if (bindingView.etAccount.getText().toString().equals("")) {
                    bindingView.imgAccountDel.setVisibility(View.INVISIBLE);
                } else {
                    bindingView.imgAccountDel.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void checkCanRegister() {
        if (!bindingView.etAccount.getText().toString().equals("") && !bindingView.etPsw.getText().toString().equals("") && bindingView.etPswRe.getText().toString().equals(bindingView.etPsw.getText().toString())) {
            canRegister = true;
            bindingView.tvRegister.setTextColor(Color.RED);

            bindingView.layRegister.setBackgroundResource(R.drawable.login_layout_cantpress);
        } else {
            canRegister = false;
            bindingView.tvRegister.setTextColor(Color.WHITE);
            bindingView.layRegister.setBackgroundResource(R.drawable.login_layout);

        }
    }

}
