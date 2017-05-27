package com.mxy.englishstudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.bean.UserWord;
import com.mxy.englishstudy.utils.DebugUtil;

import java.util.List;

/**
 * Created by mxy on 2017/4/28.
 */

public class PersonalWordItemAdapter extends RecyclerView.Adapter<PersonalWordItemAdapter.ViewHolder> {
    Context mContext;
    List<UserWord> userWordList;
    public PersonalWordItemAdapter(List<UserWord> userWordList, Context context) {
        this.mContext = context;
        this.userWordList = userWordList;
    }

    @Override
    public PersonalWordItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_cv_tv, parent, false);
        PersonalWordItemAdapter.ViewHolder holder = new PersonalWordItemAdapter.ViewHolder(v);
        holder.textView = (TextView) v.findViewById(R.id.tv_word);
        return holder;
    }

    @Override
    public void onBindViewHolder(PersonalWordItemAdapter.ViewHolder holder, int position) {
        holder.textView.setText(userWordList.get(position).getWord());


    }

    @Override
    public int getItemCount() {
        return userWordList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View v) {
            super(v);
        }
    }}
