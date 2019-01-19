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
import com.bwie.wang.bwm.adapter.RecyclerHotAdapter;
import com.bwie.wang.bwm.bean.home.Home_Shop_List_Bean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;

/**
 * @author wangbingjun
 */
public class Home_Shop_List_Adapter extends XRecyclerView.Adapter<Home_Shop_List_Adapter.ViewHolder> {
    private Context mContext;
    private List<Home_Shop_List_Bean.ResultBean> mjihe;

    public Home_Shop_List_Adapter(Context context) {
        mContext = context;
        mjihe = new ArrayList<>();
    }

    public void setMjihe(List<Home_Shop_List_Bean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    public void addMjihe(List<Home_Shop_List_Bean.ResultBean> jihe) {
        mjihe.addAll(jihe);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_shop_list_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(mContext).load(mjihe.get(position).getMasterPic()).into(holder.shopImage);
        holder.shopTitle.setText(mjihe.get(position).getCommodityName());
        holder.shopPrice.setText("¥" + mjihe.get(position).getPrice());
        holder.shopSell.setText("已售" + mjihe.get(position).getSaleNum() + "件");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到点击的下标
                mCallBack.getData(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView shopImage;
        TextView shopTitle;
        TextView shopPrice;
        TextView shopSell;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopImage = itemView.findViewById(R.id.Shop_Image);
            shopTitle = itemView.findViewById(R.id.Shop_Title);
            shopPrice = itemView.findViewById(R.id.Shop_Price);
            shopSell = itemView.findViewById(R.id.Shop_Sell);
        }
    }


    public interface CallBack {
        void getData(int i);
    }

    public RecyclerHotAdapter.CallBack mCallBack;

    public void setCallBack(RecyclerHotAdapter.CallBack callBack) {
        mCallBack = callBack;
    }
}
