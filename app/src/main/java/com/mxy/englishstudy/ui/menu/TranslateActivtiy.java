package com.mxy.englishstudy.ui.menu;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVDeleteOption;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.GetCallback;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mxy.englishstudy.R;
import com.mxy.englishstudy.adapter.SampleAdapter;
import com.mxy.englishstudy.app.Constants;
import com.mxy.englishstudy.base.BaseActivity;
import com.mxy.englishstudy.bean.BingModel;

import com.mxy.englishstudy.bean.UserWord;
import com.mxy.englishstudy.databinding.ActivityTranslateBinding;

import com.mxy.englishstudy.http.BingAsyncTask;
import com.mxy.englishstudy.utils.CheckNetwork;
import com.mxy.englishstudy.utils.DebugUtil;
import com.mxy.englishstudy.utils.Player;
import com.mxy.englishstudy.utils.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mxy.englishstudy.app.Constants.BING_BASE_AGE;
import static com.mxy.englishstudy.app.Constants.BING_BASE_BEFORE;

/**
 * Created by mxy on 2017/4/4.
 */


public class TranslateActivtiy extends BaseActivity<ActivityTranslateBinding> {
    private static String TAG = TranslateActivtiy.class.getSimpleName();


    private String result = null;

    private boolean isMarked = false;

    private String urlVoice = null;

    private String nowUserId;
    private String nowUserName;
    SharedPreferences sp;


    public TranslateActivtiy() {

    }


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        setTitle("翻译");
        if (!CheckNetwork.isNetworkConnected(getApplicationContext())) {
            showNoNetwork();
        }
        //拿到当前用户的对象
        sp = getApplicationContext().getSharedPreferences("FILE_USER", MODE_PRIVATE);
        nowUserId = sp.getString("nowUserId", "tourist");
        nowUserName = sp.getString("nowUserName", "小明");
        showContentView();


        initViewEvent();


    }

    private void initMarked(String word) {

        String cql = "select objectId from UserWords where userid=? and word= ?";
        try {
            AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
                @Override
                public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                    if (avCloudQueryResult.getResults().size() == 0) {
                        isMarked = false;
                        bindingView.include.imageViewMarkStar.setVisibility(View.VISIBLE);
                        bindingView.include.imageViewMarkStar.setImageResource(R.drawable.ic_star_border_white_24dp);
                    } else {
                        isMarked = true;
                        bindingView.include.imageViewMarkStar.setVisibility(View.VISIBLE);
                        bindingView.include.imageViewMarkStar.setImageResource(R.drawable.ic_grade_white_24dp);
                        UserWord word_objectid = (UserWord) avCloudQueryResult.getResults().get(0);
                        DebugUtil.error("isMarked2333" + word_objectid.getObjectId());
                        sp.edit()
                                .putString("word_objectId", word_objectid.getObjectId())
                                .commit();


                    }

                }
            }, UserWord.class, nowUserId, word);
        } catch (Exception e) {
            DebugUtil.debug("doCloudQuery出异常");
        }
    }


    private void initViewEvent() {
        bindingView.tvClear.setVisibility(View.INVISIBLE);
        bindingView.tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindingView.etMainInput.setText("");
            }
        });
        bindingView.etMainInput.setTextSize(20);
        bindingView.etMainInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (bindingView.etMainInput.getEditableText().toString().length() != 0) {
                    bindingView.tvClear.setVisibility(View.VISIBLE);
                } else {
                    bindingView.tvClear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bindingView.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bindingView.recyclerView.setNestedScrollingEnabled(false);
        bindingView.buttonTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckNetwork.isNetworkConnected(getApplicationContext())) {
                    showNoNetwork();
                } else if (bindingView.etMainInput.getText() == null || bindingView.etMainInput.getText().length() == 0) {
                    Snackbar.make(bindingView.buttonTranslate, "输入为空", Snackbar.LENGTH_SHORT).show();
                } else {
                    sendReq(bindingView.etMainInput.getText().toString());
                    if (!nowUserId.equals("tourist")) {
                        bindingView.include.imageViewMarkStar.setVisibility(View.INVISIBLE);
                        initMarked(bindingView.etMainInput.getText().toString());

                    }
                }
            }
        });
        bindingView.include.imageViewVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player.getInstance().play(urlVoice);
            }
        });
        bindingView.include.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND).setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, result);
                startActivity(Intent.createChooser(intent, getString(R.string.choose_app_to_share)));
            }
        });

        bindingView.include.imageViewCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getApplication().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", result);
                manager.setPrimaryClip(clipData);

                Snackbar.make(bindingView.include.imageViewCopy,
                        R.string.copy_done,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        bindingView.include.imageViewMarkStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckNetwork.isNetworkConnected(getApplicationContext())) {
                    showNoNetwork();
                } else {
                    isMarked = !isMarked;
                    String word = bindingView.etMainInput.getText().toString();
                    if (isMarked) {
                        bindingView.include.imageViewMarkStar.setImageResource(R.drawable.ic_grade_white_24dp);
                        //请求服务器把这个单词记录到个人单词本表里面

                        if (!nowUserId.equals("tourist")) {
                            AVObject table_user_word = new AVObject("UserWords");// 构建对象
                            table_user_word.put("userid", nowUserId);
                            table_user_word.put("username", nowUserName);
                            table_user_word.put("word", word);
                            table_user_word.saveInBackground();// 保存到服务端
                        }

                    } else {
                        bindingView.include.imageViewMarkStar.setImageResource(R.drawable.ic_star_border_white_24dp);
                        //请求服务器单词本如果有该单词就删除
                        if (!nowUserId.equals("tourist")) {
                            String cql = "delete from UserWords where objectId =?";
                            AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
                                @Override
                                public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                    if (e == null) {
                                        Snackbar.make(bindingView.include.imageViewMarkStar, "已从单词本中删除", Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        DebugUtil.error(e.toString());
                                        Snackbar.make(bindingView.include.imageViewMarkStar, "从单词本中删除不成功", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            }, sp.getString("word_objectId", "11111"));
                        }
                    }
                }

            }
        });


    }

    private void sendReq(String in) {

        bindingView.progressBar.setVisibility(View.VISIBLE);

        // 监听输入面板的情况，如果激活则隐藏
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(bindingView.buttonTranslate.getWindowToken(), 0);
        }
        //格式化输入的英文字母
        in = inputFormat(in);
        //在建立异步任务之前 最后检测网络状态
        if (!CheckNetwork.isNetworkConnected(getApplicationContext())) {
            showNoNetwork();
        }
        //拼接请求的字符串
        //如computer
        //http://cn.bing.com/dict/search?q=computer&FORM=BDVSP6&mkt=zh-cn
        final String url = BING_BASE_BEFORE + in + BING_BASE_AGE;
        //建立异步任务
        BingAsyncTask bingAsyncTask = new BingAsyncTask(getBaseContext(), bindingView);
        bingAsyncTask.execute(url);


    }


    //去掉输入文本中的回车符号
    private String inputFormat(String in) {
        in = in.replace("\n", "");
        return in;
    }


    private void showNoNetwork() {
        Snackbar.make(bindingView.buttonTranslate, R.string.no_network_connection, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.settings, new View.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                }).show();
    }


    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, TranslateActivtiy.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DebugUtil.error(TAG, "------onResume---");
    }

}
