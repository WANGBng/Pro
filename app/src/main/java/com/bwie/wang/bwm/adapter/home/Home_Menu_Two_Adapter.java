package com.bwie.wang.bwm.adapter.home;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.bean.home.Home_Menu_Two_Bean;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangbingjun
 */
public class Home_Menu_Two_Adapter extends RecyclerView.Adapter<Home_Menu_Two_Adapter.ViewHolder> {
    private Context mContext;
    private List<Home_Menu_Two_Bean.ResultBean> mjihe;
    private int setClick = 0;

    public Home_Menu_Two_Adapter(Context context) {
        mContext = context;
        mjihe = new ArrayList<>();
    }

    public void setMjihe(List<Home_Menu_Two_Bean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_menu_second_adapter, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.Two_Title.setText(mjihe.get(position).getName());
        //得到条目的id
        final int id = mjihe.get(position).getId();
        //条目点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack != null) {
                    mCallBack.callBack(id);
                    setClick = position;
                    notifyDataSetChanged();
                }
            }
        });

        // 改变点击的item的颜色
       /* if (setClick == position) {
            holder.Two_Title.setTextColor(Color.RED);
        } else {
            holder.Two_Title.setTextColor(Color.WHITE);
        }*/
    }

    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Two_Image;
        TextView Two_Title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Two_Image = itemView.findViewById(R.id.Two_Image);
            Two_Title = itemView.findViewById(R.id.Two_Title);
        }
    }

    //接口
    public interface CallBack {
        void callBack(int i);
    }

    public CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }
}
