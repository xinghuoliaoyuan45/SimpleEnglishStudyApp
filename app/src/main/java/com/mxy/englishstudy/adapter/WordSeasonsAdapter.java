package com.mxy.englishstudy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.bean.wordsclass.SeasonsBean;
import com.mxy.englishstudy.ui.gank.child.WordTranslateActivtiy;

import java.util.List;

/**
 * Created by mxy on 2017/4/25.
 */

public class WordSeasonsAdapter extends RecyclerView.Adapter<WordSeasonsAdapter.ViewHolder>{
    List<SeasonsBean> list;
    Context mContext;


    public WordSeasonsAdapter(List<SeasonsBean>  list, Context context) {
        this.list = list;
        mContext = context;
    }

    @Override
    public WordSeasonsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_one_tv_2, viewGroup, false);
        WordSeasonsAdapter.ViewHolder holder = new WordSeasonsAdapter.ViewHolder(v);
        holder.textView = (TextView) v.findViewById(R.id.textView);
        return holder;
    }

    @Override
    public void onBindViewHolder(WordSeasonsAdapter.ViewHolder viewHolder, int i) {
        final SeasonsBean seasonsBean = list.get(i);
        viewHolder.textView.setText("英:"+seasonsBean.getEn()+" 中:"+seasonsBean.getCh());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WordTranslateActivtiy.class);
                intent.putExtra("wordname",seasonsBean.getEn());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View v) {
            super(v);
        }
    }
}
