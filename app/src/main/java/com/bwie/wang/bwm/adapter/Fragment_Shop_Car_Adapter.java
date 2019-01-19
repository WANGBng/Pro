package com.bwie.wang.bwm.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.Shop_Car_Add_Reduce_View;
import com.bwie.wang.bwm.bean.Fragment_Shop_Car_Bean;

import java.util.ArrayList;
import java.util.List;
/**
 * @author wangbingjun
 */
public class Fragment_Shop_Car_Adapter extends RecyclerView.Adapter<Fragment_Shop_Car_Adapter.ViewHolder> {
    private Context mContext;
    private List<Fragment_Shop_Car_Bean.ResultBean> mjihe;

    public Fragment_Shop_Car_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Fragment_Shop_Car_Bean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(mContext).inflate(R.layout.fragment_shop_car_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
            //商品图片
        Glide.with(mContext).load(mjihe.get(i).getPic()).into(viewHolder.Shop_Car_Image);
        //商品的标题
        viewHolder.Shop_Car_Name.setText(mjihe.get(i).getCommodityName());
        //商品的价钱
        viewHolder.Shop_Car_Price.setText("¥"+mjihe.get(i).getPrice());
        //商品的复选框
            //根据我记录的状态，改变勾选
        viewHolder.Shop_Car_CheckBox.setChecked(mjihe.get(i).isIschecked());
        //复选框状态监听
        viewHolder.Shop_Car_CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //优先改变自己的状态
                mjihe.get(i).setIschecked(viewHolder.Shop_Car_CheckBox.isChecked());
                //回调，目的是告诉activity，有人选中状态被改变
                if (mShopCallBackListener != null) {
                    mShopCallBackListener.callBack(mjihe);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox Shop_Car_CheckBox;
        ImageView Shop_Car_Image;
        TextView Shop_Car_Name,Shop_Car_Price;
        Shop_Car_Add_Reduce_View Shop_Car_AddReduce;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Shop_Car_CheckBox=itemView.findViewById(R.id.Shop_Car_CheckBox);
            Shop_Car_Image=itemView.findViewById(R.id.Shop_Car_Image);
            Shop_Car_Name=itemView.findViewById(R.id.Shop_Car_Name);
            Shop_Car_Price=itemView.findViewById(R.id.Shop_Car_Price);
            Shop_Car_AddReduce=itemView.findViewById(R.id.Shop_Car_AddReduce);
        }
    }

    /**
     * 修改商品的全选和反选
     * @param isSelectAll
     */
    public void selectOrRemoveAll(boolean isSelectAll) {
        //循环商品
        for (Fragment_Shop_Car_Bean.ResultBean shopCarBean : mjihe) {
            shopCarBean.setIschecked(isSelectAll);
        }
        //刷新
        notifyDataSetChanged();
    }



    /**
     * 定义接口，起过渡作用
     */
    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack(List<Fragment_Shop_Car_Bean.ResultBean> mlist);

    }

}
