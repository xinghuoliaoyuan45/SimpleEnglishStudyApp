package com.mxy.englishstudy.ui.gank.child;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.cocosw.bottomsheet.BottomSheet;
import com.example.xrecyclerview.XRecyclerView;
import com.mxy.englishstudy.R;
import com.mxy.englishstudy.adapter.WordAnimalsAdapter;
import com.mxy.englishstudy.adapter.WordBodyAdapter;
import com.mxy.englishstudy.adapter.WordColorsAdapter;
import com.mxy.englishstudy.adapter.WordFooddrinkAdapter;
import com.mxy.englishstudy.adapter.WordSeasonsAdapter;
import com.mxy.englishstudy.adapter.WordVehiclesAdapter;
import com.mxy.englishstudy.base.BaseFragment;
import com.mxy.englishstudy.bean.wordsclass.AnimalsBean;
import com.mxy.englishstudy.bean.wordsclass.BodyBean;
import com.mxy.englishstudy.bean.wordsclass.ColorsBean;
import com.mxy.englishstudy.bean.wordsclass.FoodDrinkBean;
import com.mxy.englishstudy.bean.wordsclass.SeasonsBean;
import com.mxy.englishstudy.bean.wordsclass.VehiclesBean;
import com.mxy.englishstudy.databinding.FragmentCustomBinding;
import com.mxy.englishstudy.http.cache.ACache;
import com.mxy.englishstudy.utils.DebugUtil;
import com.mxy.englishstudy.utils.SPUtils;
import com.mxy.englishstudy.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class CustomFragment extends BaseFragment<FragmentCustomBinding> {

    private static final String TAG = "CustomFragment";
    private static final String TYPE = "mType";


    private View mHeaderView;

    WordBodyAdapter wordBodyAdapter;
    WordAnimalsAdapter wordAnimalsAdapter;
    WordColorsAdapter wordColorsAdapter;
    WordSeasonsAdapter wordSeasonsAdapter;
    WordFooddrinkAdapter wordFooddrinkAdapter;
    WordVehiclesAdapter wordVehiclesAdapter;

    List<AnimalsBean> list_animals = new ArrayList<>();
    List<ColorsBean> list_colors = new ArrayList<>();
    List<BodyBean> list_bodys = new ArrayList<>();
    List<FoodDrinkBean> list_fooddrink = new ArrayList<>();
    List<SeasonsBean> list_seasons = new ArrayList<>();
    List<VehiclesBean> list_vehicles = new ArrayList<>();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DebugUtil.error("--CustomFragment   ----onActivityCreated");

        // 禁止下拉刷新
        bindingView.xrvCustom.setPullRefreshEnabled(false);

        bindingView.xrvCustom.setLoadingMoreEnabled(false);
        // 去掉刷新头
        bindingView.xrvCustom.clearHeader();


        initHeadView();


    }

    void initHeadView() {
        if (mHeaderView == null) {
            mHeaderView = View.inflate(getContext(), R.layout.header_item_gank_custom, null);
            bindingView.xrvCustom.addHeaderView(mHeaderView);
        }
        initHeader(mHeaderView);
    }

    @Override
    public int setContent() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void loadData() {


    }

    private void changeAnimalsContent(final TextView textView, final String content) {
        String cql = "select * from wordanimal";
        showLoading();
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e == null) {
                    list_animals = (List<AnimalsBean>) avCloudQueryResult.getResults();
                    textView.setText(content);
                    SPUtils.putString("gank_cala", content);
                    wordAnimalsAdapter = new WordAnimalsAdapter(list_animals, getActivity());
                    bindingView.xrvCustom.setLayoutManager(new LinearLayoutManager(getActivity()));
                    bindingView.xrvCustom.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                    bindingView.xrvCustom.setAdapter(wordAnimalsAdapter);
                    showContentView();

                }
            }
        }, AnimalsBean.class);


    }

    private void changeBodyContent(final TextView textView, final String content) {
        String cql = "select * from wordbody";
        showLoading();
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e == null) {
                    list_bodys = (List<BodyBean>) avCloudQueryResult.getResults();
                    textView.setText(content);
                    SPUtils.putString("gank_cala", content);

                    wordBodyAdapter = new WordBodyAdapter(list_bodys, getActivity());
                    bindingView.xrvCustom.setLayoutManager(new LinearLayoutManager(getActivity()));
                    bindingView.xrvCustom.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                    bindingView.xrvCustom.setAdapter(wordBodyAdapter);
                    showContentView();
                }
            }
        }, BodyBean.class);

    }

    private void changeColorContent(final TextView textView, final String content) {
        String cql = "select * from wordcolor";
        showLoading();
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e == null) {
                    list_colors = (List<ColorsBean>) avCloudQueryResult.getResults();
                    textView.setText(content);

                    SPUtils.putString("gank_cala", content);

                    wordColorsAdapter = new WordColorsAdapter(list_colors, getActivity());
                    bindingView.xrvCustom.setLayoutManager(new LinearLayoutManager(getActivity()));
                    bindingView.xrvCustom.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                    bindingView.xrvCustom.setAdapter(wordColorsAdapter);
                    showContentView();
                }
            }
        }, ColorsBean.class);

    }

    private void changeFoodDrinkContent(final TextView textView, final String content) {
        String cql = "select * from wordfooddrink";
        showLoading();
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e == null) {
                    list_fooddrink = (List<FoodDrinkBean>) avCloudQueryResult.getResults();
                    textView.setText(content);
                    SPUtils.putString("gank_cala", content);

                    wordFooddrinkAdapter = new WordFooddrinkAdapter(list_fooddrink, getActivity());
                    bindingView.xrvCustom.setLayoutManager(new LinearLayoutManager(getActivity()));
                    bindingView.xrvCustom.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                    bindingView.xrvCustom.setAdapter(wordFooddrinkAdapter);
                    showContentView();
                }
            }
        }, FoodDrinkBean.class);

    }

    private void changeSeasonsContent(final TextView textView, final String content) {
        String cql = "select * from wordseason";
        showLoading();
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e == null) {
                    list_seasons = (List<SeasonsBean>) avCloudQueryResult.getResults();
                    textView.setText(content);
                    SPUtils.putString("gank_cala", content);

                    wordSeasonsAdapter = new WordSeasonsAdapter(list_seasons, getActivity());
                    bindingView.xrvCustom.setLayoutManager(new LinearLayoutManager(getActivity()));
                    bindingView.xrvCustom.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                    bindingView.xrvCustom.setAdapter(wordSeasonsAdapter);

                    showContentView();

                }
            }
        }, SeasonsBean.class);
    }

    private void changeVehiclesContent(final TextView textView, final String content) {
        String cql = "select * from wordvehicle";
        showLoading();
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if (e == null) {
                    list_vehicles = (List<VehiclesBean>) avCloudQueryResult.getResults();
                    textView.setText(content);
                    SPUtils.putString("gank_cala", content);

                    wordVehiclesAdapter = new WordVehiclesAdapter(list_vehicles, getActivity());
                    bindingView.xrvCustom.setLayoutManager(new LinearLayoutManager(getActivity()));
                    bindingView.xrvCustom.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                    bindingView.xrvCustom.setAdapter(wordVehiclesAdapter);
                    showContentView();
                }
            }
        }, VehiclesBean.class);

    }


    private void initHeader(View mHeaderView) {
        final TextView txName = (TextView) mHeaderView.findViewById(R.id.tx_name);
        String gankCala = SPUtils.getString("gank_cala", "身体");
        txName.setText(gankCala);
        View view = mHeaderView.findViewById(R.id.ll_choose_catalogue);
        changeBodyContent(txName, "身体");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog)
                        .title("选择分类")
                        .sheet(R.menu.gank_bottomsheet)
                        .listener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case R.id.body:
                                        if (isOtherType("身体")) {
                                            changeBodyContent(txName, "身体");
                                        }
                                        break;
                                    case R.id.colors:
                                        if (isOtherType("颜色")) {
                                            changeColorContent(txName, "颜色");
                                        }
                                        break;
                                    case R.id.seasons:
                                        if (isOtherType("季节")) {
                                            changeSeasonsContent(txName, "季节");
                                        }
                                        break;
                                    case R.id.vehicles:
                                        if (isOtherType("交通工具")) {
                                            changeVehiclesContent(txName, "交通工具");
                                        }
                                        break;
                                    case R.id.fooddrink:
                                        if (isOtherType("食品饮料")) {
                                            changeFoodDrinkContent(txName, "食品饮料");
                                        }
                                        break;
                                    case R.id.animals:
                                        if (isOtherType("动物")) {
                                            changeAnimalsContent(txName, "动物");
                                        }
                                        break;
                                }
                            }
                        }).show();

            }
        });
    }


    private boolean isOtherType(String selectType) {
        String clickText = SPUtils.getString("gank_cala", "身体");
        if (clickText.equals(selectType)) {
            ToastUtil.showToast("当前已经是" + selectType + "分类");
            return false;
        } else {
            return true;
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.error("--CustomFragment   ----onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        DebugUtil.error(TAG + "   ----onResume");
    }
}
