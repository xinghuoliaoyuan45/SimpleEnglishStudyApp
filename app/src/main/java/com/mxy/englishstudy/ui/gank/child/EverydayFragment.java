package com.mxy.englishstudy.ui.gank.child;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mxy.englishstudy.R;

import com.mxy.englishstudy.app.Constants;
import com.mxy.englishstudy.base.BaseFragment;

import com.mxy.englishstudy.bean.DailyOne;
import com.mxy.englishstudy.databinding.FragmentDailyOneBinding;

import com.mxy.englishstudy.utils.CheckNetwork;
import com.mxy.englishstudy.utils.DebugUtil;
import com.mxy.englishstudy.utils.Player;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;


import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * 每日一句
 */
public class EverydayFragment extends BaseFragment<FragmentDailyOneBinding> {

    private static final String TAG = "EverydayFragment";

    private ImageView ivVoice;
    private ImageView ivCopy;
    private ImageView ivShare;
    private ImageView ivDaily;
    private TextView tvChinese;
    private TextView tvEnglish;

    private String strChinese;
    private String strEnglish;
    private String strImageUrl;
    private String strUrlVoice;
    private Handler handler = new Handler();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!CheckNetwork.isNetworkConnected(getContext())) {
            showNoNetwork();
        }

        DebugUtil.error("--EverydayFragment   ----onActivityCreated");
        initView();
        initEvent();

        loadDataView();

    }


    protected void loadDataView() {
        DebugUtil.error("EverydayFragment loadDataView");
        OkHttpClient httpClient = new OkHttpClient();
        okhttp3.Request.Builder requestBuild = new okhttp3.Request.Builder().url(Constants.DAILY_SENTENCE).method("GET",null);
        okhttp3.Request request = requestBuild.build();
        Call mcall = httpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                DailyOne dailyOne = gson.fromJson(response.body().string(), DailyOne.class);
                try {
                    strImageUrl = dailyOne.getPicture2();
                    strEnglish = dailyOne.getContent();
                    strChinese = dailyOne.getNote();
                    strUrlVoice = dailyOne.getTts();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvChinese.setText(strChinese);
                            tvEnglish.setText(strEnglish);
                            Glide.with(getActivity())
                                    .load(strImageUrl)
                                    .asBitmap()
                                    .centerCrop()
                                    .into(ivDaily);
                        }
                    });


                } catch (Exception e) {
                    DebugUtil.error("jsonObject.getString()时候失败");
                }


            }

        });


    }

    private void initView() {
        ivVoice = bindingView.imageViewVoice;
        ivCopy = bindingView.imageViewCopy;
        ivShare = bindingView.imageViewShare;
        tvChinese = bindingView.textViewChi;
        tvEnglish = bindingView.textViewEng;
        ivDaily = bindingView.imageViewDaily;
    }

    private void initEvent() {
        ivVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player.getInstance().play(strUrlVoice);

            }
        });
        ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", String.valueOf(tvEnglish.getText() + "\n" + tvChinese.getText()));
                manager.setPrimaryClip(clipData);

                Snackbar.make(ivVoice, R.string.copy_done, Snackbar.LENGTH_SHORT).show();
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND).setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(tvEnglish.getText()) + "\n" + tvChinese.getText());
                startActivity(Intent.createChooser(intent, getString(R.string.choose_app_to_share)));
            }
        });

    }

    @Override
    public int setContent() {
        return R.layout.fragment_daily_one;
    }

    @Override
    protected void onRefresh() {
        showContentView();
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.error("--EverydayFragment   ----onDestroy");
    }

    private void showNoNetwork() {
        Snackbar.make(bindingView.getRoot(), R.string.no_network_connection, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.settings, new View.OnClickListener() {
                    public void onClick(View v) {
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                }).show();
    }

}
