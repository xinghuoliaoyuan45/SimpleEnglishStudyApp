package com.mxy.englishstudy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.mxy.englishstudy.bean.Account;
import com.mxy.englishstudy.databinding.ActivityMainBinding;
import com.mxy.englishstudy.databinding.NavHeaderMainBinding;
import com.mxy.englishstudy.ui.book.BookFragment;
import com.mxy.englishstudy.ui.gank.GankFragment;
import com.mxy.englishstudy.ui.menu.NavAboutActivity;
import com.mxy.englishstudy.ui.menu.NavDownloadActivity;
import com.mxy.englishstudy.ui.menu.NavVoiceActivity;
import com.mxy.englishstudy.ui.menu.PersonWordActivity;
import com.mxy.englishstudy.ui.menu.SelectHeadActivity;
import com.mxy.englishstudy.ui.menu.TranslateActivtiy;
import com.mxy.englishstudy.utils.CommonUtils;
import com.mxy.englishstudy.utils.DebugUtil;
import com.mxy.englishstudy.utils.PerfectClickListener;
import com.mxy.englishstudy.view.MyFragmentPagerAdapter;
import com.mxy.englishstudy.view.statusbar.StatusBarUtil;
import com.mxy.englishstudy.view.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private FrameLayout llTitleMenu;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private NavigationView navView;
    private DrawerLayout drawerLayout;
    private ViewPager vpContent;


    String user_id;

    // 一定需要对应的bean
    private ActivityMainBinding mBinding;
    private ImageView llTitileRecommend;
    private ImageView llTitileBook;
    private String cql = "select * from Account where objectId = ?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugUtil.error("MainActivity onCreate");
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initStatusView();
        initId();


        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawerLayout,
                CommonUtils.getColor(R.color.colorTheme));
        initContentFragment();
        initDrawerLayout();
        initListener();

    }




    private void initStatusView() {
        ViewGroup.LayoutParams layoutParams = mBinding.include.viewStatus.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        mBinding.include.viewStatus.setLayoutParams(layoutParams);
    }

    private void initId() {
        drawerLayout = mBinding.drawerLayout;
        navView = mBinding.navView;
        fab = mBinding.include.fab;
        toolbar = mBinding.include.toolbar;
        llTitleMenu = mBinding.include.llTitleMenu;
        vpContent = mBinding.include.vpContent;
        fab.setVisibility(View.GONE);
        llTitileRecommend = mBinding.include.ivTitleRecommend;
        llTitileBook = mBinding.include.ivTitleBook;
    }

    private void initListener() {
        llTitleMenu.setOnClickListener(this);
        mBinding.include.ivTitleRecommend.setOnClickListener(this);
        mBinding.include.ivTitleBook.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    NavHeaderMainBinding bind;

    /**
     * inflateHeaderView 进来的布局要宽一些
     */
    private void initDrawerLayout() {
        navView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navView.getHeaderView(0);
        bind = DataBindingUtil.bind(headerView);
        bind.setListener(this);

        SharedPreferences sp =
                getApplicationContext().getSharedPreferences("FILE_USER", MODE_PRIVATE);
        user_id = sp.getString("nowUserId", "-1");




        bind.llNavExit.setOnClickListener(this);
        bind.ivAvatar.setOnClickListener(this);

        bind.llNavQueryword.setOnClickListener(listener);
        bind.llNavScanDownload.setOnClickListener(listener);
        bind.llNavNoteword.setOnClickListener(listener);
        bind.llNavAbout.setOnClickListener(listener);
        bind.llNavWebVoice.setOnClickListener(listener);

    }

    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new GankFragment());
        mFragmentList.add(new BookFragment());
        // 注意使用的是：getSupportFragmentManager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        vpContent.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        vpContent.setOffscreenPageLimit(2);
        vpContent.addOnPageChangeListener(this);
        mBinding.include.ivTitleRecommend.setSelected(true);
        vpContent.setCurrentItem(0);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }

    }


    private PerfectClickListener listener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(final View v) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
            mBinding.drawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (v.getId()) {
                        case R.id.ll_nav_queryword:// 查词
                            TranslateActivtiy.start(MainActivity.this);
                            break;
                        case R.id.ll_nav_noteword://单词本
                            PersonWordActivity.start(MainActivity.this);
                            break;
                        case R.id.ll_nav_scan_download://扫码下载
                            NavDownloadActivity.start(MainActivity.this);
                            break;
                        case R.id.ll_nav_about:// 关于简单英语
                            NavAboutActivity.start(MainActivity.this);
                            break;
                        case R.id.ll_nav_web_voice://视频
                            //String movieUrl = "https://xinghuoliaoyuan45.github.io/mxy/";
                            //WebViewActivity.loadUrl(MainActivity.this, movieUrl,"视频");
                            NavVoiceActivity.start(MainActivity.this);

                            break;

                    }
                }
            }, 260);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu:// 开启菜单
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_recommend:// 推荐栏
                if (vpContent.getCurrentItem() != 0) {//不然cpu会有损耗
                    llTitileRecommend.setSelected(true);
                    llTitileBook.setSelected(false);
                    vpContent.setCurrentItem(0);
                }
                break;

            case R.id.iv_title_book:// 书籍栏
                if (vpContent.getCurrentItem() != 1) {
                    llTitileRecommend.setSelected(false);
                    llTitileBook.setSelected(true);
                    vpContent.setCurrentItem(1);
                }
                break;
            case R.id.iv_avatar: // 头像进入修改头像
                Intent intent = new Intent(getApplicationContext(), SelectHeadActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                break;
            case R.id.ll_nav_exit:// 退出应用
                finish();
                //在onDestroy中清理 SharedPreferences
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                TranslateActivtiy.start(MainActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                llTitileRecommend.setSelected(true);
                llTitileBook.setSelected(false);
                break;
            case 1:
                llTitileBook.setSelected(true);
                llTitileRecommend.setSelected(false);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                // 不退出程序，进入后台
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        DebugUtil.error("MainActivity onDestroy");
        SharedPreferences sp =
                getApplicationContext().getSharedPreferences("FILE_USER", MODE_PRIVATE);
        sp.edit().clear().commit();
        super.onDestroy();
    }
    protected void onStop(){
        super.onStop();
        DebugUtil.error("MainActivity onStop");
    }

    /**
     * /数据的加载在 onStart里面
     */

    protected void onStart(){
        super.onStart();
        if (user_id != "-1") {
            AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
                @Override
                public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                    if (e == null) {
                        Account account = (Account) avCloudQueryResult.getResults().get(0);
                        //初始化头像
                        String head_image = account.getheadimage();
                        int headimage = getApplicationContext().getResources().getIdentifier(head_image, "drawable", getPackageName());
                        bind.ivAvatar.setImageResource(headimage);
                        //初始化名字
                        String user_name = account.getUsername();
                        bind.tvUsername.setText(user_name);
                    } else {
                        DebugUtil.error("进入mainActivity通过id查询account表出错");
                    }

                }
            }, Account.class, user_id);
        }else{
            bind.ivAvatar.setImageResource(R.drawable.a15);
            bind.tvUsername.setText("小明");

        }
        bind.tvLevel.setText("level 1.0");
        DebugUtil.error("MainActivity onStart");
    }
    @Override
    protected void onResume() {
        DebugUtil.error("MainActivity onResume");
        super.onResume();

    }
}
