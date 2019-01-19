package com.bwie.wang.bwm.adapter.list;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.DateUntil;
import com.bwie.wang.bwm.bean.Fragment_List_Bean;

import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;

/**
 * @author wangbingjun
 *  待评价的FirstRecycler
 */
public class List_Comment_First_Adapter extends RecyclerView.Adapter<List_Comment_First_Adapter.ViewHolder> {
            private Context mContext;
            private List<Fragment_List_Bean.ResultBean> mjihe;

    public List_Comment_First_Adapter(Context context) {
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
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_comment_first_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
                //订单号：
            viewHolder.CommentOrderCode.setText(mjihe.get(i).getOrderId());
            //时间：
        String dateToString = DateUntil.DateUtils.getDateToString(mjihe.get(i).getOrderTime());
        viewHolder.CommentTime.setText(dateToString);
            //recyycler
        List_Comment_Second_Adapter secondAdapter = new List_Comment_Second_Adapter(mContext, mjihe.get(i).getDetailList());
            viewHolder.CommentSecondRecycler.setAdapter(secondAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.CommentSecondRecycler.setLayoutManager(manager);
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            TextView CommentOrderCode;
            RecyclerView CommentSecondRecycler;
            TextView CommentTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CommentOrderCode=itemView.findViewById(R.id.CommentOrderCode);
            CommentSecondRecycler=itemView.findViewById(R.id.CommentSecondRecycler);
            CommentTime=itemView.findViewById(R.id.CommentTime);


        }
    }
}
