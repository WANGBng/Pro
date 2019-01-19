package com.bwie.wang.bwm.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.wang.bwm.R;
import com.bwie.wang.bwm.Until.APis;
import com.bwie.wang.bwm.adapter.List_Pay_Adapter;
import com.bwie.wang.bwm.adapter.list.List_AllOrder_Adapter;
import com.bwie.wang.bwm.adapter.list.List_Comment_First_Adapter;
import com.bwie.wang.bwm.bean.Fragment_List_Bean;
import com.bwie.wang.bwm.iprisenter.IPrenserterImp;
import com.bwie.wang.bwm.iview.IView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author wangbingjun
 */
public class ListFragment extends Fragment implements IView {
    View view;
    @BindView(R.id.List_All)
    public RelativeLayout mListAll;
    @BindView(R.id.List_Pay)
    public RelativeLayout List_Pay;
    @BindView(R.id.List_Receive)
    public RelativeLayout List_Receive;
    @BindView(R.id.List_Comment)
    public RelativeLayout List_Comment;
    @BindView(R.id.List_Finish)
    public RelativeLayout List_Finish;
    @BindView(R.id.PayOrderCode)
    public TextView mOrderCode;
    @BindView(R.id.PayOrderTime)
    public TextView mOrderTime;
    //待付款
    public RecyclerView mFragmentListPayRecycler;
    //全部订单
    public RecyclerView mFragmentListAllOrderRecycler;
    @BindView(R.id.Shop_Car_Comment_First_Recycler)
    public RecyclerView mShopCarCommentFirstRecycler;
    IPrenserterImp mIPrenserterImp;

    private List_Pay_Adapter mPayAdapter;
    private List_Comment_First_Adapter mCommentFirstAdapter;
    private ImageView mPayImage;
    private ImageView mAll_listImage;
    //全部订单的适配器

    private List_AllOrder_Adapter mOrderAdapter;
    Unbinder unbinder;


    @BindView(R.id.all_listImage)
    ImageView allListImage;
    @BindView(R.id.all_listText)
    TextView allListText;
    @BindView(R.id.payImage)
    ImageView payImage;
    @BindView(R.id.payText)
    TextView payText;
    @BindView(R.id.receiveImage)
    ImageView receiveImage;
    @BindView(R.id.receiveText)
    TextView receiveText;
    @BindView(R.id.commentImage)
    ImageView commentImage;
    @BindView(R.id.commentText)
    TextView commentText;
    @BindView(R.id.finishImage)
    ImageView finishImage;
    @BindView(R.id.finishText)
    TextView finishText;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.PayOrder)
    TextView PayOrder;
    @BindView(R.id.Fragment_List_Pay_Recycler)
    RecyclerView FragmentListPayRecycler;
    @BindView(R.id.List_AllOrder_Recycler)
    RecyclerView ListAllOrderRecycler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list_car, container, false);
        //绑定

        unbinder = ButterKnife.bind(R.layout.list_car, view);
        //不能用ButterKnife
        mIPrenserterImp = new IPrenserterImp(this);
        mFragmentListPayRecycler = view.findViewById(R.id.Fragment_List_Pay_Recycler);
        mPayImage = (ImageView) view.findViewById(R.id.payImage);
        mAll_listImage = (ImageView) view.findViewById(R.id.all_listImage);
        mFragmentListAllOrderRecycler = view.findViewById(R.id.List_AllOrder_Recycler);
        //待付款点击事件
        initpayImage();
        //所有的订单
        initall_listImage();

        return view;
    }

    /**
     * 全部订单点击事件
     */
    private void initall_listImage() {
        mAll_listImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //布局
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(OrientationHelper.VERTICAL);
                mFragmentListAllOrderRecycler.setLayoutManager(manager);
                //适配器
                mOrderAdapter = new List_AllOrder_Adapter(getActivity());
                mFragmentListAllOrderRecycler.setAdapter(mOrderAdapter);
                //网络请求
                int mstatus = 0;
                int page = 1;
                int count = 10;
                //网络请求
                mIPrenserterImp.startGet(APis.FRAGMENT_LIST + "&status=" + mstatus + "&page=" + page + "&count=" + count, Fragment_List_Bean.class);

            }
        });
    }

    /**
     * 待付款点击事件
     */
    private void initpayImage() {
        mPayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出Recycler
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(OrientationHelper.VERTICAL);
                mFragmentListPayRecycler.setLayoutManager(manager);
                //适配器
                mPayAdapter = new List_Pay_Adapter(getActivity());
                mFragmentListPayRecycler.setAdapter(mPayAdapter);
                int mstatus = 1;
                int page = 1;
                int count = 10;
                //网络请求
                mIPrenserterImp.startGet(APis.FRAGMENT_LIST + "&status=" + mstatus + "&page=" + page + "&count=" + count, Fragment_List_Bean.class);

            }
        });
    }

    /**
     * 待评价点击事件
     */
    @OnClick(R.id.receiveImage)
    public void ShopComment() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        mShopCarCommentFirstRecycler.setLayoutManager(manager);
        //适配器
        mCommentFirstAdapter = new List_Comment_First_Adapter(getActivity());
        mShopCarCommentFirstRecycler.setAdapter(mCommentFirstAdapter);
        //网络请求
        int mstatus = 3;
        int page = 1;
        int count = 10;
        //网络请求
        mIPrenserterImp.startGet(APis.FRAGMENT_LIST + "&status=" + mstatus + "&page=" + page + "&count=" + count, Fragment_List_Bean.class);
    }

    /**
     * 获取数据
     *
     * @param
     */
    @Override
    public void setData(Object o) {
        if (o instanceof Fragment_List_Bean) {
            Fragment_List_Bean fragmentShopCarBean = (Fragment_List_Bean) o;
            List<Fragment_List_Bean.ResultBean> result = fragmentShopCarBean.getResult();
            for (int i = 0; i < result.size(); i++) {
                List<Fragment_List_Bean.ResultBean.DetailListBean> detailList = result.get(i).getDetailList();
                //待付款
                mPayAdapter.setMjihe(detailList);
            }
            //待评价
            mCommentFirstAdapter.setMjihe(result);
            //使用的订单
            mOrderAdapter.setMjihe(result);
        }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(getActivity(), "主人,使用的订单有问题呀", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}