package com.bwie.wang.bwm.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.APis;
import com.bwie.wang.bwm.adapter.find.Find_Recycler_Adapter;
import com.bwie.wang.bwm.bean.find.Find_XRecycler_Bean;
import com.bwie.wang.bwm.iprisenter.IPrenserterImp;
import com.bwie.wang.bwm.iview.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindFragment extends Fragment implements IView {
    View view;

    @BindView(R.id.Find_Recycler)
    XRecyclerView xRecyclerView;
    private Find_Recycler_Adapter mFindRecyclerAdapter;
    IPrenserterImp mIPrenserterImp;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_find, container, false);
        ButterKnife.bind(this, view);
        mIPrenserterImp = new IPrenserterImp(this);
        //设置Recycler
        initRecycler(view);
        return view;
    }

    private void initRecycler(View view) {
        //展示布局格式
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        //适配器
        mFindRecyclerAdapter = new Find_Recycler_Adapter(getActivity());
        xRecyclerView.setAdapter(mFindRecyclerAdapter);
        //网络请求
        // page=1&count=1
        mIPrenserterImp.startGet(APis.FIND_PATH + "?page=" + 1 + "&count=" + 10, Find_XRecycler_Bean.class);
    }

    /**
     * 得到数据
     *
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof Find_XRecycler_Bean) {
            Find_XRecycler_Bean findRecyclerBean = (Find_XRecycler_Bean) o;
            mFindRecyclerAdapter.setMjihe(findRecyclerBean.getResult());
        }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(getActivity(), "主人,奴家还木有网络呐", Toast.LENGTH_LONG).show();
    }
}