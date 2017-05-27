package com.mxy.englishstudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxy.englishstudy.MainActivity;
import com.mxy.englishstudy.R;
import com.mxy.englishstudy.view.webview.WebViewActivity;

import java.util.zip.Inflater;

/**
 * Created by mxy on 2017/5/6.
 */

public class VoiceTitleAdpter extends RecyclerView.Adapter<VoiceTitleAdpter.VoiceViewHolder> {
    private static String TAG = VoiceViewHolder.class.getSimpleName();
    private Context mcontext;
    private LayoutInflater inflater;
    private int[] images;
    private String[] title;
    private String[] info;
    private String[] link;

    public VoiceTitleAdpter(Context context, int[] images, String[] title, String[] info,String[] link) {
        this.mcontext = context;
        this.inflater = LayoutInflater.from(context);
        this.images = images;
        this.title = title;
        this.info = info;
        this.link = link;
    }

    @Override
    public VoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VoiceViewHolder(inflater.inflate(R.layout.item_voice_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(final VoiceViewHolder holder, final int position) {
        holder.imageView.setImageResource(images[position]);
        holder.textView1.setText(title[position]);
        holder.textView2.setText(info[position]);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(mcontext,link[position],title[position]);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 11;
    }

    public class VoiceViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        LinearLayout ll;

        public VoiceViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
            textView1 = (TextView) itemView.findViewById(R.id.voice_title);
            textView2 = (TextView) itemView.findViewById(R.id.vocie_content);
            ll =(LinearLayout)itemView.findViewById(R.id.ll_item);
        }
    }
}
