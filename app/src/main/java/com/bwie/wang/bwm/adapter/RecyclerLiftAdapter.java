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
import com.bwie.wang.bwm.bean.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangbingjun
 */
public class RecyclerLiftAdapter extends RecyclerView.Adapter<RecyclerLiftAdapter.ViewHolder> {
    private Context mContext;
    private List<Bean.ResultBean.PzshBean.CommodityListBeanX> mjihe;

    public RecyclerLiftAdapter(Context context) {
        mContext = context;
        mjihe = new ArrayList<>();
    }

    public void setMjihe(List<Bean.ResultBean.PzshBean.CommodityListBeanX> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_lift_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(mContext).load(mjihe.get(position).getMasterPic()).into(holder.liftImage);
        holder.liftTitle.setText(mjihe.get(position).getCommodityName());
        holder.liftPrice.setText(String.valueOf("￥："+ mjihe.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.getData(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView liftImage;
        TextView liftTitle;
        TextView liftPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            liftImage = itemView.findViewById(R.id.Lift_Image);
            liftTitle = itemView.findViewById(R.id.Lift_Title);
            liftPrice = itemView.findViewById(R.id.Lift_Price);
        }
    }

    public interface CallBack {
        void getData(int position);
    }

    public RecyclerHotAdapter.CallBack mCallBack;


    public void setCallBack(RecyclerHotAdapter.CallBack callBack) {
        mCallBack = callBack;
    }
}
