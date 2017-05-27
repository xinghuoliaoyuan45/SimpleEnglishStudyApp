package com.mxy.englishstudy.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.adapter.VoiceTitleAdpter;
import com.mxy.englishstudy.base.BaseActivity;
import com.mxy.englishstudy.databinding.ActivityVocieBinding;

/**
 * Created by mxy on 2017/5/6.
 */

public class NavVoiceActivity extends BaseActivity<ActivityVocieBinding> {
    VoiceTitleAdpter voiceTitleAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocie);
        setTitle("精品英语电影");
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        //为recyclerView瀑布流布局
        bindingView.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        bindingView.recyclerView.setItemAnimator(new DefaultItemAnimator());
        showContentView();
        initDataView();
        bindingView.recyclerView.setAdapter(voiceTitleAdpter);
    }

    private void initDataView() {

        int[] images = new int[]{R.drawable.chaoneng, R.drawable.ice, R.drawable.wanju,
                R.drawable.compture, R.drawable.madajia, R.drawable.xunlong,
                R.drawable.feiwu, R.drawable.qianyuqianxun, R.drawable.yuanshiren,
                R.drawable.guaishou, R.drawable.tuzi};
        String[] title = new String[]{
                "超能陆战队",
                "冰雪奇缘",
                "玩具总动员",
                "机器人总动员",
                "马达加斯加的企鹅",
                "驯龙高手",
                "飞屋环游记",
                "千与千寻",
                "疯狂的原始人",
                "怪兽大学",
                "疯狂动物城"
        };
        String[] link = new String[]{
                "http://video.tudou.com/v/XMjc0NDgyNTIxMg==.html?from=s1.8-1-1.2&spm=a2h0k.8191414.0.0",
                "http://video.tudou.com/v/XMjY2ODkwMDY2NA==.html?from=s1.8-1-1.2&spm=a2h0k.8191414.0.0",
                "http://video.tudou.com/v/XMTI0NDEzNDY4.html?spm=a2h0k.8191414.0.0",
                "http://video.tudou.com/v/XMjUzMjc0MDky.html?spm=a2h0k.8191414.0.0",
        "http://video.tudou.com/v/XNzkzNDI5OTAw.html?spm=a2h0k.8191414.0.0",
        "http://video.tudou.com/v/XNzUzNDA2NDg4.html?spm=a2h0k.8191414.0.0",
        "http://video.tudou.com/v/XMTk1NzYwNTky.html?spm=a2h0k.8191414.0.0",
        "http://video.tudou.com/v/XODIzMTU0MTI0.html?from=s1.8-1-1.2&spm=a2h0k.8191414.0.0",
        "http://video.tudou.com/v/XNTQzNTU3NjE2.html?spm=a2h0k.8191414.0.0",
        "http://video.tudou.com/v/XNTI0NTgwNDI4.html?spm=a2h0k.8191414.0.0",
        "http://video.tudou.com/v/XMTQ4NDI1ODQ3Ng==.html?spm=a2h0k.8191414.0.0"};
        String[] info = new String[]{
                "故事设定在一个融合东西方文化（旧金山+东京）的虚构大都市旧京山（San Fransokyo）中，一名精通机器人的...",
                "《Frozen》讲述一个诅咒令王国被冰天雪地永久覆盖，乐观无畏的安娜（由克里斯汀·贝尔配音）和一个大胆的...",
                "距上一次的冒险已经过去11个年头，转眼间安迪变成了17岁的阳光男孩。这年夏天，安迪即将开始大学生活...",
                "公元2805年，人类文明高度发展，却因污染和生活垃圾大量增加使得地球不再适于人类居住。地球人被迫...",
                "片中企鹅们化身“精英之中的精英”，要去拯救世界，在飞船上吃着芝士味零食，不断用咯吱咯吱的咀嚼声打乱...",
                "当世界还没有被浇筑成钢铁丛林,当科技还没有发达到所向披靡,北欧大地上的主人,是以狩猎、捕鱼为主要生计的游牧...",
                "他们有一个梦想，那就是有朝一日要去南美洲的“仙境瀑布”探险，但直到艾丽去世，这个梦想也未能实现。终于有一天...",
                "千寻和爸爸妈妈一同驱车前往新家,在郊外的小路上不慎进入了神秘的隧道他们去到了另外一个诡异世界一个中...",
                "原始人克鲁德一家六口在老爸Grug(尼古拉斯·凯奇 Nicolas Cage 配音)的庇护下生活。每天抢夺鸵鸟蛋...",
                "大眼仔迈克（Mike Wazowski）和毛怪萨利（James P. Sullivan）是最好的朋友，不过他们可不是一开始就是这...",
                "疯狂动物城是一座独一无二的现代动物都市。每种动物在这里都有自己的居所，比如富丽堂皇的撒哈拉广..."
        };
        voiceTitleAdpter=new VoiceTitleAdpter(getBaseContext(),images,title,info,link);

    }
    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavVoiceActivity.class);
        mContext.startActivity(intent);
    }
}
