package com.guoxiaoxing.demo.cache;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guoxiaoxing.demo.R;
import com.guoxiaoxing.demo.bitmap.ImageGridActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author: guoxiaoxing
 * Email: guoxiaoxingv@163.com
 * Site:  https://github.com/guoxiaoxing
 * Date: 16/4/6 上午11:39
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<String> mList = new ArrayList<>();


    public ListAdapter(Context context) {
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mTvName.setText(mList.get(position));

        holder.mTvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();

                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(mContext, CacheActivity.class);
                        break;
                    case 1:
                        intent = new Intent(mContext, ImageGridActivity.class);
                        break;
                }
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<String> list) {
        mList.clear();
        mList.addAll(list);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        TextView mTvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}  