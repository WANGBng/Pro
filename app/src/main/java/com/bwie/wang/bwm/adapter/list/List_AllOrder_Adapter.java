package com.bwie.wang.bwm.adapter.list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.bean.Fragment_List_Bean;

import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;

/**
 * @author wangbingjun
 */
public class List_AllOrder_Adapter extends RecyclerView.Adapter<List_AllOrder_Adapter.ViewHolder> {
    private Context mContext;
    private List<Fragment_List_Bean.ResultBean> mjihe;

    public List_AllOrder_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Fragment_List_Bean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_allorder_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.AllOrderRecycler.setLayoutManager(manager);
        //适配器
        List_AllOrder_Second_Adapter secondAdapter = new List_AllOrder_Second_Adapter(mContext, mjihe);
        viewHolder.AllOrderRecycler.setAdapter(secondAdapter);
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView AllOrderRecycler;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            AllOrderRecycler=itemView.findViewById(R.id.AllOrderRecycler);
        }
    }
}
