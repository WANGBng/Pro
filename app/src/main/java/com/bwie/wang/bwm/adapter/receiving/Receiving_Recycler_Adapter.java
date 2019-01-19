package com.bwie.wang.bwm.adapter.receiving;

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
import com.bwie.wang.bwm.Until.Shop_Car_Add_Reduce_View;
import com.bwie.wang.bwm.bean.Fragment_Shop_Car_Bean;

import java.util.List;
/**
 * @author wangbingjun
 */
public class Receiving_Recycler_Adapter extends RecyclerView.Adapter<Receiving_Recycler_Adapter.ViewHolder> {
        private Context mContext;
        private List<Fragment_Shop_Car_Bean.ResultBean> mjihe;

    public Receiving_Recycler_Adapter(Context context, List<Fragment_Shop_Car_Bean.ResultBean> mjihe) {
        mContext = context;
        this.mjihe = mjihe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(mContext).inflate(R.layout.activity_receiving_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mjihe.get(i).getPic()).into(viewHolder.receivingAdapterImage);
        viewHolder.receivingAdapterName.setText(mjihe.get(i).getCommodityName());
        viewHolder.receivingAdapterPrice.setText("￥"+mjihe.get(i).getPrice()+"0");

    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView receivingAdapterImage;
            TextView receivingAdapterName;
            TextView receivingAdapterPrice;
            Shop_Car_Add_Reduce_View receivingAdapterView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            receivingAdapterImage =itemView.findViewById(R.id.Receiving_Adapter_Image);
            receivingAdapterName =itemView.findViewById(R.id.Receiving_Adapter_Name);
            receivingAdapterPrice =itemView.findViewById(R.id.Receiving_Adapter_Price);
            receivingAdapterView =itemView.findViewById(R.id.Receiving_Adapter_View);
        }
    }
}
