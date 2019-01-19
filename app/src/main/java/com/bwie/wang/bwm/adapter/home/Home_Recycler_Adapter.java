package com.bwie.wang.bwm.adapter.home;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.wang.bwm.bean.home.Home_RecyclerBean;

import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;
/**
 * @author wangbingjun
 */
public class Home_Recycler_Adapter extends RecyclerView.Adapter<Home_Recycler_Adapter.ViewHolder> {

            private Context mContext;
            private List<Home_RecyclerBean.ResultBean> mjihe;

    public Home_Recycler_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Home_RecyclerBean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
