package com.bwie.wang.bwm.adapter.find;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.DateUntil;
import com.bwie.wang.bwm.bean.find.Find_XRecycler_Bean;

import java.util.ArrayList;
import java.util.List;
/**
 * @author wangbingjun
 */
public class Find_XRecycler_Adapter extends RecyclerView.Adapter<Find_XRecycler_Adapter.ViewHolder> {
        private Context mContext;
        private List<Find_XRecycler_Bean.ResultBean> mjihe;

    public Find_XRecycler_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Find_XRecycler_Bean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    public void addMjihe(List<Find_XRecycler_Bean.ResultBean> jihe){
        mjihe.addAll(jihe);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_find_xrecycler_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.FindAdapter_Content.setText(mjihe.get(i).getContent());
        String dateToString = DateUntil.DateUtils.getDateToString(mjihe.get(i).getCreateTime());
        viewHolder.FindAdapter_Time.setText(dateToString);
        viewHolder.FindAdapter_ThumbsNum.setText(mjihe.get(i).getGreatNum()+"");
        String[] split = mjihe.get(i).getImage().split("\\,");
        String image = split[0];
        Glide.with(mContext).load(image).into(viewHolder.FindAdapter_Image);
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            TextView FindAdapter_Content,FindAdapter_Time,FindAdapter_ThumbsNum;
            ImageView FindAdapter_Image,FindAdapter_Thumbs;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            FindAdapter_Thumbs=itemView.findViewById(R.id.FindAdapter_Thumbs);
            FindAdapter_Image=itemView.findViewById(R.id.FindAdapter_Image);
            FindAdapter_Content=itemView.findViewById(R.id.FindAdapter_Content);
            FindAdapter_Time=itemView.findViewById(R.id.FindAdapter_Time);
            FindAdapter_ThumbsNum=itemView.findViewById(R.id.FindAdapter_ThumbsNum);
        }
    }
}
