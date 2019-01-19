package com.bwie.wang.bwm.adapter.list;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.Shop_Car_Add_Reduce_View;
import com.bwie.wang.bwm.bean.Fragment_List_Bean;

import java.util.List;

import android.support.annotation.NonNull;

/**
 * @author wangbingjun
 * 全部订单的最后一个Recycler
 */
public class List_AllOrder_Last_Adapter extends RecyclerView.Adapter<List_AllOrder_Last_Adapter.ViewHolder> {
        private Context mContext;
        private List<Fragment_List_Bean.ResultBean.DetailListBean> mjihe;

    public List_AllOrder_Last_Adapter(Context context, List<Fragment_List_Bean.ResultBean.DetailListBean> jihe) {
        mContext = context;
        mjihe = jihe;
    }

    public void setMjihe(List<Fragment_List_Bean.ResultBean.DetailListBean> jihe) {
        mjihe = jihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_allorder_last_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mjihe.get(i).getCommodityPic()).into(viewHolder.lastImage);
        viewHolder.lastName.setText(mjihe.get(i).getCommodityName());
        viewHolder.lastPrice.setText(mjihe.get(i).getCommodityPrice()+"");
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView lastImage;
        TextView lastName,lastPrice;
        Shop_Car_Add_Reduce_View lastAddReduce;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lastImage=itemView.findViewById(R.id.lastImage);
            lastName=itemView.findViewById(R.id.lastName);
            lastPrice=itemView.findViewById(R.id.lastPrice);
            lastAddReduce=itemView.findViewById(R.id.lastAddReduce);
        }
    }
}
