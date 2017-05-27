package com.mxy.englishstudy.adapter;

import android.view.ViewGroup;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.base.baseadapter.BaseRecyclerViewAdapter;
import com.mxy.englishstudy.base.baseadapter.BaseRecyclerViewHolder;
import com.mxy.englishstudy.databinding.ItemListBinding;


public class Adapter extends BaseRecyclerViewAdapter<String> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_list);
    }

    class ViewHolder extends BaseRecyclerViewHolder<String, ItemListBinding> {

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void onBindViewHolder(String object, int position) {
            binding.tvText.setText("测试:  " + object);
        }
    }
}
