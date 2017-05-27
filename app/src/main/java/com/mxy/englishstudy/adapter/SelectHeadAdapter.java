package com.mxy.englishstudy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.utils.DebugUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mxy on 2017/4/21.
 */

public class SelectHeadAdapter extends RecyclerView.Adapter<SelectHeadAdapter.ViewHolder>  {
    private Context mContext;
   private List<String> list_name;
    public static LinkedList<String> ll = new LinkedList<>();


    public SelectHeadAdapter(Context context, List<String> list_name) {
        this.mContext = context;
        this.list_name = list_name;
        ll.addFirst("a0");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_headimage, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.ly = (LinearLayout) v.findViewById(R.id.ly_item_headimage);
        viewHolder.iv = (ImageView) v.findViewById(R.id.iv_image);
        viewHolder.checkbox = (CheckBox) v.findViewById(R.id.checkBox);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        int headimage = mContext.getResources().getIdentifier(list_name.get(position), "drawable", mContext.getPackageName());
        holder.iv.setImageResource(headimage);
        holder.ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean status = holder.checkbox.isChecked();
                holder.checkbox.setChecked(!status);
                if (holder.checkbox.isChecked()) {
                    holder.ly.setBackgroundColor(Color.parseColor("#ffce3d3a"));
                    ll.addFirst(list_name.get(position));
                    DebugUtil.error(ll.toString());
                } else {
                    ll.remove(list_name.get(position));
                    holder.ly.setBackgroundColor(Color.parseColor("#ffeeeeee"));
                    DebugUtil.error(ll.toString());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_name.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ly;
        ImageView iv;
        CheckBox checkbox;

        public ViewHolder(View v) {
            super(v);
        }
    }
}
