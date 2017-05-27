package com.mxy.englishstudy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.bean.ArticleBean;
import com.mxy.englishstudy.ui.gank.child.ArticleContentActivity;
import com.mxy.englishstudy.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mxy on 2017/4/15.
 */

public class ActicleTileAdapter extends RecyclerView.Adapter<ActicleTileAdapter.ViewHolder> {
    List<ArticleBean.BeauArticleBean> artList = new ArrayList<>();
    ArticleBean.BeauArticleBean art;
    Context mContext;


    public ActicleTileAdapter(List<ArticleBean.BeauArticleBean> artList, Context context) {
        this.artList = artList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_one_tv, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        art = artList.get(i);
        viewHolder.textView.setText(art.getTitle());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ArticleContentActivity.class);
                DebugUtil.error(art.toString());
                intent.putExtra("artContent", artList.get(i));
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return artList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View v) {
            super(v);
            textView=(TextView) v.findViewById(R.id.textView);

        }
    }
}
