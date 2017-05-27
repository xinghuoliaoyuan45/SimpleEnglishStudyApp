package com.mxy.englishstudy.ui.loginregister;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.mxy.englishstudy.MainActivity;
import com.mxy.englishstudy.R;
import com.mxy.englishstudy.bean.Account;
import com.mxy.englishstudy.utils.DebugUtil;
import com.mxy.englishstudy.utils.ToastUtil;

import java.util.List;

/**
 * Created by mxy on 2017/4/18.
 */

public class LoginActivity extends Activity {
    ImageView iv_top;//最上面的图片
    EditText et_account;//账号输入
    EditText et_psw;//密码输入
    LinearLayout lay_login;//登录框
    TextView tv_login;//登录文字
    TextView tv_zhdl;//随便看看
    TextView tv_wjmm;//忘记密码
    LinearLayout lay_zczh;//注册账号
    ImageView img_account_del;//账号删除
    ImageView img_psw_del;//密码删除
    boolean canlogin = false;
    boolean judge = false;
    final String cql = "select * from Account where username = ?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        tv_zhdl.setText("随便看看");
        tv_zhdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                finish();

            }
        });
        //登录本来是暗色，两个edittext都有值就会变白色
        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkCanLogin();
                if (et_account.getText().toString().equals("")) {
                    img_account_del.setVisibility(View.INVISIBLE);
                } else {
                    img_account_del.setVisibility(View.VISIBLE);
                }
            }
        });
        img_account_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_account.setText("");
            }
        });
        et_psw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkCanLogin();
                if (et_psw.getText().toString().equals("")) {
                    img_psw_del.setVisibility(View.INVISIBLE);
                } else {
                    img_psw_del.setVisibility(View.VISIBLE);
                }
            }
        });
        img_psw_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_psw.setText("");
            }
        });
        lay_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (canlogin) {
                    AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
                        @Override
                        public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                            if (e == null) {
                                // 操作成功
                                List<Account> accounts = (List<Account>) avCloudQueryResult.getResults();
                                DebugUtil.error(accounts.toString());
                                for (Account account : accounts) {
                                    judge = account.getPassword().equals(et_psw.getText().toString());
                                    if (judge) {
                                        String userId = account.getObjectId();
                                        String username = account.getUsername();
                                        SharedPreferences sp =
                                                getApplicationContext().getSharedPreferences("FILE_USER", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("nowUserId", userId);
                                        editor.putString("nowUserName", username);

                                        editor.commit();

                                        break;
                                    }
                                }
                                if (judge) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                                    finish();
                                } else {
                                    Snackbar.make(v, "用户名或密码有误", Snackbar.LENGTH_SHORT).show();
                                }

                            } else {
                                Snackbar.make(v, "查询失败", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }, Account.class, et_account.getText().toString());

                }
            }
        });

        tv_wjmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
                    @Override
                    public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {

                        if (e == null) {
                            // 操作成功
                            List<Account> accounts = (List<Account>) avCloudQueryResult.getResults();
                            if (accounts == null || accounts.size() == 0) {
                                Snackbar.make(tv_wjmm, "您输入的用户名错误", Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(tv_wjmm, "您的密码为" + accounts.get(0).getPassword().toString(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                }, Account.class, et_account.getText().toString());
            }
        });
        lay_zczh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);

            }
        });

    }


    public void checkCanLogin() {
        if (!et_account.getText().toString().equals("") && !et_psw.getText().toString().equals("")) {
            canlogin = true;
            tv_login.setTextColor(Color.RED);
            lay_login.setBackgroundResource(R.drawable.login_layout_cantpress);
        } else {
            canlogin = false;
            tv_login.setTextColor(Color.WHITE);
            lay_login.setBackgroundResource(R.drawable.login_layout);

        }
    }

    public void init() {
        iv_top = (ImageView) findViewById(R.id.iv_top);
        et_account = (EditText) findViewById(R.id.et_account);
        et_psw = (EditText) findViewById(R.id.et_psw);
        lay_login = (LinearLayout) findViewById(R.id.lay_login);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_zhdl = (TextView) findViewById(R.id.tv_zhdl);
        tv_wjmm = (TextView) findViewById(R.id.tv_wjmm);
        lay_zczh = (LinearLayout) findViewById(R.id.lay_zczh);
        img_account_del = (ImageView) findViewById(R.id.img_account_del);
        img_psw_del = (ImageView) findViewById(R.id.img_psw_del);

    }


}
