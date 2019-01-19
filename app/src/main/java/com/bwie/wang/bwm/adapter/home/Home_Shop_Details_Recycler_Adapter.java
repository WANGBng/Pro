package com.bwie.wang.bwm.adapter.home;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.DateUntil;
import com.bwie.wang.bwm.bean.home.Home_Shop_Comment_List_Bean;

import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;
/**
 * @author wangbingjun
 */
public class Home_Shop_Details_Recycler_Adapter extends RecyclerView.Adapter<Home_Shop_Details_Recycler_Adapter.ViewHolder> {
            private Context mContext;
            private List<Home_Shop_Comment_List_Bean.ResulteBean> mjihe;

    public Home_Shop_Details_Recycler_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Home_Shop_Comment_List_Bean.ResulteBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.home_shop_details_recycler,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mjihe.get(i).getHeadPic()).into(viewHolder.Head);
        Glide.with(mContext).load(mjihe.get(i).getImage()).into(viewHolder.Image);
        viewHolder.content.setText(mjihe.get(i).getContent());
        viewHolder.name.setText(mjihe.get(i).getNickName());
        String dateToString = DateUntil.DateUtils.getDateToString(mjihe.get(i).getCreateTime());
        viewHolder.time.setText(dateToString);

    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Head;
        ImageView Image;
        TextView name;
        TextView time;
        TextView content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Head=itemView.findViewById(R.id.Home_Shop_Details_Recycler_Head);
            Image=itemView.findViewById(R.id.Home_Shop_Details_Recycler_Image);
            name=itemView.findViewById(R.id.Home_Shop_Details_Recycler_NickName);
            time=itemView.findViewById(R.id.Home_Shop_Details_Recycler_Time);
            content=itemView.findViewById(R.id.Home_Shop_Details_Recycler_Content);
        }
    }
}
