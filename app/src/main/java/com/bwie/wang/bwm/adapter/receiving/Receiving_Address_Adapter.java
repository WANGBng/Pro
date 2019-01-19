package com.bwie.wang.bwm.adapter.receiving;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.bean.find.Find_Address_List_Bean;

import java.util.List;
/**
 * @author wangbingjun
 */
public class Receiving_Address_Adapter extends RecyclerView.Adapter<Receiving_Address_Adapter.ViewHolder> {
        private Context mContext;
        private List<Find_Address_List_Bean.ResultBean> mjihe;

    public Receiving_Address_Adapter(Context context, List<Find_Address_List_Bean.ResultBean> mjihe) {
        mContext = context;
        this.mjihe = mjihe;
    }

    public void setMjihe(List<Find_Address_List_Bean.ResultBean> mjihe) {
        this.mjihe = mjihe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(mContext).inflate(R.layout.receiving_address_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.ReceivingAddressAdapterName.setText(mjihe.get(i).getRealName());
        viewHolder.ReceivingAddressAdapterPhone.setText(mjihe.get(i).getPhone());
        viewHolder.ReceivingAddressAdapterDetails.setText(mjihe.get(i).getAddress());
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ReceivingAddressAdapterName,ReceivingAddressAdapterPhone,ReceivingAddressAdapterDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ReceivingAddressAdapterName=itemView.findViewById(R.id.ReceivingAddressAdapterName);
            ReceivingAddressAdapterPhone=itemView.findViewById(R.id.ReceivingAddressAdapterPhone);
            ReceivingAddressAdapterDetails=itemView.findViewById(R.id.ReceivingAddressAdapterDetails);

        }
    }
}
