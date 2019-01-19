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

import java.util.ArrayList;
import java.util.List;
/**
 * @author wangbingjun
 */
public class Receiving_Address_Other_Adapter extends RecyclerView.Adapter<Receiving_Address_Other_Adapter.ViewHolder> {
    private Context mContext;
    private List<Find_Address_List_Bean.ResultBean> mjihe;

    public Receiving_Address_Other_Adapter(Context context) {
        mContext = context;
        mjihe = new ArrayList<>();
    }
    public void setMjihe(List<Find_Address_List_Bean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.receiving_address_other_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.otherName.setText(mjihe.get(i).getRealName());
        viewHolder.otherPhone.setText(mjihe.get(i).getPhone());
        viewHolder.otherAddress.setText(mjihe.get(i).getAddress());
        // 选择的点击事件
        viewHolder.otherChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到点击的条目
                mCallBack.call(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView otherName;
        TextView otherChose;
        TextView otherPhone;
        TextView otherAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            otherName =itemView.findViewById(R.id.OtherName);
            otherChose =itemView.findViewById(R.id.OtherChose);
            otherPhone =itemView.findViewById(R.id.OtherPhone);
            otherAddress =itemView.findViewById(R.id.OtherAddress);
        }
    }

    public interface CallBack{
        void call(int i);
    }

    CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }
}
