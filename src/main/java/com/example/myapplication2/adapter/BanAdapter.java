package com.example.myapplication2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.example.myapplication2.R;
import com.example.myapplication2.bean.BannerBeans;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class BanAdapter extends DelegateAdapter.Adapter {
    private ArrayList<BannerBeans.DataDTO.BannerDTO> mBan;
    private Context mContext;
    private LinearLayoutHelper linearLayoutHelper;

    public BanAdapter(ArrayList<BannerBeans.DataDTO.BannerDTO> mBan, Context mContext, LinearLayoutHelper linearLayoutHelper) {
        this.mBan = mBan;
        this.mContext = mContext;
        this.linearLayoutHelper = linearLayoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return linearLayoutHelper;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(mContext, R.layout.item_ban_layout, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.ban.setImages(mBan).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                BannerBeans.DataDTO.BannerDTO dto = (BannerBeans.DataDTO.BannerDTO) path;
                Glide.with(mContext).load(dto.getImage_url()).into(imageView);
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return mBan.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        Banner ban;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ban = itemView.findViewById(R.id.ban);
        }
    }
}
