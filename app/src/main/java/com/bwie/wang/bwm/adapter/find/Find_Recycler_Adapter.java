package com.bwie.wang.bwm.adapter.find;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.DateUntil;
import com.bwie.wang.bwm.bean.find.Find_XRecycler_Bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @author wangbingjun
 */
public class Find_Recycler_Adapter extends RecyclerView.Adapter<Find_Recycler_Adapter.ViewHolder> {
        private Context mContext;
        private List<Find_XRecycler_Bean.ResultBean> mjihe;

    public Find_Recycler_Adapter(Context context) {
        mContext = context;
        mjihe=new ArrayList<>();
    }

    public void setMjihe(List<Find_XRecycler_Bean.ResultBean> mjihe) {
        this.mjihe = mjihe;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.find_recycler_adapter,viewGroup,false);
        return new ViewHolder(view);
    }
    //Long要转换成date
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        //头像
        Glide.with(mContext).load(mjihe.get(i).getHeadPic()).into(viewHolder.Find_Head);
        //昵称
        viewHolder.Find_Name.setText(mjihe.get(i).getNickName());
        //时间!!!
        String dateToString = DateUntil.DateUtils.getDateToString(mjihe.get(i).getCreateTime());
        viewHolder.Find_Time.setText(dateToString);
        //内容
        viewHolder.Find_Content.setText(mjihe.get(i).getContent());
        //图片
        Glide.with(mContext).load(mjihe.get(i).getImage()).into(viewHolder.Find_Picture);
        //点赞数量
        viewHolder.Thumbs_Num.setText(mjihe.get(i).getGreatNum()+"");
        //点赞
        final boolean[] boo = {false};
            viewHolder.Image_Thumbs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if (!boo[0]){
                      viewHolder.Image_Thumbs.setImageResource(R.mipmap.common_btn_prise_s);
                      viewHolder.Thumbs_Num.setText(mjihe.get(i).getGreatNum()+1+"");
                      Toast.makeText(mContext,"点赞成功",Toast.LENGTH_LONG).show();
                  }else {
                      viewHolder.Image_Thumbs.setImageResource(R.mipmap.common_btn_prise_n);
                      viewHolder.Thumbs_Num.setText(mjihe.get(i).getGreatNum()+"");
                      Toast.makeText(mContext,"点赞取消",Toast.LENGTH_LONG).show();
                  }
                  boo[0] =!boo[0];
                }
            });


    }


    @Override
    public int getItemCount() {
        return mjihe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView Find_Head,Find_Picture,Image_Thumbs;
            TextView Find_Name,Find_Time,Find_Content,Thumbs_Num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Find_Head=itemView.findViewById(R.id.Find_Head);
            Find_Picture=itemView.findViewById(R.id.Find_Picture);
            Image_Thumbs=itemView.findViewById(R.id.Image_Thumbs);
            Find_Name=itemView.findViewById(R.id.Find_Name);
            Find_Time=itemView.findViewById(R.id.Find_Time);
            Find_Content=itemView.findViewById(R.id.Find_Content);
            Thumbs_Num=itemView.findViewById(R.id.Thumbs_Num);
        }
    }
}
