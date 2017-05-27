package com.mxy.englishstudy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.bean.wordsclass.BodyBean;
import com.mxy.englishstudy.ui.gank.child.WordTranslateActivtiy;
import com.mxy.englishstudy.utils.DebugUtil;

import java.util.List;

/**
 * Created by mxy on 2017/4/15.
 */

public class WordBodyAdapter extends RecyclerView.Adapter<WordBodyAdapter.ViewHolder> {
    List<BodyBean> list;
    Context mContext;


    public WordBodyAdapter(List<BodyBean>  list, Context context) {
        this.list = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_one_tv_2, viewGroup, false);
        ViewHolder holder = new WordBodyAdapter.ViewHolder(v);
        holder.textView = (TextView) v.findViewById(R.id.textView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final BodyBean bodyBean = list.get(i);
        viewHolder.textView.setText("英:"+bodyBean.getEn()+" 中:"+bodyBean.getCh());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WordTranslateActivtiy.class);
                intent.putExtra("wordname",bodyBean.getEn());
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
