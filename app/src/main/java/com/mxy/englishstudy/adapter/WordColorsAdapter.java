package com.mxy.englishstudy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.bean.wordsclass.ColorsBean;
import com.mxy.englishstudy.ui.gank.child.WordTranslateActivtiy;


import java.util.List;

/**
 * Created by mxy on 2017/4/25.
 */

public class WordColorsAdapter extends RecyclerView.Adapter<WordColorsAdapter.ViewHolder> {
    List<ColorsBean> list;
    Context mContext;


    public WordColorsAdapter(List<ColorsBean> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @Override
    public WordColorsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_one_tv_2, viewGroup, false);
        WordColorsAdapter.ViewHolder holder = new WordColorsAdapter.ViewHolder(v);
        holder.textView = (TextView) v.findViewById(R.id.textView);
        return holder;
    }

    @Override
    public void onBindViewHolder(WordColorsAdapter.ViewHolder viewHolder, int i) {
        final ColorsBean colorsBean = list.get(i);
        viewHolder.textView.setText("英:"+colorsBean.getEn() +" 中:"+ colorsBean.getCh());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WordTranslateActivtiy.class);
                intent.putExtra("wordname",colorsBean.getEn());
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
