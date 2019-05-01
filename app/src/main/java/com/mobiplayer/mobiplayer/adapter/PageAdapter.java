package com.mobiplayer.mobiplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobiplayer.mobiplayer.R;
import com.mobiplayer.mobiplayer.model.VideoData;

import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder> {

    private List<VideoData> pagesList;
    private Context context;
    private final OnItemClickListener mItemClickListener;

    public PageAdapter(List<VideoData> pagesList, Context context, OnItemClickListener listener) {
        this.pagesList = pagesList;
        this.context = context;
        mItemClickListener = listener;
    }

    public static class PageViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView data;
        ImageView mImage;
        LinearLayout lLinearLayout;

        private PageViewHolder(View v) {
            super(v);
            lLinearLayout = (LinearLayout) v.findViewById(R.id.ll);
            mTitle = (TextView) v.findViewById(R.id.text_title);
            data = (TextView) v.findViewById(R.id.text_desc);
            mImage = (ImageView) v.findViewById(R.id.thumb_img);
        }
    }

    @Override
    public PageViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video,
                parent, false);
        return new PageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PageViewHolder holder, final int position) {
        holder.mTitle.setText(pagesList.get(position).getTitle());
        holder.data.setText(pagesList.get(position).getDescription());
        Glide.with(this.context).load(pagesList.get(position).getThumb()).
                placeholder(R.drawable.ic_logo).fitCenter().into(holder.mImage);
        holder.lLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemSelected(pagesList.get(position));
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemSelected(VideoData videoData);
    }

    @Override
    public int getItemCount() {
        return pagesList.size();
    }

}