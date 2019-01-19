package com.bwie.wang.bwm.activity.find;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.widget.ImageView;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.APis;
import com.bwie.wang.bwm.adapter.find.Find_XRecycler_Adapter;
import com.bwie.wang.bwm.bean.find.Find_XRecycler_Bean;
import com.bwie.wang.bwm.iprisenter.IPrenserterImp;
import com.bwie.wang.bwm.iview.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindActivity extends AppCompatActivity implements IView {
    /**
     * 我的圈子  我的圈子以写
     */
    @BindView(R.id.Find_Delete)
    public ImageView mFindDelete;
    @BindView(R.id.Find_XRecycler)
    public XRecyclerView mFindXRecycler;
    private Find_XRecycler_Adapter mFindXRecyclerAdapter;
    IPrenserterImp mIPrenserterImp;
    private List<Find_XRecycler_Bean.ResultBean> mFindXRecyclerBeanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        ButterKnife.bind(this);
        mIPrenserterImp=new IPrenserterImp(this);
        //数据
        initXRecycler();

    }
    int mPage;
    private void initXRecycler() {
        //布局管理
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mFindXRecycler.setLayoutManager(manager);
        //获取适配器
        mFindXRecyclerAdapter = new Find_XRecycler_Adapter(this);
        mFindXRecycler.setAdapter(mFindXRecyclerAdapter);
        mFindXRecycler.setPullRefreshEnabled(true);
        mFindXRecycler.setLoadingMoreEnabled(true);
        mFindXRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
        initData();
    }

    /**
     * 网络请求
     */
    private void initData() {
        int mCount=10;
        mIPrenserterImp.startGet(APis.MAIN_FIND+"?page="+mPage+"&count="+mCount,Find_XRecycler_Bean.class);
    }

    /**
     * 网络请求成功
     * @param o
     */
    @Override
    public void setData(Object o) {
        if (o instanceof Find_XRecycler_Bean){
            Find_XRecycler_Bean findXRecyclerBean=(Find_XRecycler_Bean)o;
            //将获取的值放到适配器
            if (mPage==1){
                mFindXRecyclerAdapter.setMjihe(findXRecyclerBean.getResult());
            }else {
                mFindXRecyclerAdapter.addMjihe(findXRecyclerBean.getResult());
            }
            mPage++;
            mFindXRecycler.refreshComplete();
            mFindXRecycler.loadMoreComplete();

        }
    }

    @Override
    public void setError(String error) {

    }
}