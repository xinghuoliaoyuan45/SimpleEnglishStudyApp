package com.mxy.englishstudy.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mxy.englishstudy.R;
import com.mxy.englishstudy.bean.BingModel;
import com.mxy.englishstudy.utils.Player;
import com.mxy.englishstudy.utils.StringFormatUtil;

import java.util.ArrayList;

/**
 * Created by mxy on 2017/4/4.
 */

public class SampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String TAG = SampleAdapter.class.getSimpleName();

    public final Context context;
    public final LayoutInflater inflater;
    public BingModel bingModel;

    public SampleAdapter(@NonNull Context context, BingModel bingModel) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.bingModel = bingModel;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SampleViewHolder(inflater.inflate(R.layout.sample_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        String number = bingModel.getSams().get(position).getNumber();
        ((SampleViewHolder) holder).textView_number.setText(number);
        String eng = bingModel.getSams().get(position).getEng();
        ((SampleViewHolder) holder).textView_ame.setText(eng);
        String chn = bingModel.getSams().get(position).getChn();
        ((SampleViewHolder) holder).textView_chn.setText(chn);
        final String urlMp3 = bingModel.getSams().get(position).getMp3Url();
        ((SampleViewHolder) holder).ImageView_ameMp3_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player.getInstance().play(urlMp3);
            }
        });
    }

    @Override

    public int getItemCount() {
        return bingModel.getSams().size();
    }

    public class SampleViewHolder extends RecyclerView.ViewHolder {

        TextView textView_ame;
        TextView textView_chn;
        ImageView ImageView_ameMp3_voice;
        TextView textView_number;


        public SampleViewHolder(View itemView) {
            super(itemView);
            textView_ame = (TextView) itemView.findViewById(R.id.textView_ame);
            textView_chn = (TextView) itemView.findViewById(R.id.textView_chn);
            ImageView_ameMp3_voice = (ImageView) itemView.findViewById(R.id.ImageView_ameMp3_voice);
            textView_number = (TextView)  itemView.findViewById(R.id.textView_number);

        }
    }

}
