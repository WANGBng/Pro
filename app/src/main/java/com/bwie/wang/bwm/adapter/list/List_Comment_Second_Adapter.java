package com.bwie.wang.bwm.adapter.list;


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

import java.util.List;
/**
 * @author wangbingjun
 */
public class List_Comment_Second_Adapter extends RecyclerView.Adapter<List_Comment_Second_Adapter.ViewHolder> {
            private Context mContext;
            private List<Fragment_List_Bean.ResultBean.DetailListBean> mjihe;

    public List_Comment_Second_Adapter(Context context, List<Fragment_List_Bean.ResultBean.DetailListBean> mjihe) {
        mContext = context;
        this.mjihe = mjihe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.list_comment_second_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            //图片
        Glide.with(mContext).load(mjihe.get(i).getCommodityPic()).into(viewHolder.CommentSecondImage);
        //标题
        viewHolder.CommentSecondName.setText(mjihe.get(i).getCommodityName());
        //价钱
        viewHolder.CommentSecondPrice.setText(mjihe.get(i).getCommodityPrice()+"");
        //TODO：去评论的点击事件
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView CommentSecondImage;
        TextView CommentSecondName,CommentSecondPrice,CommentSecondGoComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CommentSecondImage=itemView.findViewById(R.id.CommentSecondImage);
            CommentSecondName=itemView.findViewById(R.id.CommentSecondName);
            CommentSecondPrice=itemView.findViewById(R.id.CommentSecondPrice);
            CommentSecondGoComment=itemView.findViewById(R.id.CommentSecondGoComment);
        }
    }
}
