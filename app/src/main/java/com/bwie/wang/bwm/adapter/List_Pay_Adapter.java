package com.bwie.wang.bwm.adapter;


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
import com.bwie.wang.bwm.bean.Fragment_List_Bean;

import java.util.ArrayList;
import java.util.List;
/**
 * @author wangbingjun
 */
public class List_Pay_Adapter extends RecyclerView.Adapter<List_Pay_Adapter.ViewHolder> {
        private Context mContext;
        private List<Fragment_List_Bean.ResultBean.DetailListBean> mjihe;

    public List_Pay_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Fragment_List_Bean.ResultBean.DetailListBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_pay_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
             //商品图片
        Glide.with(mContext).load(mjihe.get(i).getCommodityPic()).into(viewHolder.Pay_Image);
            //商品名字
        viewHolder.Pay_Name.setText(mjihe.get(i).getCommodityName());
          //商品单价
        viewHolder.Pay_Price.setText("¥"+mjihe.get(i).getCommodityPrice());
        //商品数量
        viewHolder.Pay_Num.setText(mjihe.get(i).getCommodityCount()+"");
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView Pay_Image;
            TextView Pay_Name,Pay_Price,Pay_Num_Change,Pay_Num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Pay_Image=itemView.findViewById(R.id.Pay_Image);
            Pay_Name=itemView.findViewById(R.id.Pay_Name);
            Pay_Price=itemView.findViewById(R.id.Pay_Price);
            Pay_Num_Change=itemView.findViewById(R.id.Pay_Num_Change);
            Pay_Num=itemView.findViewById(R.id.Pay_Num);

        }
    }
}
